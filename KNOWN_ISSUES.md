# Known Issues

## v0.1.0

Currently in development.

### Resolved (found via live server testing, Week 3)
- Entity had no attributes registered → fixed via `EntityAttributeCreationEvent`
- Redundant `finalizeSpawn()` call in spawn handler caused unrelated downstream issues → handler removed
- Tamed buddies did not survive world reload → `setPersistenceRequired()` now called on tame and on load

### Resolved (found via live multiplayer testing, Week 4)
- `StayGoal` ran unconditionally and starved `FollowOwnerGoal`/`IdleGoal` → now gated on the `isStaying` toggle
- Two-headed cow rear-head offset only existed in a local dev config, not the code default → baked confirmed value (`0.4`) into `BuddyDevConfig`
- A stale `buddybeast-client.toml` on a second test machine kept overriding the new default with the old pre-fix value → resolved by deleting the stale file; documented as a one-time migration trap for any machine with a config predating this fix
- `/buddybeast killall` always removed 0 entities (wrong bounding box used) → now iterates all level entities directly

### Open
None currently open for v0.1.0.

## Planned Limitations

### Performance
- **Max 50 buddies per world** — Beyond this, tick cost becomes excessive
- **Chunk unloading** — Buddies despawn when their chunk unloads (technical limitation of Minecraft)

### Multiplayer
- **Jitter possible** — In high-latency multiplayer (> 100ms), buddy positions may jitter
- **Sync lag** — Position updates every 10 ticks (0.5 second delay)

### Data Format
- **Version changes break saves** — If NBT format changes, old buddies won't load
- **Mitigation:** Data migration logic planned for v0.2.0

## Reporting Bugs

If you find an issue:
1. Try reproducing with minimal mod setup
2. Check Minecraft log for errors
3. Report with:
   - Java version
   - Minecraft version
   - NeoForge version
   - Steps to reproduce
   - Full error log

## Status

**v0.1.0** - Release Candidate (all Week 1-4 testing complete, including live multiplayer)

Stability and compatibility will improve as development continues.
