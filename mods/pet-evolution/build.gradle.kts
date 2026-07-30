plugins {
    id("net.neoforged.gradle.userdev") version "7.0.145"
    `java-library`
}

val modId = project.property("mod_id") as String
val modName = project.property("mod_name") as String
val modVersion = project.property("mod_version") as String
val modGroup = project.property("mod_group") as String
val minecraftVersion = project.property("minecraft_version") as String
val neoforgeVersion = project.property("neoforge_version") as String

version = modVersion
group = modGroup

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.neoforged.net/releases") }
}

dependencies {
    implementation("net.neoforged:neoforge:$neoforgeVersion")
}

tasks.jar {
    archiveBaseName.set(modId)
    archiveVersion.set("$version-mc${minecraftVersion.replace(".", "_")}")
    manifest {
        attributes(
            "Specification-Title" to modId,
            "Implementation-Title" to modName,
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
}
