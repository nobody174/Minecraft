# Minecraft Addons Project

A comprehensive repository for creating and maintaining Minecraft server modifications, plugins, and data packs. This project supports multiple addon types including Forge mods, Spigot/Bukkit plugins, data packs, and resource packs.

## Project Structure

```
minecraft-addons/
├── src/
│   ├── java/
│   │   ├── mods/          # Minecraft Forge mods
│   │   └── plugins/       # Spigot/Bukkit plugins
│   ├── data-packs/        # Data packs (Lua/YAML)
│   ├── resource-packs/    # Resource packs
│   └── tools/             # Node.js utility tools
├── tests/
│   ├── java/              # JUnit tests
│   └── integration/       # Server integration tests
├── docs/                  # Documentation
├── .github/workflows/     # GitHub Actions CI/CD
├── pom.xml               # Maven configuration
├── package.json          # Node.js dependencies
└── README.md
```

## Supported Addon Types

### 1. Minecraft Forge Mods
- Written in Java
- Requires Minecraft Forge
- Version-specific (1.20.x, 1.21+, etc.)
- Uses Maven or Gradle for builds

### 2. Spigot/Bukkit Plugins
- Java-based server plugins
- Compatible with Paper, Spigot, Bukkit servers
- Plugin configuration via YAML
- Hot-reload capable

### 3. Data Packs
- Loot tables, recipes, structures
- Function files for custom mechanics
- Namespaced organization
- Version compatible

### 4. Resource Packs
- Textures, models, and sounds
- Optional data pack integration
- Multiple resolution support

### 5. Node.js Tools
- Server management utilities
- Data pack generators
- Asset processing tools

## Quick Start

### Prerequisites
- **Java**: JDK 17 or higher
- **Maven**: 3.8+
- **Node.js**: 18+ (for tools)
- **Minecraft Server**: 1.20+ (for testing)
- **Git**: Version control
- **IntelliJ IDEA** or **Eclipse**: IDE for mod development

### Setup
See [SETUP.md](SETUP.md) for detailed installation and configuration instructions.

### Building Addons

#### Java Mods/Plugins
```bash
mvn clean package
```

#### Node.js Tools
```bash
npm install
npm run build
```

## Development Workflow

1. **Choose an addon type** from `src/`
2. **Create your addon** following the template structure
3. **Write tests** in the `tests/` directory
4. **Run locally** on a test Minecraft server
5. **Commit and push** - CI/CD runs automated tests
6. **Document features** in README and SETUP.md

## Testing Strategy

### Local Testing
- Live Minecraft server instance (1.20+)
- Manual gameplay testing with community testers
- Real-world usage scenarios

### Automated Testing
- **JUnit 5** for Java code units
- **Jest** for JavaScript/Node.js tools
- **GitHub Actions** for CI/CD pipeline
- Server integration tests with Minecraft server

### Test Coverage
- Target: 80%+ code coverage for core functionality
- All public APIs must have tests
- Integration tests for server functionality

## Features Tracking

New features and planned addons are documented in [IDEAS.md](IDEAS.md). Each feature includes:
- **Status**: Idea → In Development → Testing → Ready
- **Purpose**: What problem it solves
- **Description**: Technical details
- **Type**: Mod, Plugin, Data Pack, or Tool
- **Notes**: Dependencies, version requirements, etc.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for:
- Code style guidelines
- Commit conventions
- Pull request process
- Testing requirements

## License

All code in this repository is licensed under the MIT License. See [LICENSE](LICENSE) for details.

## Author

- **Developer**: nobody174
- **Email**: nobodylearn174@gmail.com
- **Created**: 2026

## Resources

- [Minecraft Forge Documentation](https://docs.minecraftforge.net/)
- [Spigot/Bukkit Plugin Development](https://www.spigotmc.org/wiki/spigot-plugin-creators-guide/)
- [Data Pack Wiki](https://minecraft.wiki/w/Data_pack)
- [Minecraft Resource Pack Documentation](https://minecraft.wiki/w/Resource_pack)

## Status

🟢 **Active Development** - Community testing in progress
