package com.ydj.stone.core.reqres.response

import com.ydj.stone.core.enums.ErrorCode

/**
 * 请求成功的返回
 */
class SuccessResponseData : ResponseData {

    constructor(): super(true, ErrorCode.DEFAULT_SUCCESS.code, ErrorCode.DEFAULT_SUCCESS.codeName, null)

    constructor(`object`: Any) : super(true, ErrorCode.DEFAULT_SUCCESS.code,  ErrorCode.DEFAULT_SUCCESS.codeName, `object`)

    constructor(code: Int?, message: String, `object`: Any) : super(true, code, message, `object`)
}