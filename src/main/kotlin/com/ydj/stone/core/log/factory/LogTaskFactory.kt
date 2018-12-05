package com.ydj.stone.core.log.factory

import com.ydj.stone.core.enums.LogSucceed
import com.ydj.stone.core.enums.LogType
import com.ydj.stone.core.util.SpringContextHolder
import com.ydj.stone.core.util.ToolUtil
import com.ydj.stone.modular.system.mapper.LoginLogMapper
import com.ydj.stone.modular.system.mapper.OperationLogMapper
import org.slf4j.LoggerFactory
import java.util.*

class LogTaskFactory {


    companion object {
        private val logger = LoggerFactory.getLogger(LogTaskFactory::class.java)
        private val loginLogMapper : LoginLogMapper = SpringContextHolder.getBean(LoginLogMapper::class.java)
        private val operationLogMapper: OperationLogMapper = SpringContextHolder.getBean(OperationLogMapper::class.java)

        fun loginLog(userId: Int, ip:String) : TimerTask{
            return object : TimerTask() {
                override fun run() {
                    try {
                        val loginLog = LogFactory.createLoginLog(LogType.LOGIN, userId, null, ip)
                        loginLogMapper.insert(loginLog)
                    } catch (e: Exception) {
                        logger.error("创建登录日志异常!", e)
                    }

                }
            }
        }

        fun loginLog(username: String, msg: String, ip: String): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    val loginLog = LogFactory.createLoginLog(
                            LogType.LOGIN_FAIL, null, "账号:$username,$msg", ip)
                    try {
                        loginLogMapper.insert(loginLog)
                    } catch (e: Exception) {
                        logger.error("创建登录失败异常!", e)
                    }

                }
            }
        }

        fun exitLog(userId: Int?, ip: String): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    val loginLog = LogFactory.createLoginLog(LogType.EXIT, userId, null, ip)
                    try {
                        loginLogMapper.insert(loginLog)
                    } catch (e: Exception) {
                        logger.error("创建退出日志异常!", e)
                    }

                }
            }
        }

        fun bussinessLog(userId: Int?, bussinessName: String, clazzName: String, methodName: String, msg: String): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    val operationLog = LogFactory.createOperationLog(
                            LogType.BUSSINESS, userId, bussinessName, clazzName, methodName, msg, LogSucceed.SUCCESS)
                    try {
                        operationLogMapper.insert(operationLog)
                    } catch (e: Exception) {
                        logger.error("创建业务日志异常!", e)
                    }

                }
            }
        }

        fun exceptionLog(userId: Int?, exception: Exception): TimerTask {
            return object : TimerTask() {
                override fun run() {
                    val msg = ToolUtil.getExceptionMsg(exception)
                    val operationLog = LogFactory.createOperationLog(
                            LogType.EXCEPTION, userId, "", null, null, msg, LogSucceed.FAIL)
                    try {
                        operationLogMapper.insert(operationLog)
                    } catch (e: Exception) {
                        logger.error("创建异常日志异常!", e)
                    }

                }
            }
        }
    }
}