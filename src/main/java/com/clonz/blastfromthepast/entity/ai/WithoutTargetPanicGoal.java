package com.clonz.blastfromthepast.entity.ai;

import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.PanicGoal;

import java.util.function.Function;

public class WithoutTargetPanicGoal extends PanicGoal {
    public WithoutTargetPanicGoal(PathfinderMob mob, double speedModifier) {
        super(mob, speedModifier);
    }

    public WithoutTargetPanicGoal(PathfinderMob mob, double speedModifier, TagKey<DamageType> panicCausingDamageTypes) {
        super(mob, speedModifier, panicCausingDamageTypes);
    }

    public WithoutTargetPanicGoal(PathfinderMob mob, double speedModifier, Function<PathfinderMob, TagKey<DamageType>> panicCausingDamageTypes) {
        super(mob, speedModifier, panicCausingDamageTypes);
    }

    @Override
    protected boolean shouldPanic() {
        return this.mob.getTarget() == null & super.shouldPanic();
    }
}
