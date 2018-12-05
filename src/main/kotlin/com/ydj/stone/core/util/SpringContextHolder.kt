package com.ydj.stone.core.util

import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

/**
 * Spring的ApplicationContext的持有者,可以用静态方法的方式获取spring容器中的bean
 */
@Service
class SpringContextHolder : ApplicationContextAware {

    @Throws(BeansException::class)
    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextHolder.applicationContext = applicationContext
    }

    companion object {
        private var applicationContext: ApplicationContext? = null

        fun getApplicationContext(): ApplicationContext? {
            assertApplicationContext()
            return applicationContext
        }

        fun <T> getBean(beanName: String): T {
            assertApplicationContext()
            return applicationContext!!.getBean(beanName) as T
        }

        fun <T> getBean(requiredType: Class<T>): T {
            assertApplicationContext()
            return applicationContext!!.getBean(requiredType)
        }

        private fun assertApplicationContext() {
            if (SpringContextHolder.applicationContext == null) {
                throw RuntimeException("applicaitonContext属性为null,请检查是否注入了SpringContextHolder!")
            }
        }
    }

}