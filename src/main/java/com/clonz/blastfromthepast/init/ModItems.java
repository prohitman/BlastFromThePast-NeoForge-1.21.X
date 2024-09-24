package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.item.FrostbiteArmor;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


public class ModItems {
    public static final Boat.Type CEDAR_TYPE = Boat.Type.byName("cedar");

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlastFromThePast.MODID);

    public static final DeferredItem<Item> RAW_VENISON = ITEMS.register("raw_venison",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_VENSION)));

    public static final DeferredItem<Item> COOKED_VENISON = ITEMS.register("cooked_venison",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_VENSION)));

    public static final DeferredItem<Item> SAP_BALL = ITEMS.register("sap_ball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLACEROS_SPAWN_EGG = ITEMS.register("glaceros_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GLACEROS, 0x7c908b, 0xffb122, new Item.Properties()));

    public static final DeferredItem<Item> SNOWDO_SPAWN_EGG = ITEMS.register("snowdo_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SNOWDO, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> SPEARTOOTH_SPAWN_EGG = ITEMS.register("speartooth_tiger_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SPEARTOOTH, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> ICE_SPEAR = ITEMS.register("ice_spear", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> SPEARTOOTH = ITEMS.register("speartooth", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> SHAGGY_PELT = ITEMS.register("shaggy_pelt", () -> new Item(new Item.Properties()));

    public static final DeferredItem<ArmorItem> FROST_BITE_HELMET = ITEMS.register("frost_bite_helmet", () -> new FrostbiteArmor(ModArmorMaterials.FROST_BITE, ArmorItem.Type.HELMET, new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(10))));
    public static final DeferredItem<ArmorItem> FROST_BITE_CHESTPLATE = ITEMS.register("frost_bite_chestplate", () -> new FrostbiteArmor(ModArmorMaterials.FROST_BITE, ArmorItem.Type.CHESTPLATE, new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(10))));
    public static final DeferredItem<ArmorItem> FROST_BITE_LEGGINGS = ITEMS.register("frost_bite_leggings", () -> new FrostbiteArmor(ModArmorMaterials.FROST_BITE, ArmorItem.Type.LEGGINGS, new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(10))));
    public static final DeferredItem<ArmorItem> FROST_BITE_BOOTS = ITEMS.register("frost_bite_boots", () -> new FrostbiteArmor(ModArmorMaterials.FROST_BITE, ArmorItem.Type.BOOTS, new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(10))));



/*    public static final DeferredItem<BlockItem> FROZEN_PINE_LOG =
            ITEMS.registerSimpleBlockItem("cedar_log", ModBlocks.CEDAR_LOG);

    public static final DeferredItem<BlockItem> STRIPPED_FROZEN_PINE_LOG =
            ITEMS.registerSimpleBlockItem("stripped_cedar_log", ModBlocks.STRIPPED_CEDAR_LOG);

    public static final DeferredItem<BlockItem> SAPPY_FROZEN_PINE_LOG =
            ITEMS.registerSimpleBlockItem("sappy_cedar_log", ModBlocks.SAPPY_CEDAR_LOG);

    public static final DeferredItem<BlockItem> CEDAR_PLANKS =
            ITEMS.registerSimpleBlockItem("cedar_planks", ModBlocks.CEDAR_PLANKS);

    public static final DeferredItem<BlockItem> CEDAR_LEAVES =
            ITEMS.registerSimpleBlockItem("cedar_leaves", ModBlocks.CEDAR_LEAVES);
    public static final DeferredItem<BlockItem> CEDAR_DOOR =
            ITEMS.registerSimpleBlockItem("cedar_door", ModBlocks.CEDAR_DOOR);*/


}
