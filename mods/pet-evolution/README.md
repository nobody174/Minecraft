# Pet Evolution

A NeoForge mod for Minecraft 1.21.1 that lets players capture, train, evolve, and battle companion pets with persistent stats and multiplayer sync.

## Status

✅ Core systems implemented and manually verified through real multiplayer play-testing (v0.2.0 / v2.0.0 expansion). See [CHANGELOG.md](CHANGELOG.md) for the full history and [TODO.md](TODO.md) for verification status.

## Features

- **Capture & Release** — Right-click a wild creature with a Rune-Bound Vessel to capture it; right-click a block to release it back into the world.
- **Pet Stats** — HP, ATK, DEF, SPD, and Special tracked per pet, stored via Data Components and entity attachments.
- **10-Level Evolution** — Pets gain XP from battles, crafting, mining/chopping, and exploration, evolving through 10 levels with scaling stat gains.
- **Rarity Tiers** — COMMON / UNCOMMON / RARE / EPIC, rolled on capture with weighted odds and stat multipliers, shown via color-coded tooltips and a rarity-tinted vessel texture.
- **Breeding** — Breed two of your own fully-evolved, same-species pets to produce an offspring with averaged stats and a chance at a rarity upgrade.
- **Battle System** — Challenge another player's released pet to a tick-based battle with a data-driven skill system, auto-combat AI, and an optional player skill-override via a lightweight battle HUD.
- **Ownership Protection** — Released pets display a name tag, can't be stolen or attacked by other players, and default to standing in place until you choose otherwise.
- **Pet Behavior Modes** — Left-click your own released pet to cycle Stay → Follow → release from ownership.
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
- [ROADMAP.md](ROADMAP.md) — Completed milestones and what's next
- [CHANGELOG.md](CHANGELOG.md) — Version history
- [TODO.md](TODO.md) — Manual verification status for every feature
- [KNOWN_ISSUES.md](KNOWN_ISSUES.md) — Current bugs and limitations
- [FUTURE_FEATURES.md](FUTURE_FEATURES.md) — Ideas not yet scoped into a roadmap phase
