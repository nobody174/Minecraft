//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds every named {@link TrackerVisionProfile} plus which one is
 * active, and exposes the active profile's settings through the same
 * static getter/setter surface the rest of the mod already calls — so
 * adding profiles didn't require touching every call site that reads or
 * writes a setting, only how the setting is resolved. Persisted by
 * {@link TrackerVisionConfigFile}.
 */
public final class TrackerVisionConfig {

    private static final Map<String, TrackerVisionProfile> profiles = new LinkedHashMap<>();
    private static String activeProfileName;

    static {
        registerDefaultProfiles();
    }

    private TrackerVisionConfig() {
    }

    private static void registerDefaultProfiles() {
        TrackerVisionProfile defaultProfile = new TrackerVisionProfile("Default");
        profiles.put(defaultProfile.getName(), defaultProfile);

        TrackerVisionProfile pvp = new TrackerVisionProfile("PvP");
        pvp.setNearDistance(6.0F);
        pvp.setFarDistance(32.0F);
        pvp.setBracketBaseSize(20);
        pvp.setTrackingAccentColor(0xFFFF4040);
        pvp.setBeaconEnabled(false);
        profiles.put(pvp.getName(), pvp);

        TrackerVisionProfile exploration = new TrackerVisionProfile("Exploration");
        exploration.setNearDistance(12.0F);
        exploration.setFarDistance(192.0F);
        exploration.setBracketBaseSize(24);
        exploration.setTrackingAccentColor(0xFF35E0E0);
        exploration.setBeaconEnabled(true);
        exploration.setBeaconDistance(96.0F);
        profiles.put(exploration.getName(), exploration);

        activeProfileName = defaultProfile.getName();
    }

    public static TrackerVisionProfile getActiveProfile() {
        return profiles.get(activeProfileName);
    }

    public static String getActiveProfileName() {
        return activeProfileName;
    }

    public static List<String> getProfileNames() {
        return new ArrayList<>(profiles.keySet());
    }

    public static boolean hasProfile(String name) {
        return profiles.containsKey(name);
    }

    public static boolean setActiveProfile(String name) {
        if (!profiles.containsKey(name)) {
            return false;
        }
        activeProfileName = name;
        return true;
    }

    /** Adds a new profile seeded with the active profile's current settings. Returns false if the name is taken. */
    public static boolean createProfile(String name) {
        if (profiles.containsKey(name)) {
            return false;
        }
        TrackerVisionProfile copy = new TrackerVisionProfile(name);
        TrackerVisionProfile active = getActiveProfile();
        copy.setTrackingEnabled(active.isTrackingEnabled());
        copy.setNearDistance(active.getNearDistance());
        copy.setFarDistance(active.getFarDistance());
        copy.setBracketBaseSize(active.getBracketBaseSize());
        copy.setTrackingAccentColor(active.getTrackingAccentColor());
        copy.setBeaconEnabled(active.isBeaconEnabled());
        copy.setBeaconDistance(active.getBeaconDistance());
        copy.setRimBoostEnabled(active.isRimBoostEnabled());
        profiles.put(name, copy);
        return true;
    }

    /** Removes a profile. Refuses to delete the last remaining profile or the active one. */
    public static boolean deleteProfile(String name) {
        if (profiles.size() <= 1 || !profiles.containsKey(name) || name.equals(activeProfileName)) {
            return false;
        }
        profiles.remove(name);
        return true;
    }

    static void replaceProfiles(List<TrackerVisionProfile> loaded, String activeName) {
        profiles.clear();
        for (TrackerVisionProfile profile : loaded) {
            profiles.put(profile.getName(), profile);
        }
        if (profiles.isEmpty()) {
            registerDefaultProfiles();
            return;
        }
        activeProfileName = profiles.containsKey(activeName) ? activeName : profiles.keySet().iterator().next();
    }

    static Map<String, TrackerVisionProfile> getAllProfiles() {
        return profiles;
    }

    public static boolean isTrackingEnabled() {
        return getActiveProfile().isTrackingEnabled();
    }

    public static void setTrackingEnabled(boolean value) {
        getActiveProfile().setTrackingEnabled(value);
    }

    public static float getNearDistance() {
        return getActiveProfile().getNearDistance();
    }

    public static void setNearDistance(float value) {
        getActiveProfile().setNearDistance(value);
    }

    public static float getFarDistance() {
        return getActiveProfile().getFarDistance();
    }

    public static void setFarDistance(float value) {
        getActiveProfile().setFarDistance(value);
    }

    public static int getBracketBaseSize() {
        return getActiveProfile().getBracketBaseSize();
    }

    public static void setBracketBaseSize(int value) {
        getActiveProfile().setBracketBaseSize(value);
    }

    public static int getTrackingAccentColor() {
        return getActiveProfile().getTrackingAccentColor();
    }

    public static void setTrackingAccentColor(int value) {
        getActiveProfile().setTrackingAccentColor(value);
    }

    public static boolean isBeaconEnabled() {
        return getActiveProfile().isBeaconEnabled();
    }

    public static void setBeaconEnabled(boolean value) {
        getActiveProfile().setBeaconEnabled(value);
    }

    /** Distance beyond which the beacon pillar replaces the shrunk reticle. See docs/UI_STYLE_GUIDE.md. */
    public static float getBeaconDistance() {
        return getActiveProfile().getBeaconDistance();
    }

    public static void setBeaconDistance(float value) {
        getActiveProfile().setBeaconDistance(value);
    }

    /** Whether {@code RimBoostEffect}'s post-process bloom-style boost runs while the locked target's rim is drawn. */
    public static boolean isRimBoostEnabled() {
        return getActiveProfile().isRimBoostEnabled();
    }

    public static void setRimBoostEnabled(boolean value) {
        getActiveProfile().setRimBoostEnabled(value);
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
