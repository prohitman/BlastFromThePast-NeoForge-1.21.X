package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.boats.BFTPBoat;
import com.clonz.blastfromthepast.item.BFTPBoatItem;
import com.clonz.blastfromthepast.item.FrostbiteArmor;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModItems {
    public static final Boat.Type CEDAR_TYPE = Boat.Type.byName("cedar");

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlastFromThePast.MODID);

    public static final DeferredItem<Item>  BLIZZARD_REVELRY_DISC = register("blizzard_revelry_disc",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ModSounds.BLIZZARD_REVELRY_KEY)));

    public static final DeferredItem<Item> RAW_VENISON = register("raw_venison",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_VENSION)));

    public static final DeferredItem<Item> COOKED_VENISON = register("cooked_venison",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_VENSION)));

    public static final DeferredItem<Item> SAP_BALL = register("sap_ball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> STRAIGHT_GLACEROS_ANTLERS = register("straight_glaceros_antlers",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BROAD_GLACEROS_ANTLERS = register("broad_glaceros_antlers",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CURLY_GLACEROS_ANTLERS = register("curly_glaceros_antlers",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> SPIKEY_GLACEROS_ANTLERS = register("spikey_glaceros_antlers",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> BEAST_POTTERY_SHERD = register("beast_pottery_sherd",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> WOODS_POTTERY_SHERD = register("woods_pottery_sherd",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FROST_POTTERY_SHERD = register("frost_pottery_sherd",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLACEROS_SPAWN_EGG = register("glaceros_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GLACEROS, 0x7c908b, 0xffb122, new Item.Properties()));

    public static final DeferredItem<Item> SNOWDO_SPAWN_EGG = register("snowdo_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SNOWDO, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> SPEARTOOTH_SPAWN_EGG = ITEMS.register("speartooth_tiger_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SPEARTOOTH, 0x74a3af, 0xcdc9df, new Item.Properties()));


    public static final DeferredItem<Item> BURREL_SPAWN_EGG = ITEMS.register("burrel_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.BURREL, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> FROSTOMPER_SPAWN_EGG = register("frostomper_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.FROSTOMPER, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> CEDAR_BOAT = register("cedar_boat", () -> new BFTPBoatItem(false, BFTPBoat.BoatType.CEDAR, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> CEDAR_CHEST_BOAT = register("cedar_chest_boat", () -> new BFTPBoatItem(true, BFTPBoat.BoatType.CEDAR, (new Item.Properties()).stacksTo(1)));

    public static final DeferredItem<Item> PSYCHO_BERRY = register("psycho_berry",
            () -> new Item(new Item.Properties().food(ModFoods.PSYCHO_BERRY)));

    public static final DeferredItem<Item> SAP_ICE_CREAM = registerIceCream("sap_ice_cream");

    public static final DeferredItem<Item> PSYCHO_BERRY_ICE_CREAM = registerIceCream("psycho_berry_ice_cream");

    public static final DeferredItem<Item> MELON_ICE_CREAM = registerIceCream("melon_ice_cream");

    public static final DeferredItem<Item> BEAR_CLAW = register("bear_claw",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> PSYCHO_BEAR_SPAWN_EGG = register("psycho_bear_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.PSYCHO_BEAR, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> IDOL_OF_RETRIEVAL = register("idol_of_retrieval",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));

    private static DeferredItem<Item> registerIceCream(String name) {
        return register(name, () -> new Item(new Item.Properties().stacksTo(16).food(ModFoods.BOWL_ICE_CREAM)));
    }


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

    public static DeferredItem<Item> register(String name, Supplier<Item> block) {
        return ITEMS.register(name, block);
    }
}