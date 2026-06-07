# Documentation Index

Comprehensive documentation for the Minecraft Addons project.

## Getting Started

- **[README.md](../README.md)** - Project overview and features
- **[SETUP.md](../SETUP.md)** - Installation and environment setup
- **[CONTRIBUTING.md](../CONTRIBUTING.md)** - Contributing guidelines and code standards

## Addon Type Guides

### Bukkit/Spigot Plugins
- **[PLUGIN_TEMPLATE.md](PLUGIN_TEMPLATE.md)** - Complete plugin template
- Plugin structure and best practices
- Configuration files (plugin.yml, config.yml)
- Event handling and command registration
- Distribution on SpigotMC

### Minecraft Forge Mods
- **FORGE_TEMPLATE.md** *(Coming soon)*
- Mod structure and setup
- Gradle configuration
- Registry and event system
- Packaging and distribution

### Data Packs
- **[DATAPACK_TEMPLATE.md](DATAPACK_TEMPLATE.md)** - Complete data pack template
- Directory structure (functions, loot_tables, recipes, etc.)
- Function syntax and scheduling
- Advancement system
- Validation and testing

### Resource Packs
- **RESOURCEPACK_TEMPLATE.md** *(Coming soon)*
- Texture organization
- Sound definitions
- Language files
- Model customization

### Node.js Tools
- **TOOLS_GUIDE.md** *(Coming soon)*
- CLI tool development
- Data pack generators
- Server utilities
- Publishing to npm

## Feature Documentation

### Available Features

See [IDEAS.md](../IDEAS.md) for:
- Status of in-development features
- Planned addons and tools
- Testing phase projects
- Released addons

## API Documentation

- **api/** - Auto-generated API docs for each addon
  - Plugin JavaDocs
  - Function references
  - Event documentation

## Architecture

### Directory Structure
```
minecraft-addons/
├── src/
│   ├── java/              # Java projects
│   │   ├── mods/         # Forge mods
│   │   └── plugins/      # Bukkit plugins
│   ├── data-packs/        # Data pack projects
│   ├── resource-packs/    # Resource pack projects
│   └── tools/             # Node.js tools
├── tests/                 # Test suites
│   ├── java/             # JUnit tests
│   └── integration/       # Server integration tests
├── docs/                  # Documentation
└── .github/workflows/     # CI/CD pipelines
```

## Development Workflow

1. **Planning** - Add feature to [IDEAS.md](../IDEAS.md)
2. **Development** - Create addon following appropriate template
3. **Testing** - Write tests and test on server
4. **Documentation** - Update README and feature docs
5. **CI/CD** - GitHub Actions validates automatically
6. **Release** - Create GitHub release with artifacts

## Testing Guide

### Unit Tests
- JUnit 5 for Java code
- Jest for JavaScript tools
- Run with: `mvn test` or `npm test`

### Integration Tests
- Real Minecraft server testing
- Player interaction testing
- Run with: `mvn integration-test`

### Manual Testing
- Live server with testers
- Community feedback
- Real-world usage scenarios

## Code Standards

### Java Conventions
- Package naming: `com.minecraft.addon.module`
- Class naming: `PascalCase`
- Method naming: `camelCase`
- Line length: 120 characters
- Indentation: 4 spaces

### JavaScript/TypeScript Conventions
- File naming: `kebab-case.ts`
- Variable naming: `camelCase`
- Class naming: `PascalCase`
- Line length: 100 characters
- Indentation: 2 spaces

### YAML Conventions
- Indentation: 2 spaces
- Keys: `lowercase_with_underscores`
- Comments for complex sections

See [CONTRIBUTING.md](../CONTRIBUTING.md) for full standards.

## CI/CD Pipeline

### Workflows
- **java-ci.yml** - Builds, tests, and validates Java code
- **nodejs-ci.yml** - Builds, tests, and validates Node.js code
- **integration-tests.yml** - Runs server integration tests

### Coverage Requirements
- Minimum 80% code coverage
- All public APIs must have tests
- Integration tests for server functionality

## Project Resources

### External Documentation
- [Minecraft Wiki](https://minecraft.wiki)
- [Bukkit/Spigot Plugin Development](https://www.spigotmc.org/wiki/)
- [Minecraft Forge Docs](https://docs.minecraftforge.net/)
- [Data Pack Wiki](https://minecraft.wiki/w/Data_pack)

### Tools & Services
- **IntelliJ IDEA** - Java/Kotlin IDE
- **VS Code** - Data pack and tool development
- **Maven** - Java build system
- **npm** - Node.js package manager
- **GitHub Actions** - CI/CD automation

### Community
- **GitHub Issues** - Bug reports and feature requests
- **GitHub Discussions** - Community questions
- **Email** - nobodylearn174@gmail.com

## Quick Links

| Type | Template | Build | Test |
|------|----------|-------|------|
| Plugin | [PLUGIN_TEMPLATE.md](PLUGIN_TEMPLATE.md) | `mvn package` | `mvn test` |
| Data Pack | [DATAPACK_TEMPLATE.md](DATAPACK_TEMPLATE.md) | Manual | Server |
| Tools | [TOOLS_GUIDE.md](TOOLS_GUIDE.md) *(coming)* | `npm build` | `npm test` |

## Contributing Documentation

Found an error in the docs? Want to improve them?

1. Fork the repository
2. Create a branch: `git checkout -b docs/improvement`
3. Edit the relevant markdown file
4. Commit: `git commit -m "docs: improve documentation"`
5. Push and create a Pull Request

See [CONTRIBUTING.md](../CONTRIBUTING.md) for full guidelines.

## Roadmap

### Phase 1 (Current)
- ✅ Project structure
- ✅ Plugin template and guide
- ✅ Data pack template and guide
- ⏳ Community testing

### Phase 2 (Planned)
- ⏳ Forge mod template
- ⏳ Resource pack template
- ⏳ Node.js tools guide
- ⏳ Advanced feature documentation

### Phase 3 (Future)
- ⏳ First stable releases
- ⏳ Example addons
- ⏳ Video tutorials
- ⏳ Community showcase

---

**Last Updated:** June 1, 2026
**Maintained By:** nobody174 <nobodylearn174@gmail.com>
