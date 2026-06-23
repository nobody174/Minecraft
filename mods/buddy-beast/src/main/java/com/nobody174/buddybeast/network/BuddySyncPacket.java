//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.buddybeast.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import com.nobody174.buddybeast.BuddyBeast;

public record BuddySyncPacket(
    int entityId,
    float health
) implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(BuddyBeast.MOD_ID, "buddy_sync");
    public static final net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<BuddySyncPacket> TYPE =
        new net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<>(ID);

    public static final StreamCodec<ByteBuf, BuddySyncPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.INT, BuddySyncPacket::entityId,
        ByteBufCodecs.FLOAT, BuddySyncPacket::health,
        BuddySyncPacket::new
    );

    @Override
    public net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
