package kr.hqservice.economy.command.providers.entity

import kr.hqservice.framework.bukkit.core.netty.service.HQNettyService
import kr.hqservice.framework.global.core.component.registry.MutableNamedProvider
import kr.hqservice.framework.global.core.component.registry.QualifierProvider

@QualifierProvider(key = "hqeconomy.server.type")
class EconomyServerProvider(
    private val nettyService: HQNettyService
) : MutableNamedProvider {
    override fun provideQualifier(): String {
        return if(nettyService.isEnable()) "netty" else "bukkit"
    }
}