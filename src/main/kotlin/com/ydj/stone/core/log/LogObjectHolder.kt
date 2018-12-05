package com.ydj.stone.core.log

import com.ydj.stone.core.util.SpringContextHolder
import java.io.Serializable

/**
 * 被修改的bean临时存放的地方
 */
class LogObjectHolder : Serializable {

    var `object` : Object ? = null

    companion object {
        fun me()  = SpringContextHolder.getBean(LogObjectHolder::class.java)
    }
}