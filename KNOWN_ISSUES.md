# Known Issues

None currently open.

## Fixed

- HUD overlay showed stale pet stats after releasing a pet, since the push-based `PetSyncPacket` never fired on release. Fixed by having `PetHudOverlay` read `PetData` directly from the held item's data component each frame instead of caching server-pushed state.
