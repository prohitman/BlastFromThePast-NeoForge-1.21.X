package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import net.minecraft.world.entity.vehicle.Boat;
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
