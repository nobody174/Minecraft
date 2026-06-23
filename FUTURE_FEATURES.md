# Future Features

## Beyond v0.2.0 (Not Scoped Yet)

- Pet trading between players
- Cosmetic pet skins/variants
- Pet-assisted automation (fetch items, mining helpers)
- Boss-tier rare pet species

These are explicitly out of scope until planned into a roadmap phase.

## Follow-ups from v2.0.0 (documented limitations, not yet implemented)

- **Rarity-based capture ball texture switching**: 4 rarity-tier textures (`capture_ball_common/_uncommon/_rare/_epic.png`) were procedurally generated and committed in v2.0.0, but the item model only references the neutral default texture. Wiring up dynamic switching based on the item's `PetData.rarity()` data component requires either a custom `ItemModel`/`ItemModel.Unbaked` implementation (reading the component at bake/render time) or mapping rarity to `custom_model_data` for a `range_dispatch`. Both are legitimate NeoForge 1.21.1 approaches but were judged too risky to implement and ship unverified in an autonomous run with no way to launch the game and visually confirm the result. A human should pick one approach and verify in-game.
- **Skills system as JSON datapacks**: v2.0.0 ships skills as a fixed in-code registry (`SkillRegistry`) rather than Codec-backed JSON datapack entries under `data/petevolution/skills/*.json`, to avoid the dynamic-registry + reload-listener wiring risk in an unattended run. If player-facing skill customization/datapack extensibility becomes a real requirement, migrating `Skill` to a registered Codec-backed datapack registry is a reasonable follow-up — the `Skill` record itself was designed to make that migration straightforward (it's already plain serializable data).
- **DEFENSE_BUFF skill effect is a no-op placeholder**: `BattleSession.applySkill`'s `DEFENSE_BUFF` case currently does nothing beyond what the cooldown/AI-preference logic already provides (i.e., it's chosen as the AI's "defensive" pick at low HP but doesn't actually reduce incoming damage on the next hit). A real implementation would need a temporary per-participant damage-reduction modifier with its own duration tracking.
- **Battle UI player-override window is not separately enforced**: `BattleSession.PLAYER_INPUT_WINDOW_TICKS` is defined but the current implementation simply accepts whatever the last `submitPlayerChoice` was at the moment a round resolves (the full `ROUND_TICKS` duration), rather than only accepting input during a strictly shorter sub-window at the start of the round. Functionally this still satisfies "player can override, AI proceeds if no input," but a future pass could tighten this to match the originally-envisioned shorter input window for tighter turn pacing.
