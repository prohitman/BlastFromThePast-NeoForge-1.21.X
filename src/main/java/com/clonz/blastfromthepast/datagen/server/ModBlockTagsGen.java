package com.clonz.blastfromthepast.datagen.server;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsGen extends BlockTagsProvider {
    public ModBlockTagsGen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BlastFromThePast.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.CEDAR_LOG.get())
                .add(ModBlocks.SAPPY_CEDAR_LOG.get())
                .add(ModBlocks.STRIPPED_CEDAR_LOG.get());
        tag(BlockTags.PLANKS)
                .add(ModBlocks.CEDAR_PLANKS.get());
        tag(BlockTags.LEAVES)
                .add(ModBlocks.CEDAR_LEAVES.get());
    }
}
