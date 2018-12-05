package com.ydj.stone.core.log.factory

import com.ydj.stone.core.enums.LogSucceed
import com.ydj.stone.core.enums.LogType
import com.ydj.stone.modular.system.entity.LoginLog
import com.ydj.stone.modular.system.entity.OperationLog

import java.util.*

/**
 * 日志对象创建工厂
 */
class LogFactory {

    companion object {
        /**
         * 创建操作日志
         */
        fun createOperationLog(logType: LogType, userId: Int?, bussinessName: String, clazzName: String?, methodName: String?, msg: String, succeed: LogSucceed): OperationLog {
            val operationLog = OperationLog()
            operationLog.logtype = logType.message
            operationLog.logname = bussinessName
            operationLog.userid = userId
            operationLog.classname = clazzName
            operationLog.method = methodName
            operationLog.createtime = Date()
            operationLog.succeed = succeed.message
            operationLog.message = msg
            return operationLog
        }

        /**
         * 创建登录日志
         */
        fun createLoginLog(logType: LogType, userId: Int?, msg: String?, ip: String): LoginLog {
            val loginLog = LoginLog()
            loginLog.logname = logType.message
            loginLog.userid = userId
            loginLog.createtime = Date()
            loginLog.succeed = LogSucceed.SUCCESS.message
            loginLog.ip = ip
            loginLog.message = msg
            return loginLog
        }
    }
}