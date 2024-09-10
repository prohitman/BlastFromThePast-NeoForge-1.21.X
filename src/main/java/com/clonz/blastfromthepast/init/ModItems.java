package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.boats.BFTPBoat;
import com.clonz.blastfromthepast.item.BFTPBoatItem;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModItems {
    public static final Boat.Type CEDAR_TYPE = Boat.Type.byName("cedar");

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(BlastFromThePast.MODID);

    public static final DeferredItem<Item> RAW_VENISON = register("raw_venison",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_VENSION)));

    public static final DeferredItem<Item> COOKED_VENISON = register("cooked_venison",
            () -> new Item(new Item.Properties().food(ModFoods.COOKED_VENSION)));

    public static final DeferredItem<Item> SAP_BALL = register("sap_ball",
            () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GLACEROS_SPAWN_EGG = register("glaceros_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.GLACEROS, 0x7c908b, 0xffb122, new Item.Properties()));

    public static final DeferredItem<Item> SNOWDO_SPAWN_EGG = register("snowdo_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.SNOWDO, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> FROSTOMPER_SPAWN_EGG = register("frostomper_spawn_egg",
            () -> new DeferredSpawnEggItem(ModEntities.FROSTOMPER, 0x74a3af, 0xcdc9df, new Item.Properties()));

    public static final DeferredItem<Item> CEDAR_BOAT = register("cedar_boat", () -> new BFTPBoatItem(false, BFTPBoat.BoatType.CEDAR, (new Item.Properties()).stacksTo(1)));
    public static final DeferredItem<Item> CEDAR_CHEST_BOAT = register("cedar_chest_boat", () -> new BFTPBoatItem(true, BFTPBoat.BoatType.CEDAR, (new Item.Properties()).stacksTo(1)));


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
