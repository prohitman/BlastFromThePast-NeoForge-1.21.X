package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.CedarLeavesBlock;
import com.clonz.blastfromthepast.block.CedarLogBlock;
import com.clonz.blastfromthepast.block.SappyCedarLogBlock;
import com.clonz.blastfromthepast.block.StrippedCedarLogBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlastFromThePast.MODID);

    public static final DeferredBlock<Block> CEDAR_LOG = createRegistry("cedar_log",
            () -> new CedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)), new Item.Properties());

    public static final DeferredBlock<Block> STRIPPED_CEDAR_LOG = createRegistry("stripped_cedar_log",
            () -> new StrippedCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)), new Item.Properties());

    public static final DeferredBlock<Block> SAPPY_CEDAR_LOG = createRegistry("sappy_cedar_log",
            () -> new SappyCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_PLANKS = createRegistry("cedar_planks",
            () -> new StrippedCedarLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_LEAVES = createRegistry("cedar_leaves",
            () -> new CedarLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_DOOR = createRegistry("cedar_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)), new Item.Properties());


    public static <T extends Block> DeferredBlock<Block> createRegistry(String name, Supplier<T> block, Item.Properties properties) {
        DeferredBlock<Block> object = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(object.get(), properties));

        return object;
    }
}
