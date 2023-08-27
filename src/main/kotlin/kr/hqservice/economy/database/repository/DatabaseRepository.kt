package kr.hqservice.economy.database.repository

import kr.hqservice.economy.database.entity.EconomyEntity
import kr.hqservice.framework.database.component.repository.HQRepository
import java.util.UUID

interface DatabaseRepository<T: EconomyEntity> : HQRepository {
    suspend fun create(uniqueId: UUID, init: T.() -> Unit): T

    suspend fun getOrCreate(uniqueId: UUID): T

    suspend fun update(entity: T, scope: T.() -> Unit): T

    suspend fun updateById(uniqueId: UUID, scope: T.() -> Unit): T

    suspend fun delete(uniqueId: UUID)

    suspend fun count(): Long
}