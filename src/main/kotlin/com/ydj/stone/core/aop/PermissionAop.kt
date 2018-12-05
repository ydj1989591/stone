package com.ydj.stone.core.aop

import com.ydj.stone.core.common.annotion.Permission
import com.ydj.stone.core.shiro.service.PermissionCheckService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import javax.naming.NoPermissionException

@Aspect
@Component
@Order(200)
class PermissionAop {
    @Autowired
    lateinit var check: PermissionCheckService

    @Pointcut(value = "@annotation(com.ydj.stone.core.common.annotion.Permission)")
    private fun cutPermission() {

    }

    @Around("cutPermission()")
    @Throws(Throwable::class)
    fun doPermission(point: ProceedingJoinPoint): Any {
        val ms = point.signature as MethodSignature
        val method = ms.method
        val permission = method.getAnnotation(Permission::class.java!!)
        val permissions = permission.values
        if (permissions.size == 0) {

            //检查全体角色
            val result = check!!.checkAll()
            return if (result) {
                point.proceed()
            } else {
                throw NoPermissionException()
            }

        } else {

            //检查指定角色
            val result = check!!.check(permissions)
            return if (result) {
                point.proceed()
            } else {
                throw NoPermissionException()
            }
        }
    }

}