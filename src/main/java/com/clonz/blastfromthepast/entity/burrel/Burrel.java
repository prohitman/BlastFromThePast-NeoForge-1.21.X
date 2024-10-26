package com.clonz.blastfromthepast.entity.burrel;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.BurrelModel;
import com.clonz.blastfromthepast.init.ModItems;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class Burrel extends TamableAnimal implements Animatable<BurrelModel> {

    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "burrel");

    private static final EntityDataAccessor<Integer> TYPES = SynchedEntityData.defineId(Burrel.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> BABY = SynchedEntityData.defineId(Burrel.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(Burrel.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Direction> ATTACHED_FACE = SynchedEntityData.defineId(Burrel.class, EntityDataSerializers.DIRECTION);
    private static final Direction[] POSSIBLE_DIRECTIONS = new Direction[]{Direction.DOWN, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);

    public float attachChangeProgress = 0F;
    public float prevAttachChangeProgress = 0F;
    public Direction prevAttachDir = Direction.DOWN;
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> BurrelModel.createStateMap(getAnimation()));
    public AnimationState idleState = new AnimationState();
    public AnimationState climbingState = new AnimationState();

    public Burrel(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new BurrelGoToTrees(this, 1.2D));
        this.goalSelector.addGoal(2, new BurrelClimbTree(this));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new BreedGoal(this, 1.0));
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level) {
            @Override
            protected boolean canUpdatePath() {
                return super.canUpdatePath() || ((Burrel) mob).isBesideClimbableBlock() || ((Burrel) mob).jumping;
            }
        };
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(ATTACHED_FACE, Direction.from3DDataValue(compound.getByte("attachface")));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putByte("attachface", (byte) this.entityData.get(ATTACHED_FACE).get3DDataValue());
    }

    @Override
    public void tick() {
        super.tick();
        prevAttachChangeProgress = attachChangeProgress;
        if (attachChangeProgress > 0F) {
            attachChangeProgress -= 0.25F;
        }
        if (prevAttachDir != this.getAttachmentFacing()) {
            attachChangeProgress = 1F;
        }
        this.prevAttachDir = this.getAttachmentFacing();

        if (this.level().isClientSide()) {
            this.idleState.animateWhen(!this.isMoving(this), this.tickCount);
            this.climbingState.animateWhen(this.isMoving(this) && this.isBesideClimbableBlock(), this.tickCount);
        }
        Vec3 vector3d = this.getDeltaMovement();
        if (!this.level().isClientSide()) {
            this.setBesideClimbableBlock(this.horizontalCollision);
            if (this.onGround() || this.isInWater() || this.isInLava()) {
                this.setDirectionFacing(Direction.DOWN);
            } else {
                Direction closestDirection = Direction.DOWN;
                double closesDistance = 100;
                for (Direction direction : POSSIBLE_DIRECTIONS) {
                    BlockPos antPos = new BlockPos(Mth.floor(this.getX()), Mth.floor(this.getY()), Mth.floor(this.getZ()));
                    BlockPos offsetPos = antPos.relative(direction);
                    Vec3 offset = Vec3.atCenterOf(offsetPos);
                    if (closesDistance > this.position().distanceTo(offset) && level().loadedAndEntityCanStandOnFace(offsetPos, this, direction.getOpposite())) {
                        closesDistance = this.position().distanceTo(offset);
                        closestDirection = direction;
                    }
                }
                this.entityData.set(ATTACHED_FACE, closesDistance > this.getBbWidth() * 0.5F + 0.7F ? Direction.DOWN : closestDirection);
            }
        }
        boolean flag = false;
        if (this.getAttachmentFacing() != Direction.DOWN) {
            if (!this.horizontalCollision && this.getAttachmentFacing() != Direction.UP) {
                Vec3 vec3 = Vec3.atLowerCornerOf(this.getAttachmentFacing().getNormal());
                this.setDeltaMovement(vector3d.add(vec3.normalize().multiply(0.1F, 0.1F, 0.1F)));
            }
        }

        if (this.getAttachmentFacing() != Direction.DOWN) {
            this.setDeltaMovement(vector3d.multiply(0.6D, 0.4D, 0.6D));
        }

    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        return false;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(BABY, false);
        builder.define(TYPES, 0);
        builder.define(CLIMBING, (byte) 0);
        builder.define(ATTACHED_FACE, Direction.DOWN);
    }

    public void setDirectionFacing(Direction directionFacing) {
        this.entityData.set(ATTACHED_FACE, directionFacing);
    }

    public Direction getAttachmentFacing() {
        return this.entityData.get(ATTACHED_FACE);
    }

    @Override
    public boolean onClimbable() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.entityData.get(CLIMBING);
        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.entityData.set(CLIMBING, b0);
    }

    public boolean isBaby() {
        return this.entityData.get(BABY);
    }

    public void setBaby(boolean b) {
        this.entityData.set(BABY, b);
    }

    public int getTypes() {
        return this.entityData.get(TYPES);
    }

    public void setTypes(int i) {
        this.entityData.set(TYPES, i);
    }

    public boolean getSpawnBabyChance(RandomSource p) {
        return this.random.nextFloat() < 0.05F;
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        SpawnGroupData sg = super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
        RandomSource randomSource = level.getRandom();
        if (!(sg instanceof BurrelGroupData)) {
            boolean baby = getSpawnBabyChance(randomSource);
            int variant = this.random.nextInt(2);
            sg = new BurrelGroupData(baby, variant);
        }

        this.setBaby(((BurrelGroupData) sg).baby);
        this.setTypes(((BurrelGroupData) sg).variant);

        return sg;
    }

    /**
     * @return the model location
     */
    @Override
    public ResourceLocation getModelLocation() {
        return null;
    }

    /**
     * @return the DucAnimation
     */
    @Override
    public DucAnimation getAnimation() {
        return ANIMATION;
    }

    /**
     * @return Get a lazy with all animations and their keys.
     */
    @Override
    public Lazy<Map<String, AnimationState>> getAnimations() {
        return animations;
    }

    /**
     * get the animation state for a key.
     *
     * @param animation animation key
     * @return the animation state corresponding to the key.
     */
    @Override
    public Optional<AnimationState> getAnimationState(String animation) {
        return Optional.ofNullable(getAnimations().get().get("animation.burrel" + animation));
    }

    @Override
    public int tickCount() {
        return this.tickCount;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.COOKED_VENISON.get());
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Animal.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.22f);
        builder = builder.add(Attributes.MAX_HEALTH, 6D);
        builder = builder.add(Attributes.FOLLOW_RANGE, 64);
        return builder;
    }

    public static void init() {
    }

    public static class BurrelGroupData implements SpawnGroupData {
        public final boolean baby;
        public final int variant;

        public BurrelGroupData(boolean baby, int variant) {
            this.baby = baby;
            this.variant = variant;
        }

    }

}
