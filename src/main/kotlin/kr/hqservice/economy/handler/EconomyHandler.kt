package kr.hqservice.economy.handler

import kr.hqservice.economy.api.Economy
import kr.hqservice.economy.database.entity.impl.EconomyBankEntity
import kr.hqservice.economy.database.repository.EconomyBankRepository
import kr.hqservice.framework.global.core.component.Service
import org.bukkit.OfflinePlayer
import org.bukkit.Server
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

@Service
class EconomyHandler(
    private val server: Server,
    private val bankRepository: EconomyBankRepository
) : Economy {
    override suspend fun getBalance(playerName: String): Long {
        return newSuspendedTransaction {
            getWithBankAction(playerName, server::getOfflinePlayer) {
                balance
            }
        }
    }

    override suspend fun getBalance(uniqueId: UUID): Long {
        return newSuspendedTransaction {
            bankRepository.getOrCreate(uniqueId).balance
        }
    }

    override suspend fun getBalance(player: OfflinePlayer): Long {
        return newSuspendedTransaction {
            getWithBankAction(player) {
                balance
            }
        }
    }

    override suspend fun has(playerName: String, amount: Long): Boolean {
        return newSuspendedTransaction {
            getWithBankAction(playerName, server::getOfflinePlayer) {
                amount <= balance
            }
        }
    }

    override suspend fun has(uniqueId: UUID, amount: Long): Boolean {
        return newSuspendedTransaction {
            amount <= bankRepository.getOrCreate(uniqueId).balance
        }
    }

    override suspend fun has(player: OfflinePlayer, amount: Long): Boolean {
        return newSuspendedTransaction {
            getWithBankAction(player) {
                amount <= balance
            }
        }
    }

    override suspend fun withdrawPlayer(playerName: String, amount: Long) {
        val uniqueId = server.getOfflinePlayer(playerName).uniqueId
        newSuspendedTransaction {
            bankRepository.updateById(uniqueId) {
                balance -= amount
            }
        }
    }

    override suspend fun withdrawPlayer(uniqueId: UUID, amount: Long) {
        newSuspendedTransaction {
            bankRepository.updateById(uniqueId) {
                balance -= amount
            }
        }
    }

    override suspend fun withdrawPlayer(player: OfflinePlayer, amount: Long) {
        val uniqueId = player.uniqueId
        newSuspendedTransaction {
            bankRepository.updateById(uniqueId) {
                balance -= amount
            }
        }
    }

    override suspend fun depositPlayer(playerName: String, amount: Long) {
        val uniqueId = server.getOfflinePlayer(playerName).uniqueId
        bankRepository.updateById(uniqueId) {
            balance += amount
        }
    }

    override suspend fun depositPlayer(uniqueId: UUID, amount: Long) {
        newSuspendedTransaction {
            bankRepository.updateById(uniqueId) {
                balance += amount
            }
        }
    }

    override suspend fun depositPlayer(player: OfflinePlayer, amount: Long) {
        val uniqueId = player.uniqueId
        /*logCoroutineScope.launch {

        }*/
        newSuspendedTransaction {
            bankRepository.updateById(uniqueId) {
                balance += amount
            }
        }
    }

    private fun <T, R> getWithBankAction(obj: T, getPlayerBlock: (T) -> OfflinePlayer = { it as OfflinePlayer }, action: EconomyBankEntity.() -> R): R {
        val offlinePlayer = getPlayerBlock(obj)
        val uniqueId = offlinePlayer.uniqueId
        return action(bankRepository.getOrCreate(uniqueId))
    }
}