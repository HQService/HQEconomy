package kr.hqservice.economy.database.repository

import kr.hqservice.economy.database.entity.impl.EconomyBankEntity
import kr.hqservice.economy.database.table.EconomyTable
import kr.hqservice.framework.database.extension.findForUpdate
import kr.hqservice.framework.database.extension.getForUpdate
import kr.hqservice.framework.database.repository.Repository
import java.util.*

@Repository
class EconomyBankRepository {
    fun create(init: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return EconomyBankEntity.new(init)
    }

    fun getOrCreate(uniqueId: UUID): EconomyBankEntity {
        return find(uniqueId) ?: EconomyBankEntity.new {
            owner = uniqueId
            balance = 0
        }
    }

    private fun find(uniqueId: UUID): EconomyBankEntity? {
        return EconomyBankEntity.find {
            EconomyTable.owner eq uniqueId
        }.firstOrNull()
    }

    fun delete(uniqueId: UUID) {
        EconomyBankEntity.find {
            EconomyTable.owner eq uniqueId
        }.firstOrNull()?.delete()
    }

    fun count(): Long {
        return EconomyBankEntity.count()
    }

    fun updateById(uniqueId: UUID, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return EconomyBankEntity.findForUpdate {
            EconomyTable.owner eq uniqueId
        }.firstOrNull()?.apply(scope)?: EconomyBankEntity.new {
            owner = uniqueId
            balance = 0
        }.apply(scope)
    }

    fun update(entity: EconomyBankEntity, scope: EconomyBankEntity.() -> Unit): EconomyBankEntity {
        return EconomyBankEntity
            .getForUpdate(entity.id)
            .apply(scope)
    }
}