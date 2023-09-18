plugins {
    id("hq.shared")
    id("hq.shadow")
    id("kr.hqservice.resource-generator.bukkit")
}

bukkitResourceGenerator {
    main = "kr.hqservice.economy.HQEconomyPlugin"
    name = "HQEconomy"
    apiVersion = "1.17"
    depend = listOf("HQFramework")
    softDepend = listOf("Vault")
}

dependencies {
    compileOnly(libs.spigot.api)
    compileOnly(framework.core)
    runtimeOnly(project(":modules:core"))
    runtimeOnly(project(":modules:api"))
    runtimeOnly(project(":modules:hook-vault"))
}