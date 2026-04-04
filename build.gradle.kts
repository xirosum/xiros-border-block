plugins {
	id("net.fabricmc.fabric-loom-remap")
	`maven-publish`
}

version = providers.gradleProperty("mod_version").get()
group = providers.gradleProperty("maven_group").get()

base {
	archivesName = providers.gradleProperty("archives_base_name")
}

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.

	maven {
		name = "Infinituum Mod Resources"
		url = uri("https://raw.githubusercontent.com/Infinituum17/ModResources/main/maven/")
	}

	maven {
		name = "GeckoLib"
		url = uri("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
		content {
			includeGroupByRegex("software\\.bernie.*")
			includeGroup("com.eliotlash.mclib")
		}
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${providers.gradleProperty("minecraft_version").get()}")
	mappings("net.fabricmc:yarn:${providers.gradleProperty("yarn_mappings").get()}:v2")
	modImplementation("net.fabricmc:fabric-loader:${providers.gradleProperty("loader_version").get()}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${providers.gradleProperty("fabric_api_version").get()}")
	
	// YAML parsing
	implementation("org.yaml:snakeyaml:2.0")

	modImplementation("software.bernie.geckolib:geckolib-fabric-${providers.gradleProperty("minecraft_version").get()}:${providers.gradleProperty("geckolib_version").get()}")
	implementation("com.eliotlash.mclib:mclib:20")
}

tasks.processResources {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 17
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
	inputs.property("archivesName", base.archivesName)

	from("LICENSE") {
		rename { "${it}_${base.archivesName.get()}" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			artifactId = base.archivesName.get()
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
