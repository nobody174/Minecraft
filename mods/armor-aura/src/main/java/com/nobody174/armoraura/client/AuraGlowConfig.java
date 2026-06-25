//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

/**
 * Settings for the lightweight {@code /auraglow} outline-overlay effect.
 * Independent of the particle-ring system in {@link AuraConfig}.
 */
public class AuraGlowConfig {

    private static boolean enabled = false;
    private static int color = 0x33CCFF; // cyan, matches the project's original aura color target
    private static float intensity = 0.6f; // base alpha, 0..1
    private static float pulseSpeed = 1.0f; // cycles per ~ (2*PI*20) ticks, 0 disables pulsing

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static int getColor() {
        return color;
    }

    public static void setColor(int rgb) {
        color = rgb & 0xFFFFFF;
    }

    public static float getIntensity() {
        return intensity;
    }

    public static void setIntensity(float value) {
        intensity = Math.max(0.05f, Math.min(1.0f, value));
    }

    public static float getPulseSpeed() {
        return pulseSpeed;
    }

    public static void setPulseSpeed(float value) {
        pulseSpeed = Math.max(0.0f, Math.min(5.0f, value));
    }
}

// Built with assistance from Claude Code by Anthropic.
