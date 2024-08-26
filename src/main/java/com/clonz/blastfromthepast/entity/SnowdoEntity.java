package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.client.models.SnowdoModel;
import com.clonz.blastfromthepast.init.ModSounds;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.client.model.AnimatableDucModel;
import io.github.itskillerluc.duclib.data.animation.serializers.Animation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class SnowdoEntity extends Animal implements Animatable<SnowdoModel> {

    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "snowdo");
    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> GlacerosModel.createStateMap(getAnimation()));
    public int a = 0;
    public boolean trip = false;
    public int random = this.getRandom().nextIntBetweenInclusive(1200, 1300);
    public boolean tripped;

    public SnowdoEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        AttributeSupplier.Builder builder = Animal.createMobAttributes();
        builder = builder.add(Attributes.MOVEMENT_SPEED, 0.12f);
        builder = builder.add(Attributes.MAX_HEALTH, 20);
        builder = builder.add(Attributes.FOLLOW_RANGE, 16);
        return builder;
    }

    public void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.5));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public void tick() {
        super.tick();
        if (level().isClientSide()) {
            animateWhen("idle", !isMoving(this) && onGround() && !tripped);
        }

        if (level().isClientSide()) {
            animateWhen("trip", tripped);
        }

        if (isMoving(this))
        this.a ++;;

        if (!level().isClientSide() && this.a == this.random) {
            this.trip = true;

        }

        if (!level().isClientSide() && this.a == this.random + 1) {
            this.a = 0;
            this.trip = false;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return (SoundEvent) BuiltInRegistries.SOUND_EVENT
                .get(ResourceLocation.fromNamespaceAndPath("blastfromthepast","snowdo_hurt"));
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return (SoundEvent) BuiltInRegistries.SOUND_EVENT
                .get(ResourceLocation.fromNamespaceAndPath("blastfromthepast","snowdo_idle"));
    }

    @Override
    protected SoundEvent getDeathSound() {
        return (SoundEvent) BuiltInRegistries.SOUND_EVENT
                .get(ResourceLocation.fromNamespaceAndPath("blastfromthepast","snowdo_death"));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
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

    public static void init() {
    }


}