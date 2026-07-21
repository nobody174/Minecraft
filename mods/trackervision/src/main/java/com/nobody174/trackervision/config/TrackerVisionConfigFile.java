//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Loads/saves every {@link TrackerVisionProfile} plus the active profile
 * name to {@code config/trackervision/trackervision-config.json}.
 * Missing or invalid keys fall back to whatever defaults
 * {@link TrackerVisionConfig} already seeded in memory — a corrupted
 * config file degrades to defaults rather than crashing. Also reads the
 * old pre-profiles flat-key layout (a single set of settings with no
 * "profiles" array) so upgrading doesn't discard an existing config.
 */
public final class TrackerVisionConfigFile {

    private static final Logger LOGGER = LoggerFactory.getLogger("TrackerVision");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static File configFile;

    private TrackerVisionConfigFile() {
    }

    public static void load() {
        ensureInitialized();
        if (!configFile.exists()) {
            save();
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonObject root = GSON.fromJson(reader, JsonObject.class);
            if (root == null) {
                LOGGER.warn("Config file is empty, keeping defaults");
                return;
            }

            if (root.has("profiles")) {
                loadProfiles(root);
            } else {
                loadLegacyFlatConfig(root);
            }
        } catch (IOException e) {
            LOGGER.error("Failed to load config, keeping defaults", e);
        }
    }

    private static void loadProfiles(JsonObject root) {
        List<TrackerVisionProfile> loaded = new ArrayList<>();
        JsonArray profilesArray = root.getAsJsonArray("profiles");
        for (var element : profilesArray) {
            JsonObject profileJson = element.getAsJsonObject();
            if (!profileJson.has("name")) {
                continue;
            }
            TrackerVisionProfile profile = new TrackerVisionProfile(profileJson.get("name").getAsString());
            applyProfileFields(profile, profileJson);
            loaded.add(profile);
        }
        String activeName = root.has("activeProfile") ? root.get("activeProfile").getAsString() : null;
        TrackerVisionConfig.replaceProfiles(loaded, activeName);
    }

    private static void loadLegacyFlatConfig(JsonObject root) {
        TrackerVisionProfile defaultProfile = TrackerVisionConfig.getActiveProfile();
        applyProfileFields(defaultProfile, root);
    }

    private static void applyProfileFields(TrackerVisionProfile profile, JsonObject json) {
        if (json.has("trackingEnabled")) {
            profile.setTrackingEnabled(json.get("trackingEnabled").getAsBoolean());
        }
        if (json.has("nearDistance")) {
            profile.setNearDistance(json.get("nearDistance").getAsFloat());
        }
        if (json.has("farDistance")) {
            profile.setFarDistance(json.get("farDistance").getAsFloat());
        }
        if (json.has("bracketBaseSize")) {
            profile.setBracketBaseSize(json.get("bracketBaseSize").getAsInt());
        }
        if (json.has("trackingAccentColor")) {
            try {
                profile.setTrackingAccentColor(Integer.parseInt(json.get("trackingAccentColor").getAsString(), 16));
            } catch (NumberFormatException e) {
                LOGGER.warn("Invalid trackingAccentColor in config, keeping default", e);
            }
        }
        if (json.has("beaconEnabled")) {
            profile.setBeaconEnabled(json.get("beaconEnabled").getAsBoolean());
        }
        if (json.has("beaconDistance")) {
            profile.setBeaconDistance(json.get("beaconDistance").getAsFloat());
        }
        if (json.has("rimBoostEnabled")) {
            profile.setRimBoostEnabled(json.get("rimBoostEnabled").getAsBoolean());
        }
    }

    public static void save() {
        ensureInitialized();
        try (FileWriter writer = new FileWriter(configFile)) {
            JsonObject root = new JsonObject();
            JsonArray profilesArray = new JsonArray();
            for (TrackerVisionProfile profile : TrackerVisionConfig.getAllProfiles().values()) {
                profilesArray.add(toJson(profile));
            }
            root.add("profiles", profilesArray);
            root.addProperty("activeProfile", TrackerVisionConfig.getActiveProfileName());
            GSON.toJson(root, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    private static JsonObject toJson(TrackerVisionProfile profile) {
        JsonObject json = new JsonObject();
        json.addProperty("name", profile.getName());
        json.addProperty("trackingEnabled", profile.isTrackingEnabled());
        json.addProperty("nearDistance", profile.getNearDistance());
        json.addProperty("farDistance", profile.getFarDistance());
        json.addProperty("bracketBaseSize", profile.getBracketBaseSize());
        json.addProperty("trackingAccentColor", String.format("%06X", profile.getTrackingAccentColor() & 0x00FFFFFF));
        json.addProperty("beaconEnabled", profile.isBeaconEnabled());
        json.addProperty("beaconDistance", profile.getBeaconDistance());
        json.addProperty("rimBoostEnabled", profile.isRimBoostEnabled());
        return json;
    }

    private static void ensureInitialized() {
        if (configFile != null) {
            return;
        }
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("trackervision");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory", e);
        }
        configFile = configDir.resolve("trackervision-config.json").toFile();
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
