package com.clonz.blastfromthepast.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.clonz.blastfromthepast.BlastFromThePast.MODID;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLAST_FROM_THE_PAST =
            CREATIVE_TABS.register("blastfromthepast", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.blastfromthepast")).withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.RAW_VENISON.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.GLACEROS_SPAWN_EGG.get());
                        output.accept(ModItems.SNOWDO_SPAWN_EGG.get());
                        output.accept(ModItems.SPEARTOOTH_SPAWN_EGG.get());
                        output.accept(ModItems.BURREL_SPAWN_EGG.get());
                        output.accept(ModBlocks.CEDAR_LOG.get());
                        output.accept(ModBlocks.STRIPPED_CEDAR_LOG.get());
                        output.accept(ModBlocks.SAPPY_CEDAR_LOG.get());
                        output.accept(ModBlocks.CEDAR_PLANKS.get());
                        output.accept(ModBlocks.CEDAR_LEAVES.get());
                        output.accept(ModBlocks.CEDAR_DOOR.get());
                        output.accept(ModItems.RAW_VENISON.get());
                        output.accept(ModItems.COOKED_VENISON.get());
                        output.accept(ModItems.SAP_BALL.get());
                        output.accept(ModItems.SPEARTOOTH.get());
                        output.accept(ModItems.ICE_SPEAR.get());
                        output.accept(ModBlocks.BEAST_CHOPS.get());
                        output.accept(ModBlocks.BEAST_CHOPS_COOKED.get());
                        output.accept(ModBlocks.BEAST_CHOPS_GLAZED.get());
                        output.accept(ModItems.SHAGGY_PELT.get());
                        output.accept(ModBlocks.SHAGGY_BLOCK.get());
                        output.accept(ModItems.FROST_BITE_HELMET);
                        output.accept(ModItems.FROST_BITE_BOOTS);
                        output.accept(ModItems.FROST_BITE_CHESTPLATE);
                        output.accept(ModItems.FROST_BITE_LEGGINGS);

                    }).build());

}
