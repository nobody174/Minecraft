//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import com.nobody174.armoraura.ArmorAuraMod;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderFrameEvent;

import java.util.HashMap;
import java.util.Map;

public class AuraRenderer {

    private static AuraPreset currentPreset = AuraPreset.MEDIUM;
    private static long frameCount = 0;
    private static final Map<Integer, AuraData> playerAuras = new HashMap<>();

    public static class AuraData {
        public boolean hasAura;
        public int color;
        public float intensity;

        public AuraData(boolean hasAura, int color, float intensity) {
            this.hasAura = hasAura;
            this.color = color;
            this.intensity = intensity;
        }
    }

    @SubscribeEvent
    public static void onRenderFrame(RenderFrameEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null) {
            ArmorAuraMod.LOGGER.debug("[AuraRenderer] Render frame skipped: level is null");
            return;
        }

        Level level = minecraft.level;
        frameCount++;

        // Get day/night intensity
        long dayTime = level.getDayTime() % 24000;
        boolean isNight = dayTime >= 13000;
        float intensity = isNight ? 1.0f : 0.5f;

        // Render auras for players with packet data
        if (minecraft.player != null) {
            for (Player player : level.players()) {
                AuraData data = playerAuras.get(player.getId());
                if (data != null && data.hasAura) {
                    renderAura(minecraft, player, intensity);
                }
            }
        }
    }

    private static void renderAura(Minecraft minecraft, Player player, float intensity) {
        double x = player.getX();
        double baseY = player.getY();
        double z = player.getZ();

        // Use config values if available, otherwise fall back to preset
        int particlesPerRing = AuraConfig.getParticlesPerRing();
        double radius = AuraConfig.getRadius();
        double speed = AuraConfig.getRotationSpeed();
        int ringCount = AuraConfig.getRingCount();
        String particleEffectName = AuraConfig.getParticleEffect();

        // Get particle type from config
        var particleType = getParticleType(particleEffectName);
        if (particleType == null) {
            particleType = ParticleTypes.GLOW; // fallback to glow
        }

        // Rotation effect - slowly rotate the aura based on day/night intensity and speed
        double rotationOffset = (frameCount * speed * intensity) % 360;

        // Create multiple rings at different heights
        double[] ringHeights = AuraConfig.getRingHeights();
        for (int ringIndex = 0; ringIndex < ringCount && ringIndex < ringHeights.length; ringIndex++) {
            double ringHeight = ringHeights[ringIndex];

            // Create a thin ring with sparse particles
            for (int i = 0; i < particlesPerRing; i++) {
                double angle = (2.0 * Math.PI * i) / particlesPerRing + Math.toRadians(rotationOffset);
                double px = x + Math.cos(angle) * radius;
                double pz = z + Math.sin(angle) * radius;
                double py = baseY + ringHeight;

                // Use configured particle type
                minecraft.level.addParticle(
                    particleType,
                    px, py, pz,
                    0.0, 0.0, 0.0
                );
            }
        }
    }

    private static net.minecraft.core.particles.ParticleOptions getParticleType(String effectName) {
        return switch (effectName.toLowerCase()) {
            case "glow" -> ParticleTypes.GLOW;
            case "flame" -> ParticleTypes.FLAME;
            case "electric_spark" -> ParticleTypes.ELECTRIC_SPARK;
            case "crit" -> ParticleTypes.CRIT;
            case "end_rod" -> ParticleTypes.END_ROD;
            case "soul" -> ParticleTypes.SOUL;
            case "portal" -> ParticleTypes.PORTAL;
            case "dragon_breath" -> ParticleTypes.DRAGON_BREATH;
            case "happy_villager" -> ParticleTypes.HAPPY_VILLAGER;
            case "note" -> ParticleTypes.NOTE;
            default -> ParticleTypes.GLOW;
        };
    }

    public static void setPreset(AuraPreset preset) {
        currentPreset = preset;
        ArmorAuraMod.LOGGER.info("[AuraRenderer] Aura preset changed to: {}", preset.displayName);
    }

    public static AuraPreset getPreset() {
        return currentPreset;
    }

    public static void updateAuraData(int playerId, boolean hasAura, int color, float intensity) {
        ArmorAuraMod.LOGGER.info("[AuraRenderer] updateAuraData: playerId={}, hasAura={}, color=0x{}, intensity={}",
            playerId, hasAura, Integer.toHexString(color), intensity);
        playerAuras.put(playerId, new AuraData(hasAura, color, intensity));

        // Update HUD display and sound for local player
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.player.getId() == playerId) {
            ArmorAuraMod.LOGGER.info("[AuraRenderer] Updating HUD and sound for local player");
            AuraHUD.setAuraState(hasAura, color, intensity);
            AuraSoundManager.updateAuraSound(hasAura, intensity);
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
