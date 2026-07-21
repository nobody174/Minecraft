# Known Issues

## Manually verified in-client (2026-06-26/27, re-confirmed 2026-07-21)
Core rendering/HUD/tracking features have been manually tested in a
running client by the developer across two sessions: the original
2026-06-26/27 pass covered all 10 critical tests in TEST_CHECKLIST.md
(off-screen caret, through-wall silhouette, beacon threshold, search
mode, profile persistence, config screen, auto-select chaining, target-
state colors, distance scaling, reticle motion) with 60 FPS stable, no
memory leaks, and zero crashes across a 4+ hour session. A 2026-07-21
session re-confirmed the core lock/beacon/reticle behavior and separately
confirmed the category-tracking and menu UI additions (see CHANGELOG.md
[1.1.0]) working in-client. Automated tooling in the dev environment
still can't drive Minecraft's native GLFW window directly, so ongoing
verification of new changes continues to depend on a manual pass by the
developer against TEST_CHECKLIST.md.

## Rim boost shader not compatibility-tested against Iris/shader packs
`RimBoostEffect`'s custom core shader and manual full-screen-quad
post-process pass have not been tested running alongside Iris or other
shader-pack mods. See RISK_REGISTER.md's Iris-compatibility risk entry —
its scope was written against the (not-yet-built) full outline rewrite,
but the lighter `RimBoostEffect` that shipped instead carries the same
unverified-compatibility caveat.

## FOV accuracy in HUD projection
`ScreenProjection` uses the raw `Minecraft.options.fov()` setting rather
than the actual rendered FOV (`GameRenderer.getFov` is private/inaccessible
to mod code), so projected screen positions will drift slightly during
fog, zoom, or the death-animation FOV pull-in. Acceptable for a HUD
overlay; documented in `ScreenProjection`'s javadoc.
