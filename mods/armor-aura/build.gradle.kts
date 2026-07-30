import net.neoforged.gradle.dsl.common.runs.run.Run

plugins {
    id("net.neoforged.gradle.userdev") version "7.0.145"
    `java-library`
}

val modVersion = project.property("mod_version") as String
val minecraftVersion = project.property("minecraft_version") as String
val neoforgeVersion = project.property("neoforge_version") as String

version = modVersion
group = "com.nobody174.armoraura"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

minecraft {
    // accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")
}

// Plain-JUnit unit tests (no Minecraft/GameTest dependency) live in their own source set,
// entirely separate from `src/test`. NeoGradle's userdev plugin unconditionally wires
// net.neoforged.fml.junit.LaunchWrapper into the default `test` source set/task so GameTests
// can bootstrap Minecraft classes. That wiring currently ships a stale asm-analysis/asm-util
// version that collides with the module-path ASM NeoForge itself provides, crashing the JVM
// before any test runs — an upstream, currently-unresolved bug affecting the `test` task:
// https://github.com/neoforged/NeoForge/issues/1540
// https://github.com/neoforged/NeoForge/issues/2209
// https://github.com/neoforged/FancyModLoader/issues/216
// Since NeoGradle only auto-wires FML JUnit support onto the conventional `test` source set,
// a separate `unitTest` source set is never touched by it and runs as plain JUnit Platform.
sourceSets {
    create("unitTest") {
        java.srcDir("src/unitTest/java")
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val unitTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}
val unitTestRuntimeOnly by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get())
}

dependencies {
    implementation("net.neoforged:neoforge:$neoforgeVersion")

    unitTestImplementation("org.junit.jupiter:junit-jupiter-api:5.10.1")
    unitTestImplementation("org.junit.jupiter:junit-jupiter-params:5.10.1")
    unitTestRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.1")
}

val unitTest by tasks.registering(Test::class) {
    description = "Runs plain JUnit unit tests that don't require the Minecraft/FML runtime."
    group = "verification"
    testClassesDirs = sourceSets["unitTest"].output.classesDirs
    classpath = sourceSets["unitTest"].runtimeClasspath
    useJUnitPlatform()
}

tasks.named("check") {
    dependsOn(unitTest)
}

tasks.test {
    useJUnitPlatform()
    // Only GameTest classes remain in src/test — they need the modded Minecraft runtime and
    // FML's LaunchWrapper, and run via `runGameTestServer` / the gameTestServer run rather
    // than through this task, since the ASM conflict above still blocks a plain `test` run.
    // TODO: once the upstream ASM version-drift bug is fixed, re-enable `./gradlew test` for
    // GameTest classes directly instead of routing exclusively through runGameTestServer.
    exclude("**/gametest/**")
}

tasks.jar {
    archiveBaseName.set("armoraura")
    archiveVersion.set("$version-mc${minecraftVersion.replace(".", "_")}")
    manifest {
        attributes(
            "Specification-Title" to "armoraura",
            "Implementation-Title" to "ArmorAura",
            "Implementation-Version" to version,
            "Implementation-Vendor" to "nobody174"
        )
    }
}

runs {
    named("client") {
        workingDirectory(project.file("run"))
    }
    named("server") {
        workingDirectory(project.file("run"))
    }
    named("gameTestServer") {
        workingDirectory(project.file("run"))
        // Runs @GameTest-annotated methods headlessly and exits with a non-zero
        // status on failure, so CI can gate on it like any other task.
        systemProperty("neoforge.enabledGameTestNamespaces", "armoraura")
    }
}
