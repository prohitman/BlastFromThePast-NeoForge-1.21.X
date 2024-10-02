package com.clonz.blastfromthepast.events;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(modid = BlastFromThePast.MODID, bus = EventBusSubscriber.Bus.GAME)
public class NeoForgeEvents {
    @SubscribeEvent
    public static void playerInteractEvent(PlayerInteractEvent.EntityInteract event){
        if(event.getTarget() instanceof GlacerosEntity glaceros){
            if(event.getItemStack().is(Items.SHEARS) && !glaceros.isBaby() && !glaceros.isSheared()){
                glaceros.setSheared(true);
                glaceros.antlerGrowCooldown = 1000 + glaceros.getRandom().nextInt(300);
                if(!event.getEntity().isCreative()){
                    event.getEntity().getItemInHand(event.getHand()).setDamageValue(1);
                }
                event.getLevel().playSound(event.getTarget(), event.getPos(), SoundEvents.BOGGED_SHEAR, SoundSource.PLAYERS, 1 ,1);
                event.getEntity().swing(event.getHand());
                ItemStack antlers = new ItemStack(glaceros.getVariant().getAntlerItem(), 2);
                event.getLevel().addFreshEntity
                        (new ItemEntity(event.getLevel(), glaceros.getX(), glaceros.getY() + 0.5, glaceros.getZ(), antlers));

            }
        }
    }
}
