import org.gradle.api.JavaVersion.VERSION_11

plugins {
    `kotlin-dsl`
    id("com.gradle.plugin-publish") version "0.10.1"
    id("org.sonarqube") version "2.8"
}

val pluginsVersion = "0.1.7"
description = "EASY-DEPLOY gradle needed tasks"
version = pluginsVersion
group = "online.colaba"

repositories { mavenLocal(); mavenCentral() }

val dockerMainPlugin = "dockerMainPlugin"

gradlePlugin {
    plugins {
        create(dockerMainPlugin) {
            id = "$group.dockerMain"; implementationClass = "$group.DockerMainPlugin"
            description = "Docker needed tasks for root multi-project"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-main-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-plugin"

    (plugins) {
        dockerMainPlugin {
            displayName = "\uD83D\uDEE1️ Gradle dockerMain plugin for root multi-project. "
            tags = listOf("docker", "kotlin", "deploy", "build.gradle.kts", "docker-compose", "\uD83E\uDD1F\uD83C\uDFFB")
            version = pluginsVersion
        }

    }
}

dependencies {
    implementation("org.hidetake:groovy-ssh:2.10.1")
}

configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }


kotlinDslPluginOptions { experimentalWarning.set(false) }

tasks {
    wrapper { gradleVersion = "6.0" }
    val java = "11"
    compileKotlin { kotlinOptions { jvmTarget = java }; sourceCompatibility = java; targetCompatibility = java }
}
sonarqube {
    properties { property("sonar.projectKey", "steklopod_gradle-ssh-plugin") }
}

defaultTasks("tasks", "publishPlugins")

