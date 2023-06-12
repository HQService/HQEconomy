package kr.hqservice.economy.command.argument

import kr.hqservice.economy.command.providers.entity.EconomyPlayer

data class BankPlayer(
    val playerName: String,
    val playerInstance: EconomyPlayer?
)