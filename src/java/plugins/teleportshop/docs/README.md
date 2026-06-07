# TeleportShop Plugin

A comprehensive Bukkit/Paper plugin providing **teleportation features** and **player shops** with NPC traders.

## Features

### 🏠 Homes System
- `/home [name]` - Teleport to a home
- `/sethome [name]` - Set a home at current location
- `/delhome <name>` - Delete a home
- `/listhomes` - List all your homes
- Configurable max homes per player
- Teleport delay with movement cancellation

### 🗺️ Warps System
- `/warp <name>` - Teleport to a public warp
- `/setwarp <name>` - Create a warp (admin)
- `/delwarp <name>` - Delete a warp (admin)
- `/warplist` - List all available warps

### 🤝 Teleport Requests (TPA)
- `/tpa <player>` - Request to teleport to player
- `/tpahere <player>` - Request player to teleport to you
- `/tpaccept [player]` - Accept teleport request
- `/tpdeny [player]` - Deny teleport request
- `/tpcancel [player]` - Cancel your request
- Configurable timeout and cooldowns

### 🛒 Player Shops
- `/shop [player]` - Open a player's shop
- `/createshop <name>` - Create your own shop
- `/deleteshop <name>` - Delete your shop
- `/additem <shop> <buy-price> <sell-price> <stock>` - Add items
- Vault integration for economy
- Custom item pricing

### ⚙️ Utility Commands
- `/back` - Return to last location
- `/spawn` - Teleport to spawn
- `/setspawn` - Set spawn point (admin)

## Installation

1. Place `TeleportShop.jar` in your server's `plugins/` folder
2. Restart the server
3. The plugin generates default config file at `plugins/TeleportShop/config.yml`
4. (Optional) Install **Vault** for economy integration
5. (Optional) Install **PlaceholderAPI** for placeholder support

## Configuration

Edit `plugins/TeleportShop/config.yml`:

```yaml
teleport:
  home:
    max-homes: 3
    delay-seconds: 3
  warp:
    delay-seconds: 2
  tpa:
    timeout-seconds: 60
    cooldown-seconds: 5
  back:
    enabled: true
  safe-location-search: true

shop:
  enable: true
  vault-required: true

messages:
  home-set: "&a✓ Home '{name}' set!"
  teleporting: "&bTeleporting in {delay} seconds... Don't move!"
```

## Permissions

### User Permissions
```
teleportshop.home.use         - Use /home command
teleportshop.home.set         - Set homes with /sethome
teleportshop.home.delete      - Delete homes with /delhome
teleportshop.home.list        - List homes with /listhomes

teleportshop.warp.use         - Teleport to warps
teleportshop.warp.set         - Create warps (admin)
teleportshop.warp.delete      - Delete warps (admin)

teleportshop.tpa.*            - All TPA permissions
teleportshop.back.use         - Use /back command
teleportshop.spawn.use        - Use /spawn command
teleportshop.spawn.set        - Set spawn (admin)

teleportshop.shop.*           - All shop permissions
```

### Admin Permissions
```
teleportshop.admin            - Full admin access
teleportshop.bypass.cooldown  - Bypass teleport cooldowns
teleportshop.bypass.tpadeny   - Bypass TPA denials
```

## Commands Cheat Sheet

| Command | Usage | Permission |
|---------|-------|-----------|
| `/home` | `/home [name]` | teleportshop.home.use |
| `/sethome` | `/sethome [name]` | teleportshop.home.set |
| `/delhome` | `/delhome <name>` | teleportshop.home.delete |
| `/listhomes` | `/listhomes` | teleportshop.home.list |
| `/warp` | `/warp <name>` | teleportshop.warp.use |
| `/setwarp` | `/setwarp <name>` | teleportshop.warp.set |
| `/tpa` | `/tpa <player>` | teleportshop.tpa.request |
| `/tpaccept` | `/tpaccept [player]` | teleportshop.tpa.accept |
| `/back` | `/back` | teleportshop.back.use |
| `/spawn` | `/spawn` | teleportshop.spawn.use |
| `/shop` | `/shop [player]` | teleportshop.shop.use |

## Dependencies

### Required
- Paper 1.20.1+ (recommended) or Bukkit/Spigot 1.20+

### Optional
- **Vault** - For economy support in shops
- **PlaceholderAPI** - For placeholder expansion

## Configuration Examples

### Set Max Homes Per Player
```yaml
teleport:
  home:
    max-homes: 5
```

### Increase Teleport Delay
```yaml
teleport:
  home:
    delay-seconds: 5
  warp:
    delay-seconds: 3
```

### TPA Timeout
```yaml
teleport:
  tpa:
    timeout-seconds: 120
```

## Troubleshooting

### "You don't have permission"
- Check your permission group in LuckPerms or PermissionsEx
- Default perms are set in `plugin.yml`
- Use `/perm player <name> set teleportshop.home.use true` with LuckPerms

### Teleport not working
- Check console for errors
- Verify player has permission
- Check if location is safe (if safe-location-search enabled)

### Shops not appearing
- Ensure Vault is installed if economy required
- Check `shop.enable: true` in config

## Development

This plugin is built with:
- **Language**: Java 17+
- **Build Tool**: Maven
- **Framework**: Bukkit/Paper API
- **Optional**: Vault API, PlaceholderAPI

## License

All rights reserved © 2026 nobody174

## Support

- GitHub Issues: [Report bugs](https://github.com/nobody174/minecraft-addons/issues)
- Email: nobodylearn174@gmail.com

## Roadmap

- [x] MVP: Homes, Warps, TPA
- [ ] Player Shops with GUI
- [ ] NPC Traders
- [ ] Cross-server persistence (Bungee/Velocity)
- [ ] Premium features (auction house, advanced shops)
- [ ] Web dashboard
