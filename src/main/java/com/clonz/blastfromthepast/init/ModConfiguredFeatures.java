package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.TarBlock;
import com.clonz.blastfromthepast.worldgen.feature.PitFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.TreePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;
import java.util.Optional;

public class ModConfiguredFeatures {
    public static ResourceKey<ConfiguredFeature<?, ?>> TREES_FROSTBITE_FOREST = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "trees_frostbite_forest"));
    public static ResourceKey<ConfiguredFeature<?, ?>> FROSTBITE_FOREST_FLOWERS = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_forest_flowers"));
    public static ResourceKey<ConfiguredFeature<?, ?>> TAR_PIT = ResourceKey.create(Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "tar_pit"));

    public static void register(BootstrapContext<ConfiguredFeature<?, ?>> context){
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        Holder<PlacedFeature> pineChecked = placedFeatures.getOrThrow(TreePlacements.PINE_CHECKED);
        Holder<PlacedFeature> spruceChecked = placedFeatures.getOrThrow(TreePlacements.SPRUCE_CHECKED);
        FeatureUtils.register(
                context,
                TREES_FROSTBITE_FOREST,
                Feature.RANDOM_SELECTOR,
                new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(pineChecked, 0.33333334F)), spruceChecked)
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
