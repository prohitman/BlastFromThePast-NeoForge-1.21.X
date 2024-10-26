package com.clonz.blastfromthepast.entity.burrel;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;

public class BurrelGoToTrees extends Goal {
    private final Burrel burrel;
    private final double speed;
    private Path path;
    private BlockPos treePos;

    public BurrelGoToTrees(Burrel burrel, double speed) {
        this.burrel = burrel;
        this.speed = speed;
    }

    @Override
    public boolean canUse() {
        BlockPos burrelPos = burrel.blockPosition();
        for (int x = -10; x <= 10; x++) {
            for (int y = -5; y <= 5; y++) {
                for (int z = -10; z <= 10; z++) {
                    BlockPos checkPos = burrelPos.offset(x, y, z);
                    if (burrel.level().getBlockState(checkPos).is(BlockTags.LOGS)) {
                        treePos = checkPos;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        this.path = this.burrel.getNavigation().createPath(treePos, 1);
        this.burrel.getNavigation().moveTo(this.path, this.speed);
    }

    @Override
    public boolean canContinueToUse() {
        return !this.burrel.getNavigation().isDone() && this.burrel.distanceToSqr(treePos.getX(), treePos.getY(), treePos.getZ()) > 1.0;
    }

    @Override
    public void stop() {
        this.burrel.getNavigation().stop();
        this.treePos = null;
    }
}
