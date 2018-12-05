package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 用于删除业务的字典
 */
class DeleteDict : AbstractDictMap(){

    override fun init() {
        put("roleId", "角色名称")
        put("deptId", "部门名称")
        put("menuId", "菜单名称")
        put("dictId", "字典名称")
        put("noticeId", "标题")
    }

    override fun initBeWrapped() {
        putFieldWrapperMethodName("roleId", "getCacheObject")
        putFieldWrapperMethodName("deptId", "getCacheObject")
        putFieldWrapperMethodName("menuId", "getCacheObject")
        putFieldWrapperMethodName("dictId", "getCacheObject")
        putFieldWrapperMethodName("noticeId", "getCacheObject")
    }
}