package kr.hqservice.economy.database.repository.impl

import kr.hqservice.economy.database.entity.impl.EconomyBankEntity
import kr.hqservice.economy.database.table.EconomyTable
import kr.hqservice.framework.database.component.datasource.DataSource
import kr.hqservice.framework.database.component.datasource.HQDataSource
import kr.hqservice.framework.database.component.repository.HQRepository
import kr.hqservice.framework.database.component.repository.Repository
import kr.hqservice.framework.database.component.repository.Table
import kr.hqservice.framework.database.util.findForUpdate
import kr.hqservice.framework.database.util.getForUpdate
import java.util.*

@Repository
@Table(with = [EconomyTable::class])
class EconomyBankRepository(
    @DataSource override val dataSource: HQDataSource
) : HQRepository {
    suspend fun create(init: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            EconomyBankEntity.new(init)
        }
    }

    suspend fun getOrCreate(uniqueId: UUID): EconomyBankEntity {
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

    suspend fun delete(uniqueId: UUID) {
        return dataSource.query {
            EconomyBankEntity.find {
                EconomyTable.owner eq uniqueId
            }.firstOrNull()?.delete()
        }
    }

    suspend fun count(): Long {
        return dataSource.query {
            EconomyBankEntity.count()
        }
    }

    suspend fun updateById(uniqueId: UUID, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            EconomyBankEntity.findForUpdate {
                EconomyTable.owner eq uniqueId
            }.firstOrNull()?.apply(scope)?: EconomyBankEntity.new {
                owner = uniqueId
                balance = 0
            }.apply(scope)
        }
    }

    suspend fun update(entity: EconomyBankEntity, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return dataSource.query {
            EconomyBankEntity
                .getForUpdate(entity.id)
                .apply(scope)
        }
    }
}