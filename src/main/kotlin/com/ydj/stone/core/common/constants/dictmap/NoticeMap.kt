package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 通知的映射
 */
class NoticeMap: AbstractDictMap() {

   override fun init() {
        put("title", "标题")
        put("content", "内容")
    }

    override fun initBeWrapped() {}
}