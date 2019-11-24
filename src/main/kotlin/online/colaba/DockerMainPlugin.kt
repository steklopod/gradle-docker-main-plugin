package online.colaba

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class DockerMainPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = project.run {
        val removeGroup = "remove"
        description = "Docker needed tasks for root multi-project"

        registerExecutorTask()
        registerDockerComposeTask()

        tasks {
            val ps by registering(DockerCompose::class) { containers() }

            compose { finalizedBy(ps) }

            register("prune", Docker::class) { exec = "system prune -fa" }

            val removeBackAndFront by registering(Docker::class) {
                dependsOn(":$frontendService:$removeGroup"); finalizedBy(":$backendService:$removeGroup")
            }

            val removeAll by registering(Docker::class) {
                dependsOn(":$nginxService:$removeGroup"); finalizedBy(removeBackAndFront)
            }

            val composeDev by registering(DockerCompose::class) {
                dependsOn(":$backendService:assemble"); isDev = true
            }

            register("composeNginx", DockerCompose::class) { service = nginxService }
            register("composeBack", DockerCompose::class) { service = backendService }
            register("composeFront", DockerCompose::class) { service = frontendService }

            register("recomposeAll", DockerCompose::class) { dependsOn(removeAll); finalizedBy(compose) }
            register("recomposeAllDev", DockerCompose::class) { dependsOn(removeAll); finalizedBy(composeDev) }
        }
    }
}

