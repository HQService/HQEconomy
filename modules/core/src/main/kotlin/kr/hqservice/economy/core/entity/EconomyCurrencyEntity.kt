package kr.hqservice.economy.core.entity

import kotlinx.coroutines.runBlocking
import kr.hqservice.economy.api.Currency
import kr.hqservice.economy.core.repository.EconomyCurrencyRepository
import kr.hqservice.framework.bukkit.core.component.module.Module
import kr.hqservice.framework.bukkit.core.component.module.Setup
import kr.hqservice.framework.database.component.Table
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class EconomyCurrencyEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<EconomyCurrencyEntity>(EconomyCurrencyTable)

    var name by EconomyCurrencyTable.name
    var displayName by EconomyCurrencyTable.displayName
    var isDefault by EconomyCurrencyTable.isDefault

    fun toDto(): Currency {
        return Currency(name, displayName, isDefault)
    }
}

@Table
object EconomyCurrencyTable : IntIdTable("hqeconomy_currency") {
    val name = varchar("name", 32)
    val displayName = varchar("display_name", 256).nullable()
    val isDefault = bool("is_default").default(false)
}

@Module
class EconomyCurrencyTableInitializer(economyCurrencyTable: EconomyCurrencyTable) {
    @Setup
    fun setup() {
        runBlocking {
            newSuspendedTransaction {
                if (EconomyCurrencyEntity.count() == 0L) {
                    EconomyCurrencyEntity.new {
                        this.name = "default"
                        this.isDefault = true
                    }
                }
            }
        }
    }
}