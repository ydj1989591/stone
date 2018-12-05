package com.ydj.stone.core.reqres.response

import com.ydj.stone.core.enums.ErrorCode

class ErrorResponseData : ResponseData{

    private var exceptionClazz: String ? = null

    constructor(message: String) : super(false, ErrorCode.DEFAULT_ERROR.code, message, null)

    constructor(code: Int?, message: String) : super(false, code, message, null)

    constructor(code: Int?, message: String, `object`: Any):super(false, code, message, `object`)


}