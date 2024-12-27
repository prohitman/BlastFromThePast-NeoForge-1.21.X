package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.SnowdoEggBlock;
import com.clonz.blastfromthepast.client.models.SnowdoModel;
import com.clonz.blastfromthepast.entity.ai.goal.SnowdoBreedGoal;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.init.ModItems;
import com.clonz.blastfromthepast.init.ModSounds;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SnowdoEntity extends Animal implements Animatable<SnowdoModel> {
    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "snowdo");
    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> SnowdoModel.createStateMap(getAnimation()));
    public  static final EntityDataAccessor<Boolean> TRIPPED = SynchedEntityData.defineId(SnowdoEntity.class, EntityDataSerializers.BOOLEAN);
    public  static final EntityDataAccessor<Optional<UUID>> RIDDEN_PLAYER = SynchedEntityData.defineId(SnowdoEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    public  static final EntityDataAccessor<Boolean> GLIDING = SynchedEntityData.defineId(SnowdoEntity.class, EntityDataSerializers.BOOLEAN);
    public static final float movementSpeed = 0.12f;
    public int tripTicks;

    public SnowdoEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 2;
        this.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.12f)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SnowdoBreedGoal(this, 1));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, (stack) -> stack.is(ModItems.MELON_ICE_CREAM), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public void setTripped(boolean tripped){
        this.entityData.set(TRIPPED, tripped);
        if(tripped){
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0);
            this.tripTicks = 30;
        } else {
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(movementSpeed);
            this.tripTicks = 0;
        }
    }

    public boolean isTripped(){
        return this.entityData.get(TRIPPED);
    }

    public void setGliding(boolean isGliding){
        this.entityData.set(GLIDING, isGliding);
    }

    public boolean isGliding(){
        return this.entityData.get(GLIDING);
    }

    public void setRiddenPlayer(Optional<UUID> uuid){
        this.entityData.set(RIDDEN_PLAYER, uuid);
    }

    public Optional<UUID> getRiddenPlayer(){
        return this.entityData.get(RIDDEN_PLAYER);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TRIPPED, false);
        builder.define(RIDDEN_PLAYER, Optional.empty());
        builder.define(GLIDING, false);
    }

    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.isBaby() ? EntityDimensions.fixed(0.35f, 0.35f).withEyeHeight(0.2f) : super.getDefaultDimensions(pose);
    }

    public void tick() {
        super.tick();
        if(this.getVehicle() instanceof Player){
            if(this.isInWaterOrBubble()){
                this.stopRiding();
            }
        }

        if (level().isClientSide()) {
            animateWhen("idle", !isMoving(this) && onGround() && !this.isTripped());
            animateWhen("trip", this.isTripped());
            if(!this.isBaby()){
                animateWhen("glide", this.isGliding());
            }
            if(this.getRandom().nextInt(100) == 0 && !this.isBaby()){
                playAnimation("tail");
            }
        }
        if(this.getVehicle() == null){
            Vec3 vec3 = this.getDeltaMovement();
            if (!this.onGround() && vec3.y < 0.0) {
                this.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
            }
        }

        if(!this.level().isClientSide()){
          if(this.getRandom().nextInt(500) == 0 && this.isMoving(this) && this.onGround() && !this.isPassenger()){
              this.setTripped(true);
              this.level().playSound(null, this.blockPosition(), ModSounds.SNOWDO_TRIP.get(), SoundSource.AMBIENT, 1, 1);
          }
          if(this.isTripped()){
              tripTicks--;
          }
          if(tripTicks<=0){
              this.setTripped(false);
          }
            if((this.isPassenger() && !this.getVehicle().onGround()) || (!this.isPassenger() && !this.onGround())){
                this.setGliding(true);
            } else if(this.isGliding()){
                this.setGliding(false);
            }
          if(this.getVehicle() instanceof Player player){
              this.setRot(player.getYRot(), player.getXRot() * 0.5f);
              this.yRotO = this.yBodyRot = this.getYRot();

              Vec3 vec3 = player.getDeltaMovement();
              if (!player.onGround() && vec3.y < 0.0) {
                  player.setDeltaMovement(vec3.multiply(1.0, 0.6, 1.0));
                  player.hurtMarked = true;
                  player.resetFallDistance();
              }
          }
        }

        if(!(this.getVehicle() instanceof Player) && this.getRiddenPlayer().isPresent()){
            Player player = this.level().getPlayerByUUID(this.getRiddenPlayer().get());
            if(player != null){
                this.startRiding(player);
            }
        }
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source) {
        if(this.isGliding()){
            return false;
        }
        return super.causeFallDamage(fallDistance, multiplier, source);
    }
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(player.getMainHandItem().isEmpty() && !this.isBaby() && player.getFirstPassenger() == null){
            this.startRiding(player, true);
            this.setRiddenPlayer(Optional.of(player.getUUID()));

            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SNOWDO_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SNOWDO_IDLE.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SNOWDO_DEATH.get();
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(ModItems.MELON_ICE_CREAM);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return ModEntities.SNOWDO.get().create(level);
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
        return Optional.ofNullable(getAnimations().get().get("animation.snowdo." + animation));
    }

    @Override
    public int tickCount() {
        return tickCount;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setTripped(compound.getBoolean("tripped"));
        this.tripTicks = compound.getInt("tripTicks");
        if(compound.contains("RiddenPlayer")){
            this.setRiddenPlayer(Optional.of(compound.getUUID("RiddenPlayer")));
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("tripped", this.isTripped());
        compound.putInt("tripTicks", this.tripTicks);
        if(this.getRiddenPlayer().isPresent()){
            compound.putUUID("RiddenPlayer", this.getRiddenPlayer().get());
        }
    }

    public static void init(){}
}