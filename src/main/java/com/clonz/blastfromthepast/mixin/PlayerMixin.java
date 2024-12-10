package com.clonz.blastfromthepast.mixin;

import com.clonz.blastfromthepast.access.PlayerBFTPDataAccess;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Prevent inventory drops if player's items are to be stored in the Hollow.
 */
@Mixin(Player.class)
public class PlayerMixin implements PlayerBFTPDataAccess {
    // Flag to prevent inventory drops.
    @Unique
    private boolean bftp$inventoryStored = false;

    // Right before the items are dropped, but after removing curse of vanishing items.
    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;destroyVanishingCursedItems()V", shift = At.Shift.AFTER), cancellable = true)
    private void dropEquipment(CallbackInfo ci) {
        if (this.bftp$inventoryStored) {
            ci.cancel();
        }
    }

    @Override
    @Unique
    public void bftp$markInventoryStored() {
        this.bftp$inventoryStored = true;
    }

    @Override
    @Unique
    public boolean bftp$hasInventoryStored() {
        return this.bftp$inventoryStored;
    }
}
