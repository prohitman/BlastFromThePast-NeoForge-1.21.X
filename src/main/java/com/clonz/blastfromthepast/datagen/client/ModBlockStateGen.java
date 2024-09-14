package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
import com.clonz.blastfromthepast.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.ModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateGen extends BlockStateProvider {
    public ModBlockStateGen(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BlastFromThePast.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        logBlock(ModBlocks.SAPPY_CEDAR_LOG.get());
        registerWoodGroup(ModBlocks.CEDAR);
        createDoublePlantBlock(ModBlocks.WHITE_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.BLUE_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.PINK_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.VIOLET_DELPHINIUM);
    }

    public void registerWoodGroup(BFTPWoodGroup group){
        simpleBlock(group.BLOCK.get());
        simpleBlock(group.LEAVES.get());
        logBlock(group.LOG.get());
        logBlock(group.STRIPPED_LOG.get());
        axisBlock(group.WOOD.get(), this.blockTexture(group.LOG.get()), this.blockTexture(group.LOG.get()));
        axisBlock(group.STRIPPED_WOOD.get(), this.blockTexture(group.STRIPPED_LOG.get()), this.blockTexture(group.STRIPPED_LOG.get()));
        slabBlock(group.SLAB.get(), this.blockTexture(group.BLOCK.get()), this.blockTexture(group.BLOCK.get()));
        stairsBlock(group.STAIRS.get(), this.blockTexture(group.BLOCK.get()));
        models().fenceInventory(name(group.FENCE.get()) + "_inventory", this.blockTexture(group.BLOCK.get()));
        fenceBlock(group.FENCE.get(), this.blockTexture(group.BLOCK.get()));
        fenceGateBlock(group.FENCE_GATE.get(), this.blockTexture(group.BLOCK.get()));
        doorBlockWithRenderType(group.DOOR.get(), this.blockTexture(group.DOOR.get()).withSuffix("_bottom"), this.blockTexture(group.DOOR.get()).withSuffix("_top"), "cutout_mipped");
        trapdoorBlockWithRenderType(group.TRAPDOOR.get(), this.blockTexture(group.TRAPDOOR.get()), true, "cutout_mipped");
        pressurePlateBlock(group.PRESSURE_PLATE.get(), this.blockTexture(group.BLOCK.get()));
        models().buttonInventory(name(group.BUTTON.get()) + "_inventory", this.blockTexture(group.BLOCK.get()));
        buttonBlock(group.BUTTON.get(), this.blockTexture(group.BLOCK.get()));
        signBlock(group.SIGN.get(), group.WALL_SIGN.get(), this.blockTexture(group.BLOCK.get()));
        hangingSign(group.HANGING_SIGN, group.HANGING_SIGN_WALL, this.blockTexture(group.STRIPPED_LOG.get()));
    }

    private void hangingSign(DeferredBlock<CeilingHangingSignBlock> hangingSignBlock, DeferredBlock<WallHangingSignBlock> wallHangingSignBlock, ResourceLocation texture){
        ModelFile sign = models().sign(hangingSignBlock.getId().getPath(), texture);
        hangingSign(hangingSignBlock, wallHangingSignBlock, sign);
    }

    private void hangingSign(DeferredBlock<CeilingHangingSignBlock> hangingSignBlock, DeferredBlock<WallHangingSignBlock> wallHangingSignBlock, ModelFile sign) {
        simpleBlock(hangingSignBlock.get(), sign);
        simpleBlock(wallHangingSignBlock.get(), sign);
    }

    private String name(net.minecraft.world.level.block.Block block) {
        return BuiltInRegistries.BLOCK.getKey(block).getPath();
    }

    private void createDoublePlantBlock(DeferredBlock<Block> block) {
        createDoublePlantBlock(block,
                models().cross(block.getId().getPath() + "_top",
                        modLoc("block/" + block.getId().getPath() + "_top")).renderType("cutout_mipped"),
                models().cross(block.getId().getPath() + "_bottom",
                        modLoc("block/" + block.getId().getPath() + "_bottom")).renderType("cutout_mipped"));
    }

    private void createDoublePlantBlock(DeferredBlock<Block> block, ModelFile upper, ModelFile bottom) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            ModelFile model = bottom;
            if (state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER) {
                model = upper;
            }
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .uvLock(true)
                    .build();
        });
    }
}
