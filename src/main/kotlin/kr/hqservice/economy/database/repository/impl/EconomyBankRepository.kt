package kr.hqservice.economy.database.repository.impl

import kr.hqservice.economy.database.entity.impl.EconomyBankEntity
import kr.hqservice.economy.database.repository.DatabaseRepository
import kr.hqservice.economy.database.table.EconomyLogTable
import kr.hqservice.economy.database.table.EconomyTable
import kr.hqservice.framework.database.component.datasource.HQDataSource
import kr.hqservice.framework.database.component.repository.Table
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQSingleton
import org.koin.core.annotation.Named
import java.util.*

@Component
@Named("economy")
@HQSingleton(binds = [DatabaseRepository::class])
@Table(with = [EconomyLogTable::class, EconomyTable::class])
class EconomyBankRepository(
    @Named("economy") private val dataSource: HQDataSource
) : DatabaseRepository<EconomyBankEntity> {
    override suspend fun create(uniqueId: UUID, init: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            EconomyBankEntity.new(init)
        }
    }

    override suspend fun get(uniqueId: UUID): EconomyBankEntity {
        return find(uniqueId) ?: dataSource.query {
            EconomyBankEntity.new {
                owner = uniqueId
                balance = 0
            }
        }
    }

    private suspend fun find(uniqueId: UUID): EconomyBankEntity? {
        return dataSource.query {
            EconomyBankEntity.find {
                EconomyTable.owner eq uniqueId
            }.firstOrNull()
        }
    }

    override suspend fun delete(uniqueId: UUID) {
        return dataSource.query {
            EconomyBankEntity.find {
                EconomyTable.owner eq uniqueId
            }.firstOrNull()?.delete()
        }
    }

    override suspend fun count(): Long {
        return dataSource.query {
            EconomyBankEntity.count()
        }
    }

    override suspend fun updateById(uniqueId: UUID, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            val entity = get(uniqueId)
            entity.scope()
            entity.flush()
            entity
        }
    }

    override suspend fun update(entity: EconomyBankEntity, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            entity.scope()
            entity.flush()
            entity
        }
    }

    override fun getDataSource(): HQDataSource {
        return dataSource
    }
}