package kr.hqservice.economy.command.providers.entity

interface EconomyPlayer {
    fun getName(): String

    fun sendMessage(message: String)
}