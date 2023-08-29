package kr.hqservice.economy.core.entity

import kr.hqservice.framework.database.component.Table
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

class EconomyLogEntity(
    id: EntityID<Long>
) : LongEntity(id) {
    companion object : LongEntityClass<EconomyLogEntity>(EconomyLogTable)

    var accountId by EconomyLogTable.accountId
    var currencyId by EconomyLogTable.currencyId
    var amount by EconomyLogTable.amount
    var transactedAt by EconomyLogTable.transactedAt
}

@Table
object EconomyLogTable : LongIdTable("hqeconomy_log") {
    val accountId = uuid("account_id")
    val currencyId = integer("currency_id")
    val amount = long("amount")
    val transactedAt = datetime("transacted_at").default(LocalDateTime.now())
}