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
    }
}


// Built with assistance from Claude Code by Anthropic.
