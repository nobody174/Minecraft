# Pet Evolution

A NeoForge mod for Minecraft 1.21.1 that lets players capture, train, and evolve companion pets with persistent stats and multiplayer sync.

## Status

🚧 In active development (v0.1.0) — not yet released.

## Features (v0.1.0 scope)

- **Capture System** — Right-click a wild creature with a capture ball to store it as an item.
- **Pet Stats** — HP, ATK, DEF, SPD tracked per pet, stored via Data Components.
- **Experience System** — Pets gain XP from battles, crafting, and exploration.
- **Evolution Mechanics** — Pets evolve once stat thresholds are reached.
- **Pet UI** — Tooltip and HUD display of pet stats and evolution progress.
- **Multiplayer Sync** — Pet data persists across server restarts and client transfers.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.233
- Java 21

## Building

```powershell
./gradlew build
```

JAR output: `build/libs/petevolution-<version>-mc1_21_1.jar`

## Documentation

- [ARCHITECTURE.md](ARCHITECTURE.md) — System design and data flow
- [ROADMAP.md](ROADMAP.md) — Planned features and milestones
- [CHANGELOG.md](CHANGELOG.md) — Version history
- [KNOWN_ISSUES.md](KNOWN_ISSUES.md) — Current bugs and limitations
