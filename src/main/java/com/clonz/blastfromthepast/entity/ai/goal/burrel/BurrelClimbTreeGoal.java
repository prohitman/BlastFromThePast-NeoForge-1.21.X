package com.clonz.blastfromthepast.entity.ai.goal.burrel;

import com.clonz.blastfromthepast.entity.Burrel;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

public class BurrelClimbTreeGoal extends Goal {
    private final Burrel burrel;
    private float desiredYrot;

    public BurrelClimbTreeGoal(Burrel burrel) {
        this.burrel = burrel;
    }

    @Override
    public boolean canUse() {
        if (burrel.isBesideClimbableBlock()) {
            if (burrel.targetTree != null && !burrel.wantsToBeOnGround()) return true;
            else burrel.setBesideClimbableBlock(false);
        }
        return false;
    }

    @Override
    public void start() {
        desiredYrot = lookAngle(EntityAnchorArgument.Anchor.FEET, new Vec3(burrel.targetTree.getX() + 0.5, burrel.getY(), burrel.targetTree.getZ() + 0.5)).y;
        burrel.setYRot(desiredYrot);
    }

    @Override
    public boolean canContinueToUse() {
        return burrel.isBesideClimbableBlock() && burrel.targetTree != null && !burrel.wantsToBeOnGround();
    }

    @Override
    public void tick() {
        if (burrel.targetTree == null) {
            stop();
            return;
        }
        burrel.getNavigation().stop();
        burrel.setYRot(desiredYrot);
        burrel.setPos(burrel.getBlockX() + 0.5, burrel.getY() + 0.4, burrel.getBlockZ() + 0.5);
        BlockPos pos = burrel.targetTree.mutable().setY((int)burrel.getY()).immutable();
        BlockState blockState = burrel.level().getBlockState(pos);
        if (!(blockState.is(BlockTags.LOGS) || blockState.is(BlockTags.LEAVES))) {
            burrel.setPos(pos.getCenter().subtract(0, 0.5, 0));
            stop();
        }
    }

    public Vec2 lookAngle(EntityAnchorArgument.Anchor anchor, Vec3 target) {
        Vec3 vec3 = anchor.apply(burrel);
        double d = target.x - vec3.x;
        double e = target.y - vec3.y;
        double f = target.z - vec3.z;
        double g = Math.sqrt(d * d + f * f);
        return new Vec2(Mth.wrapDegrees((float)(-(Mth.atan2(e, g) * 57.2957763671875))), Mth.wrapDegrees((float)(Mth.atan2(f, d) * 57.2957763671875) - 90.0F));
    }

    @Override
    public void stop() {
        burrel.setBesideClimbableBlock(false);
        burrel.targetTree = null;
    }
}
