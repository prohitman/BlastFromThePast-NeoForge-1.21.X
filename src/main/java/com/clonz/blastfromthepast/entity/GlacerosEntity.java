package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.entity.ai.goal.EatDelphiniumGoal;
import com.clonz.blastfromthepast.entity.ai.goal.GlacerosAlertPanicGoal;
import com.clonz.blastfromthepast.entity.ai.goal.GlacerosFightGoal;
import com.clonz.blastfromthepast.entity.ai.goal.MoveAwayFromBlockGoal;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.init.ModItems;
import com.clonz.blastfromthepast.init.ModSounds;
import com.mojang.serialization.Codec;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.monster.hoglin.Hoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import javax.swing.plaf.BorderUIResource;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.IntFunction;

public class GlacerosEntity extends Animal implements Animatable<GlacerosModel>, VariantHolder<GlacerosEntity.Variant> {
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "glaceros");
    public static final ResourceLocation BABY_LOCATIION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "baby_glaceros");
    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> GlacerosModel.createStateMap(getAnimation()));
    private static final EntityDataAccessor<Integer> DATA_STRENGTH_ID = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_VARIANT_ID = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.INT);
    public  static final EntityDataAccessor<Boolean> PANICKING = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.BOOLEAN);
    public  static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.BOOLEAN);
    public  static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.BOOLEAN);
    public  static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.defineId(GlacerosEntity.class, EntityDataSerializers.BOOLEAN);

    public int a = 0;
    public boolean readytoPlay = false;
    public int random = 120;
    public int antlerGrowCooldown;
    public int alertCooldown;

    public GlacerosEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2f)
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_DAMAGE, 0);
    }

    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new GlacerosAlertPanicGoal(this, 2.0));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.4));
        this.goalSelector.addGoal(4, new BreedGoal(this, 1.1));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1, itemStack -> itemStack.is(this.getVariant().getDelphinium().asItem()) ,false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new EatDelphiniumGoal(this, 1, 15));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(7, new MoveAwayFromBlockGoal(this, Blocks.FIRE, 1.7, 12));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(0, new GlacerosFightGoal(this, GlacerosEntity.class, false));
        this.targetSelector.addGoal(5, new AvoidEntityGoal<>(this, PsychoBearEntity.class, 20, 1.2f, 2.0f));//should avoid only when in sight?
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        RandomSource randomsource = level.getRandom();
        this.setRandomStrength(randomsource);
        GlacerosEntity.Variant glaceros$variant;
        if (spawnGroupData instanceof GlacerosEntity.GlacerosGroupData) {
            glaceros$variant = ((GlacerosEntity.GlacerosGroupData)spawnGroupData).variant;
        } else {
            //glaceros$variant = Util.getRandom(GlacerosEntity.Variant.values(), randomsource);
            glaceros$variant = getVariantFromChance(randomsource);
            spawnGroupData = new GlacerosEntity.GlacerosGroupData(glaceros$variant);
        }

        this.setVariant(glaceros$variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    private Variant getVariantFromChance(RandomSource random){
        float chance = random.nextFloat();
        if(chance <= 0.25){
            return Variant.STRAIGHT;
        } else if(0.25 < chance && chance <= 0.5){
            return Variant.SPIKEY;
        } else if(0.5 < chance && chance <= 0.75){
            return Variant.CURLY;
        } else {
            return Variant.BROAD;
        }
    }

    static class GlacerosGroupData extends AgeableMob.AgeableMobGroupData {
        public final GlacerosEntity.Variant variant;

        GlacerosGroupData(Variant variant) {
            super(true);
            this.variant = variant;
        }
    }

    @Override
    protected void actuallyHurt(DamageSource damageSource, float damageAmount) {
        super.actuallyHurt(damageSource, damageAmount);
        if(damageSource.is(DamageTypeTags.PANIC_CAUSES)){
            List<GlacerosEntity> glacerosEntities = this.level()
                    .getEntitiesOfClass(GlacerosEntity.class, this.getBoundingBox().inflate(16));
            if(!glacerosEntities.isEmpty()){
                for(GlacerosEntity glaceros : glacerosEntities){
                    glaceros.setPanicking(true);
                    glaceros.alertCooldown = 40 + glaceros.getRandom().nextInt(20);
                }
            }
        }
    }

    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            animateWhen("idle", !isMoving(this) && onGround());
            animateWhen("eat", this.isEating());
        }

        if(this.isSheared() && !this.level().isClientSide){
            if(antlerGrowCooldown != 0){
                antlerGrowCooldown--;
            } else {
                this.setSheared(false);
            }
        }

        if (!isMoving(this))
            this.a ++;;

        if (!level().isClientSide() && this.a == this.random) {
            this.readytoPlay = true;

        }

        if (!level().isClientSide() && this.a == this.random + 1) {
            this.a = 0;
            this.readytoPlay = false;
        }

        if(!level().isClientSide){
            if(alertCooldown != 0){
                alertCooldown--;
            } else {
                this.setPanicking(false);
            }
            this.setRunning(this.moveControl.getSpeedModifier() > 1.1);
        }
    }

    private void setStrength(int strength) {
        this.entityData.set(DATA_STRENGTH_ID, Math.max(1, Math.min(5, strength)));
    }

    private void setRandomStrength(RandomSource random) {
        int i = random.nextFloat() < 0.04F ? 5 : 3;
        this.setStrength(1 + random.nextInt(i));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(this.getVariant().getDelphinium().asItem());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        GlacerosEntity child = ModEntities.GLACEROS.get().create(level);
        child.setBaby(true);
        child.setVariant(this.getVariant());
        GlacerosEntity otherGlaceros = (GlacerosEntity) otherParent;
        if(otherGlaceros.getVariant() != this.getVariant()){
            if(this.getRandom().nextFloat() < 0.5){
                child.setVariant(otherGlaceros.getVariant());
            }
        }

        return child;
    }

    @Override
    public ResourceLocation getModelLocation() {
        return null;
    }

    @Override
    public DucAnimation getAnimation() {
        return ANIMATION;
    }

    @Override
    public Lazy<Map<String, AnimationState>> getAnimations() {
        return animations;
    }

    @Override
    public Optional<AnimationState> getAnimationState(String animation) {
        return Optional.ofNullable(getAnimations().get().get("animation.glaceros." + animation));
    }

    @Override
    public int tickCount() {
        return tickCount;
    }

    public static void init() {
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_VARIANT_ID, 0);
        builder.define(DATA_STRENGTH_ID, 0);
        builder.define(PANICKING, false);
        builder.define(EATING, false);
        builder.define(SHEARED, false);
        builder.define(RUNNING, false);
    }

    @Override
    public boolean isPanicking() {
        return this.entityData.get(PANICKING);
    }

    public void setPanicking(boolean panicking) {
        this.entityData.set(PANICKING, panicking);
    }

    public boolean isEating() {
        return this.entityData.get(EATING);
    }

    public void setEating(boolean eating) {
        this.entityData.set(EATING, eating);
    }

    public boolean isSheared() {
        return this.entityData.get(SHEARED);
    }

    public void setSheared(boolean sheared) {
        this.entityData.set(SHEARED, sheared);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    @Override
    public void setVariant(GlacerosEntity.Variant variant) {
        this.entityData.set(DATA_VARIANT_ID, variant.id);
    }

    @Override
    public GlacerosEntity.Variant getVariant() {
        return GlacerosEntity.Variant.byId(this.entityData.get(DATA_VARIANT_ID));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getVariant().id);
        compound.putBoolean("Panicking", this.isPanicking());
        compound.putBoolean("Eating", this.isEating());
        compound.putBoolean("Sheared", this.isSheared());
        compound.putInt("AntlerCooldown", antlerGrowCooldown);
        compound.putInt("AlertCooldown", alertCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(Variant.byId(compound.getInt("Variant")));
        this.setPanicking(compound.getBoolean("Panicking"));
        this.setEating(compound.getBoolean("Eating"));
        this.setSheared(compound.getBoolean("Sheared"));
        antlerGrowCooldown = compound.getInt("AntlerCooldown");
        alertCooldown = compound.getInt("AlertCooldown");
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SOUND_ENTITY_GLACEROS_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SOUND_ENTITY_GLACEROS_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SOUND_ENTITY_GLACEROS_DEATH.get();
    }

    //////////////////////////////// VARIANTSSSSSSSSSSS

    public enum Variant implements StringRepresentable {
        STRAIGHT(0,"normal", ModBlocks.VIOLET_DELPHINIUM.get(), ModItems.STRAIGHT_GLACEROS_ANTLERS.get()),
        BROAD(1, "broad", ModBlocks.BLUE_DELPHINIUM.get(), ModItems.BROAD_GLACEROS_ANTLERS.get()),
        CURLY(2, "curly", ModBlocks.PINK_DELPHINIUM.get(), ModItems.CURLY_GLACEROS_ANTLERS.get()),
        SPIKEY(3, "spikey", ModBlocks.WHITE_DELPHINIUM.get(), ModItems.SPIKEY_GLACEROS_ANTLERS.get());

        public static final Codec<GlacerosEntity.Variant> CODEC = StringRepresentable.fromEnum(GlacerosEntity.Variant::values);
        private static final IntFunction<GlacerosEntity.Variant> BY_ID = ByIdMap.continuous(GlacerosEntity.Variant::getId, values(), ByIdMap.OutOfBoundsStrategy.CLAMP);
        final int id;
        private final String name;
        private final Block delphinium;
        private final Item antlerItem;

        Variant(int id, String name, Block delphinium, Item antlerItem) {
            this.id = id;
            this.name = name;
            this.delphinium = delphinium;
            this.antlerItem = antlerItem;
        }

        public Block getDelphinium() {
            return delphinium;
        }

        public Item getAntlerItem() {
            return antlerItem;
        }

        public int getId() {
            return this.id;
        }

        public static GlacerosEntity.Variant byId(int id) {
            return BY_ID.apply(id);
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
