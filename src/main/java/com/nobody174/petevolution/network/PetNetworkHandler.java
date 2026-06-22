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

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import com.nobody174.petevolution.PetEvolution;
import com.nobody174.petevolution.client.ClientPetState;

public class PetNetworkHandler {
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(PetEvolution.MOD_ID);

        registrar.playToClient(
            PetSyncPacket.TYPE,
            PetSyncPacket.CODEC,
            PetNetworkHandler::handlePetSyncPacket
        );
    }

    private static void handlePetSyncPacket(PetSyncPacket packet, IPayloadContext context) {
        context.enqueueWork(() -> {
            if (Minecraft.getInstance().player == null) {
                return;
            }
            ClientPetState.setActivePetData(packet.petData());
        });
    }
}

// Built with assistance from Claude Code by Anthropic.
