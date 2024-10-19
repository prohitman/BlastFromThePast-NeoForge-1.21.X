package com.clonz.blastfromthepast.entity.burrel;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.client.models.BurrelModel;
import com.clonz.blastfromthepast.init.ModItems;
import io.github.itskillerluc.duclib.client.animation.DucAnimation;
import io.github.itskillerluc.duclib.entity.Animatable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

public class Burrel extends TamableAnimal implements Animatable<BurrelModel> {

    public static final ResourceLocation LOCATION = ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "burrel");
    public static final DucAnimation ANIMATION = DucAnimation.create(LOCATION);
    private final Lazy<Map<String, AnimationState>> animations = Lazy.of(() -> BurrelModel.createStateMap(getAnimation()));
    public AnimationState idleState = new AnimationState();

    public Burrel(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 5;
        this.setPathfindingMalus(PathType.DAMAGE_FIRE, -1.0F);
        this.setPathfindingMalus(PathType.DANGER_FIRE, -1.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new WallClimberNavigation(this, level) {
            @Override
            protected boolean canUpdatePath() {
                return super.canUpdatePath();
            }
        };
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(2, new LookAtPlayerGoal(this, Player.class, 5.0F));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, 0.4F));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1.0, 10.0F, 2.0F));
        this.goalSelector.addGoal(6, new BreedGoal(this, 1.0));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.idleState.animateWhen(!this.isMoving(this), this.tickCount);
        }
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

}
