package com.clonz.blastfromthepast.util;

import com.clonz.blastfromthepast.entity.AnimatedAttacker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class EntityHelper {

    public static void spawnSmashAttackParticles(LevelAccessor level, AABB attackBounds, int power) {
        Vec3 boundsBottomCenter = attackBounds.getBottomCenter();
        BlockPos pos = BlockPos.containing(boundsBottomCenter.subtract(0, 1.0E-5F, 0));
        Vec3 particleCenter = boundsBottomCenter.add(0.0, 0.5, 0.0);
        BlockParticleOption dustPillar = new BlockParticleOption(ParticleTypes.DUST_PILLAR, level.getBlockState(pos));

        double zSpeed;
        int index;
        double x;
        double y;
        double z;
        double xSpeed;
        double ySpeed;
        for(index = 0; (float)index < (float)power / 3.0F; ++index) {
            x = particleCenter.x + level.getRandom().nextGaussian() / 2.0;
            y = particleCenter.y;
            z = particleCenter.z + level.getRandom().nextGaussian() / 2.0;
            xSpeed = level.getRandom().nextGaussian() * 0.2;
            ySpeed = level.getRandom().nextGaussian() * 0.2;
            zSpeed = level.getRandom().nextGaussian() * 0.2;
            level.addParticle(dustPillar, x, y, z, xSpeed, ySpeed, zSpeed);
        }

        for(index = 0; (float)index < (float)power / 1.5F; ++index) {
            x = particleCenter.x + attackBounds.getXsize() * Math.cos(index) + level.getRandom().nextGaussian() / 2.0;
            y = particleCenter.y;
            z = particleCenter.z + attackBounds.getZsize() * Math.sin(index) + level.getRandom().nextGaussian() / 2.0;
            xSpeed = level.getRandom().nextGaussian() * 0.05;
            ySpeed = level.getRandom().nextGaussian() * 0.05;
            zSpeed = level.getRandom().nextGaussian() * 0.05;
            level.addParticle(dustPillar, x, y, z, xSpeed, ySpeed, zSpeed);
        }

    }

    public static <T extends Mob & AnimatedAttacker<T, A>, A extends AnimatedAttacker.AttackType<T, A>> List<LivingEntity> hitTargetsWithAOEAttack(T attacker, AABB attackBounds, float attackDamage, float attackKnockback, boolean spawnParticles) {
        List<LivingEntity> hitTargets = new ArrayList<>();
        if(!attacker.level().isClientSide){
            List<LivingEntity> targets = attacker.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, attacker, attackBounds);
            targets.forEach(target -> {
                if (target.invulnerableTime <= 0) {
                    boolean hurtTarget = doHurtTarget(attacker, target, attackDamage, attackKnockback);
                    if(hurtTarget){
                        hitTargets.add(target);
                    }
                }
            });
        } else if(spawnParticles){
            spawnSmashAttackParticles(attacker.level(), attackBounds, 750);
        }
        return hitTargets;
    }

    // A copy of Mob#doHurtTarget, but with the ability to specify the attack damage and knockback values
    public static boolean doHurtTarget(LivingEntity attacker, LivingEntity target, float attackDamage, float attackKnockback){
        DamageSource damagesource = attacker.damageSources().mobAttack(attacker);
        if (attacker.level() instanceof ServerLevel serverlevel) {
            attackDamage = EnchantmentHelper.modifyDamage(serverlevel, attacker.getWeaponItem(), target, damagesource, attackDamage);
        }

        boolean hurt = target.hurt(damagesource, attackDamage);
        if (hurt) {
            if (attacker.level() instanceof ServerLevel serverlevel) {
                attackKnockback = EnchantmentHelper.modifyKnockback(serverlevel, attacker.getWeaponItem(), attacker, damagesource, attackKnockback);
            }
            if (attackKnockback > 0.0F) {
                target.knockback(
                        attackKnockback * 0.5F,
                        Mth.sin(attacker.getYRot() * Mth.DEG_TO_RAD),
                        -Mth.cos(attacker.getYRot() * Mth.DEG_TO_RAD)
                );
                attacker.setDeltaMovement(attacker.getDeltaMovement().multiply(0.6, 1.0, 0.6));
            }

            if (attacker.level() instanceof ServerLevel serverlevel) {
                EnchantmentHelper.doPostAttackEffects(serverlevel, target, damagesource);
            }

            attacker.setLastHurtMob(target);
        }

        return hurt;
    }

    public static void throwTarget(LivingEntity attacker, LivingEntity target, double attackKnockback) {
        double knockbackResistance = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double effectiveKnockback = attackKnockback - knockbackResistance;
        if (effectiveKnockback > 0) {
            double knockbackScale = effectiveKnockback * 0.5F;
            Vec3 knockbackVec = attacker.getViewVector(1.0F).normalize().scale(knockbackScale);
            target.push(knockbackVec.x, knockbackScale, knockbackVec.z);
            target.hurtMarked = true;
        }
    }

    public static void strongKnockback(LivingEntity attacker, LivingEntity target, double attackKnockback) {
        double knockbackResistance = target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double effectiveKnockback = attackKnockback - knockbackResistance;
        if (effectiveKnockback > 0) {
            Vec3 knockbackVec = attacker.getViewVector(1.0F).normalize().scale(effectiveKnockback);
            target.push(knockbackVec.x, target.onGround() ? Math.min(0.4, effectiveKnockback) : 0, knockbackVec.z);
            target.hurtMarked = true;
        }
    }
}
