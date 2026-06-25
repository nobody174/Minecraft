//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.networking;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Server-to-client broadcast describing a player's current {@code /auraglow}
 * state, so the glow is visible to other players in multiplayer.
 */
public record AuraGlowStatePacket(
    int playerId,
    boolean glowing,
    int color,
    float intensity
) implements CustomPacketPayload {

    public static final Type<AuraGlowStatePacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("armoraura", "aura_glow_state")
    );

    public static final StreamCodec<ByteBuf, AuraGlowStatePacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.VAR_INT, AuraGlowStatePacket::playerId,
        ByteBufCodecs.BOOL, AuraGlowStatePacket::glowing,
        ByteBufCodecs.INT, AuraGlowStatePacket::color,
        ByteBufCodecs.FLOAT, AuraGlowStatePacket::intensity,
        AuraGlowStatePacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
