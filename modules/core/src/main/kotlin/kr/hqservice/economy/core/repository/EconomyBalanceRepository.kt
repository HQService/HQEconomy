package kr.hqservice.economy.core.repository

import kr.hqservice.economy.core.entity.EconomyAccountEntity
import kr.hqservice.framework.database.repository.ExposedRepository
import org.jetbrains.exposed.sql.SizedIterable
import java.util.UUID

interface EconomyBalanceRepository : ExposedRepository<Int, EconomyAccountEntity> {
    suspend fun findByAccountIdForUpdate(playerId: UUID): SizedIterable<EconomyAccountEntity>

    suspend fun findByAccountIdAndCurrencyIdForUpdate(playerId: UUID, currencyId: Int): EconomyAccountEntity?
}