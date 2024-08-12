package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegistryBuilder;
import net.minecraft.core.registries.Registries;

import static net.minecraft.core.registries.Registries.ENTITY_TYPE;
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, BlastFromThePast.MODID);

    public static <T extends Mob> DeferredHolder<EntityType<?>, EntityType<T>> registerMob(String name, EntityType.EntityFactory<T> entity,
                                                                                           float width, float height, int primaryEggColor, int secondaryEggColor) {
        DeferredHolder<EntityType<?>, EntityType<T>> entityType = ENTITIES.register(name,
                () -> EntityType.Builder.of(entity, MobCategory.CREATURE).sized(width, height).build(name));

        return entityType;
    }

    public static final DeferredHolder<EntityType<?>, EntityType<GlacerosEntity>> GLACEROS = registerMob("glaceros", GlacerosEntity::new,
            0.4f, 0.7f, 0x302219, 0xACACAC);
    // () -> EntityType.Builder.of(GlacerosEntity::new, MobCategory.CREATURE).sized(1, 3)
    //         .build( ResourceLocation.tryBuild(BlastFromThePast.MODID, "glaceros").toString()));

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlacerosEntity.init();
        });
    }


    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(GLACEROS.get(), GlacerosEntity.createAttributes().build());
    }
}