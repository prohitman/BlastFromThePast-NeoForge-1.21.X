package com.clonz.blastfromthepast.entity.burrel;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BurrelWallClimbNavigator extends GroundPathNavigation {

    @Nullable
    private BlockPos pathToPosition;

    public BurrelWallClimbNavigator(Mob mob, Level level) {
        super(mob, level);
    }

    public Path createPath(BlockPos pos, int p) {
        this.pathToPosition = pos;
        return super.createPath(pos, p);
    }

    public Path createPath(Burrel pEntity, int accu) {
        this.pathToPosition = pEntity.blockPosition();
        return super.createPath(pEntity, accu);
    }

    public boolean moveTo(Burrel en, double ch) {
        Path path1 = this.createPath((Burrel) en, 0);
        if (path1 != null) {
            return this.moveTo(path1, ch);
        } else {
            this.pathToPosition = en.blockPosition();
            this.speedModifier = ch;
            return true;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(!this.isDone()){
            super.tick();
        }else if (this.pathToPosition != null){
            Vec3 xz = new Vec3(this.pathToPosition.getX() + 0.5F - this.mob.getX(), 0, this.pathToPosition.getZ() + 0.5F - this.mob.getZ());
            double dist = xz.length();
            if (dist < this.mob.getBbWidth() || this.mob.getY() > (double)this.pathToPosition.getY()) {
                this.pathToPosition = null;
            } else {
                this.mob.getMoveControl().setWantedPosition((double)this.pathToPosition.getX(), (double)this.mob.getY(), (double)this.pathToPosition.getZ(), this.speedModifier);
            }
        }
    }

    @Override
    protected void doStuckDetection(Vec3 vec) {
        if (this.tick - this.lastStuckCheck > 40) {
            if (vec.distanceToSqr(new Vec3(this.lastStuckCheckPos.x, vec.y, this.lastStuckCheckPos.z)) < 2.25D) {
                this.stop();
            }
            this.lastStuckCheck = this.tick;
            this.lastStuckCheckPos = vec;
        }
    }

}
