package com.clonz.blastfromthepast.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Items{
        public static final TagKey<Item> FROSTOMPER_FOOD = TagKey.create(Registries.ITEM, ModEntities.FROSTOMPER.getId().withSuffix("_food"));
        public static final TagKey<Item> FROSTOMPER_TEMPT_ITEMS = TagKey.create(Registries.ITEM, ModEntities.FROSTOMPER.getId().withSuffix("_tempt_items"));
        public static final TagKey<Item> BABY_FROSTOMPER_FOOD = TagKey.create(Registries.ITEM, ModEntities.FROSTOMPER.getId().withPrefix("baby_").withSuffix("_food"));
        public static final TagKey<Item> BABY_FROSTOMPER_TEMPT_ITEMS = TagKey.create(Registries.ITEM, ModEntities.FROSTOMPER.getId().withPrefix("baby_").withSuffix("_tempt_items"));
        public static final TagKey<Item> PSYCHO_BEAR_FOOD = TagKey.create(Registries.ITEM, ModEntities.PSYCHO_BEAR.getId().withSuffix("_food"));
        public static final TagKey<Item> PSYCHO_BEAR_TEMPT_ITEMS = TagKey.create(Registries.ITEM, ModEntities.PSYCHO_BEAR.getId().withSuffix("_tempt_items"));
        public static final TagKey<Item> BABY_PSYCHO_BEAR_FOOD = TagKey.create(Registries.ITEM, ModEntities.PSYCHO_BEAR.getId().withPrefix("baby_").withSuffix("_food"));
        public static final TagKey<Item> BABY_PSYCHO_BEAR_TEMPT_ITEMS = TagKey.create(Registries.ITEM, ModEntities.PSYCHO_BEAR.getId().withPrefix("baby_").withSuffix("_tempt_items"));
        public static final TagKey<Item> PSYCHO_BEAR_PACIFIER = TagKey.create(Registries.ITEM, ModEntities.PSYCHO_BEAR.getId().withSuffix("_pacifier"));
    }
    public static class Blocks{
        public static final TagKey<Block> FROSTOMPER_CAN_BREAK = TagKey.create(Registries.BLOCK, ModEntities.FROSTOMPER.getId().withSuffix("_can_break"));
    }

    public static class EntityTypes{
        public static final TagKey<EntityType<?>> PSYCHO_BEAR_IGNORES = TagKey.create(Registries.ENTITY_TYPE, ModEntities.PSYCHO_BEAR.getId().withSuffix("_ignores"));
    }
}
