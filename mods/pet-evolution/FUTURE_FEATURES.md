# Future Features

## Beyond v0.2.0 (Not Scoped Yet)

- Pet trading between players
- Cosmetic pet skins/variants
- Pet-assisted automation (fetch items, mining helpers)
- Boss-tier rare pet species

These are explicitly out of scope until planned into a roadmap phase.

## Follow-ups from v2.0.0 (documented limitations, not yet implemented)

- **Skills system as JSON datapacks**: v2.0.0 ships skills as a fixed in-code registry (`SkillRegistry`) rather than Codec-backed JSON datapack entries under `data/petevolution/skills/*.json`, to avoid the dynamic-registry + reload-listener wiring risk in an unattended run. If player-facing skill customization/datapack extensibility becomes a real requirement, migrating `Skill` to a registered Codec-backed datapack registry is a reasonable follow-up — the `Skill` record itself was designed to make that migration straightforward (it's already plain serializable data).
- **Battle UI player-override window is not separately enforced**: `BattleSession.PLAYER_INPUT_WINDOW_TICKS` is defined but the current implementation simply accepts whatever the last `submitPlayerChoice` was at the moment a round resolves (the full `ROUND_TICKS` duration), rather than only accepting input during a strictly shorter sub-window at the start of the round. Functionally this still satisfies "player can override, AI proceeds if no input," but a future pass could tighten this to match the originally-envisioned shorter input window for tighter turn pacing.

## Resolved follow-ups (since v2.0.0)

- **Rarity-based capture ball texture switching** — implemented. `PetData.syncCustomModelData()` sets the vanilla `custom_model_data` component (the only mechanism available in 1.21.1, which predates the data-component-driven model system added in 1.21.2) to `rarity.ordinal() + 1` on capture; the item model's `overrides` key off that value to render the matching rarity texture. Cleared on release so an emptied ball reverts to the neutral default. Not yet visually confirmed in-game (requires manual play-test).
- **DEFENSE_BUFF skill effect** — implemented. `BattleParticipant` now tracks a one-hit defense buff (halves the next incoming damage instance, consumed on that hit) activated by `BattleSession.applySkill`'s `DEFENSE_BUFF` case. Not yet manually verified in-game.
