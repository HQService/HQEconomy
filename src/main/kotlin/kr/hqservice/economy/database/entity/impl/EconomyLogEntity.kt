package kr.hqservice.economy.database.entity.impl

import kr.hqservice.economy.database.entity.EconomyEntity
import kr.hqservice.economy.database.table.EconomyLogTable
import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EconomyLogEntity(
    id: EntityID<Long>
) : LongEntity(id), EconomyEntity {
    companion object : LongEntityClass<EconomyLogEntity>(EconomyLogTable)

    var ownerTable by EconomyBankEntity referencedOn EconomyLogTable.ownerTable
    var amount by EconomyLogTable.amount
    var balance by EconomyLogTable.balance
    var log by EconomyLogTable.log
}