# Data Pack Template

Complete template for creating a Minecraft data pack.

## Directory Structure

```
datapack-name/
├── data/
│   └── namespace/
│       ├── functions/
│       │   ├── load.mcfunction
│       │   ├── tick.mcfunction
│       │   └── events/
│       │       └── player_join.mcfunction
│       ├── loot_tables/
│       │   ├── chests/
│       │   └── entities/
│       ├── recipes/
│       │   ├── crafting/
│       │   ├── smelting/
│       │   └── custom/
│       ├── advancement/
│       │   └── root.json
│       ├── tags/
│       │   ├── blocks/
│       │   ├── items/
│       │   └── functions/
│       └── structures/
└── pack.mcmeta
```

## Essential Files

### 1. pack.mcmeta

```json
{
  "pack": {
    "pack_format": 26,
    "description": "Brief description of data pack",
    "supported_formats": {
      "min_inclusive": 26,
      "max_inclusive": 26
    }
  }
}
```

**Pack Format by Version:**
- 1.20.1 - 26
- 1.20.0 - 26
- 1.19.2 - 12
- 1.19.0 - 12

### 2. Load Function (data/namespace/functions/load.mcfunction)

```
# Load function - runs once when data pack loads

# Initialize scoreboards
scoreboard objectives add custom_stat dummy

# Say we loaded
tellraw @a ["",{"text":"[DataPack] ","color":"blue","bold":true},{"text":"Loaded successfully!","color":"green"}]
```

### 3. Tick Function (data/namespace/functions/tick.mcfunction)

```
# Tick function - runs every game tick
# Schedule this to run: /schedule function namespace:tick 1t replace

# Put logic that needs to run every tick here
```

### 4. Schedule Load and Tick

```mcfunction
# In load.mcfunction, add:
schedule function namespace:tick 1t replace
```

## Example Files

### Custom Recipe (Crafting)

**File: data/namespace/recipes/custom_item.json**
```json
{
  "type": "minecraft:crafting_shaped",
  "pattern": [
    "###",
    "# #",
    "###"
  ],
  "key": {
    "#": {
      "item": "minecraft:iron_ingot"
    }
  },
  "result": {
    "item": "minecraft:diamond",
    "count": 1
  }
}
```

### Loot Table

**File: data/namespace/loot_tables/chests/custom_chest.json**
```json
{
  "type": "minecraft:chest",
  "pools": [
    {
      "rolls": 1,
      "entries": [
        {
          "type": "minecraft:item",
          "name": "minecraft:diamond",
          "weight": 5
        },
        {
          "type": "minecraft:item",
          "name": "minecraft:emerald",
          "weight": 10
        }
      ]
    }
  ]
}
```

### Function with NBT Tags

**File: data/namespace/functions/events/player_join.mcfunction**
```
# Run when player joins
execute as @a[tag=!joined] run function namespace:on_first_join
execute as @a run tag @s add joined
```

### Tag (Function)

**File: data/namespace/tags/functions/tick.json**
```json
{
  "values": [
    "namespace:tick"
  ]
}
```

### Advancement

**File: data/namespace/advancements/root.json**
```json
{
  "display": {
    "icon": {
      "item": "minecraft:diamond"
    },
    "title": {
      "text": "Custom Achievement"
    },
    "description": {
      "text": "Achievement description"
    },
    "background": "minecraft:textures/gui/advancements/backgrounds/stone.png",
    "show_toast": true,
    "announce_to_chat": true
  },
  "criteria": {
    "trigger": {
      "trigger": "minecraft:impossible"
    }
  }
}
```

## Important Considerations

### Namespace Convention
- Use your addon name as namespace
- Example: `namespace:function_name`
- Prevents conflicts with other data packs

### Function Scheduling
```mcfunction
# One-time execution (next tick)
schedule function namespace:function_name 1t

# Repeating (every 20 ticks = 1 second)
schedule function namespace:function_name 20t replace

# Clear schedule
schedule clear namespace:function_name
```

### Scoreboards
```mcfunction
# Create objective
scoreboard objectives add my_stat dummy

# Set value
scoreboard players set @a my_stat 0

# Add to value
scoreboard players add @a my_stat 1

# Check value
execute if score @a my_stat matches 10.. run say reached 10
```

### Tags
```mcfunction
# Add tag
tag @a add custom_tag

# Remove tag
tag @s remove custom_tag

# Test for tag
execute if entity @a[tag=custom_tag] run say found tagged players
```

## Testing the Data Pack

1. **Create test world**
   ```bash
   # Copy data pack to world
   cp -r datapack-name world/datapacks/
   ```

2. **Start server and load world**

3. **Check data pack loaded:**
   ```
   /datapack list
   ```

4. **Enable data pack:**
   ```
   /datapack enable "namespace:datapack-name"
   ```

5. **Test functions:**
   ```
   /function namespace:load
   /function namespace:tick
   ```

## Documentation

Create these files in the `docs/` directory:

### FEATURES.md
- List all features and mechanics
- Explain how each feature works
- Include command examples

### INSTALL.md
- How to install the data pack
- Version requirements
- Any compatibility notes

### COMMANDS.md
- All commands provided by the data pack
- Syntax and arguments
- Examples

### CONFIG.md
- Any configurable scoreboards/tags
- How to customize behavior
- Default values

## Validation Tools

### Online Validators
- [Minecraft JSON Validator](https://jsoncrack.com/)
- [Data Pack Linting](https://mcutils.github.io/datapack-linter/)

### Commands
```mcfunction
# Check for errors
/datapack list

# Reload all data packs
/reload

# Show function tree
/function namespace:
```

## Best Practices

1. **Always use namespaces** - Prevents conflicts
2. **Test thoroughly** - Verify on actual server
3. **Document functions** - Add comments in .mcfunction files
4. **Use tags** - Group related functions and items
5. **Handle edge cases** - Test with multiple players
6. **Version compatibility** - Test on target versions
7. **Optimize performance** - Keep tick functions lightweight

## Distribution

1. Create `.zip` file with data pack structure
2. Upload to:
   - GitHub Releases
   - Curseforge
   - Planet Minecraft
   - Modrinth
3. Include README with installation instructions

## Resources

- [Official Minecraft Wiki - Data Packs](https://minecraft.wiki/w/Data_pack)
- [NBT Format](https://minecraft.wiki/w/NBT_format)
- [Advancement Documentation](https://minecraft.wiki/w/Advancement)
- [Loot Tables](https://minecraft.wiki/w/Loot_table)
