package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.GlacerosEntity;
import com.clonz.blastfromthepast.entity.SnowdoEntity;
import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.clonz.blastfromthepast.entity.boats.BFTPBoat;
import com.clonz.blastfromthepast.entity.boats.BFTPChestBoat;
import com.clonz.blastfromthepast.util.HitboxHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;

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
            1f, 2.2f, 0x302219, 0xACACAC);

    public static final DeferredHolder<EntityType<?>, EntityType<SnowdoEntity>> SNOWDO = registerMob("snowdo",   SnowdoEntity::new,
            0.6f, 1.1f, 0x302219, 0xACACAC);

    public static final DeferredHolder<EntityType<?>, EntityType<FrostomperEntity>> FROSTOMPER = registerMob("frostomper", FrostomperEntity::new,
            HitboxHelper.pixelsToBlocks(77.0F), HitboxHelper.pixelsToBlocks(70.0F), 0x302219, 0xACACAC);

    public static final DeferredHolder<EntityType<?>, EntityType<BFTPBoat>> BFTPBOAT = ENTITIES.register("boat",() -> EntityType.Builder
            .<BFTPBoat>of(BFTPBoat::new, MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .clientTrackingRange(10)
            .build(getName("boat")));

    public static final DeferredHolder<EntityType<?>, EntityType<BFTPChestBoat>> BFTPCHEST_BOAT = ENTITIES.register("chest_boat",() -> EntityType.Builder
            .<BFTPChestBoat>of(BFTPChestBoat::new, MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .clientTrackingRange(10)
            .build(getName("chest_boat")));

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> GlacerosEntity.init());
        event.enqueueWork(() -> SnowdoEntity.init());
        event.enqueueWork(() -> FrostomperEntity.init());
    }

    @SubscribeEvent
    public static void  registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GLACEROS.get(), GlacerosEntity.createAttributes().build());
        event.put(ModEntities.SNOWDO.get(), SnowdoEntity.createAttributes().build());
        event.put(ModEntities.FROSTOMPER.get(), FrostomperEntity.createAttributes().build());
    }

    private static String getName(String name) {
        return BlastFromThePast.MODID + ":" + name;
    }
}