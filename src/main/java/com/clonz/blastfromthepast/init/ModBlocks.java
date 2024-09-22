package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BeastChopsBlock;
import com.clonz.blastfromthepast.block.CustomLeavesBlock;
import com.clonz.blastfromthepast.block.CustomLogBlock;
import com.clonz.blastfromthepast.block.CustomPlanksBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlastFromThePast.MODID);

    public static final DeferredBlock<CustomLogBlock> STRIPPED_CEDAR_LOG = createRegistry("stripped_cedar_log",
            () -> new CustomLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), null, true, 5, 5), new Item.Properties());

    public static final DeferredBlock<CustomLogBlock> CEDAR_LOG = createRegistry("cedar_log",
            () -> new CustomLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), STRIPPED_CEDAR_LOG, true, 5, 5), new Item.Properties());

    public static final DeferredBlock<CustomLogBlock> SAPPY_CEDAR_LOG = createRegistry("sappy_cedar_log",
            () -> new CustomLogBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG), CEDAR_LOG, true, 5, 5), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_PLANKS = createRegistry("cedar_planks",
            () -> new CustomPlanksBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS), true, 5, 5), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_LEAVES = createRegistry("cedar_leaves",
            () -> new CustomLeavesBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES), true, 5, 5), new Item.Properties());

    public static final DeferredBlock<Block> CEDAR_DOOR = createRegistry("cedar_door",
            () -> new DoorBlock(BlockSetType.OAK, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)), new Item.Properties());

    public static final DeferredBlock<Block> BEAST_CHOPS = createRegistry("beast_chops",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)), new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> BEAST_CHOPS_COOKED = createRegistry("beast_chops_cooked",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)).setLevelAndSaturation(8, 12F), new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> BEAST_CHOPS_GLAZED = createRegistry("beast_chops_glazed",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)).setLevelAndSaturation(8, 12F), new Item.Properties().stacksTo(1));


    public static <T extends Block> DeferredBlock<T> createRegistry(String name, Supplier<T> block, Item.Properties properties) {
        DeferredBlock<T> object = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(object.get(), properties));

        return object;
    }
}
