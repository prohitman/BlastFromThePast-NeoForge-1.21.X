package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
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
        registerWoodGroup(ModBlocks.CEDAR);
        createWithParent(ModBlocks.SAPPY_CEDAR_LOG);
        singleTextureDoublePlantBlock(ModBlocks.WHITE_DELPHINIUM, true);
        singleTextureDoublePlantBlock(ModBlocks.BLUE_DELPHINIUM, true);
        singleTextureDoublePlantBlock(ModBlocks.VIOLET_DELPHINIUM, true);
        singleTextureDoublePlantBlock(ModBlocks.PINK_DELPHINIUM, true);
    }

    private void registerWoodGroup(BFTPWoodGroup woodGroup){
        createWithParent(woodGroup.BLOCK);
        createWithParent(woodGroup.LOG);
        createWithParent(woodGroup.STRIPPED_LOG);
        createWithParent(woodGroup.WOOD);
        createWithParent(woodGroup.STRIPPED_WOOD);
        createWithParent(woodGroup.SLAB);
        createWithParent(woodGroup.STAIRS);
        createWithParent(woodGroup.LEAVES);
        createSuffixedParent(woodGroup.FENCE, "_inventory");
        createWithParent(woodGroup.FENCE_GATE);
        createWithParent(woodGroup.PRESSURE_PLATE);
        createSuffixedParent(woodGroup.BUTTON, "_inventory");
        withExistingParent(woodGroup.TRAPDOOR.getId().getPath(),
                modLoc("block/" + woodGroup.BLOCK.getId().getPath() + "_trapdoor_bottom"));
        singleTextureBlock(woodGroup.DOOR);
        basicItem(woodGroup.SIGN_ITEM.get());
        basicItem(woodGroup.HANGING_SIGN_ITEM.get());
    }

    private void createWithParent(DeferredBlock<? extends Block> key) {
        withExistingParent(key.getId().getPath(), modLoc( "block/" + key.getId().getPath()));
    }

    private void singleTextureBlock(DeferredBlock<? extends Block> key){
        singleTexture(key.getId().getPath(), mcLoc("item/generated"), "layer0",
                modLoc("item/" + key.getId().getPath())).renderType("cutout_mipped");
    }

    private void createSuffixedParent(DeferredBlock<? extends Block> handler, String suffix) {
        withExistingParent(handler.getId().getPath(), modLoc( "block/" + handler.getId().getPath() + suffix));
    }

    private void singleTextureDoublePlantBlock(DeferredBlock<Block> block, boolean isTop){
        singleTexture((block.getId().getPath()),
                mcLoc("item/generated"),
                "layer0", modLoc("block/" + block.getId().getPath() + (isTop ? "_top" : "_bottom")));
    }
}


