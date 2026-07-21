# TrackerVision - Modern Entity Tracking Framework

A modern entity tracking and visualization framework for NeoForge Minecraft 1.21.1. Lock targets, track them across the world, and customize your tracking experience with multiple profiles.

## What is TrackerVision?

TrackerVision replaces vanilla glowing with a flexible, customizable tracking system. Manually lock entities with `/track lock @e` or auto-select the nearest target. See them at any distance with a sky-to-target beacon, through terrain with silhouettes, and customize your setup with profiles.

## Key Features

✨ **Manual & Auto-Tracking** — Lock any entity or auto-select nearest  
🔍 **Search Mode** — Reveal all nearby entities with one toggle  
📊 **Multiple Profiles** — Default, PvP, and Exploration configs  
🎯 **Sky-to-Target Beacon** — 300px vertical pillar (visible at 48+ blocks)  
👻 **Through-Wall Silhouette** — See targets through terrain  
💥 **Rim-Boost Shader** — Custom post-process bloom effect  
⚙️ **In-Game Config** — Full settings screen, no file editing  
🎨 **Smart Coloring** — Red (hostile), amber (distant), cyan (default)  

## How It Works

1. Use `/track lock @e[limit=1,sort=nearest]` to lock a target
2. See it on your HUD with distance and direction
3. Use `/track search true` to reveal all nearby mobs
4. Switch profiles: `/track profile use PvP`
5. Configure: ESC → Mods → Config

## Perfect For

- **PvP Tracking** — Always know where your opponent is
- **Mob Farming** — Find mobs at any distance
- **Boss Hunting** — Track specific entities reliably
- **Exploration** — Never lose your target in dark caves

## Compatibility

- **Minecraft:** 1.21.1
- **NeoForge:** 1.21.1+
- **Java:** 21+
- **Side:** Client-only (100% server-safe)

## Requirements

- Minecraft 1.21.1
- NeoForge 1.21.1 or higher
- Java 21 or higher

## Installation

1. Download JAR from releases
2. Place in `.minecraft/mods/`
3. Launch with NeoForge
4. Check Mods list to verify load

See INSTALLATION_GUIDE for detailed steps.

## Quick Commands

```
/track lock @e[limit=1,sort=nearest]     # Lock nearest entity
/track search true                         # Toggle reveal mode
/track profile use PvP                     # Switch profile
/track config rimBoostEnabled false        # Disable rim shader
/track status                              # Show current lock
```

## Entity Selector Examples

```
# Any type, closest
/track lock @e[limit=1,sort=nearest]

# Specific mob type
/track lock @e[type=minecraft:zombie,limit=1,sort=nearest]

# By distance
/track lock @e[distance=..50,limit=1,sort=nearest]

# By entity name
/track lock @e[name="MyEntity",limit=1]

# Hostile mobs only
/track lock @e[type=#minecraft:attackable,limit=1,sort=nearest]
```

## Performance

✅ **60 FPS stable** with 20+ simultaneous tracked entities  
✅ **Minimal memory** impact (normal Java heap usage)  
✅ **Zero lag** — render-thread only optimization  

## Tested & Verified

- ✅ 4+ hours extended play-testing
- ✅ 20+ simultaneous mobs tracked
- ✅ All modes (Peaceful, Creative, Survival)
- ✅ Profile switching, persistence, edge cases
- ✅ Zero crashes, full stability

## Known Limitations

- Iris shader packs not yet compatible (workaround: disable rim-boost)
- Entity selector syntax must follow Minecraft standard

For more details, see the full documentation on GitHub.

## Support

- **Issues:** [GitHub Issues](https://github.com/nobody174/trackervision/issues)
- **Questions:** [GitHub Discussions](https://github.com/nobody174/trackervision/discussions)
- **Repository:** [GitHub](https://github.com/nobody174/trackervision)

## Roadmap

**v1.0.1 (2-4 weeks):** Entity-type filtering, bug fixes  
**v1.1:** Iris shader compatibility  
**v2.0+:** Team systems, boss radar integration, minimap sync  

---

**Status:** v1.0.0 Production Ready  
**License:** All rights reserved © 2025 nobody174  
**Built with:** NeoForge 1.21.1, Gradle 8.9
