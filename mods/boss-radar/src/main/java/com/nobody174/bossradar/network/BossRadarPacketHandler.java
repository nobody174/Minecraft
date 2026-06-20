//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.network;

import net.minecraft.client.Minecraft;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BossRadarPacketHandler {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void handleSyncPacket(BossRadarSyncPacket packet, IPayloadContext context) {
        LOGGER.info("[BossRadar] Received sync packet: targetName={}, distance={}", packet.getTargetName(), packet.getDistance());

        context.enqueueWork(() -> {
            Minecraft mc = Minecraft.getInstance();
            var player = mc.player;
            if (player != null) {
                if (packet.getTargetName().isEmpty()) {
                    LOGGER.info("[BossRadar] Clearing boss radar data (no target)");
                    player.getPersistentData().remove("BossRadar_TargetName");
                    player.getPersistentData().remove("BossRadar_Distance");
                    player.getPersistentData().remove("BossRadar_TargetX");
                    player.getPersistentData().remove("BossRadar_TargetY");
                    player.getPersistentData().remove("BossRadar_TargetZ");
                } else {
                    String targetName = packet.getTargetName();
                    if (targetName != null) {
                        LOGGER.info("[BossRadar] Storing boss data: {} at distance {}", targetName, packet.getDistance());
                        player.getPersistentData().putString("BossRadar_TargetName", targetName);
                    }
                    player.getPersistentData().putDouble("BossRadar_Distance", packet.getDistance());
                    player.getPersistentData().putDouble("BossRadar_TargetX", packet.getTargetX());
                    player.getPersistentData().putDouble("BossRadar_TargetY", packet.getTargetY());
                    player.getPersistentData().putDouble("BossRadar_TargetZ", packet.getTargetZ());
                }
            } else {
                LOGGER.warn("[BossRadar] Player is null in packet handler!");
            }
        });
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
