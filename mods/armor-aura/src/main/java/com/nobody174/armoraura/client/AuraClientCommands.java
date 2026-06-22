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
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
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
        );
    }

    private static int executePreset(CommandContext<CommandSourceStack> context) {
        String presetName = StringArgumentType.getString(context, "preset");
        AuraPreset preset = AuraPreset.fromName(presetName);
        AuraRenderer.setPreset(preset);
        AuraConfig.applyPreset(preset);
        Component message = Component.literal("✦ Aura preset: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(preset.displayName).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeParticles(CommandContext<CommandSourceStack> context) {
        int count = IntegerArgumentType.getInteger(context, "count");
        AuraConfig.setParticlesPerRing(count);
        Component message = Component.literal("✦ Particles per ring: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.valueOf(count)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeRadius(CommandContext<CommandSourceStack> context) {
        float radius = FloatArgumentType.getFloat(context, "radius");
        AuraConfig.setRadius(radius);
        Component message = Component.literal("✦ Aura radius: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", radius)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeSpeed(CommandContext<CommandSourceStack> context) {
        float speed = FloatArgumentType.getFloat(context, "speed");
        AuraConfig.setRotationSpeed(speed);
        Component message = Component.literal("✦ Rotation speed: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.format("%.2f", speed)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeRings(CommandContext<CommandSourceStack> context) {
        int rings = IntegerArgumentType.getInteger(context, "count");
        AuraConfig.setRingCount(rings);
        Component message = Component.literal("✦ Ring count: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(String.valueOf(rings)).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeEffect(CommandContext<CommandSourceStack> context) {
        String effect = StringArgumentType.getString(context, "effect");
        AuraConfig.setParticleEffect(effect);
        Component message = Component.literal("✦ Particle effect: ").withStyle(ChatFormatting.GOLD)
            .append(Component.literal(effect).withStyle(ChatFormatting.AQUA));
        context.getSource().sendSuccess(() -> message, false);
        return 1;
    }

    private static int executeStatus(CommandContext<CommandSourceStack> context) {
        Component message = Component.literal("╔════ Aura Status ════╗\n").withStyle(ChatFormatting.AQUA)
            .append(Component.literal("Particles: ").withStyle(ChatFormatting.GOLD))
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
