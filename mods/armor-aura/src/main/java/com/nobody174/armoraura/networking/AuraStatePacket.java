//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record AuraStatePacket(
    int playerId,
    boolean hasAura,
    int auraColor,
    float intensity
) implements CustomPacketPayload {

    public static final Type<AuraStatePacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("armoraura", "aura_state")
    );

    public static final StreamCodec<ByteBuf, AuraStatePacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, AuraStatePacket::playerId,
        ByteBufCodecs.BOOL, AuraStatePacket::hasAura,
        ByteBufCodecs.INT, AuraStatePacket::auraColor,
        ByteBufCodecs.FLOAT, AuraStatePacket::intensity,
        AuraStatePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
