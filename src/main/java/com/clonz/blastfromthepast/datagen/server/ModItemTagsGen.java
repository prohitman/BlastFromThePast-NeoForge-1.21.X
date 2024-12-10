package com.clonz.blastfromthepast.datagen.server;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.init.ModItems;
import com.clonz.blastfromthepast.init.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsGen extends ItemTagsProvider {

    public ModItemTagsGen(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, BlastFromThePast.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ItemTags.DECORATED_POT_SHERDS)
                .add(ModItems.BEAST_POTTERY_SHERD.get())
                .add(ModItems.FROST_POTTERY_SHERD.get())
                .add(ModItems.WOODS_POTTERY_SHERD.get());

        tag(ItemTags.MEAT)
                .add(ModItems.COOKED_VENISON.get())
                .add(ModItems.RAW_VENISON.get());
        tag(Tags.Items.FOODS_COOKED_MEAT)
                .add(ModItems.COOKED_VENISON.get());
        copy(BlockTags.LOGS_THAT_BURN, ItemTags.LOGS_THAT_BURN);
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);

        tag(ModTags.Items.FROSTOMPER_FOOD)
                .add(ModItems.MELON_ICE_CREAM.get());
        tag(ModTags.Items.FROSTOMPER_TEMPT_ITEMS)
                .addTag(ModTags.Items.FROSTOMPER_FOOD);
        tag(ModTags.Items.BABY_FROSTOMPER_FOOD)
                .add(ModItems.SAP_ICE_CREAM.get());
        tag(ModTags.Items.BABY_FROSTOMPER_TEMPT_ITEMS)
                .addTag(ModTags.Items.BABY_FROSTOMPER_FOOD);

        tag(ModTags.Items.PSYCHO_BEAR_FOOD)
                .add(ModItems.PSYCHO_BERRY_ICE_CREAM.get()); // TODO: Change to beast chops
        tag(ModTags.Items.PSYCHO_BEAR_TEMPT_ITEMS)
                .addTag(ModTags.Items.PSYCHO_BEAR_FOOD);
        tag(ModTags.Items.BABY_PSYCHO_BEAR_FOOD)
                .add(ModItems.PSYCHO_BERRY.get());
        tag(ModTags.Items.BABY_PSYCHO_BEAR_TEMPT_ITEMS)
                .addTag(ModTags.Items.BABY_PSYCHO_BEAR_FOOD);
        tag(ModTags.Items.PSYCHO_BEAR_PACIFIER)
                .add(ModItems.PSYCHO_BERRY_ICE_CREAM.get());
        tag(ModTags.Items.GLACEROS_ANTLERS)
                .add(ModItems.BROAD_GLACEROS_ANTLERS.get())
                .add(ModItems.CURLY_GLACEROS_ANTLERS.get())
                .add(ModItems.SPIKEY_GLACEROS_ANTLERS.get())
                .add(ModItems.STRAIGHT_GLACEROS_ANTLERS.get());
    }
}
