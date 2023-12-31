package kr.hqservice.economy.core.command

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.api.service.EconomyService
import kr.hqservice.framework.bukkit.core.extension.sendColorizedMessage
import kr.hqservice.framework.command.ArgumentLabel
import kr.hqservice.framework.command.Command
import kr.hqservice.framework.command.CommandExecutor
import kr.hqservice.framework.netty.api.NettyPlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.text.DecimalFormat

@Command(label = "eco", isOp = true)
class EconomyCommand(private val economyService: EconomyService) {
    companion object {
        private val format = DecimalFormat("#,###")
    }

    @CommandExecutor(
        label = "deposit",
        description = "해당 플레이어에게 돈을 추가합니다."
    )
    suspend fun executeDeposit(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer,
        @ArgumentLabel("재화") currency: Currency,
        @ArgumentLabel("금액") amount: Long
    ) {
        if (amount <= 0) {
            sender.sendColorizedMessage("0보다 큰 수를 입력해주세요.")
            return
        }
        withContext(Dispatchers.IO) {
            economyService.deposit(target.getUniqueId(), currency.name, amount)
        }
        sender.sendColorizedMessage("&a성공적으로 ${target.getName()} 님의 ${currency.displayName ?: currency.name}을(를) ${amount}만큼 추가 하였습니다.")
        target.sendColorizedMessage("&a계좌에 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 추가 되었습니다.")
    }

    @CommandExecutor(
        label = "withdraw",
        description = "해당 플레이어에게서 돈을 차감합니다."
    )
    suspend fun executeWithdraw(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer,
        @ArgumentLabel("재화") currency: Currency,
        @ArgumentLabel("금액") amount: Long
    ) {
        if (amount <= 0) {
            sender.sendColorizedMessage("0보다 큰 수를 입력해주세요.")
            return
        }
        withContext(Dispatchers.IO) {
            economyService.withdraw(target.getUniqueId(), currency.name, amount)
        }
        sender.sendColorizedMessage("&a성공적으로 ${target.getName()} 님의 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 차감 되었습니다.")
        target.sendColorizedMessage("&a계좌에서 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 차감 되었습니다.")
    }

    @CommandExecutor(
        label = "getBalance",
        description = "대상이 해당 재화를 얼만큼 가지고 있는지 확인합니다."
    )
    suspend fun executeGetBalance(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer,
        @ArgumentLabel("재화") currency: Currency,
        @ArgumentLabel("타겟") viewer: Player?
    ) {
        val balance = withContext(Dispatchers.IO) {
            economyService.getBalance(target.getUniqueId(), currency.name)
        }

        if (viewer != null) {
            viewer.sendColorizedMessage("&7 ${target.getName()}&7 님의 ${balance.currency.displayName}: &e${format.format(balance.balance)}")
        } else sender.sendColorizedMessage("&7 ${target.getName()}&7 님의 ${balance.currency.displayName}: &e${format.format(balance.balance)}")
    }

    @CommandExecutor(
        label = "getBalances",
        description = "대상의 모든 재화를 확인합니다."
    )
    suspend fun executeGetBalances(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer,
        @ArgumentLabel("타겟") viewer: Player?
    ) {
        val balances = withContext(Dispatchers.IO) {
            economyService.getBalances(target.getUniqueId())
        }
        if (balances.isEmpty()) {
            sender.sendColorizedMessage("&c현재 플레이어 ${target.getName()}님은 아무 재화도 보유하고 있지 않습니다.")
            return
        }
        val view = viewer ?: sender
        view.sendColorizedMessage("&f플레이어 ${target.getName()} 님의 계좌:")
        balances.forEach { balance ->
            if (balance.currency.displayName != null) {
                view.sendColorizedMessage(" &7- ${balance.currency.displayName}:&e ${format.format(balance.balance)}")
            } else {
                view.sendColorizedMessage(" &7- ${balance.currency.displayName}: &e${format.format(balance.balance)}")
            }
        }
    }
}