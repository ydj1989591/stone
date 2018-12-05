package com.ydj.stone.core.exception

import com.ydj.stone.core.enums.BizExceptionEnum
import com.ydj.stone.core.enums.CoreExceptionEnum
import java.lang.RuntimeException

/**
 * 业务异常的封装
 */
class ServiceException(
        code:Int, errorMessage:String
) : RuntimeException(errorMessage){
    private var code:Int
    private var errorMessage:String

    init {
        this.code = code
        this.errorMessage = errorMessage
    }

    constructor(exception: CoreExceptionEnum):this(exception.code, exception.message)

    constructor(exception: BizExceptionEnum): this(exception.code, exception.message)

}