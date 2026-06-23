# Changelog

All notable changes to Pet Evolution are documented here.

## [Unreleased] — v0.1.0-rc1

### Added
- Project scaffolding (Gradle, NeoForge metadata, folder structure)
- Initial mod class and event bus registration
- `PetData` data component (Codec + StreamCodec) with HP/ATK/DEF/SPD/XP/evolution stage
- `CaptureBallItem`: capture wild mobs (right-click), release them back into the world (right-click block)
- Capture restrictions: players, vehicles, leashed mobs cannot be captured
- Tooltip display of pet stats on the capture ball
- XP system: killing an entity while holding a captured pet ball grants XP (`PetXpEvent`)
- Evolution: stat thresholds trigger automatic stage-up with stat gains (`EvolutionRules`)
- HUD overlay showing active pet's stats, XP, and evolution stage
- `SpeciesStats`: base stats now vary by captured species category (hostile/passive/neutral)
- Crafting XP: `PlayerEvent.ItemCraftedEvent` grants XP to held capture ball
- Exploration XP: tracks `Player.walkDist` delta per-player, batched every 20 ticks, grants XP per distance threshold
- `PetStatApplier`: applies PetData stats to the released mob's vanilla attributes (max health, attack damage, armor, movement speed) so stats and evolution have real gameplay effect

### Fixed
- HUD stale-state bug: `PetHudOverlay` now reads `PetData` directly from the held capture ball's data component each frame instead of relying on a server-pushed `PetSyncPacket`, which never fired on release and left the HUD showing outdated stats

### Removed
- `PetSyncPacket`, `PetNetworkHandler`, `ClientPetState` — redundant once the HUD reads the (already vanilla-synced) ItemStack data component directly; see REUSED_FROM.md for rationale

## [Unreleased] — v0.2.0 (Phase 2, in progress)

### Added
- `component/PetOwnerData.java` + `component/ModAttachments.java`: NeoForge entity attachments giving released pets a persistent owner UUID (`PET_OWNER`) and live stat snapshot (`RELEASED_PET_DATA`) independent of the original capture ball item
- `event/PetBattleEvent.java`: pet-vs-pet battle system. Right-click another player's released pet while holding an empty capture ball to challenge it with your own nearby released pet; resolved instantly via derived battle power (`hp + atk*2 + def + spd`); winner gains XP and has stats reapplied; 30-second per-pet cooldown prevents rapid re-challenge XP farming
- `component/PetRarity.java`: rarity tiers (COMMON/UNCOMMON/RARE/EPIC) rolled on capture with weighted odds (60/25/12/3) and a stat multiplier (1.0/1.15/1.35/1.6); shown color-coded in the tooltip and HUD

- `event/PetBreedingEvent.java`: breeding mechanics. Right-click your own fully-evolved released pet (holding an empty capture ball) while another of your own fully-evolved pets of the same species is within 6 blocks; offspring base stats are the average of both parents' with a 5% chance of a one-tier rarity upgrade (scaling stats up proportionally); 5-minute per-pet breeding cooldown

### Fixed
- Re-capturing a previously-released pet no longer resets it to species base stats — `CaptureBallItem` now checks for an existing `RELEASED_PET_DATA` attachment and preserves battle/XP progress if present

## [Unreleased] — v2.0.0 (Major Expansion, in progress — built autonomously, manual play-testing outstanding)

### Changed — Modular package restructure (pure refactor, zero behavior change)
- Reorganized the previously-flat package layout into `capture/` (capture mechanics + item binding: `CaptureBallItem`, `ModItems`), `creature/` (stats/leveling/data model: `PetData`, `PetRarity`, `EvolutionRules`, `PetStatApplier`, `SpeciesStats`, `ModAttachments`, `PetOwnerData`, `ModDataComponents`), `battle/` (combat engine: `PetBattleEvent` plus new v2.0 classes), `skills/` (new), `client/ui/` (UI overlays: `PetHudOverlay`, `PetTooltipHandler` plus new battle HUD), and `render/` (new). `event/` retains `PetXpEvent` and `PetBreedingEvent`, which aren't part of the new combat/skill systems.
- Build verified BUILD SUCCESSFUL before any further v2.0 work began.

### Added — Creature stats/leveling upgrade
- `PetData` gains a 5th core stat, `special`, for non-physical skill power scaling. Re-nested the `StreamCodec.composite` groups as `CoreStats(hp,atk,def,spd,special)` (5 fields — now exactly at the 6-field-pair arity cap) + `MetaFields(xp,evoStage,speciesId,rarity)` (unchanged).
- **Design decision**: no parallel `level` field was introduced. `PetData.level()` derives level from `evoStage` (`level = evoStage + 1`), since evoStage already gates evolution-based progression and a separate level concept would duplicate that axis.
- `EvolutionRules` gains `SPECIAL_GAIN_PER_STAGE`, a `SKILL_SLOT_UNLOCK_LEVEL` table, and `unlockedSkillCount(level)`.
- `SpeciesStats`, `PetStatApplier` (documented as intentionally NOT applying Special to any vanilla attribute), tooltip, HUD, breeding, and (legacy instant-resolution) battle power all updated to account for Special.

### Added — Skills system (`skills/`)
- New `Skill` data model (id, displayName, effectType, element, power, cooldownTicks), `SkillEffectType` (PHYSICAL_DAMAGE/SPECIAL_DAMAGE/HEAL/DEFENSE_BUFF), and `SkillElement` (NEUTRAL/FIERCE/GUARDIAN/SWIFT with a simple rock-paper-scissors advantage/disadvantage multiplier).
- `SkillRegistry`: a fixed, shared 5-skill list plus a universal `RECOVER` heal skill (the auto-combat low-HP fallback). `skillsForLevel(level)` returns the level-gated prefix of unlocked skills (3-5 skills depending on level).
- **Design decision**: skills are an in-code fixed registry, NOT Codec-backed JSON datapack entries. JSON datapacks for skills in 1.21.1 would require dynamic registry + reload-listener wiring; for a fixed, designer-authored skill list with no current need for player-datapack extensibility, that complexity/risk wasn't justified for this autonomous run. The `Skill` record itself is still plain data with no embedded behavior, preserving the data-driven spirit even though it isn't datapack-loaded.

### Changed — Battle engine upgrade (`battle/`)
- Replaced the instant stat-power-comparison battle resolution with a hybrid, tick-driven system: `BattleParticipant` (battle-local HP pool + per-skill cooldowns, kept separate from the entity's live vanilla health so a battle doesn't actually damage/kill the released mob in the world), `BattleAi` (HP-based auto-combat: prefers a ready defensive/heal skill below 35% HP, otherwise highest-power ready offensive skill, with a guaranteed fallback), `BattleSession` (resolves one round every 2 seconds; SPD stat decides turn order; applies skill effects with an element advantage/disadvantage multiplier), and `BattleEngine` (`ServerTickEvent.Post`-driven registry of active sessions; rewards the winner's XP and reapplies stats on conclusion).
- `PetBattleEvent` now starts a `BattleSession` instead of resolving instantly, but the right-click trigger, nearby-owned-pet lookup, and existing 30-second per-pet anti-farm cooldown are preserved exactly. Added one new safety check, `BattleEngine.hasActiveSession()`, to prevent queuing a second session for a pet already mid-battle (a gap that didn't exist under instant resolution).
- Fixed during the post-implementation regression pass: `BattleSession.computeDamage` was re-deriving the target's element via a second, independent `BattleAi.chooseSkill()` call instead of reusing the skill already chosen for that target earlier in the same round — could disagree with the actual skill being applied. Now threads the already-resolved element through.

### Added — Minimal battle UI + networking (`client/ui/`, `battle/`)
- `BattleHudOverlay`: a `GuiGraphics`-only overlay (no GUI Screen, no mouse capture) showing two flat-color HP bars and, while awaiting input, up to 5 numbered skill prompts (vanilla hotbar-key style, 1-5). Polls raw GLFW digit-key state once per client tick, only while a battle HUD is active, to avoid registering new `KeyMapping`s that could conflict with hotbar slots outside of battle.
- `BattleSkillChoicePayload` (client-to-server, new `CustomPacketPayload`): the player's skill-override pick. **This is intentionally NOT a regression of the `PetSyncPacket` push-packet pattern removed earlier in development.** That removal eliminated a redundant *server-push state-sync* packet once the HUD started reading `PetData` directly off the synced ItemStack data component. This payload is the opposite direction and a fundamentally different kind of packet: genuinely new *player input* (a skill pick) that has no other path to the server — there is no data component or attachment the server could "just read" instead. Removing this pattern here would mean dropping the player-override feature entirely, not simplifying an already-redundant sync path.
- `BattleStateSyncPayload` (server-to-client): minimal, event-driven (once per battle round, not per-tick) HP-fraction + unlocked-skill-id sync for the HUD. This one IS a server-push sync packet, but it's justified rather than redundant — a `BattleSession` exists only in server memory, with no synced component/attachment the client could read directly, unlike `PetData`.
- `BattleNetworking` registers both channels and validates the skill choice server-side (must be one of the challenger's actually-unlocked skills, or the universal `RECOVER` skill) before accepting it.

### Added — Original capture device visuals (`render/`)
- "Rune-Bound Vessel" item model + 5 procedurally-generated 32x32 PNG textures (via a one-off `java.awt.image.BufferedImage` + `ImageIO` script, not part of the build): a faceted hexagonal silhouette with a horizontal seam band and a glowing rune-diamond core — an original design, explicitly not a sphere-split-into-two-colored-halves shape. One neutral default texture plus 4 rarity-tier variants (`capture_ball_common/_uncommon/_rare/_epic.png`) with glow colors matched to each `PetRarity`'s existing `ChatFormatting` color.
- Added the item's display name ("Rune-Bound Vessel") to `en_us.json`, which was previously missing.
- **Resolved**: the 4 rarity-variant textures are now wired up to switch dynamically based on `PetData.rarity()`, via vanilla `overrides`/`custom_model_data` predicates on `capture_ball.json` — the only mechanism available in 1.21.1, which predates the data-component-driven item model system added in 1.21.2. `PetData.syncCustomModelData(ItemStack)` sets the component to `rarity.ordinal() + 1` (0 reserved for the neutral default); `CaptureBallItem` calls it on capture and clears `DataComponents.CUSTOM_MODEL_DATA` on release so an emptied ball reverts to the default texture. Not yet visually confirmed in-game.

### Fixed — DEFENSE_BUFF skill effect (was a no-op placeholder)
- `BattleParticipant` now tracks a one-hit defense buff: activating it (via `BattleSession.applySkill`'s `DEFENSE_BUFF` case) halves the next incoming damage instance, consumed on that hit. Previously this case did nothing beyond `BattleAi` already preferring a defensive skill at low HP — the AI's choice now has a real effect.

### Added — Testing infrastructure
- `PetEvolution.DEBUG_LOGGING`: a simple static boolean (overridable via `-Dpetevolution.debug=true`), gating verbose logging in `BattleSession` (round resolution, conclusion).
- `/petevolution test` command (registered via `RegisterCommandsEvent`, requires permission level 2): spawns a fully-evolved EPIC test pet (wolf, full stats including Special, all 5 skills unlocked) owned by the invoking player at their position, for quick manual battle/skill verification without first capturing a real mob.

### Verification note for v2.0
Everything above was verified by **successful compilation** (`BUILD SUCCESSFUL`) after every step, plus a careful manual code-trace of the original capture → release → XP → evolve, battle-trigger, and breeding flows to confirm the refactor and new systems didn't silently change their behavior (see TODO.md for the explicit list of what still needs human in-game play-testing — this is everything new in v2.0, since this autonomous run had no way to launch the actual Minecraft client).
