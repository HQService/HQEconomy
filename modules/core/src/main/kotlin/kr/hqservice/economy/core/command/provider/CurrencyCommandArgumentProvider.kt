package kr.hqservice.economy.core.command.provider

import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.api.registry.CurrencyRegistry
import kr.hqservice.framework.command.CommandArgumentProvider
import kr.hqservice.framework.command.CommandContext
import kr.hqservice.framework.command.argument.exception.ArgumentFeedback
import kr.hqservice.framework.global.core.component.Component
import org.bukkit.Location

@Component
class CurrencyCommandArgumentProvider(private val currencyRegistry: CurrencyRegistry) : CommandArgumentProvider<Currency> {
    override suspend fun cast(context: CommandContext, argument: String?): Currency {
        argument ?: throw ArgumentFeedback.RequireArgument
        return currencyRegistry.findCurrencyByName(argument)
            ?: throw ArgumentFeedback.Message("&c${argument} 이름의 currency 가 존재하지 않습니다.")
    }

    override suspend fun getTabComplete(context: CommandContext, location: Location?): List<String> {
        return currencyRegistry.getCurrencyAll().map { it.name }
    }
}