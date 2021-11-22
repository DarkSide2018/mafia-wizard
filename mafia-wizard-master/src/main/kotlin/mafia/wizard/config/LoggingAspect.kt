package mafia.wizard.config

import mafia.wizard.common.toJson
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.After
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component
import java.util.Arrays
import java.util.logging.Logger
import java.util.stream.Collectors


@Component
@Aspect
class LoggingAspect {

    private val logger: Logger = Logger.getLogger(this.javaClass.name)

    @Pointcut(
        "@annotation(mafia.wizard.config.Trace) "
    )
    fun log() {}

    @Before("log()")
    fun beforeLogMethod(jp: JoinPoint) {
        logger.info("before $jp, args=[${jp.args.toJson()}]")
    }

    @After("log()")
    fun afterLogMethod(jp: JoinPoint) {
        logger.info("after $jp")
    }


    @Around("log()")
    @Throws(Throwable::class)
    fun executionTime(point: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val `object` = point.proceed()
        val endtime = System.currentTimeMillis()
        logger.info(
            ("Class Name: " + point.signature.declaringTypeName +
                    ". Method Name: " + point.signature.name +
                    ". Time taken for Execution is : " + (endtime - startTime) + "ms")
        )
        if (`object` != null) {
            logger.info("returned object: ${`object`.toJson()}")
        }
        return `object`
    }
}


annotation class Trace()