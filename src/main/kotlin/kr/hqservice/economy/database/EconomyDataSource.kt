package kr.hqservice.economy.database

import kotlinx.coroutines.CoroutineScope
import kr.hqservice.economy.coroutine.EconomyCoroutineScope
import kr.hqservice.framework.database.DatabaseHost
import kr.hqservice.framework.database.component.datasource.HQDataSource
import kr.hqservice.framework.database.component.datasource.MySQLDataSource
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQSingleton
import kr.hqservice.framework.yaml.config.HQYamlConfiguration
import org.koin.core.annotation.Named

@Named("economy")
@HQSingleton(binds = [HQDataSource::class])
@Component
class EconomyDataSource(
    @Named("bank") coroutineSource: EconomyCoroutineScope,
    config: HQYamlConfiguration
) : MySQLDataSource(
    DatabaseHost(
        config.getString("db.host"),
        config.getInt("db.port"),
        config.getString("db.user"),
        config.getString("db.password"),
        config.getString("db.database")
    )
) , CoroutineScope by coroutineSource