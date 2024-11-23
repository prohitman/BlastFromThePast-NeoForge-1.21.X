package com.clonz.blastfromthepast.worldgen.biome;

import com.clonz.blastfromthepast.init.ModBiomes;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.Climate;
import terrablender.api.Region;
import terrablender.api.RegionType;

import java.util.function.Consumer;

public class BFTPOverworldRegion extends Region
{

    public BFTPOverworldRegion(ResourceLocation name, int weight)
    {
        super(name, RegionType.OVERWORLD, weight);
    }

    @Override
    public void addBiomes(Registry<Biome> registry, Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper)
    {
        this.addModifiedVanillaOverworldBiomes(mapper, b -> {
            b.replaceBiome(Biomes.SNOWY_TAIGA, ModBiomes.FROSTBITE_FOREST);
            b.replaceBiome(Biomes.FROZEN_RIVER, ModBiomes.FROSTBITE_RIVER);
        });
    }
}