package kr.hqservice.economy.coroutine

import kotlinx.coroutines.Dispatchers
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.coroutine.component.HQCoroutineScope

abstract class EconomyCoroutineScope(
    hqPlugin: HQBukkitPlugin
) : HQCoroutineScope(hqPlugin, Dispatchers.IO)