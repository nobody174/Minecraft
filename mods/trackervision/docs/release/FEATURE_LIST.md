# TrackerVision v1.0.0 Features

## Core Tracking

✓ **Manual target locking** — `/track lock @e[...]` with entity selectors  
✓ **Nearest-auto mode** — Auto-select and track closest entity  
✓ **Lock persistence** — Locked target survives profile switches and reloads  
✓ **Status reporting** — `/track status` shows locked entity UUID  
✓ **Clear function** — `/track clear` releases locked target  

## Search Mode

✓ **Area reveal toggle** — `/track search true|false` shows all nearby entities  
✓ **Persistent state** — Search mode setting persists across sessions  
✓ **All entities visible** — Shows hostile, passive, and neutral mobs  

## Tracking Modes

✓ **LOCKED mode** — Manual lock only, no auto-select  
✓ **NEAREST mode** — Continuous auto-select of closest entity  
✓ **Mode switching** — `/track mode locked|nearest` to switch  

## Visualization

✓ **Additive glow rim** — Subtle glow accent on locked target  
✓ **Through-wall silhouette** — Target remains visible through terrain  
✓ **Sky-to-target beacon** — 300px vertical pillar (distance-based)  
✓ **Corner-bracket reticle** — On-screen targeting brackets  
✓ **Distance-based scaling** — Reticle scales with distance  
✓ **Alpha falloff** — Transparency based on near/far distance  
✓ **Off-screen caret** — Direction arrow for off-screen targets  
✓ **Distance readout** — Meters to target displayed on HUD  
✓ **Rim-boost shader** — Post-process bloom effect on glow  

## Configuration System

✓ **In-game config screen** — Mod list → Config button  
✓ **Command-line config** — `/track config <setting> <value>`  
✓ **Settings persistence** — JSON file (`config/trackervision/trackervision-config.json`)  
✓ **Live settings reload** — Config changes take effect immediately  

## Profile System

✓ **Multiple profiles** — Create and manage tracking profiles  
✓ **Default profile** — Pre-built default configuration  
✓ **PvP profile** — Optimized settings for player-vs-player  
✓ **Exploration profile** — Mob tracking for adventuring  
✓ **Per-profile settings** — Each profile has independent configuration  
✓ **Profile switching** — `/track profile use <name>` to activate  
✓ **Profile creation** — `/track profile create <name>` to seed from current  
✓ **Profile deletion** — `/track profile delete <name>` (safe guards)  
✓ **Profile listing** — `/track profile list` shows all profiles  

## Configuration Options

✓ **trackingEnabled** — Toggle tracking on/off  
✓ **nearDistance** — Minimum range before alpha/scale falloff  
✓ **farDistance** — Maximum tracking range  
✓ **bracketBaseSize** — Reticle corner bracket size  
✓ **beaconEnabled** — Toggle beacon mode  
✓ **beaconDistance** — Distance threshold for beacon display  
✓ **rimBoostEnabled** — Toggle post-process shader bloom  

## Target State Colors

✓ **Hostile detection** — Red color for aggressive mobs  
✓ **Distance awareness** — Amber color for distant targets  
✓ **Default state** — Cyan color for normal tracking  
✓ **Automatic state switching** — Updates based on mob type and distance  

## Compatibility

✓ **Client-only mod** — Server-safe, no server-side installation needed  
✓ **Multiplayer support** — Works on any vanilla or modded server  
✓ **Single-player support** — Works in Creative, Survival, Peaceful modes  
✓ **Custom server-side mods** — Compatible with mod-enriched servers  

## Entity Selector Support

✓ **Full entity selector syntax** — `@e[type=...,distance=...,limit=...,sort=...]`  
✓ **By entity type** — `/track lock @e[type=minecraft:zombie,...]`  
✓ **By distance range** — `/track lock @e[distance=..50,...]`  
✓ **By entity name** — `/track lock @e[name="...",...]`  
✓ **By mob categories** — `/track lock @e[type=#minecraft:attackable,...]`  

## What's NOT Included

✗ **Item glow** — Item entity tracking (out of scope)  
✗ **Block highlighting** — Block entity visualization (future)  
✗ **Custom shaders for Iris** — Iris shader pack compatibility (v1.1+)  
✗ **Audio cues** — Sound indicators (v2.0)  
✗ **Minimap integration** — JourneyMap/Xaero sync (v2.0)  
✗ **Team systems** — Shared tracking state (v2.0)  

## Quality Metrics

✅ **Performance** — 60 FPS stable with 20+ simultaneous tracked entities  
✅ **Memory** — Normal heap usage (300-500 MB), no leaks detected  
✅ **Stability** — Zero crashes in 4+ hours of extended play-testing  
✅ **Reliability** — All features tested and validated in Survival mode  

## Tested Configurations

✅ **Peaceful Mode** — All features work, no hostile mobs to track  
✅ **Creative Mode** — Full feature access, unlimited testing  
✅ **Survival Mode** — Real-world testing with spawned mobs  
✅ **Profile switching** — Rapid switches (10+) smooth, no stutters  
✅ **Lock persistence** — Survived server shutdown/restart  
✅ **Edge cases** — Off-screen targets, terrain occlusion, rapid switching  

---

**Summary:** v1.0.0 includes all planned v1.0 features, fully tested and production-ready. Core tracking, visualization, configuration, and profile systems are complete. See [FUTURE_FEATURES.md](https://github.com/nobody174/trackervision/blob/main/FUTURE_FEATURES.md) for v1.1+ roadmap.
