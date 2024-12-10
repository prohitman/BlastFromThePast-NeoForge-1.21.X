package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {

    public static ResourceKey<PlacedFeature> TREES_FROSTBITE_FOREST = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "trees_frostbite_forest"));

    public static ResourceKey<PlacedFeature> FROSTBITE_FOREST_FLOWERS = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_forest_flowers"));

    public static ResourceKey<PlacedFeature> TAR_PIT = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "tar_pit"));

    public static void register(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> treesFrostbiteForestConfigured
                = configuredFeatures.getOrThrow(ModConfiguredFeatures.TREES_FROSTBITE_FOREST);
        PlacementUtils.register(context, TREES_FROSTBITE_FOREST, treesFrostbiteForestConfigured,
                // base value reduced from 10 for standard spruce tree placement
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1F, 1)));
        Holder<ConfiguredFeature<?, ?>> frosbiteForestFlowersConfigured = configuredFeatures.getOrThrow(ModConfiguredFeatures.FROSTBITE_FOREST_FLOWERS);
        PlacementUtils.register(
                context,
                FROSTBITE_FOREST_FLOWERS,
                frosbiteForestFlowersConfigured,
                RarityFilter.onAverageOnceEvery(7),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                CountPlacement.of(ClampedInt.of(UniformInt.of(-3, 1), 0, 1)),
                BiomeFilter.biome()
        );

        Holder<ConfiguredFeature<?, ?>> tarPitConfigured = configuredFeatures.getOrThrow(ModConfiguredFeatures.TAR_PIT);
        PlacementUtils.register(
                context,
                TAR_PIT,
                tarPitConfigured,
                RarityFilter.onAverageOnceEvery(40),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP_WORLD_SURFACE,
                BiomeFilter.biome()
        );
    }
}
