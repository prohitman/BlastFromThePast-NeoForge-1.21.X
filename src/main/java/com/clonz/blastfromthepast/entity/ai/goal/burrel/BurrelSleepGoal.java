package com.clonz.blastfromthepast.entity.ai.goal.burrel;

import com.clonz.blastfromthepast.entity.Burrel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;

import java.util.ArrayList;
import java.util.List;

public class BurrelSleepGoal extends Goal {
    private final Burrel burrel;
    private final List<Goal> removedGoals = new ArrayList<>();

    public BurrelSleepGoal(Burrel burrel) {
        this.burrel = burrel;
    }

    @Override
    public void start() {
        for (WrappedGoal goal : burrel.goalSelector.getAvailableGoals()) {
            if (goal.getGoal() instanceof RandomLookAroundGoal || goal.getGoal() instanceof LookAtPlayerGoal) {
                removedGoals.add(goal.getGoal());
            }
        }
        for (Goal goal : removedGoals) {
            burrel.goalSelector.removeGoal(goal);
        }
        burrel.setSleeping(true);
    }

    @Override
    public boolean canUse() {
        if (burrel.isBaby()) return false;
        return canContinueToUse();
    }

    @Override
    public boolean canContinueToUse() {
        return !burrel.isPanicking() && burrel.level().getBlockState(burrel.blockPosition().below()).is(BlockTags.LEAVES) && burrel.level().isNight();
    }

    public void tick() {
        burrel.getNavigation().stop();
    }

    public void stop() {
        for (Goal goal : removedGoals) {
            burrel.goalSelector.addGoal(5, goal);
        }
        removedGoals.clear();
        burrel.setSleeping(false);
    }
}
