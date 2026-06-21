# Known Issues

## v0.1.0

Currently in development. No known issues yet.

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

**v0.1.0** - Early Development (In Progress)

Stability and compatibility will improve as development continues.
