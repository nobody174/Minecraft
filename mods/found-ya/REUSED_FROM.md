# Reused From

Patterns adapted from sibling portfolio mods (not copy-pasted verbatim —
re-derived to match TrackerVision's modern visual direction).

## From `boss-radar`
- Entity scanning structure (`BossRadarItem.scanForBosses` →
  `tracking/` detection logic): distance-filtered `level.getEntitiesOfClass()`
  scan pattern. Saves re-deriving the scan-throttling approach.
- HUD distance-color-coding concept (re-themed, not reused visually).
- **Explicitly not reused:** boss-radar's `CustomPacketPayload` /
  `RegisterPayloadHandlersEvent` networking pattern. TrackerVision's
  tracking state is purely client-side cosmetic and never needs to leave
  the client, so there's no networking layer at all — an earlier draft of
  this file claimed the pattern was reused; it never was.

## From `armor-aura`
- Client command registration shape (`AuraClientCommands` →
  `client/command/TrackCommand`): `RegisterClientCommandsEvent` +
  nested `.then()` brigadier tree.
- `EntityRenderersEvent.AddLayers` registration pattern for attaching custom
  render layers.
- **Explicitly not reused:** the additive-bloom glow render style. It reads
  as soft/dated for TrackerVision's goals; v0.1 uses the vanilla outline
  buffer / post-shader pipeline instead for a crisp, modern rim outline.

## Why this matters
Each reuse saves re-deriving working API patterns from scratch and avoids
known pitfalls (e.g. client vs server command source signatures). Visual
style is deliberately not inherited — see ARCHITECTURE.md.
