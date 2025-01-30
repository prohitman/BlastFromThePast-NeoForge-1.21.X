package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BlastFromThePast.MODID);

    public static final BFTPWoodGroup CEDAR = new BFTPWoodGroup("cedar",  MapColor.COLOR_BROWN, new Item.Properties(), BLOCKS);
    public static final BFTPStoneGroup PERMAFROST = new BFTPStoneGroup("permafrost",  MapColor.STONE, new Item.Properties());

    public static final DeferredBlock<Block> SHAGGY_BLOCK = createRegistry("shaggy_block",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.WHITE_WOOL)), new Item.Properties());
    public static final DeferredBlock<Block> BEASTLY_FEMUR = createRegistry("beastly_femur",
            () -> new FemurBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).noOcclusion()), new Item.Properties());
    public static final DeferredBlock<Block> PSYCHO_BERRY_BUSH = createRegistry("psycho_berry_bush",
            () -> new PsychoBerryBush(BlockBehaviour.Properties.ofFullCopy(Blocks.SPRUCE_LEAVES).noCollission()), new Item.Properties());
    public static final DeferredBlock<Block> PSYCHO_BERRY_SPROUT = createRegistry("psycho_berry_sprout",
            () -> new PsychoBerrySprout(BlockBehaviour.Properties.ofFullCopy(Blocks.DANDELION).randomTicks()), new Item.Properties());
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

    public static final DeferredBlock<Block> BEAST_CHOPS = createRegistry("beast_chops",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)), new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> BEAST_CHOPS_COOKED = createRegistry("beast_chops_cooked",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)).setLevelAndSaturation(8, 12F), new Item.Properties().stacksTo(1));

    public static final DeferredBlock<Block> BEAST_CHOPS_GLAZED = createRegistry("beast_chops_glazed",
            () -> new BeastChopsBlock(BlockBehaviour.Properties.of().forceSolidOn().strength(0.5F).sound(SoundType.WOOL).pushReaction(PushReaction.DESTROY)).setLevelAndSaturation(8, 20F), new Item.Properties().stacksTo(1));
    public static final DeferredBlock<Block> SNOWDO_EGG = createRegistry("snowdo_egg",
            () -> new SnowdoEggBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TURTLE_EGG).noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> TAR = createRegistry("tar",
            () -> new TarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POWDER_SNOW).mapColor(MapColor.COLOR_BLACK).strength(4F).sound(SoundType.SLIME_BLOCK)), new Item.Properties());

    public static <T extends Block> DeferredBlock<T> createRegistry(String name, Supplier<T> block, Item.Properties properties) {
        DeferredBlock<T> object = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> new BlockItem(object.get(), properties));

        return object;
    }
}
