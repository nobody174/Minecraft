# GlowTools Server+Client

**Version:** 1.0.0 (Early Access / Work in Progress)  
**Minecraft Version:** 1.21.1  
**Mod Loader:** NeoForge 21.1.233

---

## Overview

GlowTools is a NeoForge mod that adds **customizable particle effects to your weapons and tools**. Equip a sword, pickaxe, axe, or shovel and watch it glow with stunning visual effects that can be changed at any time!

Perfect for:
- ✨ Adding visual flair to your combat tools
- 🎨 Personalizing your weapon's appearance
- 👥 Showing off your style on multiplayer servers

---

## Features

✅ **20+ Particle Effects** - Choose from a wide variety of visual effects:
- Flame, Soul, Enchanted Hit, Glow, Electric Spark, and more

✅ **Instant Effect Switching** - Change effects on the fly with a simple command

✅ **Server & Client Synced** - Works seamlessly on multiplayer servers

✅ **First-Person & Third-Person** - Effects visible in all views

✅ **Tool-Based** - Works with swords, pickaxes, axes, and shovels

---

## Installation

1. **Download** the latest JAR from the [Releases](https://github.com/nobody174/Minecraft/releases) page
2. **Place** `glowtools-1.0.0-mc1_21_1.jar` in your mods folder:
   - **Client:** `%APPDATA%/.minecraft/mods/`
   - **Server:** `mods/`
3. **Launch** Minecraft (NeoForge 1.21.1 required)

---

## Usage

### Basic Command

```
/glowtool effect <effect_name>
```

### Available Effects

| Effect Name | Description |
|-------------|-------------|
| `flame` | Orange/red fire particles |
| `soul` | Blue spirit particles |
| `enchanted_hit` | Purple explosive sparkles ✨ |
| `enchant` | Purple/blue sparkles that rise |
| `glow` | Bright white sparkles |
| `electric_spark` | Yellow/white lightning sparks |
| `soul_fire_flame` | Blue nether fire |
| `sweep_attack` | White sweep effect |
| `crit` | White critical hit sparkles |
| `poof` | White smoke puff |
| `end_rod` | Purple particle trail |
| `portal` | Swirling purple portal effect |
| `glow_squid_ink` | Cyan ink particles |
| `dragon_breath` | Purple-pink cloud |
| `small_flame` | Smaller flame particles |
| `snowflake` | Falling snowflake particles |
| `gust` | Wind particle burst |
| `totem_of_undying` | Rainbow particles |
| `spore_blossom_air` | Cyan falling particles |
| `sculk_soul` | Creepy cyan soul particles |

### Examples

Change your tool's effect to **soul fire**:
```
/glowtool effect soul_fire_flame
```

Switch to **electric sparks**:
```
/glowtool effect electric_spark
```

Try **rainbow particles** from Totem of Undying:
```
/glowtool effect totem_of_undying
```

---

## How It Works

1. **Hold a tool** (sword, pickaxe, axe, or shovel) in your main hand
2. **Run** `/glowtool effect <name>` to set your desired particle effect
3. **Particles spawn** continuously while you're holding the tool
4. **Switch effects** anytime with another command
5. **Effect persists** even if you switch tools and come back

The effect is stored server-side, so it stays with you across sessions!

---

## Configuration

Particle behavior can be adjusted in `GlowToolsConfig.java`:

```java
public static final int PARTICLES_PER_TICK = 1;      // Density
public static final double PARTICLE_SPREAD = 0.3;    // Spread radius
public static final double PARTICLE_VELOCITY = 0.05; // Movement speed
```

---

## Technical Details

### Architecture
- **Server-side spawning** - All particles generated server-side and synced to all clients
- **NBT persistence** - Effect choice stored in player persistent data
- **Rotation-based positioning** - Particles spawn at weapon position using player rotation math

### Requirements
- NeoForge 21.1.233+
- Minecraft 1.21.1
- Java 21+

### Files
- `src/main/java/com/nobody174/glowtools/server/GlowToolsServerEvents.java` - Particle spawning logic
- `src/main/java/com/nobody174/glowtools/server/command/AdminCommandHandler.java` - Command handling
- `src/main/java/com/nobody174/glowtools/config/GlowToolsConfig.java` - Configuration

---

## Roadmap (Planned Features)

- 🎯 **v1.1** - Effect categories & favorites system
- 🎯 **v1.2** - Custom particle color support
- 🎯 **v1.3** - Effect performance profiles
- 🎯 **v2.0** - Custom particle patterns & animations

---

## Support & Feedback

Found a bug? Have a feature request?
- Open an [Issue](https://github.com/nobody174/Minecraft/issues) on GitHub
- Check existing issues first to avoid duplicates

---

## Credits

**Created by:** nobody174  
**Special Thanks:** NeoForge community, TinkersConstruct (rotation math reference)

---

## License

This project is licensed under the PolyForm Noncommercial License 1.0.0 -
see the [LICENSE](../../LICENSE) file for details.

---

**Enjoy your glowing tools! ✨**
