package com.clonz.blastfromthepast.entity.ai.goal;

import com.clonz.blastfromthepast.entity.GlacerosEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class EatDelphiniumGoal extends MoveToBlockGoal {
    private GlacerosEntity mob;
    private int ticksWaited;

    public EatDelphiniumGoal(GlacerosEntity mob, double speedModifier, int searchRange) {
        super(mob, speedModifier, searchRange);
        this.mob = mob;
    }

    @Override
    public double acceptedDistance() {
        return 2.15;
    }

    @Override
    public void start() {
        super.start();
        this.ticksWaited = 0;
        mob.setEating(false);
    }

    @Override
    public void tick() {
        if (this.isReachedTarget()) {
            Vec3 lookVec = blockPos.getBottomCenter();
            mob.getLookControl().setLookAt(lookVec);
            if (this.ticksWaited >= 40) {
                mob.setEating(false);
                this.onReachedTarget();
            } else {
                if(!mob.isEating()){
                    mob.setEating(true);
                }
                if(mob.getRandom().nextFloat() < 0.05f){
                    mob.playSound(SoundEvents.PANDA_EAT, 1.0F, 1.0F);
                }
                this.ticksWaited++;
            }
        } else {
            if(mob.isEating()){
                mob.setEating(false);
            }
        }

        super.tick();
    }

    protected void onReachedTarget() {
        if (net.neoforged.neoforge.event.EventHooks.canEntityGrief(this.mob.level(), mob)) {
            mob.level().destroyBlock(blockPos, false, mob);
        }
    }

    @Override
    protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
        Block delphinium = levelReader.getBlockState(blockPos).getBlock();
        return delphinium == mob.getVariant().getDelphinium();
    }
}
