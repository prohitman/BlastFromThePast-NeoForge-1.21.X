package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.FrostomperModel;
import com.clonz.blastfromthepast.entity.ai.*;
import com.clonz.blastfromthepast.entity.ai.attacker.AnimatedMeleeAttackGoal;
import com.clonz.blastfromthepast.entity.ai.attacker.AttackerBodyRotationControl;
import com.clonz.blastfromthepast.entity.pack.EntityPack;
import com.clonz.blastfromthepast.entity.ai.navigation.BFTPGroundPathNavigation;
import com.clonz.blastfromthepast.entity.pack.EntityPackAgeableMobData;
import com.clonz.blastfromthepast.entity.pack.EntityPackHolder;
import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.init.ModTags;
import com.clonz.blastfromthepast.mixin.AbstractChestedHorseAccessor;
import com.clonz.blastfromthepast.util.HitboxHelper;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.BodyRotationControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FrostomperEntity extends AbstractChestedHorse implements Animatable<FrostomperModel>, EntityPackHolder<FrostomperEntity>, AnimatedAttacker<FrostomperEntity, FrostomperEntity.FrostomperAttackType> {
    public static final DucAnimation ANIMATION = DucAnimation.create(ModEntities.FROSTOMPER.getId());
    public static final DucAnimation BABY_ANIMATION = DucAnimation.create(ModEntities.FROSTOMPER.getId().withPrefix("baby_"));
    public static final EntityDimensions BABY_FROSTOMPER_DIMENSIONS = EntityDimensions.scalable(HitboxHelper.pixelsToBlocks(28.0F), HitboxHelper.pixelsToBlocks(22.0F));
    private static final EntityDataAccessor<OptionalInt> DATA_ACTIVE_ATTACK_TYPE = SynchedEntityData.defineId(FrostomperEntity.class, EntityDataSerializers.OPTIONAL_UNSIGNED_INT);
    private static final double PARENT_TARGETING_DISTANCE = 16.0D;
    private static final int CHARGE_ATTACK_COOLDOWN = 900;
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> FrostomperModel.createStateMap(getAnimation()));
    protected static final TargetingConditions PARENT_TARGETING = TargetingConditions.forNonCombat()
            .ignoreLineOfSight()
            .selector(entity -> entity instanceof FrostomperEntity && ((FrostomperEntity)entity).isBred());
    protected final TargetingConditions parentTargeting;
    @Nullable
    private EntityPack<FrostomperEntity> pack;
    private final AnimatedAttacker.AttackTicker<FrostomperEntity, FrostomperAttackType> attackTicker = new AttackTicker<>(this);
    private int ticksUntilNextCharge;

    public FrostomperEntity(EntityType<? extends FrostomperEntity> entityType, Level level) {
        super(entityType, level);
        this.setPathfindingMalus(PathType.LEAVES, 0.0F);
        this.parentTargeting = PARENT_TARGETING.copy().selector(entity -> HitboxHelper.isCloseEnoughForTargeting(this, entity, true, PARENT_TARGETING_DISTANCE));
        ((AbstractChestedHorseAccessor)this).setBabyDimensions(BABY_FROSTOMPER_DIMENSIONS);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AgeableMob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.75)
                .add(Attributes.ATTACK_DAMAGE, 12.0)
                .add(Attributes.ATTACK_KNOCKBACK, 1.5)
                .add(Attributes.FOLLOW_RANGE, 32.0)
                .add(Attributes.STEP_HEIGHT, 1.0);
    }

    public static void init() {
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.2));
        //this.goalSelector.addGoal(1, new RunAroundLikeCrazyGoal(this, 1.2));
        this.goalSelector.addGoal(2, new HitboxAdjustedBreedGoal(this, 1.0));
        this.goalSelector.addGoal(4, new HitboxAdjustedFollowParentGoal(this, 1.0));
        this.goalSelector.addGoal(5, new AnimatedMeleeAttackGoal<>(this, 1.0, true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.7));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        if (this.canPerformRearing()) {
            this.goalSelector.addGoal(9, new RandomStandGoal(this));
        }
        this.addBehaviourGoals();
        this.targetSelector.addGoal(0, new PackHurtByTargetGoal<>(this, AgeableMob::isBaby, FrostomperEntity.class));
    }

    @Override
    protected void addBehaviourGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, this::isTemptItem, false));
    }

    protected boolean isTemptItem(ItemStack stack) {
        return stack.is(this.isBaby() ? ModTags.Items.BABY_FROSTOMPER_TEMPT_ITEMS : ModTags.Items.FROSTOMPER_TEMPT_ITEMS);
    }

    @Override
    protected void randomizeAttributes(RandomSource random) {
        // TODO: Implement randomized attributes logic
    }

    @Override
    protected void followMommy() {
        if (this.isBred() && this.isBaby() && !this.isEating()) {
            FrostomperEntity mommy = this.level()
                    .getNearestEntity(FrostomperEntity.class, this.parentTargeting, this, this.getX(), this.getY(), this.getZ(), this.getBoundingBox().inflate(PARENT_TARGETING_DISTANCE));
            if (mommy != null && HitboxHelper.getDistSqrBetweenHitboxes(this, mommy) > 4.0D) {
                this.navigation.createPath(mommy, 0);
            }
        }
    }

    @Override
    protected void doPlayerRide(Player player) {
        if(this.isTamed()){
            super.doPlayerRide(player);
        }
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new BFTPGroundPathNavigation(this, level);
    }

    public int getMaxHeadYRot() {
        return 45;
    }

    @Override
    public ResourceLocation getModelLocation() {
        return null;
    }

    @Override
    public DucAnimation getAnimation() {
        return this.isBaby() ? BABY_ANIMATION : ANIMATION;
    }

    @Override
    public Lazy<Map<String, AnimationState>> getAnimations() {
        return this.animations;
    }

    @Override
    public Optional<AnimationState> getAnimationState(String animation) {
        return Optional.ofNullable(this.getAnimations().get().get("animation.frostomper." + animation));
    }

    @Override
    public int tickCount() {
        return this.tickCount;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.isAlive()) {
            if (this.horizontalCollision && EventHooks.canEntityGrief(this.level(), this)) {
                boolean destroyedBlock = false;
                AABB breakBox = this.getBoundingBox().inflate(0.2);
                Iterator<BlockPos> nearbyBlockPositions = BlockPos.betweenClosed(Mth.floor(breakBox.minX), Mth.floor(breakBox.minY), Mth.floor(breakBox.minZ), Mth.floor(breakBox.maxX), Mth.floor(breakBox.maxY), Mth.floor(breakBox.maxZ)).iterator();

                breakNearbyBlocks:
                while(true) {
                    BlockPos nearbyBlockPos;
                    BlockState nearbyBlockState;
                    do {
                        if (!nearbyBlockPositions.hasNext()) {
                            /*
                            if (!destroyedBlock && this.onGround()) {
                                this.jumpFromGround();
                            }
                             */
                            break breakNearbyBlocks;
                        }

                        nearbyBlockPos = nearbyBlockPositions.next();
                        nearbyBlockState = this.level().getBlockState(nearbyBlockPos);
                    } while(!(nearbyBlockState.is(ModTags.Blocks.FROSTOMPER_CAN_BREAK)));

                    destroyedBlock = this.level().destroyBlock(nearbyBlockPos, true, this) || destroyedBlock;
                }
            }
        }
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(this.isBaby() ? ModTags.Items.BABY_FROSTOMPER_FOOD : ModTags.Items.FROSTOMPER_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        FrostomperEntity offspring = ModEntities.FROSTOMPER.get().create(serverLevel);
        if(offspring != null){
            this.setOffspringAttributes(otherParent, offspring);
        }
        return offspring;
    }

    @Override
    protected void setOffspringAttributes(AgeableMob parent, AbstractHorse child) {
        // TODO: Implement randomized offspring attributes logic
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        if (spawnGroupData == null) {
            spawnGroupData = new FrostomperEntity.FrostomperGroupData(BlastFromThePast.getUniversalEntityPacks(level.getLevel().getServer()).createFreshPack(), true);
        }

        if(spawnGroupData instanceof FrostomperGroupData frostomperGroupData){
            frostomperGroupData.addPackMember(this);
        }
        SpawnGroupData spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        if(this.isBaby()){
            this.setAge(AgeableMob.BABY_START_AGE * 3);
        }
        return spawnData;
    }

    @Override
    public void setBaby(boolean baby) {
        this.setAge(baby ? AgeableMob.BABY_START_AGE * 3 : 0);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 6;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.DONKEY_AMBIENT;
    }

    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.DONKEY_ANGRY;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.DONKEY_DEATH;
    }

    @Override
    @Nullable
    protected SoundEvent getEatingSound() {
        return SoundEvents.DONKEY_EAT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.DONKEY_HURT;
    }

    @Override
    protected boolean handleEating(Player player, ItemStack stack) {
        if (!this.isFood(stack)) {
            return false;
        } else {
            boolean fed = false;
            if (this.getHealth() < this.getMaxHealth()) {
                this.heal(2.0F);
                fed = true;
            }
            if (this.isTamed() && this.getAge() == 0 && this.canFallInLove()) {
                this.setInLove(player);
                fed = true;
            }
            if (this.isBaby()) {
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0), this.getRandomY() + 0.5, this.getRandomZ(1.0), 0.0, 0.0, 0.0);
                if (!this.level().isClientSide) {
                    this.ageUp(10);
                    fed = true;
                }
            }
            if (!this.isTamed() && this.getTemper() < this.getMaxTemper() && !this.level().isClientSide) {
                this.modifyTemper(10);
                if (this.getTemper() >= this.getMaxTemper() && this.canBeTamed() && !EventHooks.onAnimalTame(this, player)) {
                    this.tameWithName(player);
                }
                fed = true;
            }

            if (!fed) {
                return false;
            } else {
                if (!this.isSilent()) {
                    SoundEvent eatingSound = this.getEatingSound();
                    if (eatingSound != null) {
                        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), eatingSound, this.getSoundSource(), 1.0F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
                    }
                }

                this.gameEvent(GameEvent.EAT);
                return true;
            }
        }
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal animal, @Nullable AgeableMob baby) {
        super.finalizeSpawnChildFromBreeding(level, animal, baby);
        if(baby instanceof FrostomperEntity babyFrostomper){
            Optional.ofNullable(this.getLoveCause())
                    .or(() -> Optional.ofNullable(animal.getLoveCause()))
                    .ifPresent(loveCause -> babyFrostomper.setBred(true));
        }
    }

    protected boolean canBeTamed() {
        return this.isBaby() || this.isBred();
    }

    @Override
    protected boolean canPerformRearing() {
        return false;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal != this && otherAnimal instanceof FrostomperEntity frostomper) {
            return this.canParent() && frostomper.canParent();
        }

        return false;
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.DONKEY_JUMP, 0.4F, 1.0F);
    }

    @Override
    protected void playChestEquipsSound() {
        this.playSound(SoundEvents.DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    @Override
    public SoundEvent getSaddleSoundEvent() {
        return SoundEvents.HORSE_SADDLE;
    }

    @Override
    @Nullable
    public EntityPack<FrostomperEntity> getPack() {
        return this.pack;
    }

    @Override
    public void setPack(@Nullable EntityPack<FrostomperEntity> pack) {
        this.pack = pack;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (this.level() instanceof ServerLevel serverLevel) {
            this.readPackData(compound, this, serverLevel);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        this.savePackData(compound);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ACTIVE_ATTACK_TYPE, OptionalInt.empty());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        super.onSyncedDataUpdated(pKey);
        if(DATA_ACTIVE_ATTACK_TYPE.equals(pKey)){
            this.attackTicker.reset();
            if(this.getActiveAttackType() == FrostomperAttackType.CHARGE){
                this.ticksUntilNextCharge = CHARGE_ATTACK_COOLDOWN;
            }
        }
    }

    @Override
    protected BodyRotationControl createBodyControl() {
        return new AttackerBodyRotationControl<>(this);
    }

    @Override
    public void setActiveAttackType(@Nullable FrostomperAttackType attackType){
        this.entityData.set(DATA_ACTIVE_ATTACK_TYPE, attackType == null ? OptionalInt.empty() : OptionalInt.of(attackType.ordinal()));
    }

    @Override
    @Nullable
    public FrostomperAttackType getActiveAttackType(){
        OptionalInt ordinal = this.entityData.get(DATA_ACTIVE_ATTACK_TYPE);
        return ordinal.isEmpty() ? null : FrostomperAttackType.byOrdinal(ordinal.getAsInt());
    }

    @Override
    public void tick() {
        super.tick();
        FrostomperAttackType activeAttackType = this.getActiveAttackType();
        if (this.level().isClientSide()) {
            this.animateWhen("idle", !this.isMoving(this) && this.onGround() && activeAttackType == null);
            if(!this.isBaby()){
                this.animateWhen("double_stomp", activeAttackType == FrostomperAttackType.DOUBLE_STOMP);
                this.animateWhen("fling", activeAttackType == FrostomperAttackType.FLING);
                this.animateWhen("charge", activeAttackType == FrostomperAttackType.CHARGE);
            }
        }
        if (activeAttackType != null && activeAttackType.blocksHeadRotation()) {
            this.clampHeadRotationToBody();
        }
        this.attackTicker.tick();
        this.ticksUntilNextCharge = Math.max(this.ticksUntilNextCharge - 1, 0);
    }

    @Override
    public void travel(Vec3 travelVector) {
        FrostomperAttackType attackType = this.getActiveAttackType();
        if (attackType != null && attackType.blocksMovementInput() && this.onGround()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.0D, 1.0D, 0.0D));
            travelVector = travelVector.multiply(0.0D, 1.0D, 0.0D);
        }
        super.travel(travelVector);
    }

    @Override
    public boolean isAlliedTo(Entity other) {
        if(other == null){
            return false;
        } else if (super.isAlliedTo(other)) {
            return true;
        } else if (this.isAlliedToDefault(other)) {
            return this.getTeam() == null && other.getTeam() == null;
        } else {
            return false;
        }
    }

    protected boolean isAlliedToDefault(Entity other) {
        return other.getType().equals(this.getType());
    }

    @Override
    public FrostomperAttackType selectAttackTypeForTarget(@Nullable LivingEntity target) {
        if(target != null){
            boolean canCharge = this.ticksUntilNextCharge <= 0;
            int randomInt;
            if(canCharge){
                randomInt = this.random.nextInt(10);
                if(randomInt < 7){
                    return FrostomperAttackType.CHARGE;
                } else if(randomInt < 9){
                    return FrostomperAttackType.FLING;
                } else{
                    return FrostomperAttackType.DOUBLE_STOMP;
                }
            } else{
                randomInt = this.random.nextInt(3);
                if(randomInt < 2){
                    return FrostomperAttackType.FLING;
                } else{
                    return FrostomperAttackType.DOUBLE_STOMP;
                }
            }
        }
        return null;
    }



    @Override
    protected Vec2 getRiddenRotation(LivingEntity entity) {
        FrostomperAttackType activeAttackType = this.getActiveAttackType();
        boolean rotationBlocked = activeAttackType != null && activeAttackType.blocksRotationInput();
        return rotationBlocked ? new Vec2(this.getXRot(), this.getYRot()) : super.getRiddenRotation(entity);
    }

    @Override
    protected Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        FrostomperAttackType activeAttackType = this.getActiveAttackType();
        boolean movementBlocked = activeAttackType != null && activeAttackType.blocksMovementInput();
        return movementBlocked ? Vec3.ZERO : super.getRiddenInput(player, travelVector);
    }

    protected static class FrostomperGroupData extends EntityPackAgeableMobData<FrostomperEntity> {
        public FrostomperGroupData(EntityPack<FrostomperEntity> entityPack, boolean shouldSpawnBaby) {
            super(entityPack, shouldSpawnBaby);
        }
    }

    public enum FrostomperAttackType implements AttackType<FrostomperEntity, FrostomperAttackType>{
        FLING(Mth.floor(0.38F * 20), Mth.floor(0.75F * 20), new Vec3(0.5, 0.5, 0.5), 8),
        DOUBLE_STOMP(Mth.floor(0.63F * 20), 20, new Vec3(0.5, 0.5, 0.5), 10),
        CHARGE(5, 10, new Vec3(0.5, 0.5, 0.5), 12);

        private final int attackPoint;
        private final int attackDuration;
        private final Vec3 attackRadius;
        private final float attackDamage;

        FrostomperAttackType(int attackPoint, int attackDuration, Vec3 attackRadius, float attackDamage) {
            this.attackPoint = attackPoint;
            this.attackDuration = attackDuration;
            this.attackRadius = attackRadius;
            this.attackDamage = attackDamage;
        }

        @Override
        public int getAttackPoint() {
            return this.attackPoint;
        }

        @Override
        public int getAttackDuration() {
            return this.attackDuration;
        }

        @Override
        public Vec3 getAttackRadius(){
            return this.attackRadius;
        }

        @Override
        public float getAttackDamage() {
            return this.attackDamage;
        }

        @Override
        public boolean blocksMovementInput() {
            return this == DOUBLE_STOMP;
        }

        @Override
        public boolean blocksWalkAnimation() {
            return this == DOUBLE_STOMP || this == CHARGE;
        }

        @Override
        public boolean blocksRotationInput() {
            return this == DOUBLE_STOMP;
        }

        @Override
        public boolean blocksBodyRotation() {
            return this == DOUBLE_STOMP;
        }

        @Override
        public boolean blocksHeadRotation() {
            return this == DOUBLE_STOMP || this == FLING;
        }

        @Override
        public boolean hasAttackPointAt(int attackTicker) {
            if(this == CHARGE){
                return true;
            }
            return attackTicker == this.getAttackPoint();
        }

        public static FrostomperAttackType byOrdinal(int pOrdinal) {
            if (pOrdinal < 0 || pOrdinal > values().length) {
                pOrdinal = 0;
            }

            return values()[pOrdinal];
        }
    }
}
