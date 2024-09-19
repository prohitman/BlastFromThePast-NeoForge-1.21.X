package com.clonz.blastfromthepast.entity.misc;

import net.minecraft.world.item.ItemStack;

public interface FoodRaider {

    boolean wantsMoreFood();

    void gotFood(int ticks);

    boolean isWantedFood(ItemStack stack);

    void takeFood(ItemStack stack);
}
