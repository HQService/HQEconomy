package kr.hqservice.economy.coroutine.impl

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.job
import kr.hqservice.economy.coroutine.EconomyCoroutineScope
import kr.hqservice.framework.bukkit.core.HQBukkitPlugin
import kr.hqservice.framework.global.core.component.Component
import kr.hqservice.framework.global.core.component.HQSingleton
import org.koin.core.annotation.Named
import org.koin.core.component.getScopeName
import java.util.logging.Level
import java.util.logging.Logger

@Named("log")
@HQSingleton(binds = [EconomyCoroutineScope::class])
@Component
class EconomyLogCoroutineScope(plugin: HQBukkitPlugin, logger: Logger) : EconomyCoroutineScope(plugin) {
    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        logger.log(Level.SEVERE, throwable) {
            "EconomyLogCoroutineScope 에서 오류 ${throwable::class.simpleName} 이(가) 발생하였습니다. \n" +
                    "job: ${context.job} \n" +
                    "scopeName: ${context.getScopeName()} \n" +
                    "stackTrace 를 출력합니다. \n"
        }
    }
    private val coroutineName = CoroutineName("EconomyCoroutineScope")

    override fun getExceptionHandler(): CoroutineExceptionHandler {
        return exceptionHandler
    }

    override fun getCoroutineName(): CoroutineName {
        return coroutineName
    }
}