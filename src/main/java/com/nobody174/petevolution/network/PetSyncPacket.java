//
// Pet Evolution
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/pet-evolution
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.petevolution.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.component.PetData;

public record PetSyncPacket(
    int playerId,
    PetData petData
) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(PetEvolution.MOD_ID, "pet_sync");
    public static final Type<PetSyncPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<ByteBuf, PetSyncPacket> CODEC = StreamCodec.composite(
        net.minecraft.network.codec.ByteBufCodecs.VAR_INT, PetSyncPacket::playerId,
        PetData.STREAM_CODEC, PetSyncPacket::petData,
        PetSyncPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
