package kr.hqservice.economy.hook.vault

import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.bukkit.core.component.module.Module
import kr.hqservice.framework.bukkit.core.component.module.Setup
import kr.hqservice.framework.bukkit.core.component.registry.PluginDepend
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.ServicesManager


@PluginDepend(["Vault"])
@Module
class VaultHookModule(
    private val serviceManager: ServicesManager,
    private val economy: Economy,
    private val plugin: HQBukkitPlugin
) {

    @Setup
    fun setup() {
        serviceManager.register(Economy::class.java, economy, plugin, ServicePriority.Highest)
    }
}