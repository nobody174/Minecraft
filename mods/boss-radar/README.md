# Boss Radar

A craftable compass that detects nearby bosses and displays their location, distance, and status on a real-time HUD overlay.

**Version:** 1.0.0  
**Author:** nobody174  
**License:** All rights reserved © 2025 nobody174  
**Engine:** NeoForge 1.21.1  

---

## ⚠️ Installation Requirements

**This mod requires installation on BOTH client AND server:**

- **Client-side:** Install on your Minecraft client (your mods folder)
- **Server-side:** Server administrator must install on the dedicated/multiplayer server

The mod will **not work** if only installed on the client or server alone. The server performs boss detection and sends data to the client for rendering.

---

## Features

✅ **Real-Time Boss Detection** — Scans for tracked mobs within 128 blocks every 0.5 seconds  
✅ **Distance Ring HUD** — Color-coded ring around crosshair (red < 32m, yellow 32-64m, green > 64m)  
✅ **Target Display** — Shows boss name and exact distance in meters  
✅ **Dynamic Updates** — HUD updates continuously as you move relative to bosses  
✅ **Craftable Item** — Made with compass, gold nuggets, and ender eye  
✅ **Extensible Design** — Support for modded bosses (configured via entity tags)

---

## Detected Mobs (Vanilla)

- **Ender Dragon** (Boss)
- **Wither** (Boss)
- **Elder Guardian** (Mini-boss)
- **Warden** (Elite)
- **Ravager** (Dangerous mob)
- **Evoker** (Dangerous mob)

---

## Installation

### Server Setup
1. Download `boss-radar-1.0.0-mc1_21_1.jar`
2. Place it in your server's `mods/` folder
3. Restart the server

### Client Setup
1. Ensure NeoForge 1.21.1 is installed
2. Download `boss-radar-1.0.0-mc1_21_1.jar`
3. Place it in `%appdata%\.minecraft\mods\`
4. Launch Minecraft with your NeoForge profile

**Both the server AND client must have the mod installed for it to work.**

---

## How to Use

1. **Craft the item:**
   ```
    C          (Compass)
   GEG         (Gold Nugget, Ender Eye, Gold Nugget)
    G          (Gold Nugget)
   ```

2. **Hold it in your main or off-hand:**
   - Detection automatically starts when the item is in your hand
   - You'll see a message: "Boss Radar activated!"

3. **Watch the HUD:**
   - A colored ring appears around your crosshair when a boss is nearby
   - Text below shows the boss name and distance in meters
   - Ring color indicates distance:
     - 🔴 **Red** — Boss is close (< 32 meters)
     - 🟡 **Yellow** — Boss is moderate distance (32-64 meters)
     - 🟢 **Green** — Boss is far (> 64 meters)

4. **The display updates in real-time** as you and the boss move

---

## Technical Details

| Setting | Value |
|---------|-------|
| Detection Range | 128 blocks |
| Scan Interval | 10 ticks (0.5 seconds) |
| Server-side Detection | Required ✓ |
| Client-side Rendering | Required ✓ |
| Multiplayer Support | Yes (per-player tracking) |
| Performance Impact | Minimal |

---

## Building from Source

For developers who want to build the mod themselves:

**Requirements:**
- Java 21 or higher
- Git

**Steps:**
1. Clone the repository:
   ```bash
   git clone https://github.com/nobody174/Minecraft.git
   cd Minecraft/mods/boss-radar
   ```

2. Build the JAR:
   ```bash
   ./gradlew build
   ```

3. The compiled JAR will be at:
   ```
   build/libs/boss-radar-1.0.0-mc1_21_1.jar
   ```

4. Copy to your mods folder and launch!

---

## Troubleshooting

**HUD not showing?**
- Verify both client and server have the mod installed
- Confirm you're holding the Boss Radar item
- Check that bosses exist within 128 blocks

**Mod doesn't load?**
- Ensure NeoForge 1.21.1 is installed (not Forge)
- Verify file is named `boss-radar-1.0.0-mc1_21_1.jar`
- Check that server and client versions match

**Server says mod is missing?**
- Ask your server administrator to install the mod
- Verify they restarted the server after installation

---

## Future Enhancements

- Support for additional modded bosses
- Configurable detection range
- Custom color themes
- Audio alerts for nearby bosses
- Boss health tracking (if server-side data available)

---

## Credits

Built with [Claude Code](https://claude.com/claude-code) by Anthropic  
Icon artwork by nobody174

---

*"It's never too late to give up!" — Boss Radar Motto*
