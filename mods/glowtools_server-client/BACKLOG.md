# Backlog - GlowTools

## Red Line menu system

Every mod with commands should get the same clickable in-game menu pattern
already implemented in **Found Ya!** (`TrackerMenuScreen` + submenus,
opened via keybind or bare `/track`), where every menu action has an
equivalent typed command that still works unchanged. This is meant to be a
consistent UX ("red line") across all mods in this repo that expose
commands — not a one-off.

GlowTools has `/glowtool effect <effect_name>` — a natural candidate for a
menu with a clickable list of available particle effects instead of
requiring the player to remember/type effect names.

Not started yet — reference Found Ya!'s menu implementation
(`mods/found-ya/`) as the template when this gets picked up.
