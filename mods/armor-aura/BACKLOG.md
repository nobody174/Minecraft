# Backlog - ArmorAura

Cross-mod features and larger work not yet scheduled. See TODO.md for
completed-feature history and near-term polish items.

## Red Line menu system

Every mod with commands should get the same clickable in-game menu pattern
already implemented in **Found Ya!** (`TrackerMenuScreen` + submenus,
opened via keybind or bare `/track`), where every menu action has an
equivalent typed command that still works unchanged. This is meant to be a
consistent UX ("red line") across all mods in this repo that expose
commands — armoraura, boss-radar, buddy-beast, etc. — not a one-off.

ArmorAura has 8 commands today (`preset`, `particles`, `radius`, `speed`,
`rings`, `effect`, `status`, `reload`, plus `/auraglow`'s own subcommands)
that are natural candidates for menu entries once this is tackled.

Not started yet — reference Found Ya!'s menu implementation
(`mods/found-ya/`) as the template when this gets picked up.

## GameTests blocked by upstream NeoForge/NeoGradle bug

`./gradlew runGameTestServer` currently fails immediately with:

```
IllegalStateException: Module named org.objectweb.asm.tree.analysis was
already on the JVMs module path loaded from .../asm-analysis-9.8.jar but
class-path contains it at location .../asm-analysis-9.5.jar
```

Root cause: NeoGradle's `writeMinecraftClasspathGameTestServer` /
`writeMinecraftClasspathJunit` tasks generate a classpath file that bundles
both asm-analysis 9.5 (from `net.neoforged.fancymodloader:junit-fml`'s own
transitive dependency) and 9.8 (from NeoForge itself), which collide at JVM
module-path load time. This is a known, currently-unresolved upstream bug —
not something fixable from this mod's `build.gradle.kts`:
- https://github.com/neoforged/NeoForge/issues/1540
- https://github.com/neoforged/NeoForge/issues/2209
- https://github.com/neoforged/FancyModLoader/issues/216

The same bug blocked plain `./gradlew test` too; that was worked around by
moving plain-JUnit tests into a separate `unitTest` source set/task that
NeoGradle never wires the FML launcher onto (see `build.gradle.kts`). That
workaround does **not** apply to GameTests — they need a real, running
Minecraft server instance via the FML launcher, so there's no way to route
around the broken launcher for these specifically.

Status: `empty_3x3x3.nbt` structure file is in place
(`src/main/resources/data/armoraura/gametest/structures/`) and the 11
GameTest methods in `AuraConfigGameTest.java` are otherwise ready to run —
this is purely blocked on the upstream fix landing in a future NeoGradle
release. Revisit `runGameTestServer` once NeoForge/NeoGradle ships a fix for
the linked issues above.
