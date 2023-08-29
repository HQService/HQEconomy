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
import kr.hqservice.framework.netty.extension.sendMessage
import org.bukkit.command.CommandSender

@Command(label = "eco", isOp = true)
class EconomyCommand(private val economyService: EconomyService) {
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
        sender.sendMessage("성공적으로 ${target.getName()} 님의 ${currency.displayName ?: currency.name}을(를) ${amount}만큼 추가 하였습니다.")
        target.sendMessage("&a계좌에 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 추가 되었습니다.")
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
        sender.sendMessage("&a성공적으로 ${target.getName()} 님의 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 차감 되었습니다.")
        target.sendMessage("&a계좌에서 ${currency.displayName ?: currency.name}이(가) ${amount}만큼 차감 되었습니다.")
    }

    @CommandExecutor(
        label = "getBalance",
        description = "대상이 해당 재화를 얼만큼 가지고 있는지 확인합니다."
    )
    suspend fun executeGetBalance(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer,
        @ArgumentLabel("재화") currency: Currency
    ) {
        val balance = withContext(Dispatchers.IO) {
            economyService.getBalance(target.getUniqueId(), currency.name)
        }

        sender.sendColorizedMessage("&a플레이어 ${target.getName()} 님은 재화 ${balance.currency.name}을(를) ${balance.balance} 만큼 지니고 있습니다.")
    }

    @CommandExecutor(
        label = "getBalances",
        description = "대상의 모든 재화를 확인합니다."
    )
    suspend fun executeGetBalances(
        sender: CommandSender,
        @ArgumentLabel("대상") target: NettyPlayer
    ) {
        val balances = withContext(Dispatchers.IO) {
            economyService.getBalances(target.getUniqueId())
        }
        if (balances.isEmpty()) {
            sender.sendColorizedMessage("&c현재 플레이어 ${target.getName()}님은 아무 재화도 보유하고 있지 않습니다.")
            return
        }
        sender.sendColorizedMessage("&a플레이어 ${target.getName()} 님의 계좌:")
        balances.forEach { balance ->
            if (balance.currency.displayName != null) {
                sender.sendColorizedMessage(" &f- ${balance.currency.name} &7(${balance.currency.displayName})&a: ${balance.balance}")
            } else {
                sender.sendColorizedMessage(" &f- ${balance.currency.name}: ${balance.balance}")
            }
        }
    }
}