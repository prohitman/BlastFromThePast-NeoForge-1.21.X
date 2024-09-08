package com.clonz.blastfromthepast.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EntityHelper {
    public static boolean haveSameOwners(Entity entity1, Entity entity2) {
        if(entity1 instanceof OwnableEntity ownable1 && entity2 instanceof OwnableEntity ownable2){
            return ownable1.getOwner() == ownable2.getOwner();
        }
        return false;
    }

    public static void doAreaOfEffectAttack(LivingEntity mob, AABB attackBounds, float damage){
        if(!mob.level().isClientSide){
            List<LivingEntity> targets = mob.level().getNearbyEntities(LivingEntity.class, TargetingConditions.DEFAULT, mob, attackBounds);
            AttributeInstance attackDamage = mob.getAttribute(Attributes.ATTACK_DAMAGE);
            if(attackDamage != null){
                double originalAttackDamage = attackDamage.getBaseValue();
                attackDamage.setBaseValue(damage);
                targets.forEach(mob::doHurtTarget);
                attackDamage.setBaseValue(originalAttackDamage);
            }
        } else{
            spawnAreaOfEffectParticles(mob.level(), attackBounds, 750);
        }
    }

    public static void spawnAreaOfEffectParticles(LevelAccessor level, AABB attackBounds, int power) {
        Vec3 boundsBottomCenter = attackBounds.getBottomCenter();
        BlockPos pos = BlockPos.containing(boundsBottomCenter);
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
}
