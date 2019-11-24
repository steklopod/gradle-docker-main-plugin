# Gradle `dockerMain` plugin  [![Build Status](https://travis-ci.org/steklopod/gradle-docker-main-plugin.svg?branch=master)](https://travis-ci.org/steklopod/gradle-docker-main-plugin)

🛡️ Gradle `dockerMain` plugin for root multi-project

### Structure must be
```shell script
 root
    |-|backend/
    |  - build/libs/*.jar
    |  - build.gradle.kts
    |  - docker-compose.yml
    |  - docker-compose.dev.yml (optional)
    |
    |-|frontend/
    |  - |dist/
    |  - build.gradle.kts
    |  - docker-compose.yml
    |  - docker-compose.dev.yml (optional)
    |
    |-|nginx/
    |   - build.gradle.kts
    |   - docker-compose.yml
    |   - default.conf
    |   - domain.crt (optional)
    |   - domain.key (optional)
    |   - docker-compose.dev.yml (optional)
```

### Quick start
1. You only need to have `docker-compose.yml` file in root of project
2. In your `build.gradle.kts` file:
```kotlin
plugins {
     id("online.colaba.dockerMain") version "0.1.7"
}
```

### Rerun/start 🎯
```shell script
./gradlew -q
```

### Available gradle tasks for `dockerMain` plugin`:

> Name of service for all tasks equals to ${project.name} 

* `removeBackAndFront` - remove containers
* `removeAll` - remove all containers

* `compose` - docker compose up all docker-services with recreate and rebuild
* `composeNginx`, `composeBack`, `composeFront` - compose up with recreate and rebuild

* `prune` - remove unused data

* `recomposeAll` - compose up after removing current docker-service

___
##### Optional

> `docker-compose.dev.yml`, `Dockerfile` & `Dockerfile.dev` files are optionals.

Optional tasks: 

* `composeDev` - compose up all docker-services from `docker-compose.dev.yml` file with recreate and rebuild. 
Depends on `backend:assemble`;
* `recomposeAllDev` - compose up after removing current docker-service from `docker-compose.dev.yml` file. 

___
* `backend`'s **jar** distribution path: `${project.rootDir}/backend/build/libs/*.jar`

* `frontend`'s **dist** folder path: `${project.rootDir}/frontend/dist`
