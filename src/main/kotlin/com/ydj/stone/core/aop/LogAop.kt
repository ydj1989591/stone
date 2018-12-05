package com.ydj.stone.core.aop

import com.ydj.stone.core.common.annotion.BussinessLog
import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap
import com.ydj.stone.core.log.LogManager
import com.ydj.stone.core.log.LogObjectHolder
import com.ydj.stone.core.log.factory.LogTaskFactory
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.core.util.Contrast
import com.ydj.stone.core.util.HttpContext
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 日志记录
 */
@Aspect
@Component
class LogAop {
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Pointcut(value = "@annotation(com.ydj.stone.core.common.annotion.BussinessLog)")
    fun cutService() {
    }

    @Around("cutService()")
    @Throws(Throwable::class)
    fun recordSysLog(point: ProceedingJoinPoint): Any {

        //先执行业务
        val result = point.proceed()

        try {
            handle(point)
        } catch (e: Exception) {
            log.error("日志记录出错!", e)
        }

        return result
    }

    @Throws(Exception::class)
    private fun handle(point: ProceedingJoinPoint) {

        //获取拦截的方法名
        val sig = point.signature
        var msig: MethodSignature? = null
        if (sig !is MethodSignature) {
            throw IllegalArgumentException("该注解只能用于方法")
        }
        msig = sig
        val target = point.target
        val currentMethod = target.javaClass.getMethod(msig.name, *msig.parameterTypes)
        val methodName = currentMethod.name

        //如果当前用户未登录，不做日志
        val user = ShiroKit.getUser() ?: return

        //获取拦截方法的参数
        val className = point.target.javaClass.name
        val params = point.args

        //获取操作名称
        val annotation = currentMethod.getAnnotation(BussinessLog::class.java!!)
        val bussinessName = annotation.value
        val key = annotation.key
        val dictClass = annotation.dict

        val sb = StringBuilder()
        for (param in params) {
            sb.append(param)
            sb.append(" & ")
        }

        //如果涉及到修改,比对变化
        val msg: String
        if (bussinessName.contains("修改") || bussinessName.contains("编辑")) {
            val obj1 = LogObjectHolder.me().`object`
            val obj2 = HttpContext.getRequestParameters()
            msg = Contrast.contrastObj(dictClass.java, key, obj1!!, obj2)
        } else {
            val parameters = HttpContext.getRequestParameters()
            val dictMap = dictClass.java.newInstance() as AbstractDictMap
            msg = Contrast.parseMutiKey(dictMap, key, parameters)
        }

        LogManager.me().executeLog(LogTaskFactory.bussinessLog(user.id, bussinessName, className, methodName, msg))
    }
}