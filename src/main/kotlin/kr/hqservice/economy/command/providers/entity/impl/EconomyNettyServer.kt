package kr.hqservice.economy.command.providers.entity.impl

import kr.hqservice.economy.command.providers.entity.EconomyPlayer
import kr.hqservice.economy.command.providers.entity.EconomyServer
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQSingleton
import kr.hqservice.framework.netty.api.NettyServer
import javax.inject.Named

@Named("netty")
@Component
@HQSingleton(binds = [EconomyServer::class])
class EconomyNettyServer(
    private val netty: NettyServer
) : EconomyServer {
    override fun getPlayers(): List<EconomyPlayer> {
        return netty.getPlayers().map { EconomyPlayerImpl(it.getName(), it) }
    }

    override fun getPlayer(name: String): EconomyPlayer? {
        return netty.getPlayer(name)?.run { EconomyPlayerImpl(getName(), this) }
    }
}