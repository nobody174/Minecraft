# TODO

## Week 1 — Complete

- [x] Define `PetData` record + Codec/StreamCodec
- [x] Register `ModDataComponents`
- [x] Implement `CaptureBallItem` capture interaction
- [x] Implement release interaction
- [x] Build succeeds
- [x] XP system (kill-based) + evolution stage-up
- [x] HUD overlay + tooltip display

## Current (Week 2)

- [x] In-game smoke test: capture and release confirmed working (cow/pig/sheep); HUD and tooltip confirmed showing live stats on the held ball
- [x] Additional XP sources: crafting (`PlayerEvent.ItemCraftedEvent`), exploration (`Player.walkDist` delta, 20-tick batched), mining/chopping (`BlockEvent.BreakEvent`, 1 XP/block)
- [x] In-game smoke test: confirmed kill XP grants correctly via off-hand vessel; confirmed walking-only exploration XP did NOT cover block-breaking as expected — fixed with the BlockEvent.BreakEvent source above (still needs in-game re-test)
- [x] Stat-based combat effects: `PetStatApplier` sets MAX_HEALTH/ATTACK_DAMAGE/ARMOR from PetData and scales MOVEMENT_SPEED relative to vanilla base, applied to the released mob on spawn
- [x] Multiple species base-stat variation (hostile/passive/neutral categories via `SpeciesStats`)
- [x] Creative tab registration for `CaptureBallItem` (added to Tools & Utilities)

## Technical debt flagged during Week 2

- [x] Fixed: exploration/tick XP tracking now cleans up per-player map entries on `PlayerLoggedOutEvent`
- [x] Fixed: HUD showed stale pet stats after release (no packet fired on release) — removed push-packet networking entirely, HUD now reads the held item's data component directly each frame

## Week 3

- [x] In-game smoke test of capture → release → combat-stat-effect loop confirmed (capture/release verified working end-to-end)
- [x] Full XP → evolve loop confirmed (see Phase 3 evolution-cap fix + retest below); RC1 → RC2 → RC3 versioning was never formalized but the underlying functionality is fully verified

## Phase 2 — Battle System

- [x] `PET_OWNER`/`RELEASED_PET_DATA` attachments for persistent released-pet identity
- [x] `PetBattleEvent`: right-click challenge, nearby-pet lookup, instant power-based resolution, XP reward
- [x] Fix: re-capturing a released pet preserves progress instead of resetting to species base
- [x] In-game smoke test (real 2-player co-op): battle triggered, winner XP/message confirmed (see Phase 3 battle engine section for the full retest history) — re-capture-preserves-progress confirmed as part of normal play
- [x] Fixed: 30-second per-pet battle cooldown added to prevent rapid re-challenge XP farming

## Phase 2 — Rarity Tiers

- [x] `PetRarity` enum: COMMON/UNCOMMON/RARE/EPIC, weighted roll, stat multiplier
- [x] Applied at capture time in `SpeciesStats.baseStatsFor`
- [x] Tooltip + HUD color-coded rarity display
- [x] In-game smoke test: captured multiple mobs, confirmed COMMON (white) and RARE (blue) rolls with correct stat multipliers applied and color-coded tooltip/HUD display

## Phase 2 — Breeding Mechanics

- [x] `PetBreedingEvent`: right-click your own fully-evolved released pet (with an empty capture ball) while another of your own fully-evolved pets of the same species is nearby to breed an offspring
- [x] Offspring stats are the average of both parents' stats, with a 5% chance to roll a rarity upgrade (scaling stats up proportionally)
- [x] 5-minute per-pet breeding cooldown to prevent rapid re-breeding
- [x] In-game smoke test: confirmed the evolution-stage gate correctly rejects breeding attempts on non-fully-evolved pets (chat message shown as designed)
- [x] In-game smoke test: leveled two RARE cows to max level, placed them nearby, and a 3rd RARE cow (level 0) appeared shortly after — confirmed a successful breed spawns an offspring with correct rarity. Phase 2 is now fully manually verified end-to-end.

## Phase 3 — v2.0.0 Major Expansion (built autonomously; everything below requires manual play-testing — none of it has been verified in an actual running game, only by successful compilation and code-tracing)

### Modular restructure
- [x] Package restructure into capture/creature/battle/skills/client.ui/render — build-verified, zero intended behavior change
- [x] In-game smoke test: capture/release/XP/evolve/battle/breed all confirmed working post-refactor (covered by every smoke test performed since)

### Creature stats/leveling
- [x] `special` 5th core stat added to `PetData`, `StreamCodec` re-nested to fit
- [x] `level()` derived from `evoStage` (no parallel level field)
- [x] `SpeciesStats`/`PetStatApplier`/`EvolutionRules`/tooltip/HUD/breeding updated for Special
- [x] In-game smoke test: Special stat confirmed showing correctly in tooltip/HUD and increasing per level (see the per-level stat scaling confirmation below)

### Skills system
- [x] `Skill`/`SkillEffectType`/`SkillElement`/`SkillRegistry` (fixed in-code registry, not JSON datapack — see CHANGELOG.md for reasoning)
- [x] Level-gated skill unlocking (3-5 skills by full evolution)
- [x] In-game smoke test: confirmed via the battle HUD's skill prompts showing progressively more options as a pet leveled up during testing

