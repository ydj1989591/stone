package com.ydj.stone.core.shiro

import com.ydj.stone.core.shiro.service.impl.UserAuthServiceServiceImpl
import com.ydj.stone.core.util.ValidateUtil
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.AuthenticationInfo
import org.apache.shiro.authc.AuthenticationToken
import org.apache.shiro.authc.UsernamePasswordToken
import org.apache.shiro.authc.credential.CredentialsMatcher
import org.apache.shiro.authc.credential.HashedCredentialsMatcher
import org.apache.shiro.authz.AuthorizationInfo
import org.apache.shiro.authz.SimpleAuthorizationInfo
import org.apache.shiro.realm.AuthorizingRealm
import org.apache.shiro.subject.PrincipalCollection
import java.util.HashSet

class ShiroDbRealm : AuthorizingRealm() {
    /**
     * 登录认证
     */
    @Throws(AuthenticationException::class)
    override fun doGetAuthenticationInfo(authcToken: AuthenticationToken): AuthenticationInfo {
        val shiroFactory = UserAuthServiceServiceImpl.me()
        val token = authcToken as UsernamePasswordToken
        val user = shiroFactory.user(token.username)
        val shiroUser = shiroFactory.shiroUser(user)
        return shiroFactory.info(shiroUser, user, super.getName())
    }

    /**
     * 权限认证
     */
    override fun doGetAuthorizationInfo(principals: PrincipalCollection): AuthorizationInfo {
        val shiroFactory = UserAuthServiceServiceImpl.me()
        val shiroUser = principals.primaryPrincipal as ShiroUser
        val roleList = shiroUser.roleList

        val permissionSet = HashSet<String>()
        val roleNameSet = HashSet<String>()

        for (roleId in roleList!!) {
            val permissions = shiroFactory.findPermissionsByRoleId(roleId)
            if (permissions != null) {
                for (permission in permissions!!) {
                    if (ValidateUtil.isNotEmpty(permission)) {
                        permissionSet.add(permission)
                    }
                }
            }
            val roleName = shiroFactory.findRoleNameByRoleId(roleId)
            roleNameSet.add(roleName)
        }

        val info = SimpleAuthorizationInfo()
        info.addStringPermissions(permissionSet)
        info.addRoles(roleNameSet)
        return info
    }

    /**
     * 设置认证加密方式
     */
    override fun setCredentialsMatcher(credentialsMatcher: CredentialsMatcher) {
        val md5CredentialsMatcher = HashedCredentialsMatcher()
        md5CredentialsMatcher.hashAlgorithmName = ShiroKit.hashAlgorithmName
        md5CredentialsMatcher.hashIterations = ShiroKit.hashIterations
        super.setCredentialsMatcher(md5CredentialsMatcher)
    }
}