//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AuraConfig Tests")
public class AuraConfigTest {

    @BeforeEach
    void resetConfig() {
        // Reset to defaults before each test
        AuraConfig.setParticlesPerRing(8);
        AuraConfig.setRadius(0.5);
        AuraConfig.setRotationSpeed(1.0);
        AuraConfig.setRingCount(4);
        AuraConfig.setParticleEffect("glow");
    }

    @Test
    @DisplayName("Should clamp particles per ring between 1 and 20")
    void testParticlesPerRingClamping() {
        AuraConfig.setParticlesPerRing(0);
        assertEquals(1, AuraConfig.getParticlesPerRing(), "Should clamp to minimum 1");

        AuraConfig.setParticlesPerRing(25);
        assertEquals(20, AuraConfig.getParticlesPerRing(), "Should clamp to maximum 20");

        AuraConfig.setParticlesPerRing(10);
        assertEquals(10, AuraConfig.getParticlesPerRing(), "Should accept valid value");
    }

    @Test
    @DisplayName("Should clamp radius between 0.1 and 1.5")
    void testRadiusClamping() {
        AuraConfig.setRadius(0.0);
        assertEquals(0.1, AuraConfig.getRadius(), "Should clamp to minimum 0.1");

        AuraConfig.setRadius(2.0);
        assertEquals(1.5, AuraConfig.getRadius(), "Should clamp to maximum 1.5");

        AuraConfig.setRadius(0.8);
        assertEquals(0.8, AuraConfig.getRadius(), "Should accept valid value");
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 1.0, 2.0, 3.5})
    @DisplayName("Should clamp rotation speed between 0.1 and 3.0")
    void testRotationSpeedClamping(double input) {
        AuraConfig.setRotationSpeed(input);
        double result = AuraConfig.getRotationSpeed();
        assertTrue(result >= 0.1 && result <= 3.0,
            String.format("Speed %f should be between 0.1 and 3.0", result));
    }

    @Test
    @DisplayName("Should clamp ring count between 1 and 4")
    void testRingCountClamping() {
        AuraConfig.setRingCount(0);
        assertEquals(1, AuraConfig.getRingCount(), "Should clamp to minimum 1");

        AuraConfig.setRingCount(10);
        assertEquals(4, AuraConfig.getRingCount(), "Should clamp to maximum 4");

        AuraConfig.setRingCount(3);
        assertEquals(3, AuraConfig.getRingCount(), "Should accept valid value");
    }

    @Test
    @DisplayName("Should lowercase particle effect names")
    void testParticleEffectNormalization() {
        AuraConfig.setParticleEffect("GLOW");
        assertEquals("glow", AuraConfig.getParticleEffect(), "Should normalize to lowercase");

        AuraConfig.setParticleEffect("FlAme");
        assertEquals("flame", AuraConfig.getParticleEffect(), "Should normalize mixed case");
    }

    @Test
    @DisplayName("Should apply preset correctly")
    void testApplyPreset() {
        AuraPreset preset = AuraPreset.MINIMAL;
        AuraConfig.applyPreset(preset);

        assertEquals(preset.particlesPerRing, AuraConfig.getParticlesPerRing(),
            "Particles should match preset");
        assertEquals(preset.auraRadius, AuraConfig.getRadius(),
            "Radius should match preset");
        assertEquals(preset.rotationSpeed, AuraConfig.getRotationSpeed(),
            "Rotation speed should match preset");
        assertEquals(preset.ringCount, AuraConfig.getRingCount(),
            "Ring count should match preset");
    }

    @Test
    @DisplayName("Should return correct ring heights")
    void testRingHeights() {
        double[] heights = AuraConfig.getRingHeights();
        assertNotNull(heights, "Ring heights should not be null");
        assertEquals(4, heights.length, "Should have 4 ring heights");
        assertEquals(0.3, heights[0], "First height should be 0.3");
        assertEquals(0.8, heights[1], "Second height should be 0.8");
        assertEquals(1.3, heights[2], "Third height should be 1.3");
        assertEquals(1.85, heights[3], "Fourth height should be 1.85");
    }

    @Test
    @DisplayName("Should accept valid particle effects")
    void testValidParticleEffects() {
        String[] validEffects = {"glow", "flame", "electric_spark", "crit", "end_rod",
                                "soul", "portal", "dragon_breath", "sparkle"};

        for (String effect : validEffects) {
            AuraConfig.setParticleEffect(effect);
            assertEquals(effect, AuraConfig.getParticleEffect(),
                String.format("Should accept effect: %s", effect));
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
