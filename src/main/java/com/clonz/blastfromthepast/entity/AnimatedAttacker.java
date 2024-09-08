package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.util.EntityHelper;
import com.clonz.blastfromthepast.util.HitboxHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public interface AnimatedAttacker<T extends Mob & AnimatedAttacker<T, A>, A extends AnimatedAttacker.AttackType<T, A>> {

    void setActiveAttackType(@Nullable A attackType);

    @Nullable
    A getActiveAttackType();

    A selectAttackTypeForTarget(@Nullable LivingEntity target);

    interface AttackType<T extends Mob & AnimatedAttacker<T, A>, A extends AnimatedAttacker.AttackType<T, A>>{
        int getAttackPoint();

        int getAttackDuration();

        Vec3 getAttackSize();

        float getAttackDamage();

        default void executeAttackPoint(T attacker){
            Vec3 attackSize = this.getAttackSize();
            AABB attackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, attackSize.x(), attackSize.y(), attackSize.z());
            EntityHelper.doAreaOfEffectAttack(attacker, attackBounds, this.getAttackDamage());
        }

        default boolean isTargetCloseEnoughToStart(T attacker, LivingEntity target) {
            Vec3 startAttackSize = this.getAttackSize().multiply(1, 1, 0.5D);
            AABB startAttackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, startAttackSize.x(), startAttackSize.y(), startAttackSize.z());
            return startAttackBounds.intersects(target.getHitbox());
        }

        boolean hasAttackPointAt(int attackTicker);

        default boolean blocksMovementInput(){
            return true;
        }

        default boolean blocksWalkAnimation(){
            return this.blocksMovementInput();
        }

        default boolean blocksRotationInput(){
            return true;
        }

        default boolean blocksBodyRotation() {
            return this.blocksRotationInput();
        }

        default boolean blocksHeadRotation() {
            return this.blocksRotationInput();
        }

        default boolean blocksLookAnimation(){
            return this.blocksHeadRotation();
        }
    }

    class AttackTicker<T extends Mob & AnimatedAttacker<T, A>, A extends AnimatedAttacker.AttackType<T, A>>{
        private final T attacker;
        private int attackTicker;

        public AttackTicker(T attacker) {
            this.attacker = attacker;
        }

        public void reset(){
            this.attackTicker = 0;
        }

        public void tick(){
            A activeAttackType = attacker.getActiveAttackType();
            if(activeAttackType != null){
                if(activeAttackType.hasAttackPointAt(this.attackTicker)){
                    activeAttackType.executeAttackPoint(this.attacker);
                }
                if(!this.attacker.level().isClientSide && this.attackTicker >= activeAttackType.getAttackDuration()) {
                    this.attacker.setActiveAttackType(null);
                }
            }
            activeAttackType = this.attacker.getActiveAttackType();
            if(activeAttackType != null && this.attackTicker < activeAttackType.getAttackDuration()){
                this.attackTicker++;
            }
        }
    }

}
