plugins {
    id("hq.shared")
    `maven-publish`
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(framework.core)
}

publishing {
    publications {
        create<MavenPublication>("nexus") {
            groupId = extra["projectGroup"].toString().lowercase()
            artifactId = extra["projectName"].toString().lowercase()
            version = extra["projectVersion"].toString()
            from(components["java"])

            pom {
                name.set(extra["projectName"].toString())
                url.set(extra["projectUrl"].toString())
            }
        }
    }
    repositories {
        maven("https://maven.hqservice.kr/repository/maven-releases/") {
            credentials {
                if (extra.has("nexusUsername") && extra.has("nexusPassword")) {
                    username = extra["nexusUsername"].toString()
                    password = extra["nexusPassword"].toString()
                } else if (System.getenv("nexusUsername") != null && System.getenv("nexusPassword") != null) {
                    username = System.getenv("nexusUsername")
                    password = System.getenv("nexusPassword")
                }
            }
        }
    }
}