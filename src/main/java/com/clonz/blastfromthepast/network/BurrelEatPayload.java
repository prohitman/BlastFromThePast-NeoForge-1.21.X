package com.clonz.blastfromthepast.network;

import com.clonz.blastfromthepast.BlastFromThePast;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record BurrelEatPayload(int entityId) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<BurrelEatPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(BlastFromThePast.MODID, "burrel_eat"));
    public static final StreamCodec<ByteBuf, BurrelEatPayload> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            BurrelEatPayload::entityId,
            BurrelEatPayload::new
    );
    
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
