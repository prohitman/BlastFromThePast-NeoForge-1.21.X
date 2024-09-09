package com.clonz.blastfromthepast.entity.ai;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.ChargeForward;
import com.clonz.blastfromthepast.mixin.LivingEntityAccessor;
import com.clonz.blastfromthepast.util.DebugFlags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ChargeForwardAttackGoal<T extends PathfinderMob & ChargeForward> extends Goal {
    private final T mob;
    private final int maxDistance;
    private final double speedModifier;
    private Vec3 startPos;

    public ChargeForwardAttackGoal(T mob, int maxDistance, double speedModifier) {
        this.mob = mob;
        this.maxDistance = maxDistance;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public boolean canUse() {
        return this.mob.isChargingForward();
    }

    @Override
    public void start() {
        this.startPos = this.mob.position();
        this.mob.getNavigation().stop();
        if(DebugFlags.DEBUG_CHARGE_FORWARD)
            BlastFromThePast.LOGGER.info("{} is charging forward!", this.mob);
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isChargingForward()
                && !this.mob.hasRestriction()
                && this.startPos.distanceToSqr(this.mob.position()) <= Mth.square(this.maxDistance);
    }

    @Override
    public void tick() {
        Vec3 look = this.mob.getViewVector(1.0F);
        Vec3 motion = this.mob.getDeltaMovement();
        double speed = this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED) * this.speedModifier * ((LivingEntityAccessor)this.mob).callGetBlockSpeedFactor();
        this.mob.setDeltaMovement(look.x * speed, motion.y, look.z * speed);
        this.mob.hasImpulse = true;
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        this.mob.setIsChargingForward(false);
        if(DebugFlags.DEBUG_CHARGE_FORWARD)
            BlastFromThePast.LOGGER.info("{} has stopped charging forward", this.mob);
    }

}