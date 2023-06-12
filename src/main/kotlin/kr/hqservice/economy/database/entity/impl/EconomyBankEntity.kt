package kr.hqservice.economy.database.entity.impl

import kr.hqservice.economy.database.entity.EconomyEntity
import kr.hqservice.economy.database.table.EconomyTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EconomyBankEntity(
    id: EntityID<Int>
) : IntEntity(id), EconomyEntity {
    companion object : IntEntityClass<EconomyBankEntity>(EconomyTable)

    var owner by EconomyTable.owner
    var balance by EconomyTable.balance
}