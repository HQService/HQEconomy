package kr.hqservice.economy.command.providers.entity.impl

import kr.hqservice.economy.command.providers.entity.EconomyPlayer
import kr.hqservice.framework.netty.api.NettyPlayer
import kr.hqservice.framework.netty.extension.sendMessage
import org.bukkit.entity.Player

class EconomyPlayerImpl(
    private val name: String,
    private val instance: Any
) : EconomyPlayer {
    override fun getName(): String {
        return name
    }

    override fun sendMessage(message: String) {
        when(instance) {
            is Player -> instance.sendMessage(message)
            is NettyPlayer -> instance.sendMessage(message, false)
        }
    }
}