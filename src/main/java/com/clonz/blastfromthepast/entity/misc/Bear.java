package com.clonz.blastfromthepast.entity.misc;

import net.minecraft.world.item.ItemStack;

public interface Bear {
    void tryToSit();

    boolean isSitting();

    void sit(boolean sitting);

    boolean canPerformAction();

    boolean isEating();

    void eat(boolean eating);

    ItemStack getItemInMouth();

    void putItemInMouth(ItemStack item, boolean guaranteeDrop);
}
