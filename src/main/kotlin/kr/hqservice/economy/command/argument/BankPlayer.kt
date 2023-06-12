package kr.hqservice.economy.command.argument

import kr.hqservice.framework.netty.api.NettyPlayer

data class BankPlayer(
    val playerName: String,
    val nettyPlayer: NettyPlayer?
)