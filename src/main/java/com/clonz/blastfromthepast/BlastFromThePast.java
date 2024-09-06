package com.clonz.blastfromthepast;

import com.clonz.blastfromthepast.client.models.GlacerosModel;
import com.clonz.blastfromthepast.client.models.SnowdoModel;
import com.clonz.blastfromthepast.client.models.FrostomperModel;
import com.clonz.blastfromthepast.client.renderers.GlacerosRenderer;
import com.clonz.blastfromthepast.client.renderers.SnowdoRenderer;
import com.clonz.blastfromthepast.client.renderers.FrostomperRenderer;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import com.clonz.blastfromthepast.init.*;
import io.github.itskillerluc.duclib.client.model.BaseDucModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(BlastFromThePast.MODID)
public class BlastFromThePast {
    public static final String MODID = "blastfromthepast";
    public static final Logger LOGGER = LogUtils.getLogger();
    public BlastFromThePast(IEventBus modEventBus, ModContainer modContainer)
    {
        // Register the commonSetup method for modloading
        //modEventBus.addListener(CommonNeoEvents::setup);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (ExampleMod) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);


        ModEntities.ENTITIES.register(modEventBus);
        ModItems.ITEMS.register(modEventBus);
        ModTabs.CREATIVE_TABS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
      //  if (event.getTabKey() == CreativeModeTabs.COMBAT) {
     //       event.accept(ModItems.ICE_SPEAR);
     //   }


    //    if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
    //    event.accept(ModBlocks.FROZEN_PINE_LOG);
  //      event.accept(ModBlocks.STRIPPED_FROZEN_PINE_LOG);
   //     }


    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            EntityRenderers.register(ModEntities.GLACEROS.get(), GlacerosRenderer::new);
            EntityRenderers.register(ModEntities.SNOWDO.get(), SnowdoRenderer::new);
            EntityRenderers.register(ModEntities.FROSTOMPER.get(), FrostomperRenderer::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CEDAR_DOOR.get(), RenderType.CUTOUT);

        }
        @SubscribeEvent
        public static void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(GlacerosModel.LAYER_LOCATION, () -> BaseDucModel.getLakeDefinition(GlacerosEntity.LOCATION));
            event.registerLayerDefinition(SnowdoModel.LAYER_LOCATION, () -> BaseDucModel.getLakeDefinition(SnowdoEntity.LOCATION));
            event.registerLayerDefinition(FrostomperModel.ADULT_LAYER_LOCATION, () -> BaseDucModel.getLakeDefinition(ModEntities.FROSTOMPER.getId()));
            event.registerLayerDefinition(FrostomperModel.BABY_LAYER_LOCATION, () -> BaseDucModel.getLakeDefinition(ModEntities.FROSTOMPER.getId().withPrefix("baby_")));
        }
    }
}
