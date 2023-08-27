plugins {
    `maven-publish`
    kotlin("jvm") version "1.7.21"
}

group = "kr.hqservice.economy"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.hqservice.kr/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    mavenLocal()
}

dependencies {
    compileOnly("io.papermc.paper", "paper-api", "1.19.4-R0.1-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-bukkit-core", "1.0.0-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-global-netty", "1.0.0-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-bukkit-command", "1.0.0-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-bukkit-database", "1.0.0-SNAPSHOT")
    compileOnly("kr.hqservice", "hqframework-bukkit-coroutine", "1.0.0-SNAPSHOT")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}

file(rootProject.gradle.rootProject.projectDir.path + "/credentials.gradle.kts").let {
    if (it.exists()) {
        apply(it.path)
    }
}

publishing {
    publications {
        create<MavenPublication>("nexus") {
            groupId = "kr.hqservice.economy"
            artifactId = "HQEconomy"
            version = "1.0.0-SNAPSHOT"

            from(components["java"])

            pom {
                name.set("HQEconomy")
                url.set("https://github.com/HQService/HQEconomy")
            }
        }
    }
    repositories {
        maven("https://maven.hqservice.kr/repository/maven-private/") {
            credentials {
                username = extra["nexusUsername"]!!.toString()
                password = extra["nexusPassword"]!!.toString()
            }
        }
    }
}