package kr.hqservice.economy.api

import org.bukkit.OfflinePlayer
import java.util.UUID

interface Economy {
    suspend fun getBalance(playerName: String): Long

    suspend fun getBalance(uniqueId: UUID): Long

    suspend fun getBalance(player: OfflinePlayer): Long

    suspend fun has(playerName: String, amount: Long): Boolean

    suspend fun has(uniqueId: UUID, amount: Long): Boolean

    suspend fun has(player: OfflinePlayer, amount: Long): Boolean

    suspend fun withdrawPlayer(playerName: String, amount: Long)

    suspend fun withdrawPlayer(uniqueId: UUID, amount: Long)

    suspend fun withdrawPlayer(player: OfflinePlayer, amount: Long)

    suspend fun depositPlayer(playerName: String, amount: Long)

    suspend fun depositPlayer(uniqueId: UUID, amount: Long)

    suspend fun depositPlayer(player: OfflinePlayer, amount: Long)
}