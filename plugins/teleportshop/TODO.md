//
// TeleportShop Plugin - TODO List & Task Tracking
// Author: nobody174 (nobodylearn174@gmail.com)
// Repo: https://github.com/nobody174/minecraft-addons
// License: All rights reserved © 2026 nobody174
//

# TeleportShop TODO List

**Current Phase**: 🟡 Phase 1 - MVP (Core Teleportation)  
**Progress**: Scaffolding complete, implementation starting  
**Last Updated**: June 1, 2026

---

## Phase 1: MVP - Core Teleportation System

### Core Features Implementation

#### Teleportation System
- [ ] **YAML Data Persistence**
  - [ ] Create HomeStorage class for YAML serialization
  - [ ] Create WarpStorage class for YAML serialization
  - [ ] Implement load/save on enable/disable
  - [ ] Handle file I/O safely (catch exceptions)
  - Tests needed: ConfigHandler defaults, file creation

- [ ] **Teleport Delay & Movement Cancellation**
  - [ ] Implement BukkitScheduler for delay (3 seconds default)
  - [ ] Create PlayerMoveEvent listener
  - [ ] Cancel teleport if player moves during delay
  - [ ] Add clickable chat cancel button
  - Tests needed: Teleport task scheduling, event cancellation

- [ ] **Safe Location Search**
  - [ ] Implement safe block detection (not in lava/fire/blocks)
  - [ ] Max 10 attempts to find safe spot
  - [ ] Handle edge cases (void, bedrock, etc.)
  - [ ] Add to config (enable/disable)
  - Tests needed: Location validation, world height limits

- [ ] **Last Location Tracking (/back command)**
  - [ ] Track location before every teleport
  - [ ] Store in TeleportHandler
  - [ ] Implement /back command
  - [ ] Test with multiple teleports in sequence
  - Tests needed: Location tracking on PlayerTeleportEvent

- [ ] **Cross-World Teleport Support**
  - [ ] Allow homes/warps in different worlds
  - [ ] Handle player load when switching worlds
  - [ ] Test with 3+ different worlds
  - [ ] Verify chunk loading on arrival

#### Home System Commands
- [ ] **HomeCommand (/home [name])**
  - [ ] Parse home name from args (default: "home")
  - [ ] Validate home exists
  - [ ] Apply teleport delay
  - [ ] Check movement during delay
  - [ ] Execute safe teleport

- [ ] **SetHomeCommand (/sethome [name])**
  - [ ] Parse home name (limit 1 for MVP, max 3 in config)
  - [ ] Check max homes limit
  - [ ] Save location to storage
  - [ ] Send confirmation message

- [ ] **DelHomeCommand (/delhome <name>)**
  - [ ] Validate home exists
  - [ ] Delete from storage
  - [ ] Send confirmation

- [ ] **ListHomesCommand (/listhomes)**
  - [ ] Display all player homes
  - [ ] Show home coordinates
  - [ ] Format nicely in chat

#### Warp System Commands
- [ ] **WarpCommand (/warp <name>)**
  - [ ] Get warp from storage
  - [ ] Check permission
  - [ ] Apply teleport delay
  - [ ] Execute safe teleport

- [ ] **SetWarpCommand (/setwarp <name>) [ADMIN]**
  - [ ] Check admin permission
  - [ ] Save location
  - [ ] Confirmation message

- [ ] **DelWarpCommand (/delwarp <name>) [ADMIN]**
  - [ ] Check admin permission
  - [ ] Delete from storage

- [ ] **WarpListCommand (/warplist)**
  - [ ] Display all available warps
  - [ ] Format with clickable links

#### Spawn System Commands
- [ ] **SpawnCommand (/spawn)**
  - [ ] Get spawn location from handler
  - [ ] Apply teleport delay
  - [ ] Execute teleport

- [ ] **SetSpawnCommand (/setspawn) [ADMIN]**
  - [ ] Check admin permission
  - [ ] Set spawn location
  - [ ] Persist to storage

#### Utility Commands
- [ ] **BackCommand (/back)**
  - [ ] Get last location from handler
  - [ ] Execute teleport (no delay for MVP)

#### TPA (Teleport Request) System
- [ ] **TPACommand (/tpa <player>)**
  - [ ] Validate target player online
  - [ ] Check cooldown (5 seconds default)
  - [ ] Create TPA request
  - [ ] Set expiration (60 seconds)
  - [ ] Send messages to both players

- [ ] **TPAHereCommand (/tpahere <player>)**
  - [ ] Similar to /tpa but reversed
  - [ ] Validation and messaging

- [ ] **TPAcceptCommand (/tpaccept [player])**
  - [ ] Find pending TPA request
  - [ ] Check not expired
  - [ ] Execute teleport
  - [ ] Clean up request

- [ ] **TPDenyCommand (/tpdeny [player])**
  - [ ] Find pending TPA request
  - [ ] Reject request
  - [ ] Notify requester
  - [ ] Clean up request

- [ ] **TPCancelCommand (/tpcancel [player])**
  - [ ] Cancel outgoing request
  - [ ] Notify target
  - [ ] Clean up

### Configuration & Customization

- [ ] **ConfigHandler Expansion**
  - [ ] Load all settings from config.yml
  - [ ] Provide getters for all values
  - [ ] Handle missing keys gracefully
  - [ ] Cache config values

- [ ] **Message Customization**
  - [ ] Load all messages from config.yml
  - [ ] Support color codes (&a, &c, etc.)
  - [ ] Support placeholders ({player}, {delay}, etc.)

### Event Listeners

- [ ] **PlayerListener**
  - [ ] Implement PlayerJoinEvent (load player data)
  - [ ] Implement PlayerQuitEvent (save player data, cleanup)
  - [ ] Implement PlayerTeleportEvent (track last location)
  - [ ] Implement PlayerMoveEvent (cancel teleport on movement)

### Storage & Persistence

- [ ] **YAML-Based Storage (Phase 1)**
  - [ ] Create YAMLStorage class
  - [ ] Implement save() method
  - [ ] Implement load() method
  - [ ] Handle file creation and directories
  - [ ] Error handling for I/O issues

### Testing (Phase 1)

- [ ] **Unit Tests (Target: 80%+ coverage)**
  - [ ] ConfigHandler: Test default loading
  - [ ] TeleportHandler: Test home operations
  - [ ] TeleportHandler: Test warp operations
  - [ ] TeleportHandler: Test TPA request lifecycle

- [ ] **Integration Tests**
  - [ ] Manual: Set home, restart, teleport home
  - [ ] Manual: Create warp, multiple players warp
  - [ ] Manual: TPA request timeout
  - [ ] Manual: Teleport delay cancellation on movement
  - [ ] Manual: Safe location search on dangerous terrain
  - [ ] Manual: Cross-world homes and warps

- [ ] **Manual Testing Checklist**
  - [ ] Start Paper 1.20.1 server
  - [ ] Load plugin, check no errors
  - [ ] Test all 15 MVP commands
  - [ ] Verify permission restrictions
  - [ ] Test with LuckPerms integration
  - [ ] Test data persistence (restart server)
  - [ ] Test with 5+ concurrent players

### Documentation (Phase 1)

- [ ] **User Documentation**
  - [ ] Commands guide (README.md)
  - [ ] FAQ for common issues

- [ ] **Developer Documentation**
  - [ ] API documentation
  - [ ] Architecture explanation

- [ ] **Code Documentation**
  - [ ] Add JavaDoc to public methods
  - [ ] Add comments for complex logic

---

## Phase 2: Player Shops

### Shop System Implementation
- [ ] Shop creation/deletion
- [ ] Shop item management
- [ ] Buy/sell transactions
- [ ] Stock tracking

### GUI Implementation
- [ ] Create InventoryGUI class
- [ ] Implement shop listing GUI
- [ ] Implement buy/sell interface

### Economy Integration
- [ ] Implement Vault API hooks
- [ ] Test with 3+ economy plugins
- [ ] Transaction logging

---

## Phase 3: Advanced Features

- [ ] NPC traders spawning and management
- [ ] Cross-server support (Redis)
- [ ] Auction house system
- [ ] Admin dashboard (Node.js)

---

## Phase 4: Polish & Release

- [ ] Performance optimization
- [ ] Security audit
- [ ] Complete documentation
- [ ] Release preparation

---

## Priority & Effort Estimates

| Task | Priority | Est. Hours | Status |
|------|----------|-----------|--------|
| YAML Storage | 🔴 CRITICAL | 8 hrs | ⏳ Queued |
| Teleport Delay | 🔴 CRITICAL | 6 hrs | ⏳ Queued |
| Command Logic | 🟠 HIGH | 12 hrs | ⏳ Queued |
| Event Listeners | 🟠 HIGH | 4 hrs | ⏳ Queued |
| Unit Testing | 🟠 HIGH | 6 hrs | ⏳ Queued |
| Integration Testing | 🟠 HIGH | 8 hrs | ⏳ Queued |
| **Phase 1 Total** | - | **~48 hrs** | 🟡 In Progress |

---

## Notes

- Keep repository PRIVATE during development
- Commit after each completed task
- Run tests before committing
- Update this TODO as we progress
- No feature creep - stick to MVP scope
- Quality > Speed

---

**Last Updated**: June 1, 2026  
**Next Task**: YAML Storage Implementation (1-2 days)
