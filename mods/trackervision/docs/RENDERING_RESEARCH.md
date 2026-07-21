# Rendering Research — Outline & Silhouette Strategy

Findings from deep research into NeoForge 1.21.1 rendering options, used to
decide TrackerVision's visual direction (see `UI_STYLE_GUIDE.md` for the HUD
side of this decision).

## Vanilla outline buffer (the mechanism behind spectator glowing)

`Entity.setGlowingTag()` routes the entity through `OutlineBufferSource`
using `RenderType.outline(texture)`, tinted per-entity via
`OutlineBufferSource.setColor(r,g,b,a)`. The `entity_outline.json`
post-chain then composites it: it is **not** edge detection — it's a
separable blur (horizontal+vertical) of the silhouette, alpha-composited
over the un-blurred interior so only a thin ring at the edge survives. The
"crisp 1px line" is an emergent property of a small blur radius, not true
dilation.

**Customization ceiling:**
- Per-entity RGBA color: fully supported, free. ✅
- Thickness: not exposed by default, but a mod can ship its own copy of the
  post-chain JSON with a larger blur radius — trades crispness for
  thickness (becomes blurrier, not "thick and crisp").
- Crisp + thick + anti-aliased simultaneously: **not achievable** with the
  vanilla blur-based chain. Requires a real dilation/jump-flood shader
  (custom GLSL `PostChain`).

**Decision:** v0.1 uses the vanilla outline buffer for the crisp 1px
silhouette (it's free and already looks clean) and puts the actual visual
identity into a screen-space HUD overlay layer instead (brackets, distance
text, off-screen carets — see below). A custom dilation post-shader is
deferred to the v1.0 "shader pipeline" milestone, only if the team
decides variable-thickness colored outlines are worth the GLSL investment.

## Rejected: fresnel/rim-light via vertex color

A true fresnel rim needs per-fragment `dot(viewDir, normal)`, which is
fragment-shader territory — not achievable by manipulating vertex colors
in a custom `RenderLayer`. Faking it via per-vertex dot-product color looks
worse on Minecraft's low-poly entity models (too few normals to
interpolate smoothly) than the outline buffer. **Not pursued.**

## Custom core shaders (confirmed real API, deferred)

NeoForge 1.21.1 supports custom core shaders via `RegisterShadersEvent`
(mod bus) registering a `ShaderInstance` pointed at
`assets/<modid>/shaders/core/*.json` + `.vsh`/`.fsh` GLSL files. Custom
post-process chains live under `assets/<modid>/shaders/post/`. This is the
real path to a variable-thickness, anti-aliased, per-entity-colored outline
(via jump-flood/dilation) — high effort, version-brittle, explicitly
**deferred to v1.0** per ROADMAP.md.

## Why the HUD layer is the real visual identity

Per research: the "designed in 2026" read comes overwhelmingly from
screen-space HUD furniture (corner brackets, distance-based color/alpha
falloff, off-screen direction carets) anchored to projected world
positions — not from the entity mesh outline itself. This is consistent
with how modern competitive-shooter overlays (ESP-style tracking UIs)
work: thin geometric brackets + depth-based hierarchy, not full silhouette
fills. v0.1 implementation priority reflects this: HUD overlay first,
mesh outline is supporting cast.

## Screen-space projection (cheap, per-frame)

```
Vec3 rel = worldPos.subtract(camera.getPosition());
// transform rel through combined projection * modelview (JOML Matrix4f)
// -> clip space; if clip.w <= 0 the point is behind the camera (flip
//    x/y before dividing, so off-screen bearing still points correctly)
screenX = (ndc.x * 0.5 + 0.5) * width;
screenY = (1 - (ndc.y * 0.5 + 0.5)) * height;
```
Cheap enough to do per-target per-frame in the HUD render event for the
small number of simultaneously tracked targets this mod supports.
