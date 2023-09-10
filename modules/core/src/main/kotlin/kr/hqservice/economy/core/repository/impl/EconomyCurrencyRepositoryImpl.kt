package kr.hqservice.economy.core.repository.impl

import kr.hqservice.economy.core.entity.EconomyCurrencyEntity
import kr.hqservice.economy.core.entity.EconomyCurrencyTable
import kr.hqservice.economy.core.repository.EconomyCurrencyRepository
import kr.hqservice.framework.database.repository.Repository
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Repository
class EconomyCurrencyRepositoryImpl : EconomyCurrencyRepository {
    override suspend fun findByCurrencyName(name: String): EconomyCurrencyEntity? {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.find {
                EconomyCurrencyTable.name eq name
            }.limit(1).singleOrNull()
        }
    }

    override suspend fun getDefault(): EconomyCurrencyEntity {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.find {
                EconomyCurrencyTable.isDefault eq true
            }.limit(1).single()
        }
    }

    override suspend fun count(): Long {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.count()
        }
    }

    override suspend fun delete(entity: EconomyCurrencyEntity) {
        newSuspendedTransaction {
            entity.delete()
        }
    }

    override suspend fun deleteById(id: Int) {
        newSuspendedTransaction {
            EconomyCurrencyEntity.findById(id)?.delete()
        }
    }

    override suspend fun existsById(id: Int): Boolean {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.findById(id) != null
        }
    }

    override suspend fun findAll(): SizedIterable<EconomyCurrencyEntity> {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.all()
        }
    }

    override suspend fun findById(id: Int): EconomyCurrencyEntity? {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.findById(id)
        }
    }

    override suspend fun new(entity: EconomyCurrencyEntity.() -> Unit): EconomyCurrencyEntity {
        return newSuspendedTransaction {
            EconomyCurrencyEntity.new(entity)
        }
    }
}