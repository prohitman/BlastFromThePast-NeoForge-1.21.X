package com.clonz.blastfromthepast.events;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.network.RiddenEntityPayload;
import com.clonz.blastfromthepast.network.ServerPayloadHandler;
import com.clonz.blastfromthepast.worldgen.biome.BFTPOverworldRegion;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.clonz.blastfromthepast.init.ModDecoratedPatterns;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import terrablender.api.Regions;

@EventBusSubscriber(modid = BlastFromThePast.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonNeoEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event){
        event.enqueueWork(ModDecoratedPatterns::expandVanillaPottery);
        registerFlammables();
        //ModDecoratedPatterns.init();
        event.enqueueWork(() -> {
            Regions.register(new BFTPOverworldRegion(
                    ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "overworld"),
                    2));
        });
    }

    @SubscribeEvent
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                RiddenEntityPayload.TYPE,
                RiddenEntityPayload.STREAM_CODEC,
                ServerPayloadHandler::handleRiddenEntityPayload
        );
    }

    public static void registerFlammables(){
        registerWoodGroupFlammables(ModBlocks.CEDAR);
        flammableBlock(ModBlocks.SAPPY_CEDAR_LOG.get(), 5, 6);
    }

    public static void registerWoodGroupFlammables(BFTPWoodGroup woodGroup){
        flammableBlock(woodGroup.LEAVES.get(), 5, 30);
        flammableBlock(woodGroup.BLOCK.get(), 5, 20);
        flammableBlock(woodGroup.DOOR.get(), 5, 20);
        flammableBlock(woodGroup.SLAB.get(), 5, 20);
        flammableBlock(woodGroup.STAIRS.get(), 5, 20);
        flammableBlock(woodGroup.STRIPPED_LOG.get(), 5, 5);
        flammableBlock(woodGroup.LOG.get(), 5, 5);
        flammableBlock(woodGroup.STRIPPED_WOOD.get(), 5, 5);
        flammableBlock(woodGroup.WOOD.get(), 5, 5);
        flammableBlock(woodGroup.FENCE.get(), 5, 20);
        flammableBlock(woodGroup.FENCE_GATE.get(), 5, 20);
    }

    public static void flammableBlock(Block block, int flameOdds, int burnOdds) {
        FireBlock fire = (FireBlock) Blocks.FIRE;
        fire.setFlammable(block, flameOdds, burnOdds);
    }
}
