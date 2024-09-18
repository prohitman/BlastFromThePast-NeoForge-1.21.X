package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BFTPStoneGroup;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.init.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import org.codehaus.plexus.util.StringUtils;

public class ModLangGen extends LanguageProvider {

    public ModLangGen(PackOutput output) {
        super(output, BlastFromThePast.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //Items
        addItem(ModItems.SAP_BALL);
        addItem(ModItems.RAW_VENISON);
        addItem(ModItems.COOKED_VENISON);
        addItem(ModItems.GLACEROS_SPAWN_EGG);
        addItem(ModItems.SNOWDO_SPAWN_EGG);
        addItem(ModItems.FROSTOMPER_SPAWN_EGG);
        addItem(ModItems.PSYCHO_BEAR_SPAWN_EGG);
        addItem(ModItems.CEDAR_BOAT);
        addItem(ModItems.CEDAR_CHEST_BOAT);


        addItem(ModItems.PSYCHO_BERRY);
        addItem(ModItems.SAP_ICE_CREAM);
        addItem(ModItems.PSYCHO_BERRY_ICE_CREAM);
        addItem(ModItems.MELON_ICE_CREAM);

        addItem(ModItems.BEAR_CLAW);

        //Blocks
        addBlock(ModBlocks.SAPPY_CEDAR_LOG);
        registerWoodGroup(ModBlocks.CEDAR);
        addBlock(ModBlocks.WHITE_DELPHINIUM);
        addBlock(ModBlocks.PINK_DELPHINIUM);
        addBlock(ModBlocks.VIOLET_DELPHINIUM);
        addBlock(ModBlocks.BLUE_DELPHINIUM);
        registerStoneGroup(ModBlocks.PERMAFROST);

        //Entities
        addEntity(ModEntities.GLACEROS);
        addEntity(ModEntities.SNOWDO);
        addEntity(ModEntities.FROSTOMPER);
        addEntity(ModEntities.PSYCHO_BEAR);

        add("itemGroup." + BlastFromThePast.MODID, "Blast From The Past");
    }

    private void addBlock(DeferredBlock<? extends Block> key) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    private void addBlockWithSuffix(DeferredBlock<? extends Block> key, String suffix) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")).concat(suffix));
    }

    private void addItem(DeferredItem<? extends Item> key){
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    private <T extends Entity> void addEntity(DeferredHolder<EntityType<?>, EntityType<T>> key){
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    private void registerStoneGroup(BFTPStoneGroup stoneGroup){
        addBlock(stoneGroup.BLOCK);
        addBlock(stoneGroup.COBBLESTONE);
        addBlock(stoneGroup.POLISHED);
        addBlock(stoneGroup.CHISELED_BRICKS);
        addBlock(stoneGroup.BRICKS);
        addBlock(stoneGroup.WALL);
        addBlock(stoneGroup.STAIRS);
        addBlock(stoneGroup.SLAB);
        addBlock(stoneGroup.POLISHED_SLAB);
        addBlock(stoneGroup.POLISHED_STAIRS);
        addBlock(stoneGroup.POLISHED_WALL);
        addBlock(stoneGroup.BRICKS_SLAB);
        addBlock(stoneGroup.BRICKS_STAIRS);
        addBlock(stoneGroup.BRICKS_WALL);
        addBlock(stoneGroup.COBBLESTONE_SLAB);
        addBlock(stoneGroup.COBBLESTONE_STAIRS);
        addBlock(stoneGroup.COBBLESTONE_WALL);
    }

    private void registerWoodGroup(BFTPWoodGroup woodGroup){
        addBlockWithSuffix(woodGroup.BLOCK, " Planks");
        addBlock(woodGroup.LOG);
        addBlock(woodGroup.STRIPPED_LOG);
        addBlock(woodGroup.WOOD);
        addBlock(woodGroup.STRIPPED_WOOD);
        addBlock(woodGroup.SLAB);
        addBlock(woodGroup.STAIRS);
        addBlock(woodGroup.LEAVES);
        addBlock(woodGroup.FENCE);
        addBlock(woodGroup.FENCE_GATE);
        addBlock(woodGroup.PRESSURE_PLATE);
        addBlock(woodGroup.BUTTON);
        addBlock(woodGroup.TRAPDOOR);
        addBlock(woodGroup.DOOR);
        addBlock(woodGroup.SIGN);
        addBlock(woodGroup.HANGING_SIGN);
    }

}
