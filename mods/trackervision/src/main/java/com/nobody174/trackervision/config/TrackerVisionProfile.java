//
// TrackerVision
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/trackervision
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.trackervision.config;

/**
 * One named bundle of the tunable visual/distance settings. Mutable and
 * clamped on every setter, same as the old single-profile
 * {@link TrackerVisionConfig} this replaces — clamping lives here now
 * since every profile must independently guarantee a valid runtime state.
 */
public final class TrackerVisionProfile {

    private static final float MIN_NEAR_DISTANCE = 1.0F;
    private static final float MAX_NEAR_DISTANCE = 32.0F;
    private static final float MIN_FAR_DISTANCE = 8.0F;
    private static final float MAX_FAR_DISTANCE = 256.0F;
    private static final int MIN_BRACKET_SIZE = 8;
    private static final int MAX_BRACKET_SIZE = 64;

    private String name;
    private boolean trackingEnabled = true;
    private float nearDistance = 8.0F;
    private float farDistance = 64.0F;
    private int bracketBaseSize = 24;
    private int trackingAccentColor = 0xFF35E0E0;
    private boolean beaconEnabled = true;
    private float beaconDistance = 48.0F;
    private boolean rimBoostEnabled = true;

    public TrackerVisionProfile(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isTrackingEnabled() {
        return trackingEnabled;
    }

    public void setTrackingEnabled(boolean value) {
        trackingEnabled = value;
    }

    public float getNearDistance() {
        return nearDistance;
    }

    public void setNearDistance(float value) {
        nearDistance = Math.max(MIN_NEAR_DISTANCE, Math.min(MAX_NEAR_DISTANCE, value));
    }

    public float getFarDistance() {
        return farDistance;
    }

    public void setFarDistance(float value) {
        farDistance = Math.max(MIN_FAR_DISTANCE, Math.min(MAX_FAR_DISTANCE, value));
    }

    public int getBracketBaseSize() {
        return bracketBaseSize;
    }

    public void setBracketBaseSize(int value) {
        bracketBaseSize = Math.max(MIN_BRACKET_SIZE, Math.min(MAX_BRACKET_SIZE, value));
    }

    public int getTrackingAccentColor() {
        return trackingAccentColor;
    }

    public void setTrackingAccentColor(int value) {
        trackingAccentColor = 0xFF000000 | (value & 0x00FFFFFF);
    }

    public boolean isBeaconEnabled() {
        return beaconEnabled;
    }

    public void setBeaconEnabled(boolean value) {
        beaconEnabled = value;
    }

    public float getBeaconDistance() {
        return beaconDistance;
    }

    public void setBeaconDistance(float value) {
        beaconDistance = Math.max(MIN_FAR_DISTANCE, Math.min(MAX_FAR_DISTANCE, value));
    }

    public boolean isRimBoostEnabled() {
        return rimBoostEnabled;
    }

    public void setRimBoostEnabled(boolean value) {
        rimBoostEnabled = value;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
