# Known Issues

## Manually verified in-client (2026-06-26/27, re-confirmed 2026-07-21)
Core rendering/HUD/tracking features have been manually tested in a
running client by the developer: locking a target produces the
corner-bracket reticle with distance readout at close range, and
switching to longer range produces the sky-to-target beacon beam, with
a smooth handoff between the two as distance crosses the threshold. This
was re-confirmed in a fresh play session on 2026-07-21 in addition to the
original 2026-06-26/27 testing (see TEST_REPORT_RC2_VALIDATION.md).
Automated tooling in the dev environment still can't drive Minecraft's
native GLFW window directly, so ongoing verification of new changes
continues to depend on a manual pass by the developer — this note now
reflects that the baseline v1.0 feature set has real human confirmation,
not just documentation claiming so.

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
