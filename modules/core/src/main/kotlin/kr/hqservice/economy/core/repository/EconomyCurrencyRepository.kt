package kr.hqservice.economy.core.repository

import kr.hqservice.economy.core.entity.EconomyCurrencyEntity
import kr.hqservice.framework.database.repository.ExposedRepository

interface EconomyCurrencyRepository : ExposedRepository<Int, EconomyCurrencyEntity> {
    suspend fun findByCurrencyName(name: String): EconomyCurrencyEntity?

    suspend fun getDefault(): EconomyCurrencyEntity
}