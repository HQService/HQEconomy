package kr.hqservice.economy.core.entity

import kr.hqservice.framework.database.component.Table
import kr.hqservice.framework.database.dao.id.IntIdTimestampTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime
import java.time.LocalDateTime

class EconomyAccountEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EconomyAccountEntity>(EconomyAccountTable)

    var accountId by EconomyAccountTable.accountId
    var currencyId by EconomyCurrencyEntity referencedOn EconomyAccountTable.currencyId
    var balance by EconomyAccountTable.balance
}

@Table
object EconomyAccountTable : IntIdTimestampTable("hqeconomy_account") {
    val accountId = uuid("account_id")
    val currencyId = reference("currency_id", EconomyCurrencyTable.id, onDelete = ReferenceOption.CASCADE)
    val balance = long("balance").default(0)

    override val createdAt = datetime("created_at").default(LocalDateTime.now())
    override val updatedAt = datetime("updated_at").nullable().default(null)
}