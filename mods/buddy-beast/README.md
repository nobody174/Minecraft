# Buddy Beast

A custom AI mob mod that adds intelligent, player-trained creatures to Minecraft with advanced pathfinding, decision-making, and goal-based behavior systems.

**Version:** 0.1.0 (Released)  
**Minecraft:** 1.21.1  
**NeoForge:** 21.1.233+

## Features (v0.1.0)

- **Custom Entity System** — BuddyBeast mobs, currently rendered as a
  two-headed cow (a reused vanilla model + recolor, not a custom Blockbench
  model — see Roadmap)
- **Goal-Based AI** — Follow, stay, idle, and look-at-owner behavior, with
  pathfinding confirmed working (including swimming)
- **Taming Mechanic** — Right-click an untamed buddy to tame it; right-click
  again to toggle between following you and staying in place
- **Data Persistence** — Tamed status and ownership survive world reload and
  server restart
- **Multiplayer Sync** — Health and state sync over the network; confirmed
  working live with multiple players on separate machines
- **Visual Feedback** — Health bar above the buddy's head

## Installation

1. Install [NeoForge 21.1.233+](https://neoforged.net/)
2. Download `buddybeast-0.1.0-mc1_21_1.jar` from the
   [v0.1.0 release](https://github.com/nobody174/Minecraft/releases/tag/buddy-beast-v0.1.0)
3. Place in `%appdata%\.minecraft\mods\`
4. Launch Minecraft

## Quick Start

- Find or spawn a BuddyBeast (`/summon buddybeast:buddy_beast`)
- Right-click to tame
- Right-click a tamed buddy to toggle follow/stay
- Tamed buddies persist through world reload and server restarts

## Limits

- **Max buddies per world:** ~50 before tick cost becomes a concern
  (tested up to 30 simultaneously with no issues)
- **Chunk loading:** Untamed buddies despawn if their chunk unloads, like
  any vanilla mob — this is intentional, not a bug
- **Multiplayer sync:** May be jittery in high-latency environments
  (untested above typical LAN latency)

## Known Issues

See [KNOWN_ISSUES.md](KNOWN_ISSUES.md)

## Development

See [ARCHITECTURE.md](ARCHITECTURE.md) for technical design.  
See [ROADMAP.md](ROADMAP.md) for planned features.

## License

All rights reserved © 2025 nobody174

## Credits

Built with [NeoForge](https://neoforged.net/) and [Claude Code](https://claude.com/claude-code)
