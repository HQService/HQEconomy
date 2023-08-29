package kr.hqservice.economy.api.service

import kr.hqservice.economy.api.Balance
import java.util.UUID

interface EconomyService {
    /**
     * @throws kr.hqservice.economy.api.exception.CurrencyNotFoundException
     */
    suspend fun getBalance(accountId: UUID, currencyName: String): Balance

    suspend fun getBalances(accountId: UUID): List<Balance>

    /**
     * @throws kr.hqservice.economy.api.exception.CurrencyNotFoundException
     */
    suspend fun withdraw(accountId: UUID, currencyName: String, amount: Long): Balance

    /**
     * @throws kr.hqservice.economy.api.exception.CurrencyNotFoundException
     */
    suspend fun deposit(accountId: UUID, currencyName: String, amount: Long): Balance
}