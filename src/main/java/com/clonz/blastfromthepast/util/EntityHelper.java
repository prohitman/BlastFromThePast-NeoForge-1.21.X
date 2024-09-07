package com.clonz.blastfromthepast.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.OwnableEntity;

public class EntityHelper {
    public static boolean haveSameOwners(Entity entity1, Entity entity2) {
        if(entity1 instanceof OwnableEntity ownable1 && entity2 instanceof OwnableEntity ownable2){
            return ownable1.getOwner() == ownable2.getOwner();
        }
        return false;
    }
}
