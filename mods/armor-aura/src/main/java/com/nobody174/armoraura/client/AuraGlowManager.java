//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import net.minecraft.client.player.AbstractClientPlayer;

import java.util.HashMap;
import java.util.Map;

/**
 * Tracks which players currently have the {@code /auraglow} effect active and
 * with which color/intensity. The local player's state is driven directly by
 * {@link AuraGlowConfig}; remote players are driven by whatever state was
 * synced over the network (currently: same as local, since the effect is a
 * client-only cosmetic toggle and is not synced to other clients yet).
 */
public class AuraGlowManager {

    private static final Map<Integer, RemoteState> remoteStates = new HashMap<>();

    private record RemoteState(boolean glowing, int color, float intensity) {
    }

    public static boolean isGlowing(AbstractClientPlayer player) {
        if (isLocalPlayer(player)) {
            return AuraGlowConfig.isEnabled();
        }
        RemoteState state = remoteStates.get(player.getId());
        return state != null && state.glowing();
    }

    public static int getColor(AbstractClientPlayer player) {
        if (isLocalPlayer(player)) {
            return AuraGlowConfig.getColor();
        }
        RemoteState state = remoteStates.get(player.getId());
        return state != null ? state.color() : AuraGlowConfig.getColor();
    }

    public static float getIntensity(AbstractClientPlayer player) {
        if (isLocalPlayer(player)) {
            return AuraGlowConfig.getIntensity();
        }
        RemoteState state = remoteStates.get(player.getId());
        return state != null ? state.intensity() : AuraGlowConfig.getIntensity();
    }

    public static void setRemoteState(int playerId, boolean glowing, int color, float intensity) {
        remoteStates.put(playerId, new RemoteState(glowing, color, intensity));
    }

    public static void clearRemoteState(int playerId) {
        remoteStates.remove(playerId);
    }

    public static void clearAll() {
        remoteStates.clear();
    }

    private static boolean isLocalPlayer(AbstractClientPlayer player) {
        net.minecraft.client.Minecraft minecraft = net.minecraft.client.Minecraft.getInstance();
        return minecraft.player != null && minecraft.player.getId() == player.getId();
    }
}

// Built with assistance from Claude Code by Anthropic.
