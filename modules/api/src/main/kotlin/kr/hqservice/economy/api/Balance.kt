package kr.hqservice.economy.api

data class Balance(
    val currency: Currency,
    val balance: Long
)