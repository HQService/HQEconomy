package kr.hqservice.economy.database.table

import kr.hqservice.framework.database.component.Table
import org.jetbrains.exposed.dao.id.IntIdTable

@Table
object EconomyTable : IntIdTable("economy") {
    val owner = uuid("owner")
    val balance = long("balance")
}