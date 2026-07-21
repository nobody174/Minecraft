# Changelog

## [Unreleased]

## [1.0.0-RC2] - 2026-06-26

Bug-fix candidate. Extended user testing phase. All v1.0 features verified in-client
(10/10 critical tests passed). Performance profiling passed: 60 FPS stable with tracking enabled,
no memory leaks (300-500 MB), 20+ simultaneous mobs with no FPS impact. Profile switching
preserves locked target correctly. Rim-boost shader working (subtle bloom effect, verified
in darkness). Ready for RC3 validation phase.

### Added
- `TrackerHudOverlay` beacon mode: beyond
  `TrackerVisionConfig.getBeaconDistance()`, the bracket reticle is
  replaced by a sky-to-target vertical accent beam (300px tall, fading
  top-to-bottom) capped with a chevron at the target's feet. Makes distant
  targets visible from anywhere on-screen. Toggleable via
  `/track config beaconEnabled|beaconDistance` and the config screen.
- Search Mode (`SearchModeManager`/`SearchModeScanner`,
  `/track search <true|false>`): a persistent, independent toggle that
  reveals every valid living entity within `farDistance` of the player
  with a light additive rim (neutral color, lower alpha than the locked
  target's, no through-wall silhouette pass) — distinct from
  `TrackingMode.NEAREST`, which auto-selects a single best target. Search
  Mode doesn't touch the locked target; both can be active at once.
- Multiple tracking profiles (`TrackerVisionProfile`, reworked
  `TrackerVisionConfig` as a profile-aware facade): named, switchable
  bundles of near/far distance, bracket size, accent color, and beacon
  settings, so switching activities doesn't require re-tuning every
  setting by hand. Seeded with "Default", "PvP" (tighter, short-range,
  no beacon), and "Exploration" (longer-range, beacon on) profiles.
  `/track profile list|use|create|delete`, plus a cycle button in the
  config screen. `TrackerVisionConfigFile` now persists every profile as
  an array and reads old pre-profiles config files into the seeded
  "Default" profile so upgrading doesn't lose existing settings.
- `RimBoostEffect`: a real custom core-shader post-process pass
  (`assets/trackervision/shaders/core/rim_boost.*`, registered via
  `RegisterShadersEvent`) that brightens only already-bright pixels via a
  `smoothstep` luminance threshold, giving the locked target's additive
  rim a soft bloom-like punch. Runs as a manual full-screen quad against a
  private offscreen target on `RenderLevelStageEvent.AFTER_LEVEL`, only on
  frames where the rim was actually drawn — deliberately scoped lighter
  than the full jump-flood/dilation outline rewrite docs/RENDERING_RESEARCH.md
  originally researched for this milestone, since that was explicitly
  conditional and carries a flagged Iris-compatibility risk. Toggleable
  per-profile via `/track config rimBoostEnabled` and the config screen.

## [0.5.0-RC1] - Unreleased

### Added
- Project scaffold: Gradle build, NeoForge mod metadata, package structure.
- `/track lock`, `/track clear`, `/track status` client commands.
- `TrackedTargetManager` single-target lock state.
- `TrackedTargetGlowRenderer`: thin additive rim accent on the locked
  target, hooked off `RenderLivingEvent.Post` (works for any `LivingEntity`
  type without per-renderer registration).
- `TrackerHudOverlay`: corner-bracket reticle, off-screen direction caret,
  and distance readout anchored to the locked target's projected screen
  position, with smoothstep distance-based alpha/scale falloff.
- `ScreenProjection`: manual world-to-screen projection helper for HUD
  rendering (reconstructs the camera view/projection matrix since
  `RenderGuiEvent.Post` runs after the 3D pass resets `RenderSystem`'s
  matrices for 2D HUD drawing).
- `docs/RENDERING_RESEARCH.md` and `docs/UI_STYLE_GUIDE.md`: researched
  visual-design decisions behind the above.
- `TrackerVisionConfig`/`TrackerVisionConfigFile`: JSON-backed client
  config (tracking enabled toggle, near/far distance thresholds, bracket
  size, accent color) at `config/trackervision/trackervision-config.json`.
- `/track config enabled|nearDistance|farDistance|show` commands for
  runtime config adjustment, persisted immediately on change.
- `TrackingMode` (`LOCKED`/`NEAREST`) and `/track mode locked|nearest`.
  `NearestTargetScanner` auto-selects the closest living entity within
  the configured far-distance every 10 client ticks (client-side only,
  unlike boss-radar's server-side item-tick scan).
- `TargetState` is now computed live from the tracked entity + distance
  (`TrackedTargetManager.computeState`) instead of always reporting
  `TRACKING`: hostile mobs (`instanceof Enemy`) render red, targets
  beyond the configured far-distance render amber, otherwise cyan.
- Reticle motion: a damped-spring scale pulse on target acquisition
  (settles within ~1.5s) and a continuous subtle breathing scale, both
  driven off `System.nanoTime()` per docs/UI_STYLE_GUIDE.md.
- Through-wall silhouette pass in `TrackedTargetGlowRenderer`: when a
  block occludes the line from the player's eyes to the locked target
  (`Level.clip`), a second `RenderType` (depth-test disabled, flat
  `POSITION_COLOR` format, lower alpha than the normal rim) redraws the
  target's model so the rim accent stays visible through walls instead
  of disappearing entirely — the same `NO_DEPTH_TEST` technique vanilla's
  own spectator-glow outline uses.
- `TrackerVisionConfigScreen`: in-game settings screen (mod list "Config"
  button, via `IConfigScreenFactory`) with a tracking-enabled checkbox and
  near/far distance + bracket size sliders bound directly to
  `TrackerVisionConfig`, saved on close. Built from vanilla GUI widgets
  rather than NeoForge's spec-driven `ConfigurationScreen`, since this
  mod's config is a hand-rolled JSON file, not a `ModConfigSpec`. The
  `/track config` commands remain available alongside it.
