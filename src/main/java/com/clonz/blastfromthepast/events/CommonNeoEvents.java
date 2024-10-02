package com.clonz.blastfromthepast.events;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.block.BFTPWoodGroup;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.init.ModBlocks;
import com.google.common.collect.ImmutableMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = BlastFromThePast.MODID, bus = EventBusSubscriber.Bus.MOD)
public class CommonNeoEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event){
        registerFlammables();
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
