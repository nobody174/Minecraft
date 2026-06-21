# Buddy Beast

A custom AI mob mod that adds intelligent, player-trained creatures to Minecraft with advanced pathfinding, decision-making, and goal-based behavior systems.

**Version:** 0.1.0 (Early Development)  
**Minecraft:** 1.21.1  
**NeoForge:** 21.1.233+

## Features (v0.1.0)

- **Custom Entity System** — BuddyBeast mobs with custom model and rendering
- **Goal-Based AI** — Intelligent behavior using NeoForge goal system (Follow, Stay, Idle)
- **Taming Mechanic** — Right-click to tame buddies and make them your companion
- **Data Persistence** — Buddies saved in player NBT and survive world reloads
- **Multiplayer Sync** — Network packet synchronization for multiplayer worlds
- **Visual Feedback** — Health bar, status effects, and animations

## Installation

1. Install [NeoForge 21.1.233+](https://neoforged.net/)
2. Download the latest JAR from Releases
3. Place in `%appdata%\.minecraft\mods\`
4. Launch Minecraft

## Quick Start

- Find a BuddyBeast in the wild (spawn command: `/summon buddybeast:buddy_beast`)
- Right-click with an empty hand to tame
- Right-click again to command (Follow / Stay / Idle)
- Buddies follow you and protect the area

## Limits

- **Max buddies per world:** 50 (performance)
- **Max buddies per player:** 10
- **Chunk loading:** Buddies despawn if chunk unloads (technical limitation)
- **Multiplayer sync:** May be jittery in high-latency environments

## Known Issues

See [KNOWN_ISSUES.md](KNOWN_ISSUES.md)

## Development

See [ARCHITECTURE.md](ARCHITECTURE.md) for technical design.  
See [ROADMAP.md](ROADMAP.md) for planned features.

## License

All rights reserved © 2025 nobody174

## Credits

Built with [NeoForge](https://neoforged.net/) and [Claude Code](https://claude.com/claude-code)
