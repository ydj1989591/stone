package com.ydj.stone.core.reqres.response

import com.ydj.stone.core.enums.ErrorCode

open class ResponseData (){

    /**
     * 请求是否成功
     */
    var success: Boolean? = false

    /**
     * 响应状态码
     */
    var code: Int? = ErrorCode.DEFAULT_ERROR.code

    /**
     * 响应信息
     */
    var message: String? = null

    /**
     * 响应对象
     */
    var data: Any ?= null


    constructor(success: Boolean?, code: Int?, message: String?, data: Any?):this() {
        this.success = success
        this.code = code
        this.message = message
        this.data = data
    }

    fun success(): SuccessResponseData {
        return SuccessResponseData()
    }

    fun success(`object`: Any): SuccessResponseData {
        return SuccessResponseData(`object`)
    }

    fun success(code: Int?, message: String, `object`: Any): SuccessResponseData {
        return SuccessResponseData(code, message, `object`)
    }

    fun error(message: String): ErrorResponseData {
        return ErrorResponseData(message)
    }

    fun error(code: Int?, message: String): ErrorResponseData {
        return ErrorResponseData(code, message)
    }

    fun error(code: Int?, message: String, `object`: Any): ErrorResponseData {
        return ErrorResponseData(code, message, `object`)
    }
}