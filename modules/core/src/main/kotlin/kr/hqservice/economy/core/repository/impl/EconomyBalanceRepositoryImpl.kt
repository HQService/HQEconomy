package kr.hqservice.economy.core.repository.impl

import kr.hqservice.economy.core.entity.EconomyAccountEntity
import kr.hqservice.economy.core.entity.EconomyAccountTable
import kr.hqservice.economy.core.repository.EconomyBalanceRepository
import kr.hqservice.framework.database.repository.Repository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.util.*

@Repository
class EconomyBalanceRepositoryImpl : EconomyBalanceRepository {
    override suspend fun count(): Long {
        return EconomyAccountEntity.count()
    }

    override suspend fun delete(entity: EconomyAccountEntity) {
        newSuspendedTransaction {
            entity.delete()
        }
    }

    override suspend fun deleteById(id: Int) {
        newSuspendedTransaction {
            EconomyAccountEntity.findById(id)?.delete()
        }
    }

    override suspend fun existsById(id: Int): Boolean {
        return newSuspendedTransaction {
            EconomyAccountEntity.findById(id) != null
        }
    }

    override suspend fun findAll(): SizedIterable<EconomyAccountEntity> {
        return newSuspendedTransaction {
            EconomyAccountEntity.all()
        }
    }

    override suspend fun new(entity: EconomyAccountEntity.() -> Unit): EconomyAccountEntity {
        return newSuspendedTransaction {
            EconomyAccountEntity.new(entity)
        }
    }

    override suspend fun findById(id: Int): EconomyAccountEntity? {
        return newSuspendedTransaction {
            EconomyAccountEntity.findById(id)
        }
    }

    override suspend fun findByAccountIdForUpdate(playerId: UUID): SizedIterable<EconomyAccountEntity> {
        return newSuspendedTransaction {
            EconomyAccountEntity.find {
                EconomyAccountTable.accountId eq playerId
            }.forUpdate()
        }
    }

    override suspend fun findByAccountIdAndCurrencyIdForUpdate(playerId: UUID, currencyId: Int): EconomyAccountEntity? {
        return newSuspendedTransaction {
            EconomyAccountEntity.find {
                (EconomyAccountTable.accountId eq playerId) and (EconomyAccountTable.currencyId eq currencyId)
            }.forUpdate().limit(1).singleOrNull()
        }
    }
}