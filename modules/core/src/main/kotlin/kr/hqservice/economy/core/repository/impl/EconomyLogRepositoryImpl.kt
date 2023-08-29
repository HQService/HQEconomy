package kr.hqservice.economy.core.repository.impl

import kr.hqservice.economy.core.entity.EconomyLogEntity
import kr.hqservice.economy.core.repository.EconomyLogRepository
import kr.hqservice.framework.database.repository.Repository
import org.jetbrains.exposed.sql.SizedIterable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Repository
class EconomyLogRepositoryImpl : EconomyLogRepository {
    override suspend fun count(): Long {
        return newSuspendedTransaction {
            EconomyLogEntity.count()
        }
    }

    override suspend fun delete(entity: EconomyLogEntity) {
        newSuspendedTransaction {
            entity.delete()
        }
    }

    override suspend fun deleteById(id: Long) {
        newSuspendedTransaction {
            EconomyLogEntity.findById(id)?.delete()
        }
    }

    override suspend fun existsById(id: Long): Boolean {
        return newSuspendedTransaction {
            EconomyLogEntity.findById(id) != null
        }
    }

    override suspend fun findAll(): SizedIterable<EconomyLogEntity> {
        return newSuspendedTransaction {
            EconomyLogEntity.all()
        }
    }

    override suspend fun findById(id: Long): EconomyLogEntity? {
        return newSuspendedTransaction {
            EconomyLogEntity.findById(id)
        }
    }

    override suspend fun new(entity: EconomyLogEntity.() -> Unit): EconomyLogEntity {
        return newSuspendedTransaction {
            EconomyLogEntity.new(entity)
        }
    }
}