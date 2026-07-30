plugins {
    id("net.neoforged.gradle.userdev") version "7.0.145"
    `java-library`
}

val modVersion = project.property("mod_version") as String
val minecraftVersion = project.property("minecraft_version") as String
val neoforgeVersion = project.property("neoforge_version") as String
val modGroup = project.property("mod_group") as String

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

minecraft {
    accessTransformers.file("src/main/resources/META-INF/accesstransformer.cfg")
}

dependencies {
    implementation("net.neoforged:neoforge:$neoforgeVersion")
}

tasks.jar {
    archiveBaseName.set("boss-radar")
    archiveVersion.set("$version-mc${minecraftVersion.replace(".", "_")}")
    manifest {
        attributes(
            "Specification-Title" to "bossradar",
            "Implementation-Title" to "Boss Radar",
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
