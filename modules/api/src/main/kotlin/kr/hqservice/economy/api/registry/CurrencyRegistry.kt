package kr.hqservice.economy.api.registry

import kr.hqservice.economy.api.Currency

interface CurrencyRegistry {
    suspend fun createByName(currencyName: String, displayName: String?): Currency

    suspend fun getDefault(): Currency

    suspend fun findCurrencyByName(currencyName: String): Currency?

    suspend fun deleteByName(currencyName: String)

    suspend fun getCurrencyAll(): List<Currency>
}