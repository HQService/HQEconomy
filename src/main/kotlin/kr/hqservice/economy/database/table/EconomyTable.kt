package kr.hqservice.economy.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object EconomyTable : IntIdTable("economy") {
    val owner = uuid("owner")
    val balance = long("balance")
}