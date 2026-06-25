//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;

/**
 * Attaches {@link PlayerGlowAuraLayer} to both the wide and slim player
 * renderers so {@code /auraglow} works regardless of the player's skin model.
 *
 * <p>{@link #register(IEventBus)} is the only entry point safe to call from
 * {@code ArmorAuraMod}: its signature only mentions {@link IEventBus}, which
 * exists on the dedicated server too. The method references inside this
 * class (to {@link EntityRenderersEvent.AddLayers}, a client-only event type)
 * must never be touched from common code, or class verification fails on
 * the server with a {@code NoClassDefFoundError} for client-only render
 * classes, even if the registration is runtime-guarded.</p>
 */
public class AuraGlowLayerRegistration {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(AuraGlowLayerRegistration::onAddLayers);
        NeoForge.EVENT_BUS.addListener(AuraGlowLayerRegistration::onLoggingOut);
    }

    private static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event) {
        AuraGlowManager.clearAll();
    }

    private static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        addGlowLayer(event, PlayerSkin.Model.WIDE, false);
        addGlowLayer(event, PlayerSkin.Model.SLIM, true);
    }

    private static void addGlowLayer(EntityRenderersEvent.AddLayers event, PlayerSkin.Model skinModel, boolean slim) {
        PlayerRenderer renderer = event.getSkin(skinModel);
        if (renderer != null) {
            renderer.addLayer(new PlayerGlowAuraLayer(renderer, event.getContext().getModelSet(), slim));
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
