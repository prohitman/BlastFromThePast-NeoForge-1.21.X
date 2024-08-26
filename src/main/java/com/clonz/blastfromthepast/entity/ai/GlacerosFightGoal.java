package com.clonz.blastfromthepast.entity.ai;

import com.clonz.blastfromthepast.entity.GlacerosEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class GlacerosFightGoal extends NearestAttackableTargetGoal {

    private final GlacerosEntity entity;

    public GlacerosFightGoal(Mob mob, Class targetType, boolean mustSee) {
        super(mob, targetType, mustSee);
        entity = ((GlacerosEntity) mob);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && entity.getRandom().nextInt(reducedTickDelay(1200)) == 0;
    }
}
