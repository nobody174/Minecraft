//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

public enum AuraPreset {
    MINIMAL(
        "Minimal",
        3,      // PARTICLES_PER_RING
        0.7,    // AURA_RADIUS
        4,      // RING_COUNT
        0.1,    // ROTATION_SPEED
        new double[]{0.3, 0.8, 1.3, 1.85}
    ),
    MEDIUM(
        "Medium",
        10,     // PARTICLES_PER_RING
        0.7,    // AURA_RADIUS
        2,      // RING_COUNT
        1.3,    // ROTATION_SPEED
        new double[]{0.3, 0.8, 1.3, 1.85}
    ),
    MAXIMUM(
        "Maximum",
        20,     // PARTICLES_PER_RING
        0.7,    // AURA_RADIUS
        4,      // RING_COUNT
        3.0,    // ROTATION_SPEED
        new double[]{0.3, 0.8, 1.3, 1.85}
    );

    public final String displayName;
    public final int particlesPerRing;
    public final double auraRadius;
    public final int ringCount;
    public final double rotationSpeed;
    public final double[] ringHeights;

    AuraPreset(String displayName, int particlesPerRing, double auraRadius, int ringCount,
               double rotationSpeed, double[] ringHeights) {
        this.displayName = displayName;
        this.particlesPerRing = particlesPerRing;
        this.auraRadius = auraRadius;
        this.ringCount = ringCount;
        this.rotationSpeed = rotationSpeed;
        this.ringHeights = ringHeights;
    }

    public static AuraPreset fromName(String name) {
        try {
            return AuraPreset.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return MEDIUM; // Default fallback
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
