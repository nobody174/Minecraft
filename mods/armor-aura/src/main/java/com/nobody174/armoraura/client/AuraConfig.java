//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import com.nobody174.armoraura.ArmorAuraMod;

public class AuraConfig {

    // Current aura settings
    private static int particlesPerRing = 8;
    private static double radius = 0.5;
    private static double rotationSpeed = 1.0;
    private static int ringCount = 4;
    private static String particleEffect = "glow";

    // Ring heights
    private static final double[] DEFAULT_RING_HEIGHTS = {0.3, 0.8, 1.3, 1.85};

    public static void setParticlesPerRing(int count) {
        particlesPerRing = Math.max(1, Math.min(20, count));
        ArmorAuraMod.LOGGER.info("[AuraConfig] Particles per ring: {}", particlesPerRing);
    }

    public static void setRadius(double r) {
        radius = Math.max(0.1, Math.min(1.5, r));
        ArmorAuraMod.LOGGER.info("[AuraConfig] Radius: {}", radius);
    }

    public static void setRotationSpeed(double speed) {
        rotationSpeed = Math.max(0.1, Math.min(3.0, speed));
        ArmorAuraMod.LOGGER.info("[AuraConfig] Rotation speed: {}", rotationSpeed);
    }

    public static void setRingCount(int count) {
        ringCount = Math.max(1, Math.min(4, count));
        ArmorAuraMod.LOGGER.info("[AuraConfig] Ring count: {}", ringCount);
    }

    public static void setParticleEffect(String effect) {
        particleEffect = effect.toLowerCase();
        ArmorAuraMod.LOGGER.info("[AuraConfig] Particle effect: {}", particleEffect);
    }

    public static void applyPreset(AuraPreset preset) {
        particlesPerRing = preset.particlesPerRing;
        radius = preset.auraRadius;
        rotationSpeed = preset.rotationSpeed;
        ringCount = preset.ringCount;
        ArmorAuraMod.LOGGER.info("[AuraConfig] Applied preset: {}", preset.displayName);
    }

    // Getters
    public static int getParticlesPerRing() { return particlesPerRing; }
    public static double getRadius() { return radius; }
    public static double getRotationSpeed() { return rotationSpeed; }
    public static int getRingCount() { return ringCount; }
    public static String getParticleEffect() { return particleEffect; }
    public static double[] getRingHeights() { return DEFAULT_RING_HEIGHTS; }

}

// Built with assistance from Claude Code by Anthropic.
