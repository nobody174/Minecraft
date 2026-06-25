# ArmorAura v1.1.0

**A Minecraft mod that adds a dynamic glowing aura around armored players.**

A NeoForge mod for Minecraft 1.21.1 that creates a beautiful, customizable particle aura effect around your player whenever you wear armor. Features smooth rotating rings at different heights, 10+ particle types, automatic day/night variation, and zero-restart customization.

**New in v1.1.0:** a lightweight, true colored "glow" outline effect (`/auraglow`) — a synced, multiplayer-visible silhouette glow independent of the particle ring system.

---

## ✨ Features

### Visual Effects
- **4 configurable particle rings** at different heights (feet, knees, belly, head)
- **Smooth rotation animation** with adjustable speed (0.1–3.0)
- **Automatic day/night dimming** — 50% intensity during day, full intensity at night
- **10+ particle types** — Glow, Flame, Electric Spark, Crit, End Rod, Soul, Portal, Dragon Breath, Happy Villager, Note

### Configuration System
- **JSON-based config** at `~/.minecraft/config/armoraura/aura-config.json`
- **Runtime reload** — Edit settings and apply instantly with `/armoraura reload` (no restart needed!)
- **3 built-in presets** — Minimal (subtle), Medium (balanced), Maximum (intense)
- **Fine-grained control** — Customize particles, radius, speed, rings, and effect type

### In-Game Commands
All commands are client-side and execute instantly:

```
/armoraura preset NAME          # Switch to minimal, medium, or maximum
/armoraura particles N          # Set particles per ring (1-20)
/armoraura radius X             # Set aura distance (0.1-1.5)
/armoraura speed X              # Set rotation speed (0.1-3.0)
/armoraura rings N              # Set number of rings (1-4)
/armoraura effect TYPE          # Set particle type (glow, flame, etc.)
/armoraura status               # View all current settings
/armoraura reload               # Reload config from JSON file
/armoraura enable               # Turn the particle aura on
/armoraura disable              # Turn the particle aura off
/armoraura toggle               # Flip particle aura on/off
```

### Glow Aura (`/auraglow`) — New in v1.1.0

A separate, lightweight effect: a soft pulsing colored outline rendered directly on the player's silhouette (no particles). Synced to the server so other players in multiplayer can see your glow too.

```
/auraglow toggle                # Turn glow on/off
/auraglow on                    # Turn glow on
/auraglow off                   # Turn glow off
/auraglow color NAME|HEX        # Set glow color by name or hex, e.g. /auraglow color cyan
/auraglow colors                # List all named colors and their hex codes
/auraglow intensity X           # Set base brightness (0.05-1.0)
/auraglow pulse X                # Set pulse speed (0 = static glow, up to 5.0)
/auraglow status                # View current glow settings
```

#### Named Colors

Tab-complete after `/auraglow color` to see these, or run `/auraglow colors` anytime:

| Name | Hex |
|------|-----|
| cyan | #33CCFF |
| white | #FFFFFF |
| red | #FF2222 |
| green | #33FF33 |
| gold | #FFD700 |
| purple | #9933FF |
| orange | #FF8800 |
| pink | #FF66CC |
| blue | #3366FF |
| yellow | #FFFF33 |

Any other 6-digit hex code also works, e.g. `/auraglow color FF00AA`.

The glow is purely cosmetic, client-rendered, and does not affect gameplay (no vanilla Glowing effect, no team changes). It works alongside the particle aura system above — you can run both at once. All `/armoraura` and `/auraglow` settings persist to `aura-config.json` automatically.

---

## 📦 Installation

