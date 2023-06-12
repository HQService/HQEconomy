package kr.hqservice.economy.command.providers

import kr.hqservice.economy.command.argument.BankPlayer
import kr.hqservice.framework.bukkit.core.extension.colorize
import kr.hqservice.framework.command.component.HQCommandArgumentProvider
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.netty.api.NettyServer
import org.bukkit.Location
import org.bukkit.command.CommandSender

@Component
class BankPlayerArgumentProvider(
    private val netty: NettyServer
) : HQCommandArgumentProvider<BankPlayer> {
    override fun cast(string: String): BankPlayer {
        return BankPlayer(
            string,
            netty.getPlayer(string)
        )
    }

    override fun getFailureMessage(commandSender: CommandSender, string: String?, argumentLabel: String?): String {
        return if(string == null) "&c플레이어의 이름을 입력하세요.".colorize()
        else "&c$string 플레이어를 찾을 수 없습니다."
    }

    override fun getResult(commandSender: CommandSender, string: String?): Boolean {
        return true
    }

    override fun getTabComplete(
        commandSender: CommandSender,
        location: Location?,
        argumentLabel: String?,
    ): List<String> {
        return netty.getPlayers().map { it.getName() }
    }

}