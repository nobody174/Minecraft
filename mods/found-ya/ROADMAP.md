# Roadmap

## v0.1 — MVP
- Client commands: `/track lock`, `/track clear`, `/track status`
- Single target lock
- Crisp outline renderer for the locked target (vanilla outline buffer /
  post-shader pipeline, not additive bloom)

## v0.5 — Beta
- HUD direction arrows (off-screen target indicator)
- Through-wall silhouette rendering
- Config screen

## v1.0 — Release
- Beacon mode (vertical tracking pillar)
- Search mode (area scan / reveal)
- Multiple tracking profiles
- Shader pipeline for advanced visual effects

## Future (see FUTURE_FEATURES.md)
- Team systems
- Boss tracking integration
- Network synchronization across players

## Planning Pack v2 (headline-level, forward reference)

Source: `TrackerVision_Production_Design_Package_v2/` (20 docs, one
README). Still headline/bullet-level rather than implementation detail —
same status as v1 — but confirms direction and adds scope not yet captured
here. Concrete additions worth carrying forward:

- **Tracking modes** (`05_TRACKING_ENGINE.md`): Nearest / Locked / Group /
  Filtered — v0.1 implements Locked only; the others are v0.5+/v1.0 scope.
- **Performance budget** (`11_PERFORMANCE_BUDGET.md`): target <3 FPS
  average impact while tracking 100+ entities; tracking updates per-tick,
  render updates per-frame. Informs RISK_REGISTER entry on scan cost.
- **Compatibility matrix** (`10_COMPATIBILITY_MATRIX.md`): test against
  Embeddium, Iris, Sodium derivatives, JourneyMap, Xaero — beyond the
  Iris-only check already in RISK_REGISTER.md.
- **Coding standards** (`13_CODING_STANDARDS.md`): English-only, Javadocs
  required, no magic numbers, unit-test where possible.
- **Release plan** (`16_RELEASE_PLAN.md`): Alpha = commands + outlines,
  Beta = tracking profiles, RC = optimization, 1.0 = full feature release
  — consistent with this ROADMAP's v0.1/v0.5/v1.0 split.
- **Backlog** (`17_BACKLOG.md`): adds minimap integration, sound
  indicators, AI threat scoring, search vision mode to FUTURE_FEATURES.md.

Will be fleshed out into full implementation docs as TrackerVision
approaches the milestones each section describes.
