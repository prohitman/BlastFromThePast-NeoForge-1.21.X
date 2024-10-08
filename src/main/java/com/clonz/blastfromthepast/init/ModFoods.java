package com.clonz.blastfromthepast.init;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Items;

public class ModFoods {
    public static final FoodProperties RAW_VENSION = new FoodProperties.Builder().nutrition(3)
            .saturationModifier(1.8f).build();

    public static final FoodProperties COOKED_VENSION = new FoodProperties.Builder().nutrition(8)
            .saturationModifier(12.8f).build();

    public static final FoodProperties PSYCHO_BERRY = new FoodProperties.Builder()
            .nutrition(4)
            .saturationModifier(calculateSaturationModifier(4, 1F))
            .build();

    public static final FoodProperties BOWL_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(5)
            .saturationModifier(calculateSaturationModifier(5, 1.5F))
            .usingConvertsTo(Items.BOWL)
            .build();

    // Reverse of FoodConstants.saturationByModifier
    private static float calculateSaturationModifier(int nutrition, float targetSaturation){
        return targetSaturation / 2.0F / nutrition;
    }

}