1. **Download** the latest JAR from [Releases](https://github.com/nobody174/armor-aura/releases)
2. **Place** it in your `.minecraft/mods/` folder
3. **Launch** Minecraft with NeoForge 1.21.1
4. **Equip armor** to see the aura in action!

### Requirements
- **Minecraft:** 1.21.1
- **Mod Loader:** NeoForge 21.1.233+
- **Java:** Java 21+

---

## 🚀 Quick Start

### Using Presets (Easiest)

```
/armoraura preset minimal       # Subtle, slow-rotating effect
/armoraura preset medium        # Balanced effect (2 rings)
/armoraura preset maximum       # Intense, fast-rotating effect
```

### The 3 Presets Explained

| Preset | Particles | Radius | Rings | Speed | Style |
|--------|-----------|--------|-------|-------|-------|
| **Minimal** | 3 | 0.7 | 4 | 0.1 | Subtle, slow |
| **Medium** | 10 | 0.7 | 2 | 1.3 | Balanced |
| **Maximum** | 20 | 0.7 | 4 | 3.0 | Intense, fast |

---

## 📖 How to Use

### Method 1: Quick Commands
For immediate adjustments:

```
/armoraura preset medium
/armoraura particles 15
/armoraura radius 0.8
/armoraura status
```

### Method 2: Edit Config File (Recommended)
For precise customization without restarting:

1. Open `C:\Users\[You]\AppData\Roaming\.minecraft\config\armoraura\aura-config.json`
2. Edit the values:
   ```json
   {
     "particlesPerRing": 10,      // 1-20 (more = fuller aura)
     "radius": 0.7,               // 0.1-1.5 (distance from player)
     "rotationSpeed": 1.3,        // 0.1-3.0 (how fast it spins)
     "ringCount": 2,              // 1-4 (number of particle rings)
     "particleEffect": "flame",   // particle type (see below)
     "preset": "medium"           // preset name (for reference)
   }
   ```
3. Save the file
4. In-game: `/armoraura reload`
5. **Changes apply instantly!** ⚡ No restart needed!

### Method 3: Try Example Configurations

**Flaming Inferno:**
```json
{
  "particlesPerRing": 20,
  "radius": 0.9,
  "rotationSpeed": 2.5,
  "ringCount": 4,
  "particleEffect": "flame",
  "preset": "maximum"
}
```

**Electric Aura:**
```json
{
  "particlesPerRing": 12,
  "radius": 0.6,
  "rotationSpeed": 1.8,
  "ringCount": 3,
  "particleEffect": "electric_spark",
  "preset": "medium"
}
```

**Subtle Glow:**
```json
{
  "particlesPerRing": 2,
  "radius": 0.3,
  "rotationSpeed": 0.2,
  "ringCount": 1,
  "particleEffect": "glow",
  "preset": "minimal"
}
```

---

## ⚙️ Configuration Options

### Particle Effects

Available particle types: `glow`, `flame`, `electric_spark`, `crit`, `end_rod`, `soul`, `portal`, `dragon_breath`, `happy_villager`, `note`

- `glow` — Cyan glowing particles
- `flame` — Orange/red fire particles
- `electric_spark` — Blue electric sparks
- `crit` — White critical damage sparkles
- `end_rod` — Purple End-like rods
- `soul` — Blue soul particles
- `portal` — Purple portal effect
- `dragon_breath` — Purple dragon breath
- `happy_villager` — Green happiness particles
- `note` — Musical note particles

### Ring Architecture

The aura consists of 4 rings at different heights:
- **Ring 1** (0.3) — Feet level
- **Ring 2** (0.8) — Knees level
- **Ring 3** (1.3) — Belly level
- **Ring 4** (1.85) — Head level

Use `ringCount` to control which rings are visible:
- `1` → Only feet ring (compact, subtle)
- `2` → Feet + knees (balanced)
- `3` → Feet + knees + belly (fuller)
- `4` → All rings (complete body aura)

### Parameter Ranges

- `particlesPerRing`: 1–20 (more particles = fuller aura, more performance cost)
- `radius`: 0.1–1.5 (distance from player body)
- `rotationSpeed`: 0.1–3.0 (rotation speed in radians per tick)
- `ringCount`: 1–4 (number of particle rings to render)

---

## 🎨 Customization Examples

**Mystical Soul:**
```json
{
  "particlesPerRing": 8,
  "radius": 0.5,
  "rotationSpeed": 1.0,
  "ringCount": 2,
  "particleEffect": "soul"
}
```

**Dragon Knight:**
```json
{
  "particlesPerRing": 15,
  "radius": 0.8,
  "rotationSpeed": 2.0,
  "ringCount": 4,
  "particleEffect": "dragon_breath"
}
```

**Portal Walker:**
```json
{
  "particlesPerRing": 10,
  "radius": 0.7,
  "rotationSpeed": 1.5,
  "ringCount": 3,
  "particleEffect": "portal"
}
```

---

## ⚡ Performance

Tested on RTX 3060:
- **Minimal (3 particles/ring):** ~0% FPS impact
- **Medium (10 particles/ring):** <1% FPS impact
- **Maximum (20 particles/ring):** <2% FPS impact

Fully playable on modern hardware. If you experience lag, reduce `particlesPerRing` (try 5–8).

---

## 📝 Advanced Features

### Day/Night Intensity
The aura automatically adjusts intensity based on time of day:
- **Night** (13000–23000 ticks): Full intensity
- **Day** (0–13000 ticks): 50% intensity

This is automatic and cannot be changed via config.

### Testing Workflow
Quick iteration cycle: **~5 seconds**

1. Edit JSON file
2. Save the file
3. Run `/armoraura reload` in-game
4. Observe changes instantly
5. Repeat!

No game restart needed! 🚀

---

## 🐛 Troubleshooting

**Aura not showing?**
- Make sure you're wearing armor
- Check that you're not in creative mode without armor

**Changes not applying?**
- Run `/armoraura reload` after editing JSON
- Make sure the JSON file is valid (no trailing commas, quotes are matched)
- Check that the file is saved

**Performance issues?**
- Reduce `particlesPerRing` (try 5–8)
- Reduce `radius` (try 0.4–0.5)
- Reduce `ringCount` (try 1–2)

**JSON file corrupted?**
- Delete `~/.minecraft/config/armoraura/aura-config.json`
- Restart the game to recreate with default settings

---

## 📋 All Commands Reference

| Command | Description | Example |
|---------|-------------|---------|
| `/armoraura preset NAME` | Switch preset | `/armoraura preset minimal` |
| `/armoraura particles N` | Set particles (1-20) | `/armoraura particles 15` |
| `/armoraura radius X` | Set distance (0.1-1.5) | `/armoraura radius 0.8` |
| `/armoraura speed X` | Set rotation (0.1-3.0) | `/armoraura speed 2.0` |
| `/armoraura rings N` | Set rings (1-4) | `/armoraura rings 3` |
| `/armoraura effect TYPE` | Set particle type | `/armoraura effect flame` |
| `/armoraura status` | Show all settings | `/armoraura status` |
| `/armoraura reload` | Reload from JSON | `/armoraura reload` |
| `/armoraura enable` | Turn particle aura on | `/armoraura enable` |
| `/armoraura disable` | Turn particle aura off | `/armoraura disable` |
| `/armoraura toggle` | Flip particle aura on/off | `/armoraura toggle` |
| `/auraglow toggle` / `on` / `off` | Toggle glow on/off | `/auraglow toggle` |
| `/auraglow color NAME\|HEX` | Set glow color | `/auraglow color cyan` |
| `/auraglow colors` | List named colors | `/auraglow colors` |
| `/auraglow intensity X` | Set glow brightness (0.05-1.0) | `/auraglow intensity 0.8` |
| `/auraglow pulse X` | Set pulse speed (0-5.0) | `/auraglow pulse 1.5` |
| `/auraglow status` | Show glow settings | `/auraglow status` |

---

## 📄 License

All rights reserved © 2025 nobody174

## Credits

Built with NeoForge for Minecraft 1.21.1.

---

**Enjoy your armor aura!** ✨
