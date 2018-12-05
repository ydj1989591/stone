package com.ydj.stone.core.enums

/**
 * 日志类型
 */
enum class LogType (val message: String){
    LOGIN("登录日志"),
    LOGIN_FAIL("登录失败日志"),
    EXIT("退出日志"),
    EXCEPTION("异常日志"),
    BUSSINESS("业务日志");
}

/**
 * 业务是否成功的日志记录
 */
enum class LogSucceed(val message: String){
    SUCCESS("成功"),
    FAIL("失败");
}
