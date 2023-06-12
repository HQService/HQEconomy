package kr.hqservice.economy.hanlder

import kr.hqservice.economy.Economy
import kr.hqservice.economy.coroutine.EconomyCoroutineScope
import kr.hqservice.economy.database.entity.impl.EconomyBankEntity
import kr.hqservice.economy.database.repository.DatabaseRepository
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQService
import kr.hqservice.framework.global.core.component.HQSingleton
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.koin.core.annotation.Named
import java.util.*

@Component
@HQSingleton(binds = [Economy::class])
class EconomyHandler(
    private val server: Server,
    @Named("log") private val logCoroutineScope: EconomyCoroutineScope,
    @Named("economy") private val bankRepository: DatabaseRepository<EconomyBankEntity>
) : Economy, HQService {
    override suspend fun getBalance(playerName: String): Long {
        return getWithBankAction(playerName, server::getOfflinePlayer) {
            balance
        }
    }

    override suspend fun getBalance(uniqueId: UUID): Long {
        return bankRepository.get(uniqueId).balance
    }

    override suspend fun getBalance(player: OfflinePlayer): Long {
        return getWithBankAction(player) {
            balance
        }
    }

    override suspend fun has(playerName: String, amount: Long): Boolean {
        return getWithBankAction(playerName, server::getOfflinePlayer) {
            amount <= balance
        }
    }

    override suspend fun has(uniqueId: UUID, amount: Long): Boolean {
        return amount <= bankRepository.get(uniqueId).balance
    }

    override suspend fun has(player: OfflinePlayer, amount: Long): Boolean {
        return getWithBankAction(player) {
            amount <= balance
        }
    }

    override suspend fun withdrawPlayer(playerName: String, amount: Long) {
        val uniqueId = server.getOfflinePlayer(playerName).uniqueId
        bankRepository.updateById(uniqueId) {
            balance -= amount
        }
    }

    override suspend fun withdrawPlayer(uniqueId: UUID, amount: Long) {
        bankRepository.updateById(uniqueId) {
            balance -= amount
        }
    }

    override suspend fun withdrawPlayer(player: OfflinePlayer, amount: Long) {
        val uniqueId = player.uniqueId
        bankRepository.updateById(uniqueId) {
            balance -= amount
        }
    }

    override suspend fun depositPlayer(playerName: String, amount: Long) {
        val uniqueId = server.getOfflinePlayer(playerName).uniqueId
        bankRepository.updateById(uniqueId) {
            balance += amount
        }
    }

    override suspend fun depositPlayer(uniqueId: UUID, amount: Long) {
        bankRepository.updateById(uniqueId) {
            balance += amount
        }
    }

    override suspend fun depositPlayer(player: OfflinePlayer, amount: Long) {
        val uniqueId = player.uniqueId
        /*logCoroutineScope.launch {

        }*/
        bankRepository.updateById(uniqueId) {
            balance += amount
        }
    }

    private suspend fun <T, R> getWithBankAction(obj: T, getPlayerBlock: (T) -> OfflinePlayer = { it as OfflinePlayer }, action: EconomyBankEntity.() -> R): R {
        val offlinePlayer = getPlayerBlock(obj)
        val uniqueId = offlinePlayer.uniqueId
        return action(bankRepository.get(uniqueId))
    }
}