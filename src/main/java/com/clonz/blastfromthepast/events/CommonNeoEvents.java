package com.clonz.blastfromthepast.events;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = BlastFromThePast.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonNeoEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event){
    }
}
