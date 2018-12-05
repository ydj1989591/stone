package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 角色的字典
 */
class RoleDict: AbstractDictMap() {

    override fun init() {
        put("roleId", "角色名称")
        put("num", "角色排序")
        put("pid", "角色的父级")
        put("name", "角色名称")
        put("deptid", "部门名称")
        put("tips", "备注")
        put("ids", "资源名称")
    }

    override fun initBeWrapped() {
        putFieldWrapperMethodName("pid", "getSingleRoleName")
        putFieldWrapperMethodName("deptid", "getDeptName")
        putFieldWrapperMethodName("roleId", "getSingleRoleName")
        putFieldWrapperMethodName("ids", "getMenuNames")
    }
}