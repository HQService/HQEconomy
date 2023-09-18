plugins {
    id("hq.shared")
}

dependencies {
    api(project(":modules:api"))
    api(project(":modules:core"))

    compileOnly(libs.spigot.api)
    compileOnly(framework.core)
    compileOnly(framework.inventory)
    compileOnly(framework.command)
    compileOnly(framework.database)
    compileOnly(framework.scheduler)
    compileOnly(framework.nms)
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
}