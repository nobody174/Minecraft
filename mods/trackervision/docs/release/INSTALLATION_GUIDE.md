# How to Install TrackerVision

## Requirements

- **Minecraft:** 1.21.1
- **NeoForge:** 1.21.1 or higher
- **Java:** 21 or higher

## Installation Steps

### Step 1: Install NeoForge (if not already installed)

1. Go to [neoforged.net](https://neoforged.net)
2. Download NeoForge for Minecraft 1.21.1
3. Run the installer
4. Select "Install client"
5. Verify installation: Launch Minecraft, check for "NeoForge" in version selector

### Step 2: Download TrackerVision

**Option A: From GitHub Release (Recommended)**

1. Go to [TrackerVision Releases](https://github.com/nobody174/trackervision/releases)
2. Find v1.0.0 release
3. Download `trackervision-1.0.0-mc1_21_1.jar`

**Option B: Build from Source**

```bash
git clone https://github.com/nobody174/trackervision.git
cd trackervision
./gradlew build
# JAR is at: build/libs/trackervision-1.0.0-mc1_21_1.jar
```

### Step 3: Install JAR File

**Windows:**
1. Open File Explorer
2. Press `Win + R`, type `%appdata%`
3. Navigate to `.minecraft/mods/`
4. Copy `trackervision-1.0.0-mc1_21_1.jar` into mods folder

**Mac:**
1. Open Finder
2. Press `Cmd + Shift + G`, paste: `~/Library/Application Support/minecraft/mods`
3. Copy JAR file into mods folder

**Linux:**
1. Open terminal
2. Copy JAR to: `~/.minecraft/mods/`
   ```bash
   cp trackervision-1.0.0-mc1_21_1.jar ~/.minecraft/mods/
   ```

### Step 4: Verify Installation

1. Launch Minecraft 1.21.1 with NeoForge
2. Go to Mods menu (in main screen)
3. Search for "TrackerVision"
4. If it appears, installation successful! ✅

### Step 5: First Use

1. Load into a world (Survival, Creative, or Peaceful)
2. Press ESC → Mods → Click "Config" on TrackerVision
   - Or use: `/track config show` to see all settings
3. Try locking a target: `/track lock @e[limit=1,sort=nearest]`
4. Use `/track search true` to reveal nearby entities

## Troubleshooting

### Mod doesn't appear in Mods list

**Problem:** TrackerVision not showing in mods menu

**Solutions:**
1. Verify you installed NeoForge first
   - Launch Minecraft 1.21.1 with NeoForge version
   - Check bottom-left for "NeoForge" label
2. Check JAR is in correct mods folder
   - Windows: `%appdata%/.minecraft/mods/`
   - Mac: `~/Library/Application Support/minecraft/mods`
   - Linux: `~/.minecraft/mods/`
3. Verify JAR filename: should be `trackervision-1.0.0-mc1_21_1.jar`
4. Try renaming JAR (remove any extra characters)
5. Restart Minecraft completely

### Game crashes on load

**Problem:** Game crashes when loading with TrackerVision

**Solutions:**
1. Check Java version: Open cmd/terminal and run `java -version`
   - Must show Java 21 or higher
2. Verify NeoForge version matches
   - Should be 1.21.1 build 57.1.12 or higher
3. Check for conflicting mods
   - Remove other mods one-by-one to isolate issue
   - Try running just TrackerVision alone
4. Check log file for error:
   - Windows: `%appdata%/.minecraft/logs/latest.log`
   - Mac: `~/Library/Application Support/minecraft/logs/latest.log`
   - Look for "TrackerVision" errors

### Commands don't work

**Problem:** `/track` command not recognized

**Solutions:**
1. Verify mod loaded
   - Check Mods menu (should list TrackerVision)
   - Try `/modlist` command
2. Use full command syntax: `/track lock @e[limit=1,sort=nearest]`
   - Entity selectors MUST have brackets `[...]`
   - `@e[type=zombie]` alone will fail if multiple exist
3. Try simpler command: `/track status`
   - Should show current lock state
4. Check F3 console for errors
   - Press F3 to open debug screen
   - Look for red text or warnings

### Config screen won't open

**Problem:** Can't access config screen via Mods menu

**Solutions:**
1. Verify mod is loaded (Mods menu should show it)
2. Try command instead: `/track config show`
   - This shows all settings via chat
3. Edit config file directly:
   - Location: `.minecraft/config/trackervision/trackervision-config.json`
4. Restart Minecraft after changes

### Entity selector not working

**Problem:** `/track lock @e[...]` shows error

**Solutions:**
1. Verify syntax includes brackets: `@e[...]` not `@e`
2. Use proper selector format:
   - ✓ `/track lock @e[type=zombie,limit=1,sort=nearest]`
   - ✗ `/track lock @e[type=zombie]` (will fail if 2+ zombies)
3. Always include `limit=1` and `sort=nearest` together
4. Try simplest selector: `/track lock @e[limit=1,sort=nearest]`
   - This locks the closest entity of any type
5. Check Minecraft wiki for entity selector syntax

## Uninstalling TrackerVision

To remove TrackerVision:

1. Go to `.minecraft/mods/` folder
2. Delete `trackervision-1.0.0-mc1_21_1.jar`
3. Restart Minecraft
4. (Optional) Delete config file: `.minecraft/config/trackervision/`

## Getting Help

- **GitHub Issues:** [Report bugs](https://github.com/nobody174/trackervision/issues)
- **GitHub Discussions:** [Ask questions](https://github.com/nobody174/trackervision/discussions)
- **Check README:** Full feature guide at [README.md](https://github.com/nobody174/trackervision)

## Next Steps

After installation, check out:
- **[Feature List](FEATURE_LIST.md)** — All available features
- **[Compatibility](COMPATIBILITY.md)** — Mod compatibility info
- **[README](README_RELEASE.md)** — Quick start guide

Enjoy entity tracking! 🎮
