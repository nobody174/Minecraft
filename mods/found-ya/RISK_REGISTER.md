# Risk Register

## Risk: Outline rendering via vanilla post-shader pipeline may conflict with Iris/shader packs
- Impact: Medium (visual breakage for shader-pack users)
- Likelihood: Medium
- Mitigation: Compatibility-test against Iris early (see ROADMAP v0.5
  compatibility research); provide additive-glow fallback render path if
  the outline buffer proves incompatible. Full compatibility target list
  (per `TrackerVision_Production_Design_Package_v2/10_COMPATIBILITY_MATRIX.md`):
  Embeddium, Iris, Sodium derivatives, JourneyMap, Xaero.
- Note: the v1.0 "shader pipeline" milestone was deliberately scoped down
  to `RimBoostEffect` — a lightweight custom core-shader brightness boost
  via a manual full-screen quad, not the full jump-flood/dilation outline
  rewrite this risk was originally written against. The full rewrite (and
  its Iris-conflict exposure) remains undone; this risk should stay open
  if that full version is ever picked up, but `RimBoostEffect`'s smaller
  footprint (one extra offscreen render target, toggleable per-profile via
  `rimBoostEnabled`) is lower-risk and not yet compatibility-tested
  against Iris either.
- Status: Monitoring; reduced scope lowers exposure but doesn't close it.

## Risk: Entity scan cost may scale poorly with many tracked/nearby entities
- Impact: Medium (frame drops in crowded areas)
- Likelihood: Low at v0.5 scope (`NearestTargetScanner` scans once every 10
  client ticks, single target selected; not yet stress-tested with 100+
  nearby entities)
- Mitigation: Scan throttling implemented (`NearestTargetScanner`, every
  10 ticks). `SearchModeScanner` (v1.0) introduced multi-entity scanning
  with the same 10-tick throttle and an AABB pre-filter, but has not yet
  been profiled against the 100+-entity target below. Target performance
  budget (per `..._v2/11_PERFORMANCE_BUDGET.md`): <3 FPS average impact
  while tracking 100+ entities, tracking updates per-tick, render updates
  per-frame — not yet profiled against this target.
- Status: Monitoring (needs profiling pass before v1.0 RC)
