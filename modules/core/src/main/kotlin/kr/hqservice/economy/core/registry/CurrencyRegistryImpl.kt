package kr.hqservice.economy.core.registry

import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.api.registry.CurrencyRegistry
import kr.hqservice.economy.core.repository.EconomyCurrencyRepository
import kr.hqservice.framework.global.core.component.Bean
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

@Bean
class CurrencyRegistryImpl(private val currencyRepository: EconomyCurrencyRepository) : CurrencyRegistry {
    override suspend fun createByName(currencyName: String, displayName: String?) {
        currencyRepository.new {
            this.name = currencyName
            this.displayName = displayName
        }
    }

    override suspend fun findCurrencyByName(currencyName: String): Currency? {
        return currencyRepository.findByCurrencyName(currencyName)?.toDto()
    }

    override suspend fun deleteByName(currencyName: String) {
        newSuspendedTransaction {
            currencyRepository.findByCurrencyName(currencyName)?.delete()
        }
    }

    override suspend fun getCurrencyAll(): List<Currency> {
        return newSuspendedTransaction {
            currencyRepository.findAll().map { it.toDto() }
        }
    }
}