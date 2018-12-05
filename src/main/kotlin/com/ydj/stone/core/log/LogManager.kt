package com.ydj.stone.core.log

import java.util.*
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * 日志管理器
 */
class LogManager (){
    //日志记录操作延时
    private val OPERATE_DELAY_TIME:Long = 10

    //异步操作记录日志的线程池
    private val executor: ScheduledThreadPoolExecutor = ScheduledThreadPoolExecutor(10)

    companion object {
        val logManager = LogManager()

        fun  me () : LogManager = logManager

    }

    fun executeLog(task: TimerTask){
        executor.schedule(task, OPERATE_DELAY_TIME, TimeUnit.MILLISECONDS)
    }

}