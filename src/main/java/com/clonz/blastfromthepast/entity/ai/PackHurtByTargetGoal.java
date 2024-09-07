package com.clonz.blastfromthepast.entity.ai;

import com.clonz.blastfromthepast.entity.pack.EntityPack;
import com.clonz.blastfromthepast.entity.pack.EntityPackHolder;
import com.clonz.blastfromthepast.mixin.HurtByTargetGoalAccessor;
import com.clonz.blastfromthepast.util.EntityHelper;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.phys.AABB;

public class PackHurtByTargetGoal<T extends PathfinderMob & EntityPackHolder<T>> extends HurtByTargetGoal {
    protected final T packMob;

    public PackHurtByTargetGoal(T packMob, Class<?>... toIgnoreDamage) {
        super(packMob, toIgnoreDamage);
        this.packMob = packMob;
        this.setAlertOthers();
    }

    @Override
    protected void alertOthers() {
        LivingEntity lastHurtByMob = this.packMob.getLastHurtByMob();
        if(lastHurtByMob == null){
            return;
        }
        double followDistance = this.getFollowDistance();
        AABB searchBounds = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(followDistance, 10.0, followDistance);
        EntityPack<T> pack = this.packMob.getPack();
        if(pack == null){
            return;
        }
        pack.getMembers()
                .filter(member -> this.canAlert(member, searchBounds, lastHurtByMob))
                .forEach(member -> this.alertOther(member, lastHurtByMob));
    }

    protected boolean canAlert(T member, AABB searchBounds, LivingEntity lastHurtByMob) {
        return member.getBoundingBox().intersects(searchBounds)
                && this.packMob != member
                && member.getTarget() == null
                && !EntityHelper.haveSameOwners(this.packMob, member)
                && !member.isAlliedTo(lastHurtByMob)
                && !this.ignoresAlert(member);
    }

    protected boolean ignoresAlert(T member) {
        if (this.access().getToIgnoreAlert() == null) {
            return false;
        }
        Class<?>[] toIgnoreAlert = this.access().getToIgnoreAlert();
        for (Class<?> ignoresAlert : toIgnoreAlert) {
            if (member.getClass() == ignoresAlert) {
                return true;
            }
        }
        return false;
    }

    protected HurtByTargetGoalAccessor access(){
        return (HurtByTargetGoalAccessor) this;
    }
}
