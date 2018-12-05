package com.ydj.stone.core.listener

import java.util.HashMap
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener

/**
 * ServletContext监听器
 */
class ConfigListener: ServletContextListener {

    companion object {
        private val conf = HashMap<String, String>()

        fun getConf(): Map<String, String> {
            return conf
        }
    }

    override fun contextDestroyed(arg0: ServletContextEvent) {
        conf.clear()
    }

    override fun contextInitialized(evt: ServletContextEvent) {
        val sc = evt.servletContext

        //项目发布,当前运行环境的绝对路径
        conf["realPath"] = sc.getRealPath("/").replaceFirst("/".toRegex(), "")

        //servletContextPath,默认""
        conf["contextPath"] = sc.contextPath
    }
}