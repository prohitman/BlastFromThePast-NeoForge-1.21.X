package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
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

        //Blocks
        addBlock(ModBlocks.CEDAR_DOOR);
        addBlock(ModBlocks.CEDAR_LOG);
        addBlock(ModBlocks.SAPPY_CEDAR_LOG);
        addBlock(ModBlocks.STRIPPED_CEDAR_LOG);
        addBlock(ModBlocks.CEDAR_LEAVES);
        addBlock(ModBlocks.CEDAR_PLANKS);
        addBlock(ModBlocks.BEAST_CHOPS);

        //Entities
        addEntity(ModEntities.GLACEROS);
        addEntity(ModEntities.SNOWDO);

        add("itemGroup." + BlastFromThePast.MODID, "Blast From The Past");
    }

    private void addBlock(DeferredBlock<? extends Block> key) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    private void addItem(DeferredItem<? extends Item> key) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

    private <T extends Entity> void addEntity(DeferredHolder<EntityType<?>, EntityType<T>> key) {
        add(key.get().getDescriptionId(), StringUtils.capitaliseAllWords(key.getId().getPath().replaceAll("_", " ")));
    }

}
