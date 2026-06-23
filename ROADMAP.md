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

**Rarity tiers — Complete**
- `PetRarity` enum (COMMON/UNCOMMON/RARE/EPIC), weighted roll on capture, stat multiplier applied to species base stats, color-coded in tooltip/HUD

**Breeding mechanics — Complete**
- `PetBreedingEvent`: right-click your own fully-evolved released pet (holding an empty capture ball) while another of your own fully-evolved pets of the same species is within 6 blocks to breed an offspring
- Offspring base stats are the average of both parents' stats, with a 5% chance to roll a one-tier rarity upgrade (proportionally scaling stats up)
- 5-minute per-pet breeding cooldown prevents rapid re-breeding

## Phase 3 — v2.0.0 (Major Expansion — built autonomously, manual play-testing outstanding)

**Modular package restructure — Complete**
- Flat package layout reorganized into `capture/`, `creature/`, `battle/`, `skills/` (new), `client/ui/`, `render/` (new); `event/` retains XP and breeding. Pure refactor, build-verified before any further work.

**Creature stats/leveling upgrade — Complete**
- 5th core stat (`special`) added to `PetData`, re-nested `StreamCodec.composite` groups to fit. `level()` derives from `evoStage` rather than introducing a parallel level field. `SpeciesStats`/`PetStatApplier`/`EvolutionRules`/tooltip/HUD/breeding all updated.

**Skills system — Complete**
- Data-driven `Skill` model (id, effect type, element, power, cooldown) with a fixed in-code `SkillRegistry` (5 skills + universal heal). Creatures unlock skills progressively by level (3-5 unlocked by full evolution).

**Battle engine upgrade — Complete**
- Replaced instant stat-power comparison with a tick-driven `BattleSession`/`BattleEngine`: HP-based auto-combat AI (`BattleAi`), skill cooldowns, speed-based turn order, element advantage multiplier. Existing right-click trigger and 30-second cooldown preserved.

**Minimal battle UI + networking — Complete**
- `BattleHudOverlay` (HP bars + numbered skill prompts, no GUI Screen). `BattleSkillChoicePayload` (player input, client-to-server) and `BattleStateSyncPayload` (server-to-client, event-driven) — see CHANGELOG.md for why these are intentionally NOT a regression of the removed `PetSyncPacket` pattern.

**Original capture device visuals — Complete, with a documented limitation**
- "Rune-Bound Vessel" item model + procedurally-generated textures (1 default + 4 rarity variants). Rarity-variant texture switching is NOT yet wired up in-game (documented limitation; see FUTURE_FEATURES.md).

**Testing infrastructure — Complete**
- `PetEvolution.DEBUG_LOGGING` toggle, `/petevolution test` command for quick manual verification.

**Outstanding for v2.0.0**: in-game manual play-testing of every new system above (battle engine turn resolution, skill unlocks, HUD rendering, network payloads, capture ball visuals) — this autonomous run verified everything via successful compilation and code-tracing only; see TODO.md.

See [FUTURE_FEATURES.md](FUTURE_FEATURES.md) for ideas beyond v2.0.0.