### Battle engine
- [x] `BattleParticipant`/`BattleAi`/`BattleSession`/`BattleEngine` replacing instant stat comparison
- [x] HP-based auto-combat behavior change (defensive/heal preference below 35% HP)
- [x] Skill cooldowns enforced via tick-based round resolution (one round per 2 seconds)
- [x] Existing right-click trigger, nearby-pet lookup, and 30-second cooldown preserved
- [x] Fixed during regression pass: damage advantage multiplier now uses the actually-applied skill's element, not a re-derived one
- [x] Fixed: DEFENSE_BUFF skill effect was a documented no-op — now halves the next incoming damage instance, consumed on that hit
- [x] In-game smoke test (real 2-player co-op via laptop): battle triggered correctly, HUD/HP bars showed for both pets, took multiple rounds, winner message displayed correctly — confirmed working
- [x] Found and fixed: pets visually wandered independently with normal AI during battle, totally decoupled from the HP/skill simulation — added `BattleVisuals` (AI freeze, mutual facing, per-round lunge)
- [x] In-game smoke test (2-player co-op): pets froze and faced each other correctly, confirmed working
- [x] Found and fixed: pets ended up standing inside each other after a few rounds — lunge had no way to return home, so repeated lunges compounded; now records a home spot per entity and returns to it the tick after lunging
- [x] In-game smoke test: confirmed pets stay visually separated across many rounds after the lunge/overlap fix

### Minimal battle UI + networking
- [x] `BattleHudOverlay` (HP bars + numbered skill prompts), `BattleSkillChoicePayload`, `BattleStateSyncPayload`, `BattleNetworking`
- [x] In-game smoke test (2-player co-op): battle HUD top-center confirmed showing, took multiple rounds, winner message displayed — confirmed working
- [x] Found and fixed: pressing 1/2 during the "press 1 for X, 2 for Y" prompt swapped the player's hotbar slot — vanilla's hotbar-switch keybinds also consumed the same digit-key press alongside the mod's own GLFW polling. Now drains the pending click on those 5 keybinds every tick while a battle is active (`KeyMapping.consumeClick()`), so vanilla never sees them.
- [x] Added debug logging (`-Dpetevolution.debug=true`) to the skill-choice payload's full path (client send + server accept/reject reasons) for diagnosing this kind of issue faster in future
- [x] In-game smoke test: confirmed pressing 1-5 no longer swaps hotbar slots and now correctly overrides the AI's skill choice

## Pet ownership protection and behavior modes (found during real co-op testing)

- [x] Fixed real exploit: any player could right-click and steal another player's released pet — `CaptureBallItem` now blocks capture unless the capturing player is the pet's actual owner
- [x] Fixed: released pets had no visible ownership indicator — name tag ("PlayerName's species") now set on release
- [x] Fixed: any player could melee-attack and damage an owned pet — `PetBehaviorEvent` now cancels all `AttackEntityEvent`s against owned pets regardless of attacker
- [x] `PetBehaviorMode` (STAY/FOLLOW): released pets now default to STAY (`setNoAi(true)`) instead of vanilla wandering — fixes pets walking off and becoming unfindable after release
- [x] Left-click cycle (holding any vessel, on your own pet): STAY → FOLLOW (custom goal paths toward owner) → STAY → 3rd click abandons ownership (clears name tag/owner, recapturable by anyone)
- [x] `BattleVisuals.unlockOnEnd` now restores the pet's actual STAY/FOLLOW mode after a battle instead of blanket-resetting to wandering AI
- [x] `/petevolution xp <amount>` OP command: grants XP to the nearest owned pet for testing evolution/skill thresholds without grinding
- [x] In-game smoke test (2-player co-op): pet-stealing blocked, name tag visible, damage immunity confirmed, Stay/Follow toggle confirmed working — all confirmed working
- [x] Found and fixed: abandoning ownership (3rd left-click) left the pet permanently frozen instead of becoming a free wild mob — `abandonOwnership` was applying STAY mode (`setNoAi(true)`) after clearing ownership instead of restoring normal AI
- [x] Found and fixed: `/petevolution xp` worked correctly (only affects the issuer's own nearby owned pet, correctly rejected affecting another player's pet) but a test pet sitting at 15,000+ XP never evolved past stage 2 — the old evolution cap (`MAX_STAGE = 2`, 100/300 XP thresholds) was far too short; expanded to 10 levels with a scaled XP curve, and fixed `withXp` to climb multiple stages per call instead of just one
- [x] In-game smoke test: confirmed stats scale correctly per level across the full 10-level curve on a fresh pet leveled to max (initial confusion comparing an old pre-fix pet against a new one turned out not to be a bug)
- [x] Added a defensive same-tick debounce to `CaptureBallItem.useOn` after a report of releasing 2 pets "at once" — turned out to be 2 deliberate separate clicks (one per filled vessel in different hotbar slots), which is correct expected behavior, not a bug. Debounce kept anyway as a harmless safety net against any genuine future double-dispatch.
- [x] In-game smoke test: confirmed an abandoned pet now wanders/moves freely like a normal wild mob
- [x] In-game smoke test: confirmed battle skill-override (1-5) now actually changes which skill is used, not just stops swapping hotbar slots

### Original capture device visuals
- [x] "Rune-Bound Vessel" item model + procedurally-generated textures (1 default + 4 rarity variants)
- [x] Rarity-based texture switching wired up via vanilla `custom_model_data` overrides (the only mechanism available pre-1.21.2); set on capture via `PetData.syncCustomModelData()`, cleared on release
- [x] In-game smoke test: confirmed the vessel renders with the new texture, changes color to match rolled rarity (e.g. uncommon turned green), and reverts to the default texture after release

### Testing infrastructure
- [x] `PetEvolution.DEBUG_LOGGING` toggle (`-Dpetevolution.debug=true`)
- [x] `/petevolution test` command spawns a fully-statted, fully-skilled test pet
- [x] In-game smoke test: confirmed `/petevolution test` spawns the expected EPIC wolf with correct stats (this was the same wolf referenced in earlier battle testing)
