package com.clonz.blastfromthepast.datagen.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BFTPStoneGroup;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
import com.clonz.blastfromthepast.block.FemurBlock;
import com.clonz.blastfromthepast.block.PsychoBerryBush;
import com.clonz.blastfromthepast.block.signs.SnowyStoneBlock;
import com.clonz.blastfromthepast.init.ModBlocks;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
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
        registerStoneGroup(ModBlocks.PERMAFROST);
        createDoublePlantBlock(ModBlocks.WHITE_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.BLUE_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.PINK_DELPHINIUM);
        createDoublePlantBlock(ModBlocks.VIOLET_DELPHINIUM);
        createFemurBlock();
        createPsychoBerryBush();
        createSinglePlant(ModBlocks.PSYCHO_BERRY_SPROUT);
    }

    private void createFemurBlock(){
        BlockModelBuilder connectedFemur = models().withExistingParent("femur_connected", modLoc("block/tipless_beastly_femur"));
        BlockModelBuilder femur = models().withExistingParent("femur", modLoc("block/beastly_femur"));

        getVariantBuilder(ModBlocks.BEASTLY_FEMUR.get()).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(FemurBlock.CONNECTED) ? connectedFemur : femur)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot()) % 360)
                        .build());
    }

    private void createPsychoBerryBush(){
        BlockModelBuilder grownBush = models().withExistingParent("grown_psycho_berry_bush", modLoc("block/thorns")).texture("2", "block/thorn_berries");
        BlockModelBuilder bush = models().withExistingParent("psycho_berry_bush", modLoc("block/thorns")).texture("2", "block/thorns");

        getVariantBuilder(ModBlocks.PSYCHO_BERRY_BUSH.get()).forAllStatesExcept(
                state -> ConfiguredModel.builder()
                        .modelFile(state.getValue(PsychoBerryBush.AGE) == 1 ? grownBush : bush)
                        .build());
    }

    private void registerStoneGroup(BFTPStoneGroup stoneGroup){
        createSnowyBlock(stoneGroup.BLOCK,
                models().cubeAll(this.name(stoneGroup.BLOCK.get()),
                        this.blockTexture(stoneGroup.BLOCK.get())),
                models().cubeBottomTop("snowy_" + this.name(stoneGroup.BLOCK.get()),
                        modLoc("block/" + "snowy_" + stoneGroup.BLOCK.getId().getPath()),
                        this.blockTexture(stoneGroup.BLOCK.get()),
                        this.blockTexture(Blocks.SNOW)));
        stairsBlock(stoneGroup.STAIRS.get(), this.blockTexture(stoneGroup.BLOCK.get()));
        slabBlock(stoneGroup.SLAB.get(), this.blockTexture(stoneGroup.BLOCK.get()), this.blockTexture(stoneGroup.BLOCK.get()));
        wallBlock(stoneGroup.WALL.get(), this.blockTexture(stoneGroup.BLOCK.get()));
        simpleBlock(stoneGroup.BRICKS.get());
        stairsBlock(stoneGroup.BRICKS_STAIRS.get(), this.blockTexture(stoneGroup.BRICKS.get()));
        slabBlock(stoneGroup.BRICKS_SLAB.get(), this.blockTexture(stoneGroup.BRICKS.get()), this.blockTexture(stoneGroup.BRICKS.get()));
        wallBlock(stoneGroup.BRICKS_WALL.get(), this.blockTexture(stoneGroup.BRICKS.get()));
        simpleBlock(stoneGroup.COBBLESTONE.get());
        stairsBlock(stoneGroup.COBBLESTONE_STAIRS.get(), this.blockTexture(stoneGroup.COBBLESTONE.get()));
        slabBlock(stoneGroup.COBBLESTONE_SLAB.get(), this.blockTexture(stoneGroup.COBBLESTONE.get()), this.blockTexture(stoneGroup.COBBLESTONE.get()));
        wallBlock(stoneGroup.COBBLESTONE_WALL.get(), this.blockTexture(stoneGroup.COBBLESTONE.get()));
        simpleBlock(stoneGroup.POLISHED.get());
        stairsBlock(stoneGroup.POLISHED_STAIRS.get(), this.blockTexture(stoneGroup.POLISHED.get()));
        slabBlock(stoneGroup.POLISHED_SLAB.get(), this.blockTexture(stoneGroup.POLISHED.get()), this.blockTexture(stoneGroup.POLISHED.get()));
        wallBlock(stoneGroup.POLISHED_WALL.get(), this.blockTexture(stoneGroup.POLISHED.get()));
        simpleBlock(stoneGroup.CHISELED_BRICKS.get());
    }

    private void registerWoodGroup(BFTPWoodGroup group){
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

    private void createSinglePlant(DeferredBlock<Block> block){
        createSinglePlant(block, models().cross(block.getId().getPath(),
                modLoc("block/" + block.getId().getPath())).renderType("cutout_mipped"));
    }

    private void createSinglePlant(DeferredBlock<Block> block, ModelFile model) {
        getVariantBuilder(block.get()).forAllStatesExcept(state -> ConfiguredModel.builder()
                .modelFile(model)
                .uvLock(true)
                .build());
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

    private void createSnowyBlock(DeferredBlock<Block> block, ModelFile stone, ModelFile snowy){
        getVariantBuilder(block.get()).forAllStatesExcept(state -> {
            ModelFile model = stone;
            if(state.getValue(SnowyStoneBlock.SNOWY)){
                model = snowy;
            }
            return ConfiguredModel.builder()
                    .modelFile(model)
                    .uvLock(true)
                    .build();
        });
    }

    private ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}