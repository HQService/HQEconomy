plugins {
    id("hq.shared")
    `maven-publish`
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(framework.core)
}

file(rootProject.gradle.rootProject.projectDir.path + "/credentials.gradle.kts").let {
    if (it.exists()) {
        apply(it.path)
    }
}

publishing {
    publications {
        create<MavenPublication>("nexus") {
            groupId = project.extra["projectGroup"].toString().lowercase()
            artifactId = project.extra["projectName"].toString().lowercase()
            version = project.extra["projectVersion"].toString()
            from(components["java"])

            pom {
                name.set(project.extra["projectName"].toString())
                url.set(project.extra["projectUrl"].toString())
            }
        }
    }
    repositories {
        maven("https://maven.hqservice.kr/repository/maven-releases/") {
            credentials {
                if (project.extra.has("nexusUsername") && project.extra.has("nexusPassword")) {
                    username = project.extra["nexusUsername"].toString()
                    password = project.extra["nexusPassword"].toString()
                } else if (System.getenv("nexusUsername") != null && System.getenv("nexusPassword") != null) {
                    username = System.getenv("nexusUsername")
                    password = System.getenv("nexusPassword")
                }
            }
        }
    }
}