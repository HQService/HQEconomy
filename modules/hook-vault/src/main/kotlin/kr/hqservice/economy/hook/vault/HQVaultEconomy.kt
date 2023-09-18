package kr.hqservice.economy.hook.vault

import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kr.hqservice.economy.api.registry.CurrencyRegistry
import kr.hqservice.economy.api.service.EconomyService
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.bukkit.core.component.registry.PluginDepend
import kr.hqservice.framework.global.core.component.Singleton
import net.milkbowl.vault.economy.AbstractEconomy
import net.milkbowl.vault.economy.EconomyResponse
import org.bukkit.OfflinePlayer
import org.bukkit.Server

@PluginDepend(["Vault"])
@Singleton
class HQVaultEconomy(
    private val economyService: EconomyService,
    private val currencyRegistry: CurrencyRegistry,
    private val plugin: HQBukkitPlugin,
    private val server: Server
) : AbstractEconomy() {
    override fun isEnabled(): Boolean {
        return true
    }

    override fun getName(): String {
        return "HQVaultEconomy"
    }

    override fun hasBankSupport(): Boolean {
        return false
    }

    override fun fractionalDigits(): Int {
        return 0
    }

    override fun format(amount: Double): String {
        return ""
    }

    override fun currencyNamePlural(): String {
        return ""
    }

    override fun currencyNameSingular(): String {
        return ""
    }

    override fun hasAccount(playerName: String?): Boolean {
        return true
    }

    override fun hasAccount(playerName: String?, worldName: String?): Boolean {
        return true
    }

    override fun getBalance(playerName: String): Double {
        return runBlocking {
            economyService.getBalance(
                server.getOfflinePlayer(playerName).uniqueId,
                currencyRegistry.getDefault().name
            ).balance.toDouble()
        }
    }

    override fun getBalance(player: OfflinePlayer): Double {
        return runBlocking {
            economyService.getBalance(
                player.uniqueId,
                currencyRegistry.getDefault().name
            ).balance.toDouble()
        }
    }

    override fun getBalance(player: OfflinePlayer, world: String?): Double {
        return runBlocking {
            economyService.getBalance(
                player.uniqueId,
                currencyRegistry.getDefault().name
            ).balance.toDouble()
        }
    }

    override fun getBalance(playerName: String, world: String?): Double {
        return this.getBalance(playerName)
    }

    override fun has(playerName: String, amount: Double): Boolean {
        return getBalance(playerName) >= amount
    }

    override fun has(player: OfflinePlayer, amount: Double): Boolean {
        return getBalance(player) >= amount
    }

    override fun has(player: OfflinePlayer, worldName: String?, amount: Double): Boolean {
        return getBalance(player) >= amount
    }

    override fun has(playerName: String, worldName: String?, amount: Double): Boolean {
        return getBalance(playerName) >= amount
    }

    override fun withdrawPlayer(playerName: String, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.withdraw(server.getOfflinePlayer(playerName).uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun withdrawPlayer(playerName: String, worldName: String?, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.withdraw(server.getOfflinePlayer(playerName).uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun withdrawPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.withdraw(player.uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun withdrawPlayer(player: OfflinePlayer, worldName: String?, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.withdraw(player.uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun depositPlayer(playerName: String, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.deposit(server.getOfflinePlayer(playerName).uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun depositPlayer(playerName: String, worldName: String?, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.deposit(server.getOfflinePlayer(playerName).uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun depositPlayer(player: OfflinePlayer, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.deposit(player.uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun depositPlayer(player: OfflinePlayer, worldName: String?, amount: Double): EconomyResponse {
        plugin.launch {
            economyService.deposit(player.uniqueId, currencyRegistry.getDefault().name, amount.toLong())
        }
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.SUCCESS, "")
    }

    override fun createBank(name: String?, player: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun deleteBank(name: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun bankBalance(name: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun bankHas(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun bankWithdraw(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun bankDeposit(name: String?, amount: Double): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun isBankOwner(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun isBankMember(name: String?, playerName: String?): EconomyResponse {
        return EconomyResponse(0.0, 0.0, EconomyResponse.ResponseType.NOT_IMPLEMENTED, "bank not support")
    }

    override fun getBanks(): MutableList<String> {
        return mutableListOf()
    }

    override fun createPlayerAccount(playerName: String?): Boolean {
        return true
    }

    override fun createPlayerAccount(playerName: String?, worldName: String?): Boolean {
        return true
    }

}