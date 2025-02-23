package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

public class ModBiomes {
    public static final ResourceKey<Biome> FROSTBITE_FOREST = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_forest")
    );
    public static final ResourceKey<Biome> FROSTBITE_RIVER = ResourceKey.create(
            Registries.BIOME,
            ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "frostbite_river")
    );
    public static final int FROSTBITE_WATER_COLOR = 0x43D5EE;
    public static final int FROSTBITE_WATER_FOG_COLOR = 0x041F33;
    public static final int FROSTBITE_GRASS_COLOR = 0xBFB755;
    public static final int FROSBITE_FOLIAGE_COLOR = 0xAEA42A;
    public static final float WARM_ENOUGH_TO_NOT_SNOW = 0.15F;

    public static void register(BootstrapContext<Biome> biomeBootstrapContext) {
        HolderGetter<PlacedFeature> holdergetter = biomeBootstrapContext.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> worldCarvers = biomeBootstrapContext.lookup(Registries.CONFIGURED_CARVER);
        biomeBootstrapContext.register(
                // The resource key of our configured feature.
                FROSTBITE_FOREST,
                // The actual configured feature.
                frostbiteForest(holdergetter, worldCarvers)
        );
        biomeBootstrapContext.register(
                // The resource key of our configured feature.
                FROSTBITE_RIVER,
                // The actual configured feature.
                frostbiteRiver(holdergetter, worldCarvers)
        );
    }

    public static Biome frostbiteForest(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder mobspawnsettings$builder = new MobSpawnSettings.Builder();
        mobspawnsettings$builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.BURREL.get(), 8, 1, 2))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SNOWDO.get(), 8, 2, 5))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.GLACEROS.get(), 8, 3, 7))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.SPEARTOOTH.get(), 8, 3, 6))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.PSYCHO_BEAR.get(), 8, 1, 1))
                .addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(ModEntities.FROSTOMPER.get(), 8, 1, 6));
        BiomeDefaultFeatures.commonSpawns(mobspawnsettings$builder);
        BiomeGenerationSettings.Builder biomeGenBuilder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        globalOverworldGeneration(biomeGenBuilder);
        BiomeDefaultFeatures.addFerns(biomeGenBuilder);
        BiomeDefaultFeatures.addDefaultOres(biomeGenBuilder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomeGenBuilder);
        //BiomeDefaultFeatures.addTaigaTrees(biomegenerationsettings$builder);
        addFrostbiteForestTrees(biomeGenBuilder);
        //BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        addFrostbiteForestFlowers(biomeGenBuilder);
        addTarPits(biomeGenBuilder);
        BiomeDefaultFeatures.addTaigaGrass(biomeGenBuilder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomeGenBuilder);
        BiomeDefaultFeatures.addRareBerryBushes(biomeGenBuilder);
        BiomeDefaultFeatures.addSparseJungleMelons(biomeGenBuilder);

        return biome(true, -0.5F, 0.4F,
                FROSTBITE_WATER_COLOR, FROSTBITE_WATER_FOG_COLOR,
                FROSTBITE_GRASS_COLOR, FROSBITE_FOLIAGE_COLOR,
                mobspawnsettings$builder, biomeGenBuilder, null);
    }

    private static void addTarPits(BiomeGenerationSettings.Builder biomeBuilder) {
        biomeBuilder.addFeature(GenerationStep.Decoration.LAKES, ModPlacedFeatures.TAR_PIT);
    }

    private static void addFrostbiteForestTrees(BiomeGenerationSettings.Builder biomegenerationsettings$builder) {
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.CEDAR_TREE);
    }

    private static void addFrostbiteForestFlowers(BiomeGenerationSettings.Builder biomegenerationsettings$builder) {
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.FROSTBITE_FOREST_FLOWERS);
    }

    private static void globalOverworldGeneration(BiomeGenerationSettings.Builder generationSettings) {
        BiomeDefaultFeatures.addDefaultCarversAndLakes(generationSettings);
        BiomeDefaultFeatures.addDefaultCrystalFormations(generationSettings);
        BiomeDefaultFeatures.addDefaultMonsterRoom(generationSettings);
        BiomeDefaultFeatures.addDefaultUndergroundVariety(generationSettings);
        BiomeDefaultFeatures.addDefaultSprings(generationSettings);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    private static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int waterColor, int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = (new BiomeSpecialEffects.Builder())
                .waterColor(waterColor)
                .waterFogColor(waterFogColor)
                .fogColor(12638463)
                .skyColor(calculateSkyColor(temperature))
                .ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS)
                .backgroundMusic(backgroundMusic);
        if (grassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(grassColorOverride);
        }

        if (foliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(foliageColorOverride);
        }

        return (new Biome.BiomeBuilder())
                .hasPrecipitation(hasPrecipitation)
                .temperature(temperature)
                .downfall(downfall)
                .specialEffects(biomespecialeffects$builder.build())
                .mobSpawnSettings(mobSpawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
    }

    protected static int calculateSkyColor(float temperature) {
        float skyColor = temperature / 3.0F;
        skyColor = Mth.clamp(skyColor, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - skyColor * 0.05F, 0.5F + skyColor * 0.1F, 1.0F);
    }

    public static Biome frostbiteRiver(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        MobSpawnSettings.Builder mobspawnsettings$builder = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 2, 1, 4)).addSpawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 5, 1, 5));
        BiomeDefaultFeatures.commonSpawns(mobspawnsettings$builder);
        mobspawnsettings$builder.addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntityType.DROWNED, true ? 1 : 100, 1, 1));
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        globalOverworldGeneration(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultOres(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(biomegenerationsettings$builder);
        //BiomeDefaultFeatures.addWaterTrees(biomegenerationsettings$builder);
        addFrostbiteForestTrees(biomegenerationsettings$builder);
        //BiomeDefaultFeatures.addDefaultFlowers(biomegenerationsettings$builder);
        addFrostbiteForestFlowers(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultGrass(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultMushrooms(biomegenerationsettings$builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(biomegenerationsettings$builder);
        biomegenerationsettings$builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_RIVER);

        return biome(true, WARM_ENOUGH_TO_NOT_SNOW, 0.5F, FROSTBITE_WATER_COLOR, FROSTBITE_WATER_FOG_COLOR, FROSTBITE_GRASS_COLOR, FROSBITE_FOLIAGE_COLOR, mobspawnsettings$builder, biomegenerationsettings$builder, null);
    }

}
