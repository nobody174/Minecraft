# Future Features

Ideas outside current roadmap scope, organized by target version.

## v1.0.1 (patch)
- **Entity-type filtering** for auto-select and search modes:
  - `/track type friendly` — auto-select only passive mobs (sheep, cow, chicken, etc.)
  - `/track type hostile` — auto-select only hostile mobs (zombie, skeleton, witch, etc.)
  - `/track type players` — auto-select only players
  - `/track type zombie|skeleton|witch|...` — auto-select specific entity types
  - Affects both `NEAREST` mode and `SEARCH` mode filtering

## Post-v1.0
- Team systems (shared tracking state across party members)
- Boss tracking integration (cross-mod hook with boss-radar)
- Network synchronization of tracked targets across players
- Custom shader pipeline (Iris-compatible) for advanced rim/fresnel effects
- Minimap integration (JourneyMap/Xaero waypoint sync for tracked targets)
- Sound indicators (audio cue on lock/proximity)
- AI threat scoring (rank tracked targets by computed threat level)

Source: `TrackerVision_Production_Design_Package_v2/17_BACKLOG.md`.
