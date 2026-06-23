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

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import com.nobody174.buddybeast.BuddyBeast;
import com.nobody174.buddybeast.entity.BuddyBeastEntity;

public class BuddyNetworkHandler {
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(BuddyBeast.MOD_ID);

        registrar.playToClient(
            BuddySyncPacket.TYPE,
            BuddySyncPacket.CODEC,
            BuddyNetworkHandler::handleBuddySyncPacket
        );
    }

    private static void handleBuddySyncPacket(BuddySyncPacket packet, net.neoforged.neoforge.network.handling.IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            if (level == null) {
                return;
            }
            Entity entity = level.getEntity(packet.entityId());
            if (entity instanceof BuddyBeastEntity buddy) {
                buddy.setHealth(packet.health());
            }
        });
    }
}

// Built with assistance from Claude Code by Anthropic.
