package com.clonz.blastfromthepast.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class HitboxHelper {

    //https://gist.github.com/dGr8LookinSparky/bd64a9f5f9deecf61e2c3c1592169c00
    public static double getDistSqrBetweenHitboxes(AABB first, AABB second){
        double[] firstMins = new double[]{first.minX, first.minY, first.minZ};
        double[] secondMins = new double[]{second.minX, second.minY, second.minZ};
        double[] firstMaxs = new double[]{first.maxX, first.maxY, first.maxZ};
        double[] secondMaxs = new double[]{second.maxX, second.maxY, second.maxZ};
        double distSqr = 0;
        for(int i = 0; i < 3; i++){
            if(secondMaxs[i] < firstMins[i]) {
                double dist = secondMaxs[i] - firstMins[i];
                distSqr += Mth.square(dist);
            } else if(secondMins[i] > firstMaxs[i]) {
                double dist = secondMins[i] - firstMaxs[i];
                distSqr += Mth.square(dist);
            }
        }
        return distSqr;
    }

    public static double getDistSqrBetweenHitboxes(Entity first, Entity second){
        return getDistSqrBetweenHitboxes(first.getBoundingBox(), second.getBoundingBox());
    }

    public static boolean isCloseEnoughForTargeting(LivingEntity attacker, LivingEntity target, boolean testInvisible, double range) {
        double visibilityPercent = testInvisible ? target.getVisibilityPercent(attacker) : 1.0;
        double maxDistance = Math.max(range * visibilityPercent, 2.0);
        double distanceToSqr = getDistSqrBetweenHitboxes(attacker, target);
        return !(distanceToSqr > Mth.square(maxDistance));
    }
}
