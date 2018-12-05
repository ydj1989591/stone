package com.ydj.stone.core.shiro.service

import com.ydj.stone.core.shiro.ShiroUser
import com.ydj.stone.modular.system.entity.User
import org.apache.shiro.authc.SimpleAuthenticationInfo

/**
 * 定义shirorealm所需数据的接口
 */
interface UserAuthService {
    /**
     * 根据账号获取登录用户
     *
     * @param account 账号
     */
    fun user(account: String): User

    /**
     * 根据系统用户获取Shiro的用户
     *
     * @param user 系统用户
     */
    fun shiroUser(user: User): ShiroUser

    /**
     * 获取权限列表通过角色id
     *
     * @param roleId 角色id
     */
    fun findPermissionsByRoleId(roleId: Int?): List<String>

    /**
     * 根据角色id获取角色名称
     *
     * @param roleId 角色id
     */
    fun findRoleNameByRoleId(roleId: Int?): String

    /**
     * 获取shiro的认证信息
     */
    fun info(shiroUser: ShiroUser, user: User, realmName: String): SimpleAuthenticationInfo
}