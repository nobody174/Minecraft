# GlowTools v1.0.0 - Release Notes

**Release Date:** June 20, 2026  
**Status:** Early Access (Work in Progress)

---

## What's New in v1.0.0

### Core Features ✅

- **Particle Effects Engine**
  - Server-side particle spawning synced to all clients
  - 20+ customizable particle effects
  - Real-time effect switching without server restart

- **Command System**
  - `/glowtool effect <name>` - Set your weapon's particle effect
  - NBT-based persistence - effect stays with your player

- **Tool Support**
  - Swords, Pickaxes, Axes, Shovels
  - Effects visible while holding tool
  - Works in first-person and third-person views

- **Multiplayer Ready**
  - Full server sync
  - Effects visible to other players
  - Persistent across sessions

### Available Effects

```
enchanted_hit, enchant, glow, flame, electric_spark, soul,
soul_fire_flame, sweep_attack, crit, poof, end_rod, portal,
glow_squid_ink, dragon_breath, small_flame, snowflake, gust,
totem_of_undying, spore_blossom_air, sculk_soul
```

---

## Known Limitations (v1.0.0)

- Effects only spawn while actively holding the tool
- No custom color customization (planned for v1.2)
- No favorites/presets system (planned for v1.1)
- Limited to pre-defined Minecraft particles

---

## Planned Features

### v1.1 (Q3 2026)
- Effect favorites/presets
- Custom effect categories
- Hotkey switching between saved effects

### v1.2 (Q4 2026)
- Custom particle colors
- Adjustable effect intensity
- Performance profiles (low/medium/high)

### v2.0 (2027)
- Custom particle patterns and animations
- Effect scheduling (time-based changes)
- Multiplayer effect synchronization API

---

## Technical Details

### Requirements
- Minecraft 1.21.1
- NeoForge 21.1.233+
- Java 21+

### Architecture
- **Server-Side Spawning:** All particles generated on server, sent to clients
- **NBT Persistence:** Effect choice stored in player.dat
- **Rotation-Based Math:** Particles spawn at weapon position using player rotation
- **Thread-Safe:** Uses ServerTickEvent for safe server-thread operations

### Performance Impact
- ~1 particle per tick per player (configurable)
- Negligible CPU impact
- Scales well on multiplayer servers

---

## Bug Fixes

*None yet (first release)*

---

## Installation Instructions

1. Download `glowtools-1.0.0-mc1_21_1.jar`
2. Place in mods folder:
   - **Single Player:** `%APPDATA%/.minecraft/mods/`
   - **Server:** `/server/mods/`
3. Launch game with NeoForge 1.21.1

---

## Support & Feedback

- **Report Bugs:** https://github.com/nobody174/Minecraft/issues
- **Suggest Features:** GitHub Issues (feature request label)
- **General Discussion:** GitHub Discussions

---

## Credits

**Developer:** nobody174  
**Framework:** NeoForge 21.1.233  
**Reference:** TinkersConstruct (rotation math pattern)  
**License:** MIT

---

## Changelog

### v1.0.0 - Initial Release (June 20, 2026)
- ✅ Core particle spawning system
- ✅ Command-based effect switching
- ✅ 20 particle effects
- ✅ Multiplayer server support
- ✅ Complete documentation
- ✅ GitHub release

---

**Happy modding! ✨**
