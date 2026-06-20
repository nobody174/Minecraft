package com.nobody174.glowtools.config;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;

/**
 * Configuration for GlowTools particle effects
 * Edit the PARTICLE_EFFECT setting to change the visual effect
 */
public class GlowToolsConfig {

    // ===== EFFECT SELECTION =====
    // Change this to switch between different particle effects
    // Available options (comment/uncomment to choose):

    public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.ENCHANTED_HIT;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.ENCHANT;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.GLOW;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.FLAME;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.ELECTRIC_SPARK;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SOUL;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SOUL_FIRE_FLAME;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SWEEP_ATTACK;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.CRIT;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.POOF;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.END_ROD;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.PORTAL;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.FIREFLY;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.GLOW_SQUID_INK;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.DRAGON_BREATH;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SMALL_FLAME;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.COPPER_FIRE_FLAME;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SNOWFLAKE;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.GUST;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.TOTEM_OF_UNDYING;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SPORE_BLOSSOM_AIR;
    // public static final SimpleParticleType PARTICLE_EFFECT = ParticleTypes.SCULK_SOUL;

    // ===== PARTICLE BEHAVIOR =====
    // How many particles to spawn per tick (higher = denser effect)
    public static final int PARTICLES_PER_TICK = 1;

    // Spread radius around the tool (how far particles scatter)
    public static final double PARTICLE_SPREAD = 0.3;

    // Velocity of particles (higher = faster movement)
    public static final double PARTICLE_VELOCITY = 0.05;

    // ===== EFFECT DESCRIPTIONS =====
    // Reference guide for available effects:
    /*
    ENCHANTED_HIT        - Purple explosive sparkles (default)
    ENCHANT              - Purple/blue sparkles that rise
    GLOW                 - Bright white sparkles
    FLAME                - Orange/red fire particles
    ELECTRIC_SPARK       - Yellow/white lightning sparks
    SOUL                 - Blue spirit particles
    SOUL_FIRE_FLAME      - Blue nether fire
    SWEEP_ATTACK         - White sweep effect
    CRIT                 - White critical hit sparkles
    POOF                 - White smoke puff
    END_ROD              - Purple particle trail
    PORTAL               - Swirling purple portal effect
    FIREFLY              - Small glowing yellow particles
    GLOW_SQUID_INK       - Cyan ink particles
    DRAGON_BREATH        - Purple-pink cloud
    SMALL_FLAME          - Smaller flame particles
    COPPER_FIRE_FLAME    - Copper-orange flame
    SNOWFLAKE            - Falling snowflake particles
    GUST                 - Wind particle burst
    TOTEM_OF_UNDYING     - Rainbow particles
    SPORE_BLOSSOM_AIR    - Cyan falling particles
    SCULK_SOUL           - Creepy cyan soul particles
    */
}
