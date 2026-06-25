# ArmorAura - Development Instructions

## Project Status

**Version:** v1.1.0  
**Status:** Public Release  
**Last Updated:** June 25, 2026

All core features implemented, tested, and released. Ready for public use.

### v1.1.0 — Glow Aura Feature

Added a second, independent visual effect: `/auraglow`, a lightweight colored
outline rendered directly on the player model (no particles, no custom
shaders, no framebuffer). See `client/PlayerGlowAuraLayer.java` for the
render-layer implementation and `client/AuraGlowConfig.java` /
`client/AuraGlowManager.java` for state. Synced via
`networking/AuraGlowRequestPacket.java` (client→server) and
`networking/AuraGlowStatePacket.java` (server→all clients), so other players
see your glow in multiplayer.

Implementation note: the vanilla "Glowing" outline effect derives its color
from `Entity.getTeamColor()` (scoreboard team color), which can't be hijacked
per-player without abusing the team system. Instead, `PlayerGlowAuraLayer`
renders a slightly scaled-up copy of the player model into
`RenderType.entityTranslucentEmissive`, with a fully custom packed ARGB color
and a sine-wave pulse — same general technique vanilla uses for things like
`SlimeOuterLayer`, just with our own color logic instead of being gated by
the outline buffer.

This also fixed a pre-existing bug in `server/ArmorDetectionManager.java`:
it was broadcasting an `AuraStatePacket` to every connected player, every
single server tick, regardless of whether anything changed. It now tracks
last-known state per player and only broadcasts on change.

**Post-release follow-ups (same v1.1.0 cycle):**
- Fixed dedicated-server crash (`NoClassDefFoundError: RenderLayerParent`) —
  the glow layer registration was being touched from common code that also
  runs on the server. Now gated behind `FMLEnvironment.dist == Dist.CLIENT`
  in `ArmorAuraMod`'s constructor, with the actual client-only method
  references isolated inside `AuraGlowLayerRegistration.register(IEventBus)`.
- Made the glow noticeably brighter: switched from normal alpha blending to
  **additive** blending (`PlayerGlowAuraLayer.additiveGlow(...)`, a custom
  `RenderType` built from the same shader as `entityTranslucentEmissive` but
  with `ADDITIVE_TRANSPARENCY`), and added a two-pass inner shine (1.08x) +
  outer halo (1.22x) instead of one single 1.06x pass.
- Added `/armoraura enable|disable|toggle` (was previously only possible to
  silence the particle aura by setting particles to a low count).
- Added `AuraGlowPalette` — named color shortcuts for `/auraglow color`
  (`cyan`, `white`, `red`, `green`, `gold`, `purple`, `orange`, `pink`,
  `blue`, `yellow`), tab-completable, with `/auraglow colors` to list them.
  Raw hex still works for anything not on the list.
- Fixed settings not persisting: `AuraConfigFile` previously never saved
  `AuraConfig.enabled` and didn't know about `AuraGlowConfig` at all, so
  toggling either off and restarting silently reverted to defaults. Both are
  now part of `aura-config.json` (`enabled` top-level, glow settings under a
  `glow` object) and every mutating command calls `AuraConfigFile.saveConfig()`.

## Repository Info

- **GitHub:** https://github.com/vartdalffs/Minecraft/tree/main/mods/armor-aura
- **Local Path:** `D:\Claude AI Projects\projects\GitHub\Minecraft\mods\armor-aura`
- **License:** All rights reserved © 2025 nobody174
- **Author:** nobody174 (vartdal@gmail.com)

## Key Files

### Core Implementation
- `src/main/java/com/nobody174/armoraura/ArmorAuraMod.java` — Main mod class
- `src/main/java/com/nobody174/armoraura/client/AuraRenderer.java` — Particle rendering (4-ring system)
- `src/main/java/com/nobody174/armoraura/client/AuraClientCommands.java` — All in-game commands (`/armoraura` + `/auraglow`)
- `src/main/java/com/nobody174/armoraura/client/AuraConfig.java` — Particle aura config management
- `src/main/java/com/nobody174/armoraura/client/AuraConfigFile.java` — JSON file I/O
- `src/main/java/com/nobody174/armoraura/client/AuraGlowConfig.java` — Glow-aura settings (color, intensity, pulse)
- `src/main/java/com/nobody174/armoraura/client/AuraGlowManager.java` — Per-player glow state (local + synced remote)
- `src/main/java/com/nobody174/armoraura/client/PlayerGlowAuraLayer.java` — Render layer that draws the glow outline
- `src/main/java/com/nobody174/armoraura/client/AuraGlowLayerRegistration.java` — Attaches the glow layer to player renderers

### Configuration System
- **Location:** `~/.minecraft/config/armoraura/aura-config.json`
- **Format:** JSON with hot-reload via `/armoraura reload`
- **Default preset:** MEDIUM (10 particles, 0.7 radius, 2 rings, 1.3 speed)

### Commands

`/armoraura` — particle aura, client-side only (cosmetic display preference, not synced):
1. `/armoraura preset NAME` — Switch preset (minimal, medium, maximum)
2. `/armoraura particles N` — Set particles per ring (1-20)
3. `/armoraura radius X` — Set aura distance (0.1-1.5)
4. `/armoraura speed X` — Set rotation speed (0.1-3.0)
5. `/armoraura rings N` — Set number of rings (1-4)
6. `/armoraura effect TYPE` — Set particle type (10+ types)
7. `/armoraura status` — Display all settings
8. `/armoraura reload` — Reload from JSON file

`/auraglow` — colored outline glow, client command but state is synced to the server so other players see it:
1. `/auraglow toggle` / `on` / `off` — Enable or disable
2. `/auraglow color NAME|HEX` — Set glow color by name (see `AuraGlowPalette`) or hex (e.g. `33CCFF`)
3. `/auraglow colors` — List all named colors and their hex codes
4. `/auraglow intensity X` — Set base brightness (0.05-1.0)
5. `/auraglow pulse X` — Set pulse speed (0-5.0, 0 = static)
6. `/auraglow status` — Display all glow settings

`/armoraura` also gained: `enable` / `disable` / `toggle` for the particle aura as a whole.

## Architecture Notes

### Particle Ring System
- **4 fixed heights:** 0.3 (feet), 0.8 (knees), 1.3 (belly), 1.85 (head)
- **Configurable count:** ringCount parameter (1-4) controls which rings render
- **Rotation:** Each ring rotates independently based on rotationSpeed

### Particle Types
Supported: glow, flame, electric_spark, crit, end_rod, soul, portal, dragon_breath, happy_villager, note

### Day/Night Intensity
- **Night (13000-23000 ticks):** 100% intensity
- **Day (0-13000 ticks):** 50% intensity
- **Automatic:** No config option to change this

### Client-Side Only Design
- All commands run via `RegisterClientCommandsEvent` (NOT `RegisterCommandsEvent`)
- Renderer updates in real-time from AuraConfig reads
- JSON hot-reload allows instant testing without restart

## Build System

- **Build Tool:** Gradle 8.9
- **Java Version:** 21
- **Minecraft Version:** 1.21.1
- **Mod Loader:** NeoForge 21.1.233+

### Build Commands
```bash
./gradlew build -x test          # Build JAR without tests
./gradlew clean build -x test    # Clean rebuild
```

### Compiled JAR
- **Location:** `build/libs/armoraura-[version]-mc[mc_version].jar`
- **Naming:** Auto-includes Minecraft version in filename

## CI/CD Workflows

### Enabled Workflows
1. **build.yml** — Runs on every push/PR to main
   - Builds JAR with Gradle
   - Verifies JAR creation
   - Uploads artifacts

2. **release.yml** — Triggers on tag push (e.g., `git tag v1.0.1`)
   - Builds release JAR
   - Creates GitHub release with auto-generated notes
   - Attaches JAR to release

### Triggering Release
```bash
git tag v1.0.1
git push origin v1.0.1
```

## Testing Notes

- **Unit Tests:** Disabled in build (JUnit Minecraft module conflict)
- **Manual Testing:** Run in-game via `/armoraura` commands
- **JSON Testing:** Edit config file, run `/armoraura reload`, verify visually

## Future Work (v1.1+)

See TODO.md and ROADMAP.md for planned features:
- Custom preset saving
- Keybind system
- Per-ring color customization
- Sound effects (skeleton in place)
- Multiplayer aura syncing

## Common Tasks

### Update Version
1. Edit `gradle.properties`: Change `mod_version = "1.0.1"`
2. Commit: `git commit -am "chore: bump version to 1.0.1"`
3. Tag: `git tag v1.0.1`
4. Push: `git push origin main && git push origin v1.0.1`
5. Workflow auto-creates release

### Add New Particle Type
1. Add to suggestions in `AuraClientCommands.java` line ~65
2. Add case in `AuraRenderer.getParticleType()` method
3. Rebuild and test via `/armoraura effect [type]`

### Modify Ring Heights
⚠️ **Warning:** Changing ring heights requires updating:
- `AuraRenderer.java` — Particle spawn heights
- `AuraPreset.java` — ringHeights array (if needed)
- Documentation if changed

## Documentation

- **README.md** — User guide & installation
- **ROADMAP.md** — Future plans & v1.1+ ideas
- **TODO.md** — Current progress tracking
- **.github/workflows** — CI/CD configs

## Remember

- **Always test commands in-game** after code changes
- **Test JSON hot-reload** — edit config, run `/armoraura reload`, verify
- **Keep ringCount ≤ 4** — Only 4 ring heights exist
- **Particle range is 1-20** — Performance degrades above 15 on some systems
- **Check CI/CD passes** before merging to main
