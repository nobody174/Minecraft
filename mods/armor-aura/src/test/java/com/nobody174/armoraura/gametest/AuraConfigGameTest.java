//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.gametest;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.neoforged.neoforge.gametest.GameTestHolder;
import com.nobody174.armoraura.client.AuraConfig;
import com.nobody174.armoraura.client.AuraPreset;

@GameTestHolder("armoraura")
public class AuraConfigGameTest {

    /**
     * GameTest: /armoraura preset minimal applies correct settings
     * Simulates: /armoraura preset minimal
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testPresetMinimalApplication(GameTestHelper helper) {
        AuraPreset minimal = AuraPreset.MINIMAL;
        AuraConfig.applyPreset(minimal);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == minimal.particlesPerRing,
            String.format("Particles: expected %d, got %d",
                minimal.particlesPerRing, AuraConfig.getParticlesPerRing())
        );

        helper.assertTrue(
            AuraConfig.getRingCount() == minimal.ringCount,
            String.format("Rings: expected %d, got %d",
                minimal.ringCount, AuraConfig.getRingCount())
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura preset medium applies correct settings
     * Simulates: /armoraura preset medium
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testPresetMediumApplication(GameTestHelper helper) {
        AuraPreset medium = AuraPreset.MEDIUM;
        AuraConfig.applyPreset(medium);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == medium.particlesPerRing,
            "Medium preset particles not applied"
        );

        helper.assertTrue(
            Math.abs(AuraConfig.getRadius() - medium.auraRadius) < 0.01,
            "Medium preset radius not applied"
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura particles 12 updates config
     * Simulates: /armoraura particles 12
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testParticlesCommand(GameTestHelper helper) {
        AuraConfig.setParticlesPerRing(12);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == 12,
            "Particles not updated to 12"
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura particles command clamps values
     * Simulates: /armoraura particles 25 (should clamp to 20)
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testParticlesCommandClamping(GameTestHelper helper) {
        AuraConfig.setParticlesPerRing(25);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == 20,
            String.format("Particles should clamp to 20, got %d",
                AuraConfig.getParticlesPerRing())
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura radius 1.2 updates config
     * Simulates: /armoraura radius 1.2
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testRadiusCommand(GameTestHelper helper) {
        AuraConfig.setRadius(1.2);

        helper.assertTrue(
            Math.abs(AuraConfig.getRadius() - 1.2) < 0.01,
            "Radius not updated to 1.2"
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura speed 2.5 updates rotation speed
     * Simulates: /armoraura speed 2.5
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testSpeedCommand(GameTestHelper helper) {
        AuraConfig.setRotationSpeed(2.5);

        helper.assertTrue(
            Math.abs(AuraConfig.getRotationSpeed() - 2.5) < 0.01,
            "Rotation speed not updated to 2.5"
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura rings 5 updates ring count
     * Simulates: /armoraura rings 5
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testRingsCommand(GameTestHelper helper) {
        AuraConfig.setRingCount(5);

        helper.assertTrue(
            AuraConfig.getRingCount() == 5,
            "Ring count not updated to 5"
        );

        helper.succeed();
    }

    /**
     * GameTest: /armoraura effect flame updates particle effect
     * Simulates: /armoraura effect flame
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testEffectCommand(GameTestHelper helper) {
        AuraConfig.setParticleEffect("flame");

        helper.assertTrue(
            AuraConfig.getParticleEffect().equals("flame"),
            String.format("Effect should be 'flame', got '%s'",
                AuraConfig.getParticleEffect())
        );

        helper.succeed();
    }

    /**
     * GameTest: Effect command normalizes to lowercase
     * Simulates: /armoraura effect ELECTRIC_SPARK
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testEffectCommandNormalization(GameTestHelper helper) {
        AuraConfig.setParticleEffect("ELECTRIC_SPARK");

        helper.assertTrue(
            AuraConfig.getParticleEffect().equals("electric_spark"),
            "Effect should be normalized to lowercase"
        );

        helper.succeed();
    }

    /**
     * GameTest: Config values persist across multiple commands
     * Simulates: /armoraura particles 10 → /armoraura speed 1.5 → check both still set
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testConfigPersistence(GameTestHelper helper) {
        AuraConfig.setParticlesPerRing(10);
        AuraConfig.setRotationSpeed(1.5);
        AuraConfig.setRingCount(3);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == 10 &&
            Math.abs(AuraConfig.getRotationSpeed() - 1.5) < 0.01 &&
            AuraConfig.getRingCount() == 3,
            "Config values not persistent after multiple updates"
        );

        helper.succeed();
    }

    /**
     * GameTest: Preset overrides individual settings
     * Simulates: Set individual values → apply preset → verify preset values
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testPresetOverride(GameTestHelper helper) {
        // Set custom values
        AuraConfig.setParticlesPerRing(15);
        AuraConfig.setRingCount(6);

        // Apply preset (should override)
        AuraPreset minimal = AuraPreset.MINIMAL;
        AuraConfig.applyPreset(minimal);

        helper.assertTrue(
            AuraConfig.getParticlesPerRing() == minimal.particlesPerRing,
            "Preset should override custom particles value"
        );

        helper.succeed();
    }

    /**
     * GameTest: All ring heights are accessible
     * Tests that getRingHeights() returns valid data
     */
    @GameTest(template = "armoraura:empty_3x3x3", timeoutTicks = 100)
    public static void testRingHeights(GameTestHelper helper) {
        double[] heights = AuraConfig.getRingHeights();

        helper.assertTrue(
            heights != null && heights.length == 4,
            "Ring heights should have 4 entries"
        );

        helper.assertTrue(
            heights[0] == 0.3 && heights[1] == 0.8 &&
            heights[2] == 1.3 && heights[3] == 1.85,
            "Ring heights should have correct values"
        );

        helper.succeed();
    }
}

// Built with assistance from Claude Code by Anthropic.
