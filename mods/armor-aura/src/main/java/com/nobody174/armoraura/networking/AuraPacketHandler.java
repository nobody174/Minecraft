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

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class AuraPacketHandler {

    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1.0");

        registrar.playToClient(
            AuraStatePacket.TYPE,
            AuraStatePacket.CODEC,
            (payload, context) -> {
                context.enqueueWork(() -> {
                    // Client receives aura state and updates renderer
                    com.nobody174.armoraura.client.AuraRenderer.updateAuraData(
                        payload.playerId(),
                        payload.hasAura(),
                        payload.auraColor(),
                        payload.intensity()
                    );
                }).exceptionally(e -> {
                    throw new RuntimeException("Failed to handle aura packet", e);
                });
            }
        );

        registrar.playToClient(
            AuraGlowStatePacket.TYPE,
            AuraGlowStatePacket.CODEC,
            (payload, context) -> {
                context.enqueueWork(() -> {
                    com.nobody174.armoraura.client.AuraGlowManager.setRemoteState(
                        payload.playerId(),
                        payload.glowing(),
                        payload.color(),
                        payload.intensity()
                    );
                }).exceptionally(e -> {
                    throw new RuntimeException("Failed to handle aura glow state packet", e);
                });
            }
        );

        registrar.playToServer(
            AuraGlowRequestPacket.TYPE,
            AuraGlowRequestPacket.CODEC,
            (payload, context) -> {
                context.enqueueWork(() -> {
                    if (!(context.player() instanceof ServerPlayer sender)) {
                        return;
                    }
                    int clampedColor = payload.color() & 0xFFFFFF;
                    float clampedIntensity = Math.max(0.05f, Math.min(1.0f, payload.intensity()));
                    AuraGlowStatePacket broadcast = new AuraGlowStatePacket(
                        sender.getId(), payload.glowing(), clampedColor, clampedIntensity
                    );
                    PacketDistributor.sendToPlayersTrackingEntityAndSelf(sender, broadcast);
                }).exceptionally(e -> {
                    throw new RuntimeException("Failed to handle aura glow request packet", e);
                });
            }
        );
    }
}


// Built with assistance from Claude Code by Anthropic.
