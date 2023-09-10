package kr.hqservice.economy.api

data class Currency(
    val name: String,
    val displayName: String?,
    val isDefault: Boolean
)