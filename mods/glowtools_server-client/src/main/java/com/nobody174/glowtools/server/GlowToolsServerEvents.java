package com.nobody174.glowtools.server;

import com.nobody174.glowtools.config.GlowToolsConfig;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

/**
 * Server-side particle spawning using rotation-based positioning.
 * Particles are spawned in world-space, not camera-space.
 * This approach is based on TinkersConstruct's ToolAttackUtil.
 */
@EventBusSubscriber(modid = "glowtools_sc")
public class GlowToolsServerEvents {

    // Throttling: spawn particles every N server ticks
    private static int tickCounter = 0;
    private static final int TICK_THRESHOLD = 1; // Change to 2-3 for less frequent spawning

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (tickCounter++ % TICK_THRESHOLD != 0) return;

        // Iterate through all players in the world
        for (Player player : event.getServer().getPlayerList().getPlayers()) {
            if (player == null || !(player.level() instanceof ServerLevel serverLevel)) continue;

            // Check if player is holding a tool
            ItemStack mainHandItem = player.getMainHandItem();
            if (!isTool(mainHandItem)) continue;

            // Read player's effect setting from NBT
            String effectName = player.getPersistentData().getString("glowtools:particle_effect");
            if (effectName.isEmpty()) continue; // Only spawn if effect is explicitly set

            // Get the particle effect type based on NBT name
            SimpleParticleType particleEffect = parseParticleType(effectName);

            // Calculate weapon position using rotation-based offset
            double offsetDistance = 1.0;

            // Calculate direction vector from player's rotation (yaw/pitch)
            double offsetX = -Math.sin(player.getYRot() / 180.0F * Math.PI)
                    * Math.cos(player.getXRot() / 180.0F * Math.PI) * offsetDistance;
            double offsetY = player.getBbHeight() * 0.8;
            double offsetZ = Math.cos(player.getYRot() / 180.0F * Math.PI)
                    * Math.cos(player.getXRot() / 180.0F * Math.PI) * offsetDistance;

            // Spawn particles at calculated world position
            for (int i = 0; i < GlowToolsConfig.PARTICLES_PER_TICK; i++) {
                double x = player.getX() + offsetX + (Math.random() - 0.5) * GlowToolsConfig.PARTICLE_SPREAD;
                double y = player.getY() + offsetY + (Math.random() - 0.5) * GlowToolsConfig.PARTICLE_SPREAD;
                double z = player.getZ() + offsetZ + (Math.random() - 0.5) * GlowToolsConfig.PARTICLE_SPREAD;

                double velX = (Math.random() - 0.5) * GlowToolsConfig.PARTICLE_VELOCITY;
                double velY = Math.random() * GlowToolsConfig.PARTICLE_VELOCITY;
                double velZ = (Math.random() - 0.5) * GlowToolsConfig.PARTICLE_VELOCITY;

                // Send particles to all clients from server
                serverLevel.sendParticles(particleEffect, x, y, z, 1, velX, velY, velZ, 0.0D);
            }
        }
    }

    private static boolean isTool(ItemStack item) {
        return item.getItem() instanceof SwordItem
                || item.getItem() instanceof PickaxeItem
                || item.getItem() instanceof AxeItem
                || item.getItem() instanceof ShovelItem;
    }

    private static SimpleParticleType parseParticleType(String name) {
        return switch (name.toLowerCase()) {
            case "enchanted_hit" -> ParticleTypes.ENCHANTED_HIT;
            case "enchant" -> ParticleTypes.ENCHANT;
            case "glow" -> ParticleTypes.GLOW;
            case "flame" -> ParticleTypes.FLAME;
            case "electric_spark" -> ParticleTypes.ELECTRIC_SPARK;
            case "soul" -> ParticleTypes.SOUL;
            case "soul_fire_flame" -> ParticleTypes.SOUL_FIRE_FLAME;
            case "sweep_attack" -> ParticleTypes.SWEEP_ATTACK;
            case "crit" -> ParticleTypes.CRIT;
            case "poof" -> ParticleTypes.POOF;
            case "end_rod" -> ParticleTypes.END_ROD;
            case "portal" -> ParticleTypes.PORTAL;
            case "glow_squid_ink" -> ParticleTypes.GLOW_SQUID_INK;
            case "dragon_breath" -> ParticleTypes.DRAGON_BREATH;
            case "small_flame" -> ParticleTypes.SMALL_FLAME;
            case "snowflake" -> ParticleTypes.SNOWFLAKE;
            case "gust" -> ParticleTypes.GUST;
            case "totem_of_undying" -> ParticleTypes.TOTEM_OF_UNDYING;
            case "spore_blossom_air" -> ParticleTypes.SPORE_BLOSSOM_AIR;
            case "sculk_soul" -> ParticleTypes.SCULK_SOUL;
            default -> ParticleTypes.ENCHANTED_HIT;
        };
    }
}
