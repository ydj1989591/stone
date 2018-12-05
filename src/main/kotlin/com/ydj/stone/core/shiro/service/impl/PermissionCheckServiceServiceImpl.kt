package com.ydj.stone.core.shiro.service.impl

import cn.hutool.core.collection.CollectionUtil
import com.ydj.stone.core.listener.ConfigListener
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.core.shiro.service.PermissionCheckService
import com.ydj.stone.core.util.HttpContext
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class PermissionCheckServiceServiceImpl : PermissionCheckService {
    override fun check(permissions: Array<String>): Boolean {
        val user = ShiroKit.getUser() ?: return false
        val objects = CollectionUtil.newArrayList(*permissions)
        val join = CollectionUtil.join(objects, ",")
        return if (ShiroKit.hasAnyRoles(join)) {
            true
        } else false
    }

    override fun checkAll(): Boolean {
        val request = HttpContext.getRequest()
        val user = ShiroKit.getUser() ?: return false
        var requestURI = request.getRequestURI().replaceFirst(ConfigListener.getConf().get("contextPath")!!.toRegex(), "")
        val str = requestURI.split("/".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
        if (str.size > 3) {
            requestURI = "/" + str[1] + "/" + str[2]
        }
        return if (ShiroKit.hasPermission(requestURI)) {
            true
        } else false
    }
}