package com.clonz.blastfromthepast.init;

import net.minecraft.world.food.FoodProperties;

public class ModFoods {
    public static final FoodProperties RAW_VENSION = new FoodProperties.Builder().nutrition(3)
            .saturationModifier(1.8f).build();

    public static final FoodProperties COOKED_VENSION = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(12.8f).build();

}
