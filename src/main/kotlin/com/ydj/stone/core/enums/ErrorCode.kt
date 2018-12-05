package com.ydj.stone.core.enums

/**
 * 接口请求错误代码
 */
enum class ErrorCode (val code: Int, val codeName: String) {
    DEFAULT_SUCCESS(200, "请求成功"),
    DEFAULT_ERROR(500, "网络异常")
}