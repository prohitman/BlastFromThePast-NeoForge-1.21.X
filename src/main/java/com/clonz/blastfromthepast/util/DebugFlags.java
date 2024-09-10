package com.clonz.blastfromthepast.util;

import net.neoforged.fml.loading.FMLEnvironment;

public class DebugFlags {
    public static final boolean DEBUG_ANIMATED_ATTACK = !FMLEnvironment.production;
    public static final boolean DEBUG_ENTITY_PACK = !FMLEnvironment.production;
    public static final boolean DEBUG_CHARGE_FORWARD = !FMLEnvironment.production;
}
