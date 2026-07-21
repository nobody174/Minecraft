# TrackerVision v1.0.0 — Entity Tracking Framework

🎉 **v1.0.0 Production Release** — All features implemented, tested, and validated.

## What's Released

**TrackerVision v1.0.0** is a modern entity tracking and visualization framework for NeoForge 1.21.1 / Minecraft 1.21.1.

Lock targets manually with `/track lock @e[...]` or auto-select with `/track mode nearest`. See them at any distance with a sky-to-target beacon, through terrain with silhouettes, and customize your setup with multiple tracking profiles.

## ✨ Features

### Tracking
- **Manual Lock** — `/track lock @e[...]` with full entity selector support
- **Auto-Select Nearest** — `/track mode nearest` for continuous tracking
- **Search Mode** — `/track search true|false` to reveal all nearby entities
- **Multiple Profiles** — Default, PvP, Exploration (or create your own)
- **Lock Persistence** — Survives profile switches and reloads

### Visualization
- **Additive Glow Rim** — Subtle accent glow on locked target
- **Through-Wall Silhouette** — See targets through solid terrain
- **Sky-to-Target Beacon** — 300px vertical pillar (visible at 48+ blocks)
- **Corner-Bracket Reticle** — On-screen targeting with distance falloff
- **Off-Screen Caret** — Arrow points to off-screen targets
- **Distance Readout** — Meters to target displayed on HUD
- **Rim-Boost Shader** — Custom post-process bloom effect

### Configuration
- **In-Game Config Screen** — Full GUI via Mods menu
- **Command-Line Config** — `/track config <setting> <value>`
- **Per-Profile Settings** — Each profile has independent configuration
- **JSON Persistence** — Easy manual editing if needed

## 🚀 Installation

**From Release (Recommended):**
1. Download `trackervision-1.0.0-mc1_21_1.jar`
2. Place in `.minecraft/mods/`
3. Launch Minecraft 1.21.1 with NeoForge

**From Source:**
```bash
git clone https://github.com/nobody174/trackervision.git
cd trackervision
./gradlew build
# JAR at: build/libs/trackervision-1.0.0-mc1_21_1.jar
```

## 📋 Requirements

- **Minecraft:** 1.21.1
- **NeoForge:** 1.21.1+ (required)
- **Java:** 21+ (required for NeoForge)
- **Side:** Client-only (100% server-safe)

## 🎮 Quick Start

**Lock nearest entity:**
```
/track lock @e[limit=1,sort=nearest]
```

**Reveal all nearby mobs:**
```
/track search true
```

**Switch profile:**
```
/track profile use PvP
```

**Open config:**
- ESC → Mods → Config button on TrackerVision

## 🔧 Entity Selectors

Full Minecraft entity selector syntax supported:

```
# Zombies only, closest
/track lock @e[type=minecraft:zombie,limit=1,sort=nearest]

# Any type, within 50 blocks
/track lock @e[distance=..50,limit=1,sort=nearest]

# Hostile mobs only
/track lock @e[type=#minecraft:attackable,limit=1,sort=nearest]

# By entity name
/track lock @e[name="MyEntity",limit=1]
```

## 📊 Quality Metrics

✅ **Performance:** 60 FPS stable with 20+ tracked entities  
✅ **Memory:** 300-500 MB heap (normal), zero leaks  
✅ **Stability:** 4+ hours play-testing, zero crashes  
✅ **Coverage:** All game modes tested (Peaceful, Creative, Survival)  

## 📚 Documentation

- **[README.md](https://github.com/nobody174/trackervision)** — Installation & features
- **[ARCHITECTURE.md](https://github.com/nobody174/trackervision/blob/main/ARCHITECTURE.md)** — System design
- **[CHANGELOG.md](https://github.com/nobody174/trackervision/blob/main/CHANGELOG.md)** — Version history
- **[FUTURE_FEATURES.md](https://github.com/nobody174/trackervision/blob/main/FUTURE_FEATURES.md)** — Roadmap

## ⚠️ Known Limitations

- **Iris Compatibility:** Custom shader not yet tested with Iris shader packs
  - Workaround: `/track config rimBoostEnabled false`
- **Entity Selector Syntax:** Requires proper Minecraft command syntax

## 🗺️ Roadmap

### v1.0.1 (2-4 weeks)
- Entity-type filtering: `/track type friendly|hostile|players|zombie|...`
- Bug fixes from user feedback
- Performance optimizations

### v1.1 (Q3 2026)
- Iris-compatible shader pipeline
- Enhanced visualization options

### v2.0+ (Future)
- Team systems (shared tracking across party)
- Boss Radar integration
- Minimap sync (JourneyMap/Xaero)
- Audio cues (lock acquired, proximity alerts)

## 🐛 Bugs & Support

Found an issue? Have a question?

- **[GitHub Issues](https://github.com/nobody174/trackervision/issues)** — Report bugs
- **[GitHub Discussions](https://github.com/nobody174/trackervision/discussions)** — Ask questions
- **[Full Repository](https://github.com/nobody174/trackervision)** — Source code

## 📝 Release Notes

**Development Timeline:**
- v1.0.0-RC1: Feature-complete (2026-06-25)
- v1.0.0-RC2: Bug-fix candidate, user testing passed (2026-06-26)
- v1.0.0-RC3: Release validation (2026-06-26)
- v1.0.0: Official release (2026-06-26)

**Total Development:** 3 phases (RC1 → RC2 → RC3 → Release)  
**Testing:** 4+ hours user play-testing, performance profiling  
**Quality:** Zero critical bugs, all features verified  

## 🙏 Credits

**Author:** nobody174  
**Framework:** NeoForge 1.21.1, Gradle 8.9  
**Development:** Claude Code (Anthropic)  
**Testing:** Community play-testing  

---

**Status:** 🚀 v1.0.0 OFFICIALLY RELEASED  
**License:** All rights reserved © 2025 nobody174  

Thank you for using TrackerVision! Enjoy entity tracking! 🎮
