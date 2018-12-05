package com.ydj.stone.core.shiro.service.impl

import cn.hutool.core.convert.Convert
import com.ydj.stone.core.common.factory.ConstantFactory
import com.ydj.stone.core.enums.ManagerStatus
import com.ydj.stone.core.shiro.ShiroUser
import com.ydj.stone.core.shiro.service.UserAuthService
import com.ydj.stone.core.util.SpringContextHolder
import com.ydj.stone.modular.system.entity.User
import com.ydj.stone.modular.system.mapper.MenuMapper
import com.ydj.stone.modular.system.mapper.UserMapper
import org.apache.shiro.authc.CredentialsException
import org.apache.shiro.authc.LockedAccountException
import org.apache.shiro.authc.SimpleAuthenticationInfo
import org.apache.shiro.crypto.hash.Md5Hash
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList

@Service
@DependsOn("springContextHolder")
@Transactional(readOnly = true)
class UserAuthServiceServiceImpl: UserAuthService {

    @Autowired
    lateinit var userMapper: UserMapper

    @Autowired
    lateinit var menuMapper: MenuMapper

    companion object {
        fun me(): UserAuthService {
            return SpringContextHolder.getBean(UserAuthService::class.java)
        }

    }

    override fun user(account: String): User {

        val user = userMapper!!.getByAccount(account) ?: throw CredentialsException()

        // 账号不存在
        // 账号被冻结
        if (user.status !== ManagerStatus.OK.code) {
            throw LockedAccountException()
        }
        return user
    }

    override fun shiroUser(user: User): ShiroUser {
        val shiroUser = ShiroUser()
        shiroUser.id = user.id
        shiroUser.account = user.account
        shiroUser.deptId = user.deptid
        shiroUser.deptName = ConstantFactory.me().getDeptName(user.deptid)
        shiroUser.name = user.name

        val roleArray = Convert.toIntArray(user.roleid)
        val roleList = ArrayList<Int>()
        val roleNameList = ArrayList<String>()
        for (roleId in roleArray) {
            roleList.add(roleId)
            roleNameList.add(ConstantFactory.me().getSingleRoleName(roleId))
        }
        shiroUser.roleList = roleList
        shiroUser.roleNames = roleNameList
        return shiroUser
    }

    override fun findPermissionsByRoleId(roleId: Int?): List<String> {
        return menuMapper!!.getResUrlsByRoleId(roleId)
    }

    override fun findRoleNameByRoleId(roleId: Int?): String {
        return ConstantFactory.me().getSingleRoleTip(roleId)
    }

    override fun info(shiroUser: ShiroUser, user: User, realmName: String): SimpleAuthenticationInfo {
        val credentials = user.password

        // 密码加盐处理
        val source = user.salt
        val credentialsSalt = Md5Hash(source)
        return SimpleAuthenticationInfo(shiroUser, credentials, credentialsSalt, realmName)
    }
}