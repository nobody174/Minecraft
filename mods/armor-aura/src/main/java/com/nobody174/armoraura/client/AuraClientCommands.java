//
// ArmorAura
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/master/mods/armor-aura
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

package com.nobody174.armoraura.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.nobody174.armoraura.networking.AuraGlowRequestPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.PacketDistributor;
import com.nobody174.armoraura.ArmorAuraMod;

public class AuraClientCommands {

    public static void init() {
        NeoForge.EVENT_BUS.addListener(AuraClientCommands::registerClientCommands);
    }

    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        ArmorAuraMod.LOGGER.info("[AuraClientCommands] Registering ALL client-side /armoraura commands");
        event.getDispatcher().register(
            Commands.literal("armoraura")
                // Preset command
                .then(Commands.literal("preset")
                    .then(Commands.argument("preset", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            builder.suggest("minimal");
                            builder.suggest("medium");
                            builder.suggest("maximum");
                            return builder.buildFuture();
                        })
                        .executes(AuraClientCommands::executePreset)))
                // Particle count
                .then(Commands.literal("particles")
                    .then(Commands.argument("count", IntegerArgumentType.integer(1, 20))
                        .executes(AuraClientCommands::executeParticles)))
                // Aura radius
                .then(Commands.literal("radius")
                    .then(Commands.argument("radius", FloatArgumentType.floatArg(0.1f, 1.5f))
                        .executes(AuraClientCommands::executeRadius)))
                // Rotation speed
                .then(Commands.literal("speed")
                    .then(Commands.argument("speed", FloatArgumentType.floatArg(0.1f, 3.0f))
                        .executes(AuraClientCommands::executeSpeed)))
                // Ring count
                .then(Commands.literal("rings")
                    .then(Commands.argument("count", IntegerArgumentType.integer(1, 4))
                        .executes(AuraClientCommands::executeRings)))
                // Particle effect type
                .then(Commands.literal("effect")
                    .then(Commands.argument("effect", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            builder.suggest("glow");
                            builder.suggest("flame");
                            builder.suggest("electric_spark");
                            builder.suggest("crit");
                            builder.suggest("end_rod");
                            builder.suggest("soul");
                            builder.suggest("portal");
                            builder.suggest("dragon_breath");
                            builder.suggest("happy_villager");
                            builder.suggest("note");
                            return builder.buildFuture();
                        })
                        .executes(AuraClientCommands::executeEffect)))
                // Status command
                .then(Commands.literal("status")
                    .executes(AuraClientCommands::executeStatus))
                // Reload config from file
                .then(Commands.literal("reload")
                    .executes(AuraClientCommands::executeReload))
                // Enable/disable/toggle the particle aura entirely
                .then(Commands.literal("enable")
                    .executes(AuraClientCommands::executeEnable))
                .then(Commands.literal("disable")
                    .executes(AuraClientCommands::executeDisable))
                .then(Commands.literal("toggle")
                    .executes(AuraClientCommands::executeToggle))
        );

        event.getDispatcher().register(
            Commands.literal("auraglow")
                // Toggle on/off
                .then(Commands.literal("toggle")
                    .executes(AuraClientCommands::executeGlowToggle))
                .then(Commands.literal("on")
                    .executes(AuraClientCommands::executeGlowOn))
                .then(Commands.literal("off")
                    .executes(AuraClientCommands::executeGlowOff))
                // Color (named preset like "cyan", or raw hex RGB like 33CCFF)
                .then(Commands.literal("color")
                    .then(Commands.argument("hex", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            for (String name : AuraGlowPalette.names()) {
                                builder.suggest(name);
                            }
                            return builder.buildFuture();
                        })
                        .executes(AuraClientCommands::executeGlowColor)))
                .then(Commands.literal("colors")
                    .executes(AuraClientCommands::executeGlowColorList))
                // Intensity (0.05 - 1.0)
                .then(Commands.literal("intensity")
                    .then(Commands.argument("value", FloatArgumentType.floatArg(0.05f, 1.0f))
                        .executes(AuraClientCommands::executeGlowIntensity)))
                // Pulse speed (0 = static, up to 5)
                .then(Commands.literal("pulse")
                    .then(Commands.argument("speed", FloatArgumentType.floatArg(0.0f, 5.0f))
                        .executes(AuraClientCommands::executeGlowPulse)))
                .then(Commands.literal("status")
                    .executes(AuraClientCommands::executeGlowStatus))
        );
    }

    private static int executeGlowToggle(CommandContext<CommandSourceStack> context) {
        return setGlowEnabled(context, !AuraGlowConfig.isEnabled());
    }

    private static int executeGlowOn(CommandContext<CommandSourceStack> context) {
        return setGlowEnabled(context, true);
    }

    private static int executeGlowOff(CommandContext<CommandSourceStack> context) {
        return setGlowEnabled(context, false);
    }

    private static int setGlowEnabled(CommandContext<CommandSourceStack> context, boolean enabled) {
        AuraGlowConfig.setEnabled(enabled);
        syncGlowState();
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Glow aura: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(enabled ? "ON" : "OFF").withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeGlowColor(CommandContext<CommandSourceStack> context) {
        String input = StringArgumentType.getString(context, "hex");
        Integer named = AuraGlowPalette.resolve(input);
        int rgb;
        if (named != null) {
            rgb = named;
        } else {
            String hex = input.replace("#", "");
            try {
                rgb = Integer.parseInt(hex, 16) & 0xFFFFFF;
            } catch (NumberFormatException e) {
                Component error = Component.literal("✗ Unknown color name or invalid hex: ").withStyle(ChatFormatting.RED)
                    .append(Component.literal(input).withStyle(ChatFormatting.WHITE))
                    .append(Component.literal("\nTry /auraglow colors for the list, or a 6-digit hex code.").withStyle(ChatFormatting.GRAY));
                context.getSource().sendFailure(error);
                return 0;
            }
        }
        AuraGlowConfig.setColor(rgb);
        syncGlowState();
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Glow color: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("#%06X", rgb)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeGlowIntensity(CommandContext<CommandSourceStack> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        AuraGlowConfig.setIntensity(value);
        syncGlowState();
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Glow intensity: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", value)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeGlowPulse(CommandContext<CommandSourceStack> context) {
        float speed = FloatArgumentType.getFloat(context, "speed");
        AuraGlowConfig.setPulseSpeed(speed);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Glow pulse speed: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", speed)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeGlowColorList(CommandContext<CommandSourceStack> context) {
        net.minecraft.network.chat.MutableComponent message =
            Component.literal("╔════ Glow Colors ════╗").withStyle(ChatFormatting.AQUA);
        for (String name : AuraGlowPalette.names()) {
            int rgb = AuraGlowPalette.resolve(name);
            message.append(Component.literal("\n" + name + " ").withStyle(ChatFormatting.GOLD))
                .append(Component.literal(String.format("#%06X", rgb)).withStyle(ChatFormatting.WHITE));
        }
        message.append(Component.literal("\n╚══════════════════════╝\n").withStyle(ChatFormatting.AQUA))
            .append(Component.literal("Use: /auraglow color <name|hex>").withStyle(ChatFormatting.GRAY));
        final Component finalMessage = message;
        context.getSource().sendSuccess(() -> finalMessage, false);
        return 1;
    }

    private static int executeGlowStatus(CommandContext<CommandSourceStack> context) {
        Component message = Component.literal("╔════ Glow Aura Status ════╗\n").withStyle(ChatFormatting.AQUA)
            .append(Component.literal("Enabled: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(AuraGlowConfig.isEnabled() ? "YES" : "NO").withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\nColor: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.format("#%06X", AuraGlowConfig.getColor())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\nIntensity: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.format("%.2f", AuraGlowConfig.getIntensity())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" | Pulse: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.format("%.2f", AuraGlowConfig.getPulseSpeed())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\n╚═══════════════════════════╝").withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static void syncGlowState() {
        if (Minecraft.getInstance().getConnection() == null) {
            return; // Not connected to a server yet (e.g. command run too early)
        }
        PacketDistributor.sendToServer(new AuraGlowRequestPacket(
            AuraGlowConfig.isEnabled(),
            AuraGlowConfig.getColor(),
            AuraGlowConfig.getIntensity()
        ));
    }

    private static int executeEnable(CommandContext<CommandSourceStack> context) {
        return setAuraEnabled(context, true);
    }

    private static int executeDisable(CommandContext<CommandSourceStack> context) {
        return setAuraEnabled(context, false);
    }

    private static int executeToggle(CommandContext<CommandSourceStack> context) {
        return setAuraEnabled(context, !AuraConfig.isEnabled());
    }

    private static int setAuraEnabled(CommandContext<CommandSourceStack> context, boolean enabled) {
        AuraConfig.setEnabled(enabled);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Particle aura: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(enabled ? "ON" : "OFF").withStyle(enabled ? ChatFormatting.GREEN : ChatFormatting.RED));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executePreset(CommandContext<CommandSourceStack> context) {
        String presetName = StringArgumentType.getString(context, "preset");
        AuraPreset preset = AuraPreset.fromName(presetName);
        AuraRenderer.setPreset(preset);
        AuraConfig.applyPreset(preset);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Aura preset: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(preset.displayName).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeParticles(CommandContext<CommandSourceStack> context) {
        int count = IntegerArgumentType.getInteger(context, "count");
        AuraConfig.setParticlesPerRing(count);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Particles per ring: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.valueOf(count)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeRadius(CommandContext<CommandSourceStack> context) {
        float radius = FloatArgumentType.getFloat(context, "radius");
        AuraConfig.setRadius(radius);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Aura radius: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", radius)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeSpeed(CommandContext<CommandSourceStack> context) {
        float speed = FloatArgumentType.getFloat(context, "speed");
        AuraConfig.setRotationSpeed(speed);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Rotation speed: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", speed)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeRings(CommandContext<CommandSourceStack> context) {
        int rings = IntegerArgumentType.getInteger(context, "count");
        AuraConfig.setRingCount(rings);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Ring count: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.valueOf(rings)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeEffect(CommandContext<CommandSourceStack> context) {
        String effect = StringArgumentType.getString(context, "effect");
        AuraConfig.setParticleEffect(effect);
        AuraConfigFile.saveConfig();
        Component message = Component.literal("✦ Particle effect: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(effect).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeStatus(CommandContext<CommandSourceStack> context) {
        Component message = Component.literal("╔════ Aura Status ════╗\n").withStyle(ChatFormatting.AQUA)
            .append(Component.literal("Enabled: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(AuraConfig.isEnabled() ? "YES" : "NO").withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\nParticles: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.valueOf(AuraConfig.getParticlesPerRing())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" | Radius: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.format("%.2f", AuraConfig.getRadius())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" | Speed: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.format("%.2f", AuraConfig.getRotationSpeed())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\nRings: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(String.valueOf(AuraConfig.getRingCount())).withStyle(ChatFormatting.WHITE))
            .append(Component.literal(" | Effect: ").withStyle(ChatFormatting.GOLD))
            .append(Component.literal(AuraConfig.getParticleEffect()).withStyle(ChatFormatting.WHITE))
            .append(Component.literal("\n╚═══════════════════╝").withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeReload(CommandContext<CommandSourceStack> context) {
        try {
            AuraConfigFile.loadConfig();
            Component message = Component.literal("✦ Config reloaded from file!").withStyle(ChatFormatting.GREEN)
                .append(Component.literal("\nFile: ").withStyle(ChatFormatting.GOLD))
                .append(Component.literal(AuraConfigFile.getConfigFile().getAbsolutePath()).withStyle(ChatFormatting.AQUA));
            context.getSource().sendSuccess(() -> message, false);
            ArmorAuraMod.LOGGER.info("[AuraClientCommands] Config reloaded successfully");
            return 1;
        } catch (Exception e) {
            ArmorAuraMod.LOGGER.error("[AuraClientCommands] Error reloading config", e);
            Component error = Component.literal("✗ Error: ").withStyle(ChatFormatting.RED)
                .append(Component.literal(e.getMessage()).withStyle(ChatFormatting.WHITE));
            context.getSource().sendFailure(error);
            return 0;
        }
    }
}

// Built with assistance from Claude Code by Anthropic.
