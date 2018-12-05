package com.ydj.stone.core.common.factory

import com.ydj.stone.modular.system.entity.Dict

/**
 * 常量生产工厂的接口
 */
interface IConstantFactory {
    /**
     * 根据用户id获取用户名称
     *
     * @author stylefeng
     * @Date 2017/5/9 23:41
     */
    fun getUserNameById(userId: Int?): String?

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    fun getUserAccountById(userId: Int?): String?

    /**
     * 通过角色ids获取角色名称
     */
    fun getRoleName(roleIds: String): String

    /**
     * 通过角色id获取角色名称
     */
    fun getSingleRoleName(roleId: Int?): String

    /**
     * 通过角色id获取角色英文名称
     */
    fun getSingleRoleTip(roleId: Int?): String

    /**
     * 获取部门名称
     */
    fun getDeptName(deptId: Int?): String

    /**
     * 获取菜单的名称们(多个)
     */
    fun getMenuNames(menuIds: String): String

    /**
     * 获取菜单名称
     */
    fun getMenuName(menuId: Long?): String

    /**
     * 获取菜单名称通过编号
     */
    fun getMenuNameByCode(code: String): String

    /**
     * 获取字典名称
     */
    fun getDictName(dictId: Int?): String

    /**
     * 获取通知标题
     */
    fun getNoticeTitle(dictId: Int?): String

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    fun getDictsByName(name: String, `val`: Int?): String

    /**
     * 获取性别名称
     */
    fun getSexName(sex: Int?): String

    /**
     * 获取用户登录状态
     */
    fun getStatusName(status: Int?): String

    /**
     * 获取菜单状态
     */
    fun getMenuStatusName(status: Int?): String

    /**
     * 查询字典
     */
    abstract fun findInDict(id: Int?): List<Dict>?

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    fun getCacheObject(para: String): String

    /**
     * 获取子部门id
     */
    fun getSubDeptId(deptid: Int?): List<Int>

    /**
     * 获取所有父部门id
     */
    fun getParentDeptIds(deptid: Int?): List<Int>

}