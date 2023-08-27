package kr.hqservice.economy.command.providers.entity.impl

import kr.hqservice.economy.command.providers.entity.EconomyPlayer
import kr.hqservice.economy.command.providers.entity.EconomyServer
import kr.hqservice.framework.global.core.component.Bean
import kr.hqservice.framework.global.core.component.Qualifier
import org.bukkit.Server

@Bean
@Qualifier("economy.server.bukkit")
class EconomyBukkitServer(
    private val bukkitServer: Server
) : EconomyServer {
    override fun getPlayers(): List<EconomyPlayer> {
        return bukkitServer.onlinePlayers.map { EconomyPlayerImpl(it.name, it) }
    }

    override fun getPlayer(name: String): EconomyPlayer? {
        return bukkitServer.getPlayer(name)?.run { EconomyPlayerImpl(getName(), this) }
    }
}