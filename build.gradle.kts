import org.gradle.api.JavaVersion.VERSION_11
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
     id( "com.gradle.plugin-publish") version "0.10.1"
}

version = "0.1.2"
group = "online.colaba"
description = "EASY-DEPLOY gradle needed tasks"

repositories { mavenLocal(); mavenCentral() }

gradlePlugin {
    plugins {
        val dockerMainPlugin by registering {
            id = "online.colaba.dockerMain"; implementationClass = "DockerMain"
            description = "Docker needed tasks for root multi-project"
        }
    }

}

pluginBundle {
    website = "https://github.com/steklopod/gradle-docker-main-plugin"
    vcsUrl = "https://github.com/steklopod/gradle-docker-main-plugin"

    (plugins) {
        "dockerMainPlugin" {
            displayName = "Docker needed tasks for root multi-project"
            tags = listOf("docker", "kotlin", "build.gradle.kts")
            version = "0.1.2"
        }

    }
}

dependencies{
    implementation("org.hidetake:groovy-ssh:2.10.1")
}

configure<JavaPluginConvention> { sourceCompatibility = VERSION_11; targetCompatibility = VERSION_11 }

tasks{
    withType<Wrapper> { gradleVersion = "6.0" }
    withType<KotlinCompile> { kotlinOptions { jvmTarget = "11" } }
}

defaultTasks("tasks", "publishPlugins")

