package com.clonz.blastfromthepast.block;

import com.clonz.blastfromthepast.init.ModBlocks;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class BFTPStoneGroup {
    public final DeferredBlock<Block> BLOCK;
    public final DeferredBlock<StairBlock> STAIRS;
    public final DeferredBlock<SlabBlock> SLAB;
    public final DeferredBlock<WallBlock> WALL;
    public final DeferredBlock<Block> BRICKS;
    public final DeferredBlock<StairBlock> BRICKS_STAIRS;
    public final DeferredBlock<SlabBlock> BRICKS_SLAB;
    public final DeferredBlock<WallBlock> BRICKS_WALL;
    public final DeferredBlock<Block> COBBLESTONE;
    public final DeferredBlock<StairBlock> COBBLESTONE_STAIRS;
    public final DeferredBlock<SlabBlock> COBBLESTONE_SLAB;
    public final DeferredBlock<WallBlock> COBBLESTONE_WALL;
    public final DeferredBlock<Block> CHISELED_BRICKS;
    public final DeferredBlock<Block> POLISHED;
    public final DeferredBlock<StairBlock> POLISHED_STAIRS;
    public final DeferredBlock<SlabBlock> POLISHED_SLAB;
    public final DeferredBlock<WallBlock> POLISHED_WALL;


    public final List<DeferredBlock<?>> blocks;


    public BFTPStoneGroup(String name, MapColor mapColor, Item.Properties empty){
        BLOCK = ModBlocks.createRegistry(name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(mapColor)), empty);

        STAIRS = ModBlocks.createRegistry(name + "_stairs", () -> new StairBlock(BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BLOCK.get()).mapColor(mapColor)), empty);
        SLAB = ModBlocks.createRegistry(name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BLOCK.get()).mapColor(mapColor)), empty);
        WALL = ModBlocks.createRegistry(name + "_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BLOCK.get()).mapColor(mapColor)), empty);

        BRICKS = ModBlocks.createRegistry(name + "_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(mapColor)), empty);

        BRICKS_STAIRS = ModBlocks.createRegistry(name + "_bricks_stairs", () -> new StairBlock(BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BRICKS.get()).mapColor(mapColor)), empty);
        BRICKS_SLAB = ModBlocks.createRegistry(name + "_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BRICKS.get()).mapColor(mapColor)), empty);
        BRICKS_WALL = ModBlocks.createRegistry(name + "_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BRICKS.get()).mapColor(mapColor)), empty);

        COBBLESTONE = ModBlocks.createRegistry(name + "_cobblestone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(mapColor)), empty);

        COBBLESTONE_STAIRS = ModBlocks.createRegistry(name + "_cobblestone_stairs", () -> new StairBlock(COBBLESTONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(COBBLESTONE.get()).mapColor(mapColor)), empty);
        COBBLESTONE_SLAB = ModBlocks.createRegistry(name + "_cobblestone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(COBBLESTONE.get()).mapColor(mapColor)), empty);
        COBBLESTONE_WALL = ModBlocks.createRegistry(name + "_cobblestone_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(COBBLESTONE.get()).mapColor(mapColor)), empty);

        CHISELED_BRICKS = ModBlocks.createRegistry(name + "_chiseled_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(mapColor)), empty);

        POLISHED = ModBlocks.createRegistry("polished_" + name, () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).mapColor(mapColor)), empty);

        POLISHED_STAIRS = ModBlocks.createRegistry("polished_" + name + "_stairs", () -> new StairBlock(POLISHED.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(POLISHED.get()).mapColor(mapColor)), empty);
        POLISHED_SLAB = ModBlocks.createRegistry("polished_" + name + "_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED.get()).mapColor(mapColor)), empty);
        POLISHED_WALL = ModBlocks.createRegistry("polished_" + name + "_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(POLISHED.get()).mapColor(mapColor)), empty);

        blocks = new ArrayList<>(Arrays.asList(BLOCK, STAIRS, SLAB, WALL, BRICKS, BRICKS_STAIRS, BRICKS_SLAB, BRICKS_WALL, COBBLESTONE, COBBLESTONE_STAIRS, COBBLESTONE_SLAB, COBBLESTONE_WALL, POLISHED, POLISHED_STAIRS, POLISHED_SLAB, POLISHED_WALL, CHISELED_BRICKS));
    }

    public List<DeferredBlock<StairBlock>> getStairs(){
        return new ArrayList<>(Arrays.asList(STAIRS, BRICKS_STAIRS, POLISHED_STAIRS, COBBLESTONE_STAIRS));
    }

    public List<DeferredBlock<SlabBlock>> getSlab(){
        return new ArrayList<>(Arrays.asList(SLAB, BRICKS_SLAB, POLISHED_SLAB, COBBLESTONE_SLAB));
    }

    public List<DeferredBlock<WallBlock>> getWall(){
        return new ArrayList<>(Arrays.asList(WALL, BRICKS_WALL, POLISHED_WALL, COBBLESTONE_WALL));
    }
}
