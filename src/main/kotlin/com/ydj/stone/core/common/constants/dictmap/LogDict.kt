package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 日志的字典
 */
class LogDict: AbstractDictMap() {

    override fun init() {
        put("tips", "备注")
    }

    override fun initBeWrapped() {

    }
}