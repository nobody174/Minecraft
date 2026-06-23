# Changelog

All notable changes to Buddy Beast are documented here.

## [0.1.0] - Released

### Added
- `BuddyBeastEntity` with goal-based AI: `FollowOwnerGoal`, `StayGoal`, `IdleGoal`, `LookAtOwnerGoal`
- Right-click taming, with toggleable stay/follow state
- NBT persistence (tamed state, owner, staying) that survives world reload and full server restart
- Network sync of health every 10 ticks via `BuddySyncPacket`
- Health bar rendering and death message to owner when a tamed buddy dies
- Two-headed cow appearance: reuses vanilla `CowModel` geometry with a mirrored
  second head and a hue-shifted recolor, avoiding custom Blockbench work for v0.1.0
- Live-reload dev config (`BuddyDevConfig`) for tuning visual constants without rebuilding
- `/buddybeast spawnmany <count>` and `/buddybeast killall [includeTamed]` dev/test commands

### Fixed (found via live testing)
- Entity attributes were never registered, causing a crash on mod load
- Redundant `finalizeSpawn()` call in the spawn handler
- Tamed buddies not surviving world reload (`setPersistenceRequired()` not called)
- `StayGoal` ran unconditionally and starved follow/idle behavior
- Two-headed cow rear-head offset was only set in a local dev config, not baked
  into the code default — caused visual desync between machines until fixed
- `/buddybeast killall` searched the wrong bounding box and always removed 0 entities

### Verified (live, including multiplayer with a second player on a separate machine)
- Taming, stay/follow toggle, and pathfinding (including swimming) all work correctly
- Buddy is visible and interactable (including damage/death) for other players
- Save/load and full server restart persistence
- Performance: 30 simultaneously spawned buddies via `spawnmany` with no issues
- Two-headed cow renders identically across all tested machines

### Known limitations (see KNOWN_ISSUES.md)
- Buddy appearance is a placeholder (recolored vanilla cow model); a custom
  model is deferred to v0.2.0+, not blocking this release
- Max ~50 buddies per world before tick cost becomes a concern (untested past 30)
- Chunk-unload despawn applies to untamed buddies only, by design

## Versioning

Versions follow Semantic Versioning:
- **Patch (x.x.Z)** — Bug fixes, stability improvements
- **Minor (x.Y.0)** — New features, system expansions
- **Major (X.0.0)** — Large system overhauls, breaking changes
