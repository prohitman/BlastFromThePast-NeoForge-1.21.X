package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateGen extends BlockStateProvider {
    public ModBlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BlastFromThePast.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.CEDAR_PLANKS.get());
        simpleBlock(ModBlocks.CEDAR_LEAVES.get());

        logBlock((RotatedPillarBlock) ModBlocks.CEDAR_LOG.get());
        logBlock((RotatedPillarBlock) ModBlocks.SAPPY_CEDAR_LOG.get());
        logBlock((RotatedPillarBlock) ModBlocks.STRIPPED_CEDAR_LOG.get());

        doorBlockWithRenderType((DoorBlock) ModBlocks.CEDAR_DOOR.get(), modLoc("block/cedar_door_bottom"), modLoc("block/cedar_door_top"), "cutout_mipped");
    }
}
