package kr.hqservice.economy.command.sub

import kotlinx.coroutines.launch
import kr.hqservice.economy.Economy
import kr.hqservice.economy.command.EconomyCommand
import kr.hqservice.economy.command.argument.BankPlayer
import kr.hqservice.economy.coroutine.EconomyCoroutineScope
import kr.hqservice.framework.command.component.ArgumentLabel
import kr.hqservice.framework.command.component.CommandExecutor
import kr.hqservice.framework.command.component.HQCommandNode
import kr.hqservice.framework.command.component.ParentCommand
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.netty.extension.sendMessage
import org.bukkit.command.CommandSender
import org.koin.core.annotation.Named

@Component
@ParentCommand(binds = [EconomyCommand::class])
class BankCommand(
    private val economy: Economy,
    @Named("bank") private val bankScope: EconomyCoroutineScope
) : HQCommandNode() {
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
            bankScope.launch {
                economy.depositPlayer(target.playerName, amount)
                sender.sendMessage("${target.playerName} 님한테 $amount 원 입금함")
                target.nettyPlayer?.sendMessage("님아 돈 입금댐 ㅋㅋ +${amount}", false)
            }
        }
    }
}