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
 * Client-to-server request to update the sender's own {@code /auraglow}
 * state. The server validates nothing beyond clamping (cosmetic-only, no
 * gameplay impact) and re-broadcasts an {@link AuraGlowStatePacket} to all
 * players so the glow is visible to everyone.
 */
public record AuraGlowRequestPacket(
    boolean glowing,
    int color,
    float intensity
) implements CustomPacketPayload {

    public static final Type<AuraGlowRequestPacket> TYPE = new Type<>(
        ResourceLocation.fromNamespaceAndPath("armoraura", "aura_glow_request")
    );

    public static final StreamCodec<ByteBuf, AuraGlowRequestPacket> CODEC = StreamCodec.composite(
        ByteBufCodecs.BOOL, AuraGlowRequestPacket::glowing,
        ByteBufCodecs.INT, AuraGlowRequestPacket::color,
        ByteBufCodecs.FLOAT, AuraGlowRequestPacket::intensity,
        AuraGlowRequestPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}

// Built with assistance from Claude Code by Anthropic.
