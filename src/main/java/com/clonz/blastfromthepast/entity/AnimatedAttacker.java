package com.clonz.blastfromthepast.entity;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.util.DebugFlags;
import com.clonz.blastfromthepast.util.EntityHelper;
import com.clonz.blastfromthepast.util.HitboxHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

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

        default void executeAttackPoint(T attacker, int attackTicker){
            executeSimpleAreaOfEffectAttack(attacker, this.getAttackSize(), this.getAttackDamage(), true);
        }

        static <T extends Mob & AnimatedAttacker<T, A>, A extends AttackType<T, A>> void executeSimpleAreaOfEffectAttack(T attacker, Vec3 attackSize, float attackDamage, boolean spawnParticles) {
            AABB attackBounds = HitboxHelper.createHitboxRelativeToFront(attacker, attackSize.x(), attackSize.y(), attackSize.z());
            if(!attacker.level().isClientSide){
                List<LivingEntity> targets = attacker.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, attacker, attackBounds);
                AttributeInstance attackDamageAttribute = attacker.getAttribute(Attributes.ATTACK_DAMAGE);
                if(attackDamageAttribute != null){
                    double originalAttackDamage = attackDamageAttribute.getBaseValue();
                    attackDamageAttribute.setBaseValue(attackDamage);
                    targets.forEach(target -> {
                        if(target.invulnerableTime <= 0){
                            attacker.doHurtTarget(target);
                        }
                    });
                    attackDamageAttribute.setBaseValue(originalAttackDamage);
                }
            } else if(spawnParticles){
                EntityHelper.spawnSmashAttackParticles(attacker.level(), attackBounds, 750);
            }
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

        default boolean isFinished(T attacker, int attackTicker) {
            return attackTicker >= this.getAttackDuration();
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
            A activeAttackType = this.attacker.getActiveAttackType();
            if(activeAttackType != null){
                if(activeAttackType.hasAttackPointAt(this.attackTicker)){
                    activeAttackType.executeAttackPoint(this.attacker, this.attackTicker);
                }
                if(!this.attacker.level().isClientSide && activeAttackType.isFinished(this.attacker, this.attackTicker)) {
                    if(DebugFlags.DEBUG_ANIMATED_ATTACK)
                        BlastFromThePast.LOGGER.info("Finished attack {} for {}", activeAttackType, this.attacker);
                    this.attacker.setActiveAttackType(null);
                }
            }
            activeAttackType = this.attacker.getActiveAttackType();
            if(activeAttackType != null && !activeAttackType.isFinished(this.attacker, this.attackTicker)){
                this.attackTicker++;
            }
        }
    }

}
