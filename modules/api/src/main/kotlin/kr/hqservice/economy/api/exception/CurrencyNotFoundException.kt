package kr.hqservice.economy.api.exception

class CurrencyNotFoundException(currencyName: String) : RuntimeException("Currency with name $currencyName Not Found.")