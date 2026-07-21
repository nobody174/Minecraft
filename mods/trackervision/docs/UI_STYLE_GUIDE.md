# UI Style Guide

TrackerVision's visual identity lives mostly in this layer (see
`RENDERING_RESEARCH.md` for why). Goal: read as a modern tactical-overlay
HUD — thin geometric brackets, depth-based hierarchy, restrained motion —
not the chunky/static look of 2014-era Forge HUD mods.

## Typography

Vanilla's bitmap font is the wrong era. Plan:
- **v0.1:** vanilla font scaled down (`PoseStack.scale(0.5f)` +
  `GuiGraphics.drawString(..., shadow=false)`) with a hand-drawn 1px
  outline (4 offset draws in a dim color) instead of vanilla's drop-shadow
  — reads cleaner over a busy world background.
- **v0.5+:** ship a condensed sans TTF (Rajdhani / Chakra Petch / Exo 2,
  all OFL-licensed) as a resource-pack font provider
  (`assets/trackervision/font/hud.json`). This is the single highest-impact
  typography upgrade; deferred only because it adds an asset-licensing
  step that shouldn't block v0.1 functionality.

## Color Palette (ARGB hex)

| Role | Color |
|---|---|
| Tracking (cyan accent) | `#FF35E0E0` |
| Hostile-locked (red) | `#FFFF3B3B` |
| Out-of-range (amber) | `#FFFFB02E` |
| Neutral text | `#FFE8EEF2` |
| Text outline | `#C0000000` |
| Background scrim (text only, sparingly) | `#40000000` max |

No filled boxes. No rainbow palette. Accent color encodes target state, not
decoration.

## Locked-Target Reticle

- Corner brackets only — never a full square (full boxes read as
  "2014 HUD mod").
- Base size: 24×24px box, scaled by distance falloff (see below).
- Each corner: two ~7px strokes at ~1.5px stroke width, forming an L.
- Color = current target-state accent.
- Gap between bracket and target center breathes ±2px (v0.5+ motion).

## Off-Screen Direction Indicator

- Equilateral caret, ~10px, filled accent color with 1px dark outline.
- Clamped to an inset rectangle ~24px from screen edge.
- Rotation = bearing angle `atan2(dy, dx)` from screen center to the
  (possibly off-screen) projected target position.
- v1.0: faint 16px radial glow if profiling allows; skip for v0.1.

## Distance Readout

- Centered ~14px below the bracket.
- Format: `"123m"`.
- Color follows the same state rule as the bracket (neutral / hostile-red /
  amber-out-of-range).

## Beacon Pillar Marker (v1.0 scope)

- Thin 2px vertical accent line from the target's screen-space base,
  extending ~40px upward, alpha-gradient fading top-to-bottom, capped with
  a small chevron.

## Distance-Aware Hierarchy

Normalize distance `t = clamp((dist - near) / (far - near), 0, 1)`, then
**smoothstep** it (`s = t*t*(3-2*t)`) before applying — never lerp on raw
linear `t`, it reads mechanical:
- `alpha = lerp(1.0, 0.25, s)`, floor at ~0.2 so far targets fade, not
  vanish.
- `scale = lerp(1.0, 0.7, s)`, scaled around the bracket's own center.

## Motion (GC-safe — no per-frame allocation)

- Continuous effects (breathing, pulses): drive off `System.nanoTime()`.
- Game-state-synced effects: use the render event's partial-tick.
- Reuse PoseStack/locals; never `new Vec3`/`new Matrix4f` per frame.
- **Lock-acquired pulse** (v0.5): damped-spring scale,
  `pulse = 1 + 0.25 * exp(-age*8) * sin(age*40)` where
  `age = (now - lockTimeNanos) / 1e9`.
- **Fade in/out** (v0.1): per-target alpha lerped toward 0/1 by
  `min(1, dt * speed)` each frame — frame-rate independent.
- **Breathing reticle** (v0.5): `0.85 + 0.15 * sin(now * 2.5)` modulating
  bracket alpha or gap.

## Phasing

- **v0.1:** smoothstep distance fade/scale, fade-in/out alpha lerp, vanilla
  font with hand-drawn outline. These three deliver most of the "modern"
  read on their own.
- **v0.5:** lock-acquired damped pulse, breathing reticle, TTF font.
- **v1.0:** off-screen-arrow radial glow, beacon vertex-gradient, animated
  color transitions on state change.
