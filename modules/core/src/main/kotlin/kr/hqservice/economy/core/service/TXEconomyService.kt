package kr.hqservice.economy.core.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kr.hqservice.economy.api.Balance
import kr.hqservice.economy.api.exception.CurrencyNotFoundException
import kr.hqservice.economy.api.service.EconomyService
import kr.hqservice.economy.core.entity.EconomyAccountEntity
import kr.hqservice.economy.core.entity.EconomyCurrencyEntity
import kr.hqservice.economy.core.repository.EconomyBalanceRepository
import kr.hqservice.economy.core.repository.EconomyCurrencyRepository
import kr.hqservice.economy.core.repository.EconomyLogRepository
import kr.hqservice.framework.global.core.component.Service
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.time.LocalDateTime
import java.util.*

@Service
class TXEconomyService(
    private val balanceRepository: EconomyBalanceRepository,
    private val currencyRepository: EconomyCurrencyRepository,
    private val logRepository: EconomyLogRepository
) : EconomyService {
    override suspend fun getBalance(accountId: UUID, currencyName: String): Balance {
        return newSuspendedTransaction(Dispatchers.IO) {
            val currencyEntity = currencyRepository
                .findByCurrencyName(currencyName) ?: throw CurrencyNotFoundException(currencyName)
            val balanceEntity = getOrCreateAccountEntity(accountId, currencyEntity)
            Balance(currencyEntity.toDto(), balanceEntity.balance)
        }
    }

    override suspend fun getBalances(accountId: UUID): List<Balance> {
        return newSuspendedTransaction(Dispatchers.IO) {
            balanceRepository.findByAccountIdForUpdate(accountId).map { accountEntity ->
                Balance(accountEntity.currencyId.toDto(), accountEntity.balance)
            }
        }
    }

    override suspend fun withdraw(accountId: UUID, currencyName: String, amount: Long): Balance {
        return newSuspendedTransaction(Dispatchers.IO) {
            val currencyEntity = currencyRepository
                .findByCurrencyName(currencyName) ?: throw CurrencyNotFoundException(currencyName)
            val balanceEntity = getOrCreateAccountEntity(accountId, currencyEntity)

            balanceEntity.balance -= amount
            logTransaction(accountId, currencyEntity.id.value, -amount)
            Balance(currencyEntity.toDto(), balanceEntity.balance)
        }
    }

    override suspend fun deposit(accountId: UUID, currencyName: String, amount: Long): Balance {
        return newSuspendedTransaction(Dispatchers.IO) {
            val currencyEntity = currencyRepository
                .findByCurrencyName(currencyName) ?: throw CurrencyNotFoundException(currencyName)
            val balanceEntity = getOrCreateAccountEntity(accountId, currencyEntity)

            balanceEntity.balance += amount
            logTransaction(accountId, currencyEntity.id.value, amount)
            Balance(currencyEntity.toDto(), balanceEntity.balance)
        }
    }

    private suspend fun getOrCreateAccountEntity(
        accountId: UUID,
        currencyEntity: EconomyCurrencyEntity
    ): EconomyAccountEntity {
        return newSuspendedTransaction(Dispatchers.IO) {
            balanceRepository.findByAccountIdAndCurrencyIdForUpdate(accountId, currencyEntity.id.value)
                ?: balanceRepository.new {
                    this.accountId = accountId
                    this.currencyId = currencyEntity
                }
        }
    }

    private suspend fun logTransaction(accountId: UUID, currencyId: Int, amount: Long) {
        coroutineScope {
            launch(Dispatchers.IO) {
                logRepository.new {
                    this.accountId = accountId
                    this.currencyId = currencyId
                    this.amount = amount
                    this.transactedAt = LocalDateTime.now()
                }
            }
        }
    }
}