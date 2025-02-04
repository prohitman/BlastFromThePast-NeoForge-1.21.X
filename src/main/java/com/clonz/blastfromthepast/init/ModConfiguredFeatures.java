package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.PineconeBlock;
import com.clonz.blastfromthepast.block.TarBlock;
import com.clonz.blastfromthepast.worldgen.feature.CedarFoliagePlacer;
import com.clonz.blastfromthepast.worldgen.feature.PitFeature;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.AttachedToLeavesDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.Optional;

public class ModConfiguredFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> CEDAR_TREE = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "cedar_tree"));
    public static ResourceKey<ConfiguredFeature<?, ?>> FROSTBITE_FOREST_FLOWERS = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_forest_flowers"));
    public static ResourceKey<ConfiguredFeature<?, ?>> TAR_PIT = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "tar_pit"));

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context){
        FeatureUtils.register(
                context,
                CEDAR_TREE,
                Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        BlockStateProvider.simple(ModBlocks.CEDAR.LOG.get()),
                        new StraightTrunkPlacer(11, 1, 0),
                        BlockStateProvider.simple(ModBlocks.CEDAR.LEAVES.get()),
                        new CedarFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0)),
                        new TwoLayersFeatureSize(1, 0, 2)
                )
                        .decorators(List.of(new AttachedToLeavesDecorator(0.14F, 0, 0, BlockStateProvider.simple(ModBlocks.PINECONE.get().defaultBlockState().setValue(PineconeBlock.HANGING, true)), 1, List.of(Direction.DOWN))))
                        .build()
        );
        FeatureUtils.register(
                context,
                FROSTBITE_FOREST_FLOWERS,
                Feature.SIMPLE_RANDOM_SELECTOR,
                new SimpleRandomFeatureConfiguration(
                        HolderSet.direct(
                                PlacementUtils.inlinePlaced(
                                        Feature.RANDOM_PATCH,
                                        FeatureUtils.simplePatchConfiguration(
                                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(
                                                        ModBlocks.BLUE_DELPHINIUM.get())))
                                ),
                                PlacementUtils.inlinePlaced(
                                        Feature.RANDOM_PATCH,
                                        FeatureUtils.simplePatchConfiguration(
                                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(
                                                        ModBlocks.PINK_DELPHINIUM.get())))
                                ),
                                PlacementUtils.inlinePlaced(
                                        Feature.RANDOM_PATCH,
                                        FeatureUtils.simplePatchConfiguration(
                                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(
                                                        ModBlocks.VIOLET_DELPHINIUM.get())))
                                ),
                                PlacementUtils.inlinePlaced(
                                        Feature.RANDOM_PATCH,
                                        FeatureUtils.simplePatchConfiguration(
                                                Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(
                                                        ModBlocks.WHITE_DELPHINIUM.get()))
                                        )
                                )
                        )
                )
        );

        FeatureUtils.register(
                context,
                TAR_PIT,
                ModFeatures.PIT.get(),
                new PitFeature.Configuration(
                        BlockStateProvider.simple(ModBlocks.TAR.get().defaultBlockState()), Optional.of(BlockStateProvider.simple(ModBlocks.TAR.get().defaultBlockState().setValue(TarBlock.COVER, true))), BlockStateProvider.simple(Blocks.STONE.defaultBlockState())
                )
        );
    }
}
