package kr.hqservice.economy.database.table

import org.jetbrains.exposed.dao.id.LongIdTable

object EconomyLogTable : LongIdTable("economy_log") {
    val ownerTable = reference("owner", EconomyTable)
    val amount = long("amount")
    val balance = long("balance")
    val log = varchar("log", 150)
}