package com.ydj.stone.core.shiro

import com.alibaba.fastjson.JSONObject
import com.ydj.stone.core.common.factory.ConstantFactory
import com.ydj.stone.core.util.ToolUtil
import org.apache.shiro.SecurityUtils
import org.apache.shiro.crypto.hash.Md5Hash
import org.apache.shiro.crypto.hash.SimpleHash
import org.apache.shiro.util.ByteSource

/**
 * shiro工具类
 */
class ShiroKit {

    companion object {
        private val NAMES_DELIMETER  = ","
        /**
         * 加盐参数
         */
        public val hashAlgorithmName = "MD5"
        /**
         * 循环次数
         */
        public val hashIterations = 1024

        /**
         * shiro密码加密工具类
         */
        fun md5(credentials: String, saltSource: String): String{
            val salt: ByteSource = Md5Hash(saltSource)
            return SimpleHash(hashAlgorithmName, credentials, salt, hashIterations).toString()
        }

        /**
         * 获取随机盐值
         */
        fun getRandomSalt(length: Int)= ToolUtil.getRandomString(length)

        /**
         * 获取当前 Subject
         */
        fun getSubject() = SecurityUtils.getSubject()

        /**
         * 认证通过或已记住的用户。与guset搭配使用。
         */
        fun isUser() = getSubject() !=null && getSubject().principal != null

        /**
         * 验证当前用户是否为“访客”，即未认证（包含未记住）的用户。用user搭配使用
         */
        fun isGuest() = !isUser()

        /**
         * 获取封装的 ShiroUser
         */
        fun getUser() = if(isGuest()) null else JSONObject.parseObject(JSONObject.toJSON(getSubject().principals?.primaryPrincipal).toString(), ShiroUser::class.java)

        /**
         * 从shiro获取session
         */
        fun getSession() = getSubject().session

        /**
         * 验证当前用户是否属于该角色？,使用时与lacksRole 搭配使用
         */
        fun hasRole(roleName: String) = getSubject()!=null && roleName.length > 0
                                            && getSubject().hasRole(roleName)


        /**
         * 验证当前用户是否属于以下任意一个角色。
         */
        fun hasAnyRoles(roleNames: String): Boolean{
            var hasAnyRole = false
            val subject = getSubject()
            if(subject !=null && roleNames.length > 0){
                for (role in roleNames.split(NAMES_DELIMETER)){
                    if(subject.hasRole(role.trim())){
                        hasAnyRole = true
                        break
                    }
                }
            }
            return hasAnyRole
        }

        /**
         * 验证当前用户是否拥有指定权限,使用时与lacksPermission 搭配使用
         *
         * @param permission 权限名
         * @return 拥有权限：true，否则false
         */
        fun hasPermission(permission: String) = (getSubject() != null && permission != null
                && permission.length > 0
                && getSubject().isPermitted(permission));

        /**
         * 已认证通过的用户。不包含已记住的用户，这是与user标签的区别所在。与notAuthenticated搭配使用
         *
         * @return 通过身份验证：true，否则false
         */
        fun isAuthenticated()= getSubject() != null && getSubject().isAuthenticated

        /**
         * 获取当前用户的部门数据范围的集合
         */
        fun getDeptDataScope(): MutableList<Int>? {
            val deptId = getUser()?.deptId
            return deptId?.let {
                val subDeptIds = ConstantFactory.me().getSubDeptId(deptId)
                subDeptIds.add(deptId)
                subDeptIds
            }
        }
    }
}