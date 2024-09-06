package com.clonz.blastfromthepast.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class HitboxHelper {

    //https://gist.github.com/dGr8LookinSparky/bd64a9f5f9deecf61e2c3c1592169c00
    public static double getDistSqrBetweenHitboxes(AABB first, AABB second){
        double distSqr = 0;
        distSqr += Mth.square(getDistBetweenCorners(first.minX, second.minX, first.maxX, second.maxX));
        distSqr += Mth.square(getDistBetweenCorners(first.minY, second.minY, first.maxY, second.maxY));
        distSqr += Mth.square(getDistBetweenCorners(first.minZ, second.minZ, first.maxZ, second.maxZ));
        return distSqr;
    }

    private static double getDistBetweenCorners(double min1, double min2, double max1, double max2) {
        if(max2 < min1) {
            return max2 - min1;
        } else if(min2 > max1) {
            return min2 - max1;
        }
        return 0;
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
