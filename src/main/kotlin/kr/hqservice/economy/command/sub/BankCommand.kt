package kr.hqservice.economy.command.sub

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.hqservice.economy.api.Economy
import kr.hqservice.economy.command.EconomyCommand
import kr.hqservice.economy.command.argument.BankPlayer
import kr.hqservice.framework.command.ArgumentLabel
import kr.hqservice.framework.command.Command
import kr.hqservice.framework.command.CommandExecutor
import org.bukkit.command.CommandSender

@Command(EconomyCommand::class)
class BankCommand(
    private val economy: Economy,
    private val coroutineScope: CoroutineScope,
) {
    @CommandExecutor(
        label = "add",
        description = "&f/eco add &8<플레이어> <금액> &6| &7해당 플레이어에게 돈을 추가합니다.",
        isOp = true,
        priority = 1
    )
    fun giveMoney(sender: CommandSender, target: BankPlayer, @ArgumentLabel("금액") amount: Long) {
        if(amount <= 0) {
            sender.sendMessage("§c머함 ?")
        } else {
            coroutineScope.launch(Dispatchers.IO) {
                economy.depositPlayer(target.playerName, amount)
                sender.sendMessage("${target.playerName} 님한테 $amount 원 입금함")
                target.playerInstance?.sendMessage("돈 입금됨 $amount")
            }
        }
    }
}