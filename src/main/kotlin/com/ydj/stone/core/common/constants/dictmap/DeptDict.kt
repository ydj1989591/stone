package com.ydj.stone.core.common.constants.dictmap

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap

/**
 * 部门的映射
 */
class DeptDict: AbstractDictMap() {

    override fun init() {
        put("deptId", "部门名称")
        put("num", "部门排序")
        put("pid", "上级名称")
        put("simplename", "部门简称")
        put("fullname", "部门全称")
        put("tips", "备注")
    }

    override fun initBeWrapped() {
        putFieldWrapperMethodName("deptId", "getDeptName")
        putFieldWrapperMethodName("pid", "getDeptName")
    }
}