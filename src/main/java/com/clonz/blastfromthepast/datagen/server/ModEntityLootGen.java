package com.clonz.blastfromthepast.datagen.server;

import com.clonz.blastfromthepast.init.ModEntities;
import com.clonz.blastfromthepast.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.stream.Stream;

public class ModEntityLootGen extends EntityLootSubProvider {
    public ModEntityLootGen(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        this.add(ModEntities.GLACEROS.get(), LootTable.lootTable());
        this.add(ModEntities.SNOWDO.get(), LootTable.lootTable());
        this.add(ModEntities.FROSTOMPER.get(), LootTable.lootTable());
        this.add(ModEntities.PSYCHO_BEAR.get(), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                LootItem.lootTableItem(ModItems.BEAR_CLAW)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                ).withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add(
                                LootItem.lootTableItem(ModItems.PSYCHO_BERRY)
                                        .setWeight(3)
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F))))
                ));
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return (Stream<EntityType<?>>) ModEntities.ENTITIES.getEntries().stream().map(DeferredHolder::get).filter(type -> this.canHaveLootTable(type));
    }
}
