# TeleportShop Project Overview

**Status:** 🟡 MVP Development (Week 1)  
**Target Release:** v1.0.0  
**Platform:** Paper/Bukkit 1.20.1+  
**Market:** Survival servers, SMPs, community servers

## What is TeleportShop?

A premium Bukkit plugin offering **convenient teleportation** and **player-run shops** for Minecraft servers. Monetizable features for server owners and quick revenue opportunities.

## Market Positioning

- **Primary Market**: Survival servers, SMPs
- **Secondary**: Factions, Creative servers
- **Pricing Model**: Free core + premium add-ons (NPC traders, cross-server, auction house)
- **Competing Products**: EssentialsX (free but bloated), TeleportRequest, PlayerShops (paid but limited)

**Advantage**: All-in-one solution with growth path to premium features.

## Feature Breakdown (MVP)

### Must-Have (Week 1)
✅ `/tpa` system with request/accept/deny  
✅ `/home` & `/sethome` (single home for MVP)  
✅ `/warp` & `/setwarp` (admin-only)  
✅ `/back` command (return to last location)  
✅ `/spawn` & `/setspawn`  
✅ Teleport delay (3s) with movement cancel  
✅ Permission system  
✅ Configuration file  
✅ Vault economy support (ready, not used yet)

### Good-to-Have (Phase 2)
📋 `/home` with multiple homes  
📋 Player shop GUI (inventory interface)  
📋 Item pricing (buy/sell)  
📋 Transaction logging  
📋 Admin commands for shop management  

### Nice-to-Have (Phase 3+)
💡 NPC traders (spawn merchant NPCs with custom trades)  
💡 Auction house (global listings, bid system)  
💡 Cross-server persistence (Bungee/Velocity)  
💡 Admin web dashboard  
💡 Premium modules (paid add-on)

## Revenue Strategy

**Free Tier**:
- Homes, Warps, TPA, Back, Spawn
- Basic permission nodes
- Server owners can limit features by group

**Premium Add-ons**:
- NPC Traders module (+$5/month)
- Cross-server sync (+$3/month)
- Auction house system (+$5/month)
- Web dashboard (+$5/month)
- Custom shop themes ($2-5)

**Service**:
- Setup assistance ($20 one-time)
- Customization services ($50+)

**Target**: $100-500/month per plugin if 10-50 servers adopt.

## Development Schedule

| Phase | Scope | Effort | Timeline |
|-------|-------|--------|----------|
| **MVP** | Home, Warp, TPA, Back, Spawn | ~40 hours | Week 1 |
| **Phase 2** | Multi-home, Shops GUI, Economy | ~60 hours | Week 2-3 |
| **Phase 3** | NPC, Auction, Cross-server | ~80 hours | Week 4-6 |
| **Polish** | Testing, docs, release | ~20 hours | Week 6-7 |
| **Launch** | Release on SpigotMC, Modrinth | - | Week 8 |

## Technical Stack

| Component | Tech | Notes |
|-----------|------|-------|
| **Build Tool** | Maven | Handles dependencies & packaging |
| **Language** | Java 17+ | Paper API requirements |
| **Server API** | Paper 1.20.1 | Most performant Bukkit fork |
| **Storage (MVP)** | YAML | Simple file-based persistence |
| **Storage (Future)** | MySQL/SQLite | For cross-server support |
| **Economy** | Vault API | Compatible with all economy plugins |
| **Permissions** | LuckPerms, PermissionsEx, etc. | Hooks into any permission plugin |

## Code Structure

```
src/main/java/com/minecraft/teleportshop/
├── TeleportShop.java                    # Entry point
├── commands/
│   ├── teleport/                        # Home, Warp, TPA, Back, Spawn
│   └── shop/                            # Shop commands
├── handlers/
│   ├── TeleportHandler.java             # Core logic
│   ├── ShopHandler.java                 # Shop logic
│   └── ConfigHandler.java               # Settings
├── listeners/
│   └── PlayerListener.java              # Event hooks
├── storage/                             # Data persistence (TODO)
├── gui/                                 # Inventory GUIs (TODO)
└── utils/                               # Helper functions
```

## Current Status (Week 1)

### ✅ Completed
- Project scaffolding and Maven setup
- Plugin class (TeleportShop.java)
- Configuration handler with defaults
- Command classes (15 total)
- Teleport handler (homes, warps, TPA, spawn)
- Shop handler (structure)
- Permission nodes (40+ granular perms)
- plugin.yml with full command/perm metadata
- Default config.yml with all settings

### 🔄 In Progress
- YAML data storage implementation
- Teleport delay & movement cancellation logic
- Safe location search
- Event listeners (player join/quit/teleport)

### ⏳ TODO
- GUI menus (inventory interface)
- Vault integration (economy)
- Unit tests
- Manual testing with players
- Documentation (API, setup guide)
- Performance optimization

## Testing Plan

### Unit Tests
- ConfigHandler defaults loading
- TeleportHandler home/warp operations
- Permission checks
- Message formatting

### Integration Tests
- Manual server tests (Paper 1.20.1)
- Test with 5+ players
- Verify permissions (LuckPerms)
- Test Vault economy integration
- Cross-world teleports

### Manual Scenarios
1. Player sets home, teleports, checks persistence on reload
2. Admin creates warp, players teleport to it
3. Player requests TPA, target accepts, requester teleports
4. Movement during teleport cancels it
5. `/back` returns to previous location
6. Permission groups restrict features appropriately

## Monetization Notes

**SpigotMC Listing**: Premium resource ($5-10)  
**Modrinth**: Free with optional tip  
**Direct**: Offer premium modules for $3-10/month  

**Upsell Path**:
1. Player uses free plugin on server
2. Server owner wants NPC traders → buy premium
3. Multi-server network → buy cross-server module
4. Large server → buy web dashboard

## Success Criteria (MVP Release)

✅ All 14 commands functional  
✅ Homes/Warps persist on server restart  
✅ Permissions work with LuckPerms  
✅ Zero crash bugs (safe teleports)  
✅ <50ms delay on /home (performance)  
✅ Full documentation & setup guide  
✅ 100+ downloads in first month  
✅ 4.5+ star rating on SpigotMC  

## Next Steps

1. **Implement YAML storage** - Save/load homes, warps, spawn
2. **Teleport delay system** - BukkitScheduler task
3. **Movement cancellation** - PlayerMoveEvent listener
4. **Test on server** - Deploy to Paper 1.20.1 test instance
5. **Gather feedback** - Community testing
6. **Fix bugs & optimize** - Performance review
7. **Documentation** - API docs, setup guide, commands list

## Links & Resources

- **Repository**: `src/java/plugins/teleportshop/`
- **Config**: `src/main/resources/config.yml`
- **Plugin.yml**: `src/main/resources/plugin.yml`
- **Docs**: `docs/README.md` and `docs/DEVELOPMENT.md`
- **Build**: `mvn clean package` (outputs `target/teleportshop-1.0.0.jar`)

## Author & Contact

**Developer**: nobody174  
**Email**: nobodylearn174@gmail.com  
**License**: All rights reserved © 2026 nobody174  

---

**Last Updated**: June 1, 2026  
**Version**: 1.0.0-SNAPSHOT  
**Status**: 🟡 In Development

Built with assistance from [Claude Code](https://claude.com/claude-code) by Anthropic
