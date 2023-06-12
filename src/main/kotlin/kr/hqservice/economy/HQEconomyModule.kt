package kr.hqservice.economy

import kr.hqservice.economy.service.EconomyService
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQModule

@Component
class HQEconomyModule(
    private val service: EconomyService
) : HQModule {
    override fun onEnable() {
        service.hook()
    }

    override fun onDisable() {
        service.unhook()
    }
}