//
// Boss Radar
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/boss-radar
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.bossradar.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public class BossRadarSyncPacket implements CustomPacketPayload {
    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath("bossradar", "sync");
    public static final CustomPacketPayload.Type<BossRadarSyncPacket> TYPE = new CustomPacketPayload.Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, BossRadarSyncPacket> STREAM_CODEC = CustomPacketPayload.codec(BossRadarSyncPacket::write, BossRadarSyncPacket::new);

    private final String targetName;
    private final double distance;
    private final double targetX;
    private final double targetY;
    private final double targetZ;

    public BossRadarSyncPacket(String targetName, double distance, double targetX, double targetY, double targetZ) {
        this.targetName = targetName;
        this.distance = distance;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public BossRadarSyncPacket(FriendlyByteBuf buf) {
        this.targetName = buf.readUtf();
        this.distance = buf.readDouble();
        this.targetX = buf.readDouble();
        this.targetY = buf.readDouble();
        this.targetZ = buf.readDouble();
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(targetName);
        buf.writeDouble(distance);
        buf.writeDouble(targetX);
        buf.writeDouble(targetY);
        buf.writeDouble(targetZ);
    }

    @Override
    public CustomPacketPayload.Type<BossRadarSyncPacket> type() {
        return TYPE;
    }

    public String getTargetName() {
        return targetName;
    }

    public double getDistance() {
        return distance;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
