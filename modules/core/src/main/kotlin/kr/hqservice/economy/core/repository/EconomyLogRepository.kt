package kr.hqservice.economy.core.repository

import kr.hqservice.economy.core.entity.EconomyLogEntity
import kr.hqservice.framework.database.repository.ExposedRepository

interface EconomyLogRepository : ExposedRepository<Long, EconomyLogEntity>