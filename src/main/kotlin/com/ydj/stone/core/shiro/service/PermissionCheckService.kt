package com.ydj.stone.core.shiro.service

/**
 * 检查用接口
 */
interface PermissionCheckService {
    /**
     * 检查当前登录用户是否拥有指定的角色访问当
     */
    fun check(permissions: Array<String>): Boolean

    /**
     * 检查当前登录用户是否拥有当前请求的servlet的权限
     */
    fun checkAll(): Boolean
}