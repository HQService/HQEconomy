package kr.hqservice.economy.core.entity

import kr.hqservice.economy.api.Currency
import kr.hqservice.framework.database.component.Table
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

class EconomyCurrencyEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EconomyCurrencyEntity>(EconomyCurrencyTable)

    var name by EconomyCurrencyTable.name
    var displayName by EconomyCurrencyTable.displayName

    fun toDto(): Currency {
        return Currency(name, displayName)
    }
}

@Table
object EconomyCurrencyTable : IntIdTable("hqeconomy_currency") {
    val name = varchar("name", 32)
    val displayName = varchar("display_name", 256).nullable()
}