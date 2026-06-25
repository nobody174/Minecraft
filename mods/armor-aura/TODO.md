# TODO - ArmorAura v1.1.0

## ✅ COMPLETED: v1.1.0 — Glow Aura

- [x] `PlayerGlowAuraLayer` — custom render layer drawing a scaled, colored,
      pulsing glow directly on the player model, using additive blending
      (two-pass inner shine + outer halo) for real brightness/shine
- [x] `AuraGlowConfig` / `AuraGlowManager` — local + synced remote glow state
- [x] `AuraGlowPalette` — named color shortcuts (cyan, white, red, green, gold,
      purple, orange, pink, blue, yellow), tab-completable; raw hex still works
- [x] `/auraglow` command: `toggle`/`on`/`off`, `color`, `colors`, `intensity`, `pulse`, `status`
- [x] `/armoraura enable`/`disable`/`toggle` — master on/off for the particle aura
- [x] Networking: `AuraGlowRequestPacket` (client→server), `AuraGlowStatePacket`
      (server→all clients) — glow is visible to other players in multiplayer
- [x] Fixed `ArmorDetectionManager` broadcasting `AuraStatePacket` to all
      players every tick regardless of change — now only sends on state change
- [x] Fixed dedicated server crash (`NoClassDefFoundError`) caused by
      client-only render classes being touched from common mod init code
- [x] Fixed settings not persisting across restarts — `enabled` flag and all
      glow settings are now saved to `aura-config.json` on every change
- [x] Cleanup of stale per-player state on logout (server `lastKnownState`,
      client `AuraGlowManager.remoteStates`)
- [x] `./gradlew build` passes; produced `armoraura-1.1.0-mc1_21_1.jar`

## ✅ COMPLETED: v1.0.0 Core Features

### Core Systems
- [x] Gradle build system (NeoForge 1.21.1)
- [x] Armor detection (server-side)
- [x] Particle aura rendering (4-ring architecture)
- [x] Day/night intensity variation (50% day, 100% night)
- [x] Networking & packet sync
- [x] HUD status display

### Configuration System
- [x] JSON-based config file system
- [x] Hot-reload capability (`/armoraura reload`)
- [x] Config file auto-creation on first launch
- [x] Parameter clamping & validation

### Command System
- [x] Full client-side command registration (RegisterClientCommandsEvent)
- [x] All 8 commands working:
  - [x] `/armoraura preset` (minimal, medium, maximum)
  - [x] `/armoraura particles` (1-20)
  - [x] `/armoraura radius` (0.1-1.5)
  - [x] `/armoraura speed` (0.1-3.0)
  - [x] `/armoraura rings` (1-4)
  - [x] `/armoraura effect` (10 particle types)
  - [x] `/armoraura status` (display all settings)
  - [x] `/armoraura reload` (reload from JSON)

### Testing & Validation
- [x] Tested all 8 commands in-game
- [x] Tested preset system (minimal, medium, maximum)
- [x] Tested particle effect switching (10+ types)
- [x] Tested JSON config editing + hot-reload
- [x] Tested day/night intensity calculation
- [x] Validated all parameter ranges
- [x] Verified no FPS impact (< 2% on RTX 3060 with maximum preset)

### Documentation
- [x] README.md (comprehensive feature & usage guide)
- [x] Configuration guide (JSON file structure)
- [x] Command reference (all 8 commands documented)
- [x] Example configurations (5+ examples)
- [x] Troubleshooting guide
- [x] ROADMAP.md updated for v1.0.0

---

## 📋 Future Enhancements (v1.1 & Beyond)

### v1.1 Potential Features
- [ ] Per-ring particle customization (different effects per height)
- [ ] Aura preset saving/loading (custom user presets)
- [ ] Keybind system for quick preset switching
- [ ] Color customization (RGB control per ring)
- [ ] Performance profiles (auto-adjust based on FPS)

### v1.2+ Roadmap
- [ ] Sound effects (ambient aura sound)
- [ ] Custom armor enchantment integration
- [ ] Multiple aura types (spiral, pulse, static)
- [ ] Server-side aura syncing (show player's aura to others)
- [ ] Multiplayer aura visibility settings

---

## 🔧 Deferred Features

### Sound System (Deferred - Skeleton in place)
- [ ] Load aura ambient sound (requires registry setup)
- [ ] Play on aura active (client tick event)
- [ ] Adjustable volume/pitch

### Server Integration (Deferred)
- [ ] Sync per-player aura settings across multiplayer
- [ ] Aura visibility to other players

---

## 🐛 Known Limitations

- Aura only visible to player (not synced to other clients in multiplayer)
- All rings use same particle effect (can't customize per ring individually)
- Sound system skeleton not connected to renderer

---

## Current Status

**v1.0.0 RELEASED** ✨

All core features implemented, tested, and documented. Ready for public use.

Next phase: Community feedback collection before v1.1 planning.
