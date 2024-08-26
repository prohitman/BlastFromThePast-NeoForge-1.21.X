package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.CedarLeavesBlock;
import com.clonz.blastfromthepast.block.CedarLogBlock;
import com.clonz.blastfromthepast.block.SappyCedarLogBlock;
import com.clonz.blastfromthepast.block.StrippedCedarLogBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlastFromThePast.MODID);

    public static final DeferredBlock<Block> CEDAR_LOG = BLOCKS.register("cedar_log",
            () -> new CedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> STRIPPED_CEDAR_LOG = BLOCKS.register("stripped_cedar_log",
            () -> new StrippedCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> SAPPY_CEDAR_LOG = BLOCKS.register("sappy_cedar_log",
            () -> new SappyCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));

    public static final DeferredBlock<Block> CEDAR_PLANKS = BLOCKS.register("cedar_planks",
            () -> new StrippedCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    public static final DeferredBlock<Block> CEDAR_LEAVES = BLOCKS.register("cedar_leaves",
            () -> new CedarLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)));

    public static final DeferredBlock<Block> CEDAR_DOOR = BLOCKS.register("cedar_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
}
