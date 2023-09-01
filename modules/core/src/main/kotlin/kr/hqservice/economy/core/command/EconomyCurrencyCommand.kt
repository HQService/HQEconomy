package kr.hqservice.economy.core.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.api.registry.CurrencyRegistry
import kr.hqservice.framework.bukkit.core.extension.sendColorizedMessage
import kr.hqservice.framework.command.ArgumentLabel
import kr.hqservice.framework.command.Command
import kr.hqservice.framework.command.CommandExecutor
import org.bukkit.command.CommandSender

@Command(label = "currency", parent = EconomyCommand::class)
class EconomyCurrencyCommand(private val currencyRegistry: CurrencyRegistry) {
    @CommandExecutor(
        label = "create",
        description = "재화를 등록합니다."
    )
    suspend fun executeCreate(
        sender: CommandSender,
        @ArgumentLabel("재화 이름") currencyName: String,
        @ArgumentLabel("재화 표기 이름") displayName: String?
    ) {
        withContext(Dispatchers.IO) {
            currencyRegistry.createByName(currencyName, displayName)
        }
        sender.sendColorizedMessage("&a재화 $currencyName 을(를) 성공적으로 생성하였습니다.")
    }

    @CommandExecutor(
        label = "list",
        description = "현재 등록된 재화 목록을 확인합니다."
    )
    suspend fun executeList(
        sender: CommandSender
    ) {
        val currencies = withContext(Dispatchers.IO) {
            currencyRegistry.getCurrencyAll()
        }
        if (currencies.isEmpty()) {
            sender.sendColorizedMessage("&c현재 등록된 재화가 없습니다.")
            return
        }
        sender.sendColorizedMessage("&a등록된 재화 목록:")
        currencies.forEach { currency ->
            if (currency.displayName == null) {
                sender.sendColorizedMessage("&f - ${currency.name}")
            } else {
                sender.sendColorizedMessage("&f - ${currency.name} &7(${currency.displayName})")
            }
        }
    }

    @CommandExecutor(
        label = "harddelete",
        description = "서버에서 재화가 존재하지 않았던 것처럼 완벽히 삭제합니다."
    )
    suspend fun executeHardDelete(
        sender: CommandSender,
        @ArgumentLabel("재화") currency: Currency
    ) {
        withContext(Dispatchers.IO) {
            currencyRegistry.deleteByName(currency.name)
        }
        sender.sendColorizedMessage("&c재화 ${currency.name} 을(를) 서버에서 삭제하였습니다.")
    }
}