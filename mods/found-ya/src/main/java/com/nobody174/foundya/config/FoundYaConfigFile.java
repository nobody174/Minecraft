//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.config;

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
 * Loads/saves every {@link FoundYaProfile} plus the active profile
 * name to {@code config/foundya/foundya-config.json}.
 * Missing or invalid keys fall back to whatever defaults
 * {@link FoundYaConfig} already seeded in memory — a corrupted
 * config file degrades to defaults rather than crashing. Also reads the
 * old pre-profiles flat-key layout (a single set of settings with no
 * "profiles" array) so upgrading doesn't discard an existing config.
 */
public final class FoundYaConfigFile {

    private static final Logger LOGGER = LoggerFactory.getLogger("FoundYa");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Written on every save; not yet read on load since the "profiles" key
     * alone is still enough to distinguish the current format from the
     * legacy flat layout. Reserved so a future format change has a real
     * version to branch on instead of another structural guess.
     */
    private static final int CURRENT_SCHEMA_VERSION = 1;

    private static File configFile;

    private FoundYaConfigFile() {
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
        List<FoundYaProfile> loaded = new ArrayList<>();
        JsonArray profilesArray = root.getAsJsonArray("profiles");
        for (var element : profilesArray) {
            JsonObject profileJson = element.getAsJsonObject();
            if (!profileJson.has("name")) {
                continue;
            }
            FoundYaProfile profile = new FoundYaProfile(profileJson.get("name").getAsString());
            applyProfileFields(profile, profileJson);
            loaded.add(profile);
        }
        String activeName = root.has("activeProfile") ? root.get("activeProfile").getAsString() : null;
        FoundYaConfig.replaceProfiles(loaded, activeName);
    }

    private static void loadLegacyFlatConfig(JsonObject root) {
        FoundYaProfile defaultProfile = FoundYaConfig.getActiveProfile();
        applyProfileFields(defaultProfile, root);
    }

    private static void applyProfileFields(FoundYaProfile profile, JsonObject json) {
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
            for (FoundYaProfile profile : FoundYaConfig.getAllProfiles().values()) {
                profilesArray.add(toJson(profile));
            }
            root.addProperty("schemaVersion", CURRENT_SCHEMA_VERSION);
            root.add("profiles", profilesArray);
            root.addProperty("activeProfile", FoundYaConfig.getActiveProfileName());
            GSON.toJson(root, writer);
        } catch (IOException e) {
            LOGGER.error("Failed to save config", e);
        }
    }

    private static JsonObject toJson(FoundYaProfile profile) {
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
        Path configDir = Minecraft.getInstance().gameDirectory.toPath().resolve("config").resolve("foundya");
        try {
            Files.createDirectories(configDir);
        } catch (IOException e) {
            LOGGER.error("Failed to create config directory", e);
        }
        configFile = configDir.resolve("foundya-config.json").toFile();
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
