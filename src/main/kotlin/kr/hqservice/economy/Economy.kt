package kr.hqservice.economy

import org.bukkit.OfflinePlayer

interface Economy {
    suspend fun getBalance(playerName: String): Long

    suspend fun getBalance(player: OfflinePlayer): Long

    suspend fun has(playerName: String, amount: Long): Boolean

    suspend fun has(player: OfflinePlayer, amount: Long): Boolean

    suspend fun withdrawPlayer(playerName: String, amount: Long)

    suspend fun withdrawPlayer(player: OfflinePlayer, amount: Long)

    suspend fun depositPlayer(playerName: String, amount: Long)

    suspend fun depositPlayer(player: OfflinePlayer, amount: Long)
}