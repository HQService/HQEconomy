package kr.hqservice.economy.service.impl

import kr.hqservice.economy.Economy
import kr.hqservice.economy.service.EconomyService
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQService
import kr.hqservice.framework.global.core.component.HQSingleton
import org.bukkit.Server
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.ServicePriority

@Component
@HQSingleton(binds = [EconomyService::class])
class EconomyServiceImpl(
    private val plugin: Plugin,
    private val server: Server,
    private val economy: Economy
) : EconomyService, HQService {
    override fun hook() {
        server.servicesManager.register(Economy::class.java, economy, plugin, ServicePriority.Normal)
    }

    override fun unhook() {
        server.servicesManager.unregister(Economy::class.java, economy)
    }
}