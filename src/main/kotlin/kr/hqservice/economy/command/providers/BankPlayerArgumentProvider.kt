package kr.hqservice.economy.command.providers

import kr.hqservice.economy.command.argument.BankPlayer
import kr.hqservice.economy.command.providers.entity.EconomyServer
import kr.hqservice.framework.command.CommandArgumentProvider
import kr.hqservice.framework.command.CommandContext
import kr.hqservice.framework.command.argument.exception.ArgumentFeedback
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.registry.MutableNamed
import org.bukkit.Location

@Component
class BankPlayerArgumentProvider(
    @MutableNamed(key = "economy.server.type") private val server: EconomyServer
) : CommandArgumentProvider<BankPlayer> {
    override suspend fun cast(context: CommandContext, argument: String?): BankPlayer {
        argument ?: throw ArgumentFeedback.RequireArgument
        val economyPlayer = server.getPlayer(argument) ?: throw ArgumentFeedback.Message("$argument 플레이어를 찾을 수 없습니다.")
        return BankPlayer(argument, economyPlayer)
    }

    override suspend fun getTabComplete(context: CommandContext, location: Location?): List<String> {
        return server.getPlayers().map { it.getName() }
    }
}