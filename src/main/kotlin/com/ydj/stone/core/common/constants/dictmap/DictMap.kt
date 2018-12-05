package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 字典map
 */
class DictMap: AbstractDictMap() {

    override fun init() {
        put("dictId", "字典名称")
        put("dictName", "字典名称")
        put("dictValues", "字典内容")
    }

    override  fun initBeWrapped() {

    }
}