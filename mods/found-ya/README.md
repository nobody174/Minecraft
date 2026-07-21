# Found Ya!

A modern entity tracking and visualization framework for **NeoForge 1.21.1 / Minecraft 1.21.1**.

Found Ya! replaces vanilla glowing with a flexible, customizable tracking system: persistent target locking with multiple tracking modes, category/type filtering (nearest enemy/friendly/player/specific mob), search-mode area reveals, distance-aware HUD indicators, sky-to-target beacon tracking, a clickable in-game menu, and a custom shader pipeline for enhanced visuals.

## Status

**v1.1.0 Released** — Production-ready entity tracking framework, consolidated into the `nobody174/Minecraft` monorepo. All features implemented, tested, and validated. Ready for public use.

## Features

### Core Tracking
- **Lock targets manually** with `/track lock @e`, or auto-select with the menu / `/track mode nearest`
- **Category tracking** — auto-select the nearest enemy, friendly (animal), player, or a specific mob type (zombie, wolf, etc.)
- **Search Mode** — persistent toggle `/track search true|false` reveals all nearby entities with a light rim
- **Multiple tracking profiles** — switch between Default, PvP, and Exploration profiles (or create your own) via `/track profile use <name>`

### Menu UI
- Press **K** (rebindable) or type bare `/track` to open a compact, clickable menu — no need to memorize command syntax
- Track ▸ Nearest / Enemy / Friendly / Player / By name (clickable list of online players)
- Clear tracked target, Lock (freeze the current auto-followed target), Settings ▸ Options / Search Mode / Profile
- Every menu action has an equivalent typed command that still works unchanged

### Visualization
- **Additive glow rim** on locked targets
- **Through-wall silhouette** — target remains visible when blocked by terrain
- **Sky-to-target beacon** — 300px tall vertical pillar visible from anywhere on screen at distance (beyond configurable beacon distance)
- **Bracket reticle** — on-screen corner brackets show target position with distance-based scale/alpha falloff
- **Off-screen direction caret** — points toward off-screen targets
- **Distance readout** — meters to target displayed on screen
- **Rim-boost shader** — custom post-process bloom-style punch on the locked target's glow

### Configuration
- **In-game config screen** — mod list → Config button, or menu → Settings → Options
- **Command-line config** — `/track config <setting> <value>`
- **Per-profile settings** — each profile has its own near/far distance, bracket size, accent color, beacon settings
- **Target state colors** — hostile mobs render red, distant targets render amber, otherwise cyan

### Settings
- `trackingEnabled` — toggle tracking on/off
- `nearDistance` — minimum range before alpha/scale falloff (default 8m)
- `farDistance` — maximum tracking range (default 64m)
- `bracketBaseSize` — reticle corner bracket size (default 24)
- `beaconEnabled` — toggle beacon mode (default on)
- `beaconDistance` — distance at which beacon replaces reticle (default 48m)
- `rimBoostEnabled` — toggle the post-process shader bloom (default on)

## Commands

### Lock/Clear
- `/track lock @e` — lock the nearest entity (entity selector required; see examples below)
- `/track clear` — clear current lock
- `/track status` — show locked entity UUID

### Tracking Modes
- `/track mode locked` — manual lock only
- `/track mode nearest` — auto-select closest entity
- `/track enemy` / `/track friendly` / `/track player` — auto-select nearest of that category
- `/track type <alias>` — auto-select nearest of a specific type (e.g. `/track type zombie`)

### Profiles
- `/track profile list` — show all profiles (active shown in brackets)
- `/track profile use <name>` — switch to profile
- `/track profile create <name>` — create new profile (seeded from current settings)
- `/track profile delete <name>` — delete profile (can't delete active or last remaining)

### Search Mode
- `/track search true|false` — toggle area reveal (shows all nearby entities)

### Config
- `/track config <setting> <value>` — adjust setting
- `/track config show` — display all current settings

## Entity Selector Examples

The `/track lock` command requires a **Minecraft entity selector**. Here are common patterns:

**Single entity (closest):**
```
/track lock @e[type=zombie,limit=1,sort=nearest]
```

**Any type, closest:**
```
/track lock @e[limit=1,sort=nearest]
```

**Specific distance range:**
```
/track lock @e[type=zombie,distance=..50,limit=1,sort=nearest]
```

**By name:**
```
/track lock @e[name="MyEntity",limit=1]
```

**Hostile mobs only:**
```
/track lock @e[type=#minecraft:attackable,limit=1,sort=nearest]
```

**Note:** Entity selectors **require at least one bracket argument** (even if empty: `@e[]`). The syntax `@e[type=zombie]` *without* a limit will fail if multiple matches exist. Always use `limit=1` and `sort=nearest` together for reliable single-target locking — or just use the menu (`K` or `/track`) to avoid selector syntax entirely.

## Installation

### From JAR
Download the latest JAR from [Releases](https://github.com/nobody174/Minecraft/releases) (tagged `found-ya-vX.Y.Z`) and place it in your `mods` folder.

### From Source
1. Java 21+ and Gradle 8.9+
2. `git clone https://github.com/nobody174/Minecraft.git`
3. `cd Minecraft/mods/found-ya && ./gradlew build`
4. JAR is at `build/libs/foundya-1.1.0-mc1_21_1.jar`

## Technical Details

- **NeoForge 1.21.1** with Gradle 8.9
- **Client-only mod** — safe to add to dedicated servers (auto-ignored)
- **Custom rendering** — `RenderLivingEvent.Post` for per-entity glows
- **Custom shader** — real `RegisterShadersEvent` post-process for bloom
- **JSON config** — persisted to `config/foundya/foundya-config.json`

See [ARCHITECTURE.md](ARCHITECTURE.md) for system design and [ROADMAP.md](ROADMAP.md) for future plans.

## Roadmap

### Near-term
- Performance profiling of `SearchModeScanner` against 100+ nearby entities
- Iris/shader-pack compatibility testing

### Future (v2.0+)
- Team systems (shared tracking across party members)
- Boss tracking integration with other mods
- Minimap integration (JourneyMap/Xaero sync)
- Sound indicators (audio cues on lock/proximity)

See [FUTURE_FEATURES.md](FUTURE_FEATURES.md) for the full backlog.

## Known Limitations

- **Not yet Iris-compatible** — custom shader has not been tested against shader packs (Iris, etc.)
- **Entity selector syntax** — requires proper Minecraft command syntax for typed `/track lock`; the menu (`K`/`/track`) avoids this entirely for common cases

See [KNOWN_ISSUES.md](KNOWN_ISSUES.md) for the full list, including what's been manually verified in-client.

## License

All rights reserved © 2025 nobody174.

## Links

- [GitHub Repository](https://github.com/nobody174/Minecraft/tree/main/mods/found-ya)
- [ARCHITECTURE.md](ARCHITECTURE.md) — system design and component overview
- [ROADMAP.md](ROADMAP.md) — completed milestones and future scope
- [CHANGELOG.md](CHANGELOG.md) — version history
