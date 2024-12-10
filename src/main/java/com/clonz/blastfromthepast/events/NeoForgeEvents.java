package com.clonz.blastfromthepast.events;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.entity.HollowEntity;
import com.clonz.blastfromthepast.init.ModItems;
import com.clonz.blastfromthepast.init.ModSounds;
import com.clonz.blastfromthepast.util.EntityHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
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
                event.getLevel().playSound(event.getTarget(), event.getPos(), ModSounds.GLACEROS_SHEAR.get(), SoundSource.PLAYERS, 1 ,1);
                event.getEntity().swing(event.getHand());
                ItemStack antlers = new ItemStack(glaceros.getVariant().getAntlerItem(), 2);
                event.getLevel().addFreshEntity
                        (new ItemEntity(event.getLevel(), glaceros.getX(), glaceros.getY() + 0.5, glaceros.getZ(), antlers));

            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (EntityHelper.shouldCreateHollow(player)) {
                ItemStack idol = EntityHelper.getIdolOfRetrievalInHand(player);
                if (idol != null) {
                    idol.shrink(1);
                }
                HollowEntity hollow = HollowEntity.create(player);
                player.serverLevel().addFreshEntity(hollow);
            }
        }
    }
}
