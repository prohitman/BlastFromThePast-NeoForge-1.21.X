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
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

public class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> CEDAR_TREE = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "cedar_placed"));

    public static ResourceKey<PlacedFeature> FROSTBITE_FOREST_FLOWERS = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_forest_flowers"));

    public static ResourceKey<PlacedFeature> TAR_PIT = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "tar_pit"));

    public static void register(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> cedarTreeConfigured
                = configuredFeatures.getOrThrow(ModConfiguredFeatures.CEDAR_TREE);
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
        PlacementUtils.register(context, CEDAR_TREE, cedarTreeConfigured, VegetationPlacements.treePlacement(PlacementUtils.countExtra(3, 0.1f, 2), ModBlocks.PINECONE.get()));
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
