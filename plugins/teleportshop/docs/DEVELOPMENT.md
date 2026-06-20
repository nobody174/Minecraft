# TeleportShop Development Guide

Development roadmap and technical documentation for contributors.

## Project Structure

```
teleportshop/
├── src/main/java/com/minecraft/teleportshop/
│   ├── TeleportShop.java              # Main plugin class
│   ├── commands/
│   │   ├── teleport/                  # Teleportation commands
│   │   └── shop/                      # Shop commands
│   ├── handlers/
│   │   ├── TeleportHandler.java       # Teleportation logic
│   │   ├── ShopHandler.java           # Shop system
│   │   └── ConfigHandler.java         # Configuration
│   ├── listeners/
│   │   └── PlayerListener.java        # Event listeners
│   ├── gui/                           # GUI/inventory menus
│   ├── storage/                       # Data persistence (YAML/MySQL)
│   └── utils/                         # Utility classes
├── src/test/java/                     # JUnit tests
├── src/main/resources/
│   ├── plugin.yml                     # Plugin metadata
│   └── config.yml                     # Default configuration
└── docs/                              # Documentation
```

## Development Phases

### Phase 1: MVP (Current - Week 1)
**Status**: In Progress

**Completed**:
- [x] Project structure
- [x] Maven configuration
- [x] Plugin scaffolding
- [x] Config system
- [x] Command registration

**In Progress**:
- [ ] Home system (YAML storage)
- [ ] Warp system
- [ ] TPA system
- [ ] Back & Spawn commands

**Phase 1 Scope** (MVP):
- `/tpa` system with accept/deny
- `/home` (single home), `/sethome`, `/delhome`
- `/warp` (admin), `/setwarp`, `/delwarp`
- Basic GUI listing
- Vault support
- Config for cooldowns/messages

**Testing**: Manual server testing

### Phase 2: Shop System (Week 2)
- Player shop GUI (inventory menu)
- Shop creation & deletion
- Item pricing system
- Vault integration
- Transaction logging

### Phase 3: Advanced Features (Week 3+)
- NPC traders with dialogue
- Cross-server persistence (Bungee/Velocity)
- Auction house
- Premium add-ons
- Admin dashboard (Node.js)

## Code Style Guide

### Naming Conventions
- Classes: `PascalCase` - e.g., `HomeCommand`, `TeleportHandler`
- Methods: `camelCase` - e.g., `setHome()`, `getTeleport()`
- Constants: `UPPER_SNAKE_CASE` - e.g., `MAX_HOMES`
- Variables: `camelCase` - e.g., `playerCount`, `homeName`

### Code Organization
- 1 public class per file
- Keep methods under 30 lines (refactor if larger)
- Private helper methods below public methods
- Use meaningful variable names (no `x`, `y`, `z` unless coordinates)

### JavaDoc Comments
```java
/**
 * Brief description of what method does.
 * 
 * @param paramName description of parameter
 * @return description of return value
 */
public ReturnType methodName(ParamType paramName) {
    // implementation
}
```

## Key Components

### TeleportHandler
Manages all teleportation logic:
```java
setHome(player, homeName, location)       // Store home
getHome(player, homeName)                 // Retrieve home
deleteHome(player, homeName)              // Remove home
setWarp(name, location)                   // Store warp
recordTPARequest(requester, target)       // Log TPA request
getPendingTPA(player)                     // Get incoming request
```

### ShopHandler
Manages player shops:
```java
createShop(owner, shopName)               // Create new shop
getPlayerShop(ownerUUID)                  // Retrieve shop
addShopItem(ownerUUID, item)              // Add item to shop
```

### ConfigHandler
Manages configuration:
```java
getMaxHomes()                             // Retrieve max homes setting
getHomeDelaySeconds()                     // Teleport delay
getMessage(key, replacements...)          // Get formatted message
```

## Storage Implementation

### Phase 1: YAML (File-based)
```java
// homes.yml
player-uuid:
  home-name:
    world: world
    x: 100.5
    y: 64
    z: 200.5
    yaw: 90
    pitch: 0
```

### Phase 2+: MySQL/SQLite
- Homes table: `player_uuid, home_name, world, x, y, z`
- Warps table: `name, world, x, y, z, created_by`
- Shops table: `owner_uuid, shop_name, created_at`
- Shop_items table: `shop_id, item, buy_price, sell_price, stock`

## Testing Strategy

### Unit Tests (JUnit 5)
```bash
mvn test
```

Test areas:
- [ ] ConfigHandler loading defaults
- [ ] TeleportHandler home storage/retrieval
- [ ] TPA request expiration
- [ ] ShopHandler shop creation

### Integration Tests
- Manual server testing with real players
- Test on Paper 1.20.1
- Verify permission checks work
- Test with Vault (economy)

### Manual Testing Checklist
- [ ] All commands parse correctly
- [ ] Permissions respected
- [ ] Teleport delays work
- [ ] Movement cancels teleport
- [ ] Homes persist on reload
- [ ] Warps available to all players
- [ ] TPA requests expire
- [ ] Back command tracks location

## Building & Deployment

### Build JAR
```bash
mvn clean package
# Output: target/teleportshop-1.0.0.jar
```

### Deploy to Test Server
```bash
cp target/teleportshop-1.0.0.jar ~/minecraft-server/plugins/
# Restart server
```

### Create Release
```bash
git tag v1.0.0
git push origin v1.0.0
# Create GitHub release with JAR artifact
```

## TODO Tasks

### Immediate (MVP)
- [ ] Implement YAML data loading/saving
- [ ] Teleport delay with task scheduler
- [ ] Movement listener (cancel teleport on move)
- [ ] Safe location search algorithm
- [ ] Unit tests for core logic
- [ ] Manual testing with players

### Short-term (Phase 2)
- [ ] Implement GUI menus (InventoryView)
- [ ] Shop item purchasing logic
- [ ] Vault economy integration testing
- [ ] Transaction logging

### Medium-term (Phase 3)
- [ ] MySQL migration from YAML
- [ ] NPC entity spawning
- [ ] Bungee/Velocity cross-server support
- [ ] Admin web dashboard (Node.js)

## Common Patterns

### Command Execution Pattern
```java
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player)) return true;
    Player player = (Player) sender;
    
    if (!player.hasPermission("teleportshop.permission")) {
        player.sendMessage("§cNo permission!");
        return true;
    }
    
    // Validate args
    if (args.length < required) {
        player.sendMessage("§cUsage: /command ...");
        return true;
    }
    
    // Execute logic
    return true;
}
```

### Async Task Pattern
```java
Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
    // Async work (loading from database)
    Bukkit.getScheduler().runTask(plugin, () -> {
        // Sync work (update player/inventory)
    });
});
```

## Resources

- [Bukkit Event API](https://hub.spigotmc.org/javadocs/bukkit/)
- [Paper API](https://jd.papermc.io/)
- [Vault API](https://github.com/MilkBowl/VaultAPI)
- [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI)

## Questions?

- Email: nobodylearn174@gmail.com
- GitHub Issues: Create an issue for bugs/features
- Check existing code for patterns
