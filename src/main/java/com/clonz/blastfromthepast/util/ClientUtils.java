package com.clonz.blastfromthepast.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientUtils {
    public static boolean isPlayerHoldingSpace(Player player) {
        if (player != Minecraft.getInstance().player) return false;
        return Minecraft.getInstance().options.keyJump.isDown();
    }

    public static boolean isHost(Player player) {
        return Minecraft.getInstance().player == player;
    }
}
