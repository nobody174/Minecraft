# TrackerVision v1.0.0

A modern entity tracking and visualization framework for Minecraft 1.21.1 — lock targets, track them across the world, and customize your tracking experience with multiple profiles.

## Features

✨ **Lock targets manually** with `/track lock @e` or auto-select with `/track mode nearest`  
🔍 **Search Mode** — persistent toggle reveals all nearby entities with a light rim  
📊 **Multiple tracking profiles** — Default, PvP, Exploration profiles with independent settings  
🎯 **Sky-to-target beacon** — 300px vertical pillar visible from anywhere on screen  
👻 **Through-wall silhouette** — targets remain visible when blocked by terrain  
💥 **Rim-boost shader** — custom post-process bloom effect on locked target  
⚙️ **In-game config screen** — no command-line tweaking needed  
🎨 **Color-coded targets** — hostile mobs red, distant targets amber, friendly cyan  

## Installation

1. Download `trackervision-1.0.0-mc1_21_1.jar` from [releases](https://github.com/nobody174/trackervision/releases)
2. Place in your `.minecraft/mods` folder
3. Launch Minecraft 1.21.1 with NeoForge

See [INSTALLATION_GUIDE.md](INSTALLATION_GUIDE.md) for detailed instructions.

## Quick Start

**Lock the nearest entity:**
```
/track lock @e[limit=1,sort=nearest]
```

**Toggle search mode (reveal all nearby entities):**
```
/track search true
```

**Switch tracking profiles:**
```
/track profile use PvP
```

**Open config screen:**
- Press ESC → Mods → Config button on TrackerVision

## Compatibility

| Requirement | Version |
|---|---|
| **Minecraft** | 1.21.1 |
| **NeoForge** | 1.21.1+ |
| **Java** | 21+ |
| **Side** | Client-only (server-safe) |

See [COMPATIBILITY.md](COMPATIBILITY.md) for mod compatibility details.

## Features at a Glance

### Tracking
- Manual lock with entity selectors
- Auto-select nearest target
- Multiple tracking modes
- Profile-based settings persistence

### Visualization
- Additive glow rim on targets
- Through-wall silhouette rendering
- Sky-to-target beacon pillar
- Corner-bracket reticle with distance falloff
- Off-screen direction caret
- Distance readout in meters

### Configuration
- In-game settings screen
- Per-profile customization
- JSON config file
- Live reloading on save

## Known Limitations

- **Iris Compatibility:** Custom shader not yet tested with Iris shader packs
- **Entity Selector Syntax:** Requires proper Minecraft command syntax

For full feature list, see [FEATURE_LIST.md](FEATURE_LIST.md).

## Support & Feedback

- **Issues:** [GitHub Issues](https://github.com/nobody174/trackervision/issues)
- **Discussions:** [GitHub Discussions](https://github.com/nobody174/trackervision/discussions)
- **Repository:** [GitHub](https://github.com/nobody174/trackervision)

## What's Next?

**v1.0.1 (2-4 weeks):** Entity-type filtering, bug fixes, performance optimizations  
**v2.0 (future):** Team systems, boss tracking integration, minimap sync, audio cues

See [FUTURE_FEATURES.md](https://github.com/nobody174/trackervision/blob/main/FUTURE_FEATURES.md) for the full roadmap.

---

**Status:** 🚀 v1.0.0 Official Release  
**License:** All rights reserved © 2025 nobody174  
**Built with:** NeoForge 1.21.1, Gradle 8.9, Java 21
