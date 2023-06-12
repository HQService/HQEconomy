package kr.hqservice.economy.command.providers.entity

import kr.hqservice.framework.global.core.component.HQSimpleComponent

interface EconomyServer : HQSimpleComponent {
    fun getPlayers(): List<EconomyPlayer>

    fun getPlayer(name: String): EconomyPlayer?
}