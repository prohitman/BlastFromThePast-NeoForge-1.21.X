package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.clonz.blastfromthepast.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModItemModelGen extends ItemModelProvider {

    public ModItemModelGen(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BlastFromThePast.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        //Items
        basicItem(ModItems.RAW_VENISON.get());
        basicItem(ModItems.SAP_BALL.get());
        basicItem(ModItems.COOKED_VENISON.get());
        withExistingParent(ModItems.SNOWDO_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GLACEROS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.FROSTOMPER_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        basicItem(ModItems.CEDAR_BOAT.get());
        basicItem(ModItems.CEDAR_CHEST_BOAT.get());

        //Blocks
        createWithParent(ModBlocks.CEDAR_LEAVES);
        createWithParent(ModBlocks.CEDAR_PLANKS);
        createWithParent(ModBlocks.CEDAR_LOG);
        createWithParent(ModBlocks.STRIPPED_CEDAR_LOG);
        createWithParent(ModBlocks.SAPPY_CEDAR_LOG);
        createWithParent(ModBlocks.CEDAR_LEAVES);
        singleTextureBlock(ModBlocks.CEDAR_DOOR);
    }

    private void createWithParent(DeferredBlock<? extends Block> key) {
        withExistingParent(key.getId().getPath(), modLoc( "block/" + key.getId().getPath()));
    }

    private void singleTextureBlock(DeferredBlock<? extends Block> key){
        singleTexture(key.getId().getPath(), mcLoc("item/generated"), "layer0",
                modLoc("item/" + key.getId().getPath())).renderType("cutout_mipped");
    }
}


