package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlastFromThePast.MODID);

    public static final BFTPWoodGroup CEDAR = new BFTPWoodGroup("cedar",  MapColor.COLOR_BROWN, new Item.Properties(), BLOCKS);
    public static final BFTPStoneGroup PERMAFROST = new BFTPStoneGroup("permafrost",  MapColor.STONE, new Item.Properties());

    public static final DeferredBlock<CustomLogBlock> SAPPY_CEDAR_LOG = createRegistry("sappy_cedar_log",
            () -> new CustomLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), CEDAR.LOG), new Item.Properties());

    public static final DeferredBlock<Block> WHITE_DELPHINIUM = createRegistry("white_delphinium",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)), new Item.Properties());
    public static final DeferredBlock<Block> PINK_DELPHINIUM = createRegistry("pink_delphinium",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)), new Item.Properties());
    public static final DeferredBlock<Block> VIOLET_DELPHINIUM = createRegistry("violet_delphinium",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)), new Item.Properties());
    public static final DeferredBlock<Block> BLUE_DELPHINIUM = createRegistry("blue_delphinium",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.ROSE_BUSH)), new Item.Properties());

    public static <T extends Block> DeferredBlock<T> createRegistry(String name, Supplier<T> block, Item.Properties properties) {
        DeferredBlock<T> object = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(object.get(), properties));

        return object;
    }
}
