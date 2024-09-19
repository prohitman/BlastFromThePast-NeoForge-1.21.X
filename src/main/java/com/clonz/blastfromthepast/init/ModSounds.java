package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, BlastFromThePast.MODID);

    // GLACEROS
    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_GLACEROS_DEATH = SOUND_EVENTS.register(
            "glaceros_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "glaceros_death"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_GLACEROS_HURT = SOUND_EVENTS.register(
            "glaceros_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "glaceros_hurt"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_GLACEROS_IDLE = SOUND_EVENTS.register(
            "glaceros_idle",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "glaceros_idle"))
    );

    // SNOWDO
    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_SNOWDO_DEATH = SOUND_EVENTS.register(
            "snowdo_death",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "snowdo_death"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_SNOWDO_HURT = SOUND_EVENTS.register(
            "snowdo_hurt",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "snowdo_hurt"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_SNOWDO_IDLE = SOUND_EVENTS.register(
            "snowdo_idle",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "snowdo_idle"))
    );

    public static final DeferredHolder<SoundEvent, SoundEvent> SOUND_ENTITY_SNOWDO_TRIP = SOUND_EVENTS.register(
            "snowdo_trip",
            () -> SoundEvent.createVariableRangeEvent(ResourceLocation.
                    fromNamespaceAndPath("blastfromthepast", "snowdo_trip"))
    );

    // FROSTOMPER
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_CHARGE = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_charge");
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_DEATH = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_HURT = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_IDLE = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_STOMP = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_stomp");
    public static final DeferredHolder<SoundEvent, SoundEvent> FROSTOMPER_TRUMPET = registerSoundEventForEntityType(ModEntities.FROSTOMPER, "_trumpet");

    // PSYCHO BEAR
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_DEATH = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_death");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_EAT = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_eat");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_HURT = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_hurt");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_IDLE = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_idle");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_ROAR = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_roar");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_SCRATCH = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_scratch");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_SLASH = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_slash");
    public static final DeferredHolder<SoundEvent, SoundEvent> PSYCHO_BEAR_SNORE = registerSoundEventForEntityType(ModEntities.PSYCHO_BEAR, "_snore");

    private static <T extends Entity> DeferredHolder<SoundEvent, SoundEvent> registerSoundEventForEntityType(DeferredHolder<EntityType<?>, EntityType<T>> entityTypeHolder, String suffix) {
        ResourceLocation soundEventLocation = entityTypeHolder.getId().withSuffix(suffix);
        return SOUND_EVENTS.register(
                soundEventLocation.getPath(),
                () -> SoundEvent.createVariableRangeEvent(soundEventLocation)
        );
    }
}
