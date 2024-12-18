package com.clonz.blastfromthepast.client;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderHandEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = BlastFromThePast.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientNeoEvents {
    @SubscribeEvent
    public static void renderHand(RenderHandEvent event){
        if(Minecraft.getInstance().player == null) return;
        if(Minecraft.getInstance().player.getFirstPassenger() instanceof SnowdoEntity && event.getItemStack().isEmpty()){
            event.setCanceled(true);
        }
    }
}
