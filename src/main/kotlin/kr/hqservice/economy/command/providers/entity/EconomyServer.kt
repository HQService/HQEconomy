package kr.hqservice.economy.command.providers.entity

interface EconomyServer {
    fun getPlayers(): List<EconomyPlayer>

    fun getPlayer(name: String): EconomyPlayer?
}