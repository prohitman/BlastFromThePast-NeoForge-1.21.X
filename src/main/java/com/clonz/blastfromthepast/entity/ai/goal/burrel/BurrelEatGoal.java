package com.clonz.blastfromthepast.entity.ai.goal.burrel;

import com.clonz.blastfromthepast.entity.Burrel;
import com.clonz.blastfromthepast.init.ModItems;
import com.clonz.blastfromthepast.init.ModSounds;
import com.clonz.blastfromthepast.network.BurrelEatPayload;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class BurrelEatGoal extends TargetGoal {

    private final Burrel burrel;
    private int unseenTicks;
    ItemEntity itemTarget;

    public BurrelEatGoal(Burrel burrel) {
        super(burrel, true);
        this.burrel = burrel;
    }

    @Override
    public boolean canUse() {
        findTarget();
        return itemTarget != null;
    }

    public void start() {
        this.unseenTicks = 0;
        this.burrel.setTarget(null);
        this.burrel.setWantsToBeOnGround(true);
    }

    public void stop() {
        this.itemTarget = null;
        this.burrel.getNavigation().stop();
    }

    @Override
    public void tick() {
        if (itemTarget == null || itemTarget.isRemoved()) return;
        float entityDistance = burrel.distanceTo(itemTarget);
        if (entityDistance < 1.5) {
            burrel.makeSound(ModSounds.BURREL_EAT.get());
            PacketDistributor.sendToPlayersTrackingEntity(burrel, new BurrelEatPayload(burrel.getId()));
            itemTarget.discard();
            burrel.getNavigation().stop();
        }
    }

    protected void findTarget() {
        List<ItemEntity> itemEntityList = this.mob.level().getEntitiesOfClass(ItemEntity.class, this.burrel.getBoundingBox().inflate(20, 30, 20), (livingEntity) -> true);
        ItemEntity targetCandidate = null;
        float distance = 0;
        for (ItemEntity entry : itemEntityList) {
            if (burrel.isFood(entry.getItem()) || entry.getItem().is(ModItems.SAP_BALL)) {
                float entityDistance = burrel.distanceTo(entry);
                if (entityDistance < 1.5) {
                    burrel.makeSound(ModSounds.BURREL_EAT.get());
                    PacketDistributor.sendToPlayersTrackingEntity(burrel, new BurrelEatPayload(burrel.getId()));
                    burrel.getNavigation().stop();
                    entry.discard();
                }
                else if (targetCandidate == null || entityDistance < distance) {
                    targetCandidate = entry;
                    distance = burrel.distanceTo(entry);
                }
            }
        }
        itemTarget = targetCandidate;
        if (targetCandidate != null) {
            this.burrel.getNavigation().moveTo(itemTarget, 1);
        }
    }

    @Override
    public boolean canContinueToUse() {
        if (itemTarget == null || itemTarget.isRemoved()) {
            findTarget();
            if (itemTarget == null || itemTarget.isRemoved()) {
                return false;
            }
        }
        double d = this.getFollowDistance();
        if (this.mob.distanceToSqr(itemTarget) > d * d) {
            return false;
        } else {
            if (this.mustSee) {
                if (this.mob.getSensing().hasLineOfSight(itemTarget)) {
                    this.unseenTicks = 0;
                } else if (++this.unseenTicks > reducedTickDelay(this.unseenMemoryTicks)) {
                    return false;
                }
            }

            return true;
        }
    }
}
