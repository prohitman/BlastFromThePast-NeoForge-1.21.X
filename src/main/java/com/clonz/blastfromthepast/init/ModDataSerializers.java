package com.clonz.blastfromthepast.init;

import com.clonz.blastfromthepast.BlastFromThePast;
import com.clonz.blastfromthepast.entity.FrostomperEntity;
import com.clonz.blastfromthepast.entity.misc.StateValue;
import com.clonz.blastfromthepast.entity.misc.TransitioningState;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class ModDataSerializers {
    public static final DeferredRegister<EntityDataSerializer<?>> DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, BlastFromThePast.MODID);

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<TransitioningState>> TRANSITIONING_STATE = DATA_SERIALIZERS.register("transitioning_state",
            () -> EntityDataSerializer.forValueType(StateValue.codec(TransitioningState.values())));

    public static final DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<FrostomperEntity.IdleState>> FROSTOMPER_IDLE_STATE = DATA_SERIALIZERS.register("frostomper_idle_state",
            () -> EntityDataSerializer.forValueType(StateValue.codec(FrostomperEntity.IdleState.values())));
}
