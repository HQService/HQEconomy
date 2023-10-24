package kr.hqservice.economy.core.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.api.registry.CurrencyRegistry
import kr.hqservice.economy.core.repository.EconomyCurrencyRepository
import kr.hqservice.framework.bukkit.core.extension.sendColorizedMessage
import kr.hqservice.framework.command.ArgumentLabel
import kr.hqservice.framework.command.Command
import kr.hqservice.framework.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Command(label = "currency", parent = EconomyCommand::class)
class EconomyCurrencyCommand(
    private val currencyRegistry: CurrencyRegistry,
    private val currencyRepository: EconomyCurrencyRepository
) {
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
        sender.sendColorizedMessage("&a재화 ${currencyName}을(를) 성공적으로 생성하였습니다.")
    }

    @CommandExecutor(
        label = "setDisplay",
        description = "재화의 표기 이름을 설정합니다."
    )
    suspend fun executeSetDisplay(
        sender: CommandSender,
        @ArgumentLabel("재화") currency: Currency,
        @ArgumentLabel("재화 표기 이름") displayName: String
    ) {
        withContext(Dispatchers.IO) {
            newSuspendedTransaction {
                currencyRepository.findByCurrencyName(currency.name)?.displayName = displayName
            }
        }
        sender.sendColorizedMessage("&a재화 ${currency.name} 의 표기 이름을 ${displayName}으로 설정하였습니다.")
    }

    @CommandExecutor(
        label = "setDefault",
        description = "서버의 기본 재화로 설정 합니다."
    )
    suspend fun executeSetDefault(
        sender: CommandSender,
        @ArgumentLabel("재화") currency: Currency
    ) {
        withContext(Dispatchers.IO) {
            newSuspendedTransaction {
                currencyRepository.getDefault().isDefault = false
                currencyRepository.findByCurrencyName(currency.name)?.isDefault = true
                sender.sendColorizedMessage("&a재화 ${currency.name}이(가) 기본 재화로 설정되었습니다.")
            }
        }
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
        if (currency.isDefault) {
            sender.sendColorizedMessage("&c기본으로 설정된 재화는 삭제할 수 없습니다.")
            return
        }
        withContext(Dispatchers.IO) {
            currencyRegistry.deleteByName(currency.name)
        }
        sender.sendColorizedMessage("&c재화 ${currency.name} 을(를) 서버에서 삭제하였습니다.")
    }
}