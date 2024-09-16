package com.clonz.blastfromthepast.datagen.server;

import com.clonz.blastfromthepast.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class ModRecipesGen extends RecipeProvider {
    public ModRecipesGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        createIceCream(recipeOutput, ModItems.SAP_ICE_CREAM, ModItems.SAP_BALL);
        createIceCream(recipeOutput, ModItems.PSYCHO_BERRY_ICE_CREAM, ModItems.PSYCHO_BERRY);
        createIceCream(recipeOutput, ModItems.MELON_ICE_CREAM, Items.MELON_SLICE);

    }

    private static void createIceCream(RecipeOutput recipeOutput, ItemLike iceCream, ItemLike mainIngredient) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, iceCream)
                .requires(Items.BOWL)
                .requires(Blocks.ICE)
                .requires(Items.MILK_BUCKET)
                .requires(mainIngredient)
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(iceCream.asItem()).getPath(), has(iceCream))
                .unlockedBy("has_bowl", has(Items.BOWL))
                .unlockedBy("has_ice", has(Blocks.ICE))
                .unlockedBy("has_milk_bucket", has(Items.MILK_BUCKET))
                .unlockedBy("has_" + BuiltInRegistries.ITEM.getKey(mainIngredient.asItem()).getPath(), has(mainIngredient))
                .save(recipeOutput);
    }
}
