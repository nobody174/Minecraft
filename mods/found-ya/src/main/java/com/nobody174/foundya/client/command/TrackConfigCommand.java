//
// Found Ya!
// Author:  nobody174 (nobodylearn174@gmail.com)
// Repo:    https://github.com/nobody174/Minecraft/tree/main/mods/found-ya
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.foundya.client.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import com.nobody174.foundya.config.FoundYaConfig;
import com.nobody174.foundya.config.FoundYaConfigFile;

/**
 * {@code /track config ...} — runtime-adjustable settings, persisted to
 * disk immediately on change. A full in-game config screen is v0.5+ scope
 * (see ROADMAP.md); commands are the v0.1 interim per
 * {@code TrackerVision_Production_Design_Package_v2/08_COMMAND_SPEC.md}
 * ("Future: GUI replaces commands").
 */
public final class TrackConfigCommand {
    private TrackConfigCommand() {
    }

    public static LiteralArgumentBuilder<CommandSourceStack> build() {
        return Commands.literal("config")
            .then(Commands.literal("enabled")
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(TrackConfigCommand::setEnabled)))
            .then(Commands.literal("nearDistance")
                .then(Commands.argument("value", FloatArgumentType.floatArg(1.0F, 32.0F))
                    .executes(TrackConfigCommand::setNearDistance)))
            .then(Commands.literal("farDistance")
                .then(Commands.argument("value", FloatArgumentType.floatArg(8.0F, 256.0F))
                    .executes(TrackConfigCommand::setFarDistance)))
            .then(Commands.literal("beaconEnabled")
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(TrackConfigCommand::setBeaconEnabled)))
            .then(Commands.literal("beaconDistance")
                .then(Commands.argument("value", FloatArgumentType.floatArg(8.0F, 256.0F))
                    .executes(TrackConfigCommand::setBeaconDistance)))
            .then(Commands.literal("rimBoostEnabled")
                .then(Commands.argument("value", BoolArgumentType.bool())
                    .executes(TrackConfigCommand::setRimBoostEnabled)))
            .then(Commands.literal("show")
                .executes(TrackConfigCommand::show));
    }

    private static int setEnabled(CommandContext<CommandSourceStack> ctx) {
        boolean value = BoolArgumentType.getBool(ctx, "value");
        FoundYaConfig.setTrackingEnabled(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Found Ya! tracking enabled: " + value);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setNearDistance(CommandContext<CommandSourceStack> ctx) {
        float value = FloatArgumentType.getFloat(ctx, "value");
        FoundYaConfig.setNearDistance(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Near distance set to " + FoundYaConfig.getNearDistance());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setFarDistance(CommandContext<CommandSourceStack> ctx) {
        float value = FloatArgumentType.getFloat(ctx, "value");
        FoundYaConfig.setFarDistance(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Far distance set to " + FoundYaConfig.getFarDistance());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setBeaconEnabled(CommandContext<CommandSourceStack> ctx) {
        boolean value = BoolArgumentType.getBool(ctx, "value");
        FoundYaConfig.setBeaconEnabled(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Beacon mode enabled: " + value);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setBeaconDistance(CommandContext<CommandSourceStack> ctx) {
        float value = FloatArgumentType.getFloat(ctx, "value");
        FoundYaConfig.setBeaconDistance(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Beacon distance set to " + FoundYaConfig.getBeaconDistance());
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int setRimBoostEnabled(CommandContext<CommandSourceStack> ctx) {
        boolean value = BoolArgumentType.getBool(ctx, "value");
        FoundYaConfig.setRimBoostEnabled(value);
        FoundYaConfigFile.save();
        final Component message = Component.literal("Rim boost shader enabled: " + value);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int show(CommandContext<CommandSourceStack> ctx) {
        String summary = String.format(
            "Found Ya! config: profile=%s, enabled=%s, nearDistance=%.1f, farDistance=%.1f, bracketSize=%d, "
                + "beaconEnabled=%s, beaconDistance=%.1f, rimBoostEnabled=%s",
            FoundYaConfig.getActiveProfileName(),
            FoundYaConfig.isTrackingEnabled(),
            FoundYaConfig.getNearDistance(),
            FoundYaConfig.getFarDistance(),
            FoundYaConfig.getBracketBaseSize(),
            FoundYaConfig.isBeaconEnabled(),
            FoundYaConfig.getBeaconDistance(),
            FoundYaConfig.isRimBoostEnabled());
        final Component message = Component.literal(summary);
        ctx.getSource().sendSuccess(() -> message, false);
        return 1;
    }
}

//*Built with assistance from __Claude Code__ by Anthropic.*
