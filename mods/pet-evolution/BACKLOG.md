# Backlog - Pet Evolution

## Red Line menu system

Every mod with commands should get the same clickable in-game menu pattern
already implemented in **Found Ya!** (`TrackerMenuScreen` + submenus,
opened via keybind or bare `/track`), where every menu action has an
equivalent typed command that still works unchanged. This is meant to be a
consistent UX ("red line") across all mods in this repo that expose
commands — not a one-off.

Pet Evolution's main flow (capture, evolve, breed, battle) is
item/interaction-driven rather than command-driven, so this is a smaller
fit here than for command-heavy mods — but a menu could still cover:
- Viewing a captured/released pet's stats (HP/ATK/DEF/SPD/Special, level,
  rarity) without needing tooltips
- Behavior mode (Stay/Follow) as clickable options instead of only
  left-click cycling
- The `/petevolution test` debug spawn, if kept exposed to non-debug users

Not started yet — reference Found Ya!'s menu implementation
(`mods/found-ya/`) as the template when this gets picked up.
