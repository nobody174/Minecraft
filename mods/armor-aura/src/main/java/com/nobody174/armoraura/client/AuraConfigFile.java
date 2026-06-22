//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.nobody174.armoraura.ArmorAuraMod;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AuraConfigFile {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;

    public static void init() {
        // Get the Minecraft config directory
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("armoraura");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            ArmorAuraMod.LOGGER.error("[AuraConfigFile] Failed to create config directory", e);
        }

        configFile = configDir.resolve("aura-config.json").toFile();

        // Create default config if it doesn't exist
        if (!configFile.exists()) {
            createDefaultConfig();
        }
    }

    private static void createDefaultConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject config = new JsonObject();
            config.addProperty("particlesPerRing", 8);
            config.addProperty("radius", 0.5);
            config.addProperty("rotationSpeed", 1.0);
            config.addProperty("ringCount", 4);
            config.addProperty("particleEffect", "glow");
            config.addProperty("preset", "medium");

            GSON.toJson(config, writer);
            ArmorAuraMod.LOGGER.info("[AuraConfigFile] Created default config at: {}", configFile.getAbsolutePath());
        } catch (IOException e) {
            ArmorAuraMod.LOGGER.error("[AuraConfigFile] Failed to create default config", e);
        }
    }

    public static void loadConfig() {
        // Ensure configFile is initialized
        if (configFile == null) {
            init();
        }

        if (!configFile.exists()) {
            createDefaultConfig();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject config = GSON.fromJson(reader, JsonObject.class);

            if (config == null) {
                ArmorAuraMod.LOGGER.error("[AuraConfigFile] Config file is empty");
                return;
            }

            // Load settings from JSON
            if (config.has("particlesPerRing")) {
                AuraConfig.setParticlesPerRing(config.get("particlesPerRing").getAsInt());
            }
            if (config.has("radius")) {
                AuraConfig.setRadius(config.get("radius").getAsDouble());
            }
            if (config.has("rotationSpeed")) {
                AuraConfig.setRotationSpeed(config.get("rotationSpeed").getAsDouble());
            }
            if (config.has("ringCount")) {
                AuraConfig.setRingCount(config.get("ringCount").getAsInt());
            }
            if (config.has("particleEffect")) {
                AuraConfig.setParticleEffect(config.get("particleEffect").getAsString());
            }
            if (config.has("preset")) {
                AuraPreset preset = AuraPreset.fromName(config.get("preset").getAsString());
                AuraRenderer.setPreset(preset);
            }

            ArmorAuraMod.LOGGER.info("[AuraConfigFile] Config loaded from: {}", configFile.getAbsolutePath());
        } catch (IOException e) {
            ArmorAuraMod.LOGGER.error("[AuraConfigFile] Failed to load config", e);
        }
    }

    public static void saveConfig() {
        // Ensure configFile is initialized
        if (configFile == null) {
            init();
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject config = new JsonObject();
            config.addProperty("particlesPerRing", AuraConfig.getParticlesPerRing());
            config.addProperty("radius", AuraConfig.getRadius());
            config.addProperty("rotationSpeed", AuraConfig.getRotationSpeed());
            config.addProperty("ringCount", AuraConfig.getRingCount());
            config.addProperty("particleEffect", AuraConfig.getParticleEffect());

            GSON.toJson(config, writer);
            ArmorAuraMod.LOGGER.info("[AuraConfigFile] Config saved to: {}", configFile.getAbsolutePath());
        } catch (IOException e) {
            ArmorAuraMod.LOGGER.error("[AuraConfigFile] Failed to save config", e);
        }
    }

    public static File getConfigFile() {
        // Ensure configFile is initialized before returning
        if (configFile == null) {
            init();
        }
        return configFile;
    }
}

// Built with assistance from Claude Code by Anthropic.
