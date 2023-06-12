plugins {
    `maven-publish`
    kotlin("jvm") version "1.8.0"
}

group = "kr.hqservice"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()

    maven("https://repo.papermc.io/repository/maven-public/")
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

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "kr.hqservice"
            artifactId = "HQEconomy"
            version = "1.0.0-SNAPSHOT"

            from(components["java"])
        }
    }
}