# Roadmap

## Phase 1 — v0.1.0 (Core Pet System)

**Week 1 — Complete**
- Data component structure (`PetData`)
- Capture ball item + capture mechanic
- Release mechanic (spawn pet back into world)
- XP system (kill-based) and evolution stage-up
- HUD overlay + tooltip stat display
- Pushed to private GitHub repo

**Week 2 — Complete**
- Stat tracking (HP, ATK, DEF, SPD)
- Experience system (battle/crafting/exploration XP sources)
- Evolution logic (stat-threshold triggered)
- Species-based base stat variation (hostile/passive/neutral)
- Stat-based combat effects applied to released mobs (`PetStatApplier`)

**Week 3 — RC1**
- Pet UI / HUD (status display, evolution progress) — Complete
- Tooltip stat display — Complete
- Client/server pet data consistency — resolved by reading the data component directly off the synced ItemStack rather than a custom packet (see REUSED_FROM.md)
- Code-review pass for capture/release/XP/evolution/stat-apply logic — Complete
- **RC1 tagged**: all Phase 1 core systems implemented and build-verified; in-game manual smoke test still outstanding (see TODO.md)

## Phase 2 — v0.2.0 (Extended Features)

**Battle system — In Progress**
- `PET_OWNER`/`RELEASED_PET_DATA` entity attachments give released pets a persistent identity (owner + live stats) independent of the capture ball item
- `PetBattleEvent`: right-click another player's released pet while holding an empty capture ball to challenge; nearby owned pet (within 8 blocks) is the combatant
- Instant resolution via derived "battle power" comparison (no real-time combat sim, since released pets are plain vanilla mobs)
- Winner gains XP and has stats reapplied
- Re-capturing a previously-released pet now preserves its battle/XP progress instead of resetting to species base stats (fixed alongside this feature)

- Multiple pet types / rarity tiers
- Breeding mechanics

See [FUTURE_FEATURES.md](FUTURE_FEATURES.md) for ideas beyond v0.2.0.
