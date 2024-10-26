package com.clonz.blastfromthepast.entity.burrel;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BurrelClimbTree extends Goal {
    private final Burrel burrel;
    private BlockPos targetTree;
    private boolean climbing;

    public BurrelClimbTree(Burrel burrel) {
        this.burrel = burrel;
    }

    @Override
    public boolean canUse() {
        BlockPos burrelPos = burrel.blockPosition();
        for (int x = -10; x <= 10; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -10; z <= 10; z++) {
                    BlockPos checkPos = burrelPos.offset(x, y, z);
                    BlockState state = burrel.level().getBlockState(checkPos);
                    if (state.is(BlockTags.LOGS)) {
                        targetTree = checkPos;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        climbing = true;
        burrel.getNavigation().moveTo(targetTree.getX(), targetTree.getY(), targetTree.getZ(), 1.0);
    }

    @Override
    public boolean canContinueToUse() {
        return climbing && targetTree != null && !burrel.getNavigation().isDone();
    }

    @Override
    public void tick() {
        if (targetTree != null && burrel.distanceToSqr(Vec3.atCenterOf(targetTree)) < 1.0) {
            burrel.setBesideClimbableBlock(true);
            climbing = false;
        }
        if (burrel.isBesideClimbableBlock()) {
            burrel.setDeltaMovement(burrel.getDeltaMovement().add(0.0D, 0.1D, 0.0D));
        }
    }

    @Override
    public void stop() {
        climbing = false;
        burrel.setBesideClimbableBlock(false);
        targetTree = null;
    }
}
