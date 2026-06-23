//
// Buddy Beast
//
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/Minecraft/tree/main/mods/buddy-beast
// Patreon: https://www.patreon.com/c/Nobody174
// License: All rights reserved © 2025 nobody174
// "It's never too late to give up!"
//

# Todo

**v0.1.0 is released.** Full history of what was built and fixed is in
[CHANGELOG.md](CHANGELOG.md) and [RELEASE_NOTES.md](RELEASE_NOTES.md).

## v0.2.0 Planned Features

- [ ] **Armor for buddies** — equippable armor item, defense stat boost, rendered on model
- [ ] **Saddle/riding for large buddy variants** — reuse vanilla saddle item/interaction for buddies big enough to ride (e.g. horse-based buddy)
- [ ] **Custom Blockbench model** — replace the two-headed cow placeholder once there's evidence the mod is being used (see TECHNICAL_DEBT.md)

See [ROADMAP.md](ROADMAP.md) for the full v0.2.0/v0.3.0 plan and
[FUTURE_FEATURES.md](FUTURE_FEATURES.md) for further-out ideas.

## Known Risks

| Risk | Impact | Likelihood | Status |
|------|--------|------------|--------|
| AI tick cost scales poorly | High | High | MONITORING (only tested to 30 buddies) |
| Multiplayer desync issues | High | Medium | MONITORING (works correctly so far) |
| Data migration breaks saves | High | Low | PLANNING |

See [RISK_REGISTER.md](RISK_REGISTER.md) for details.

## Technical Debt

See [TECHNICAL_DEBT.md](TECHNICAL_DEBT.md).

# Built with assistance from Claude Code by Anthropic.
