package com.ydj.stone.modular.system.command

import cn.hutool.core.date.DateUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.core.reqres.request.system.user.UserListReq
import com.ydj.stone.core.reqres.request.system.user.UserReq
import com.ydj.stone.core.reqres.response.system.user.UserResp
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.modular.system.entity.User
import com.ydj.stone.modular.system.service.DeptService
import com.ydj.stone.modular.system.service.UserService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestBody
import java.util.*

/**
 * 用户管理
 */
@Service
class UserCommand {
    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var deptService: DeptService

    private val DEFAULT_PASSWORD = "123456"

    fun list(req: UserListReq): Page<User>{

        val page = Page<User>()
        page.current = req.page!!.toLong()
        page.size = req.limit!!.toLong()

        return userService.selectUserList(page, req)
    }

    /**
     * 编辑
     */
    fun edit(req: UserReq){
        val user = User()
        user.id = req.userId
        user.account = req.account
        user.name = req.name
        user.phone = req.phone
        user.email = req.email
        user.deptid = req.deptid
        user.birthday = DateUtil.parse(req.birthday)
        user.status = req.status
        user.sex = req.sex


        if(req.userId == null){
            user.salt = ShiroKit.getRandomSalt(5)
            user.password = ShiroKit.md5(DEFAULT_PASSWORD, user.salt!!)
            user.createtime = Date()
        }

        userService.saveOrUpdate(user)
    }

    /**
     * 校验用户名
     */
    fun checkAccount(account: String, userId: Int?): Boolean{
        val queryWrapper = QueryWrapper<User>()
        queryWrapper.eq("account", account)
        userId?.let {
            queryWrapper.ne("id", userId)
        }
        val count = userService.count(queryWrapper)
        return if(count > 0) false else true
    }

    /**
     * 修改用户状态
     */
    fun changeUserStatus(userId: Int, status: Int){
        val user = User()
        user.id = userId
        user.status = status
        userService.updateById(user)
    }

    /**
     * 查询用户信息
     */
    fun queryUser(userId: Int) : UserResp? {
        val user = userService.selectUserById(userId)
        val userResp = UserResp()
        BeanUtils.copyProperties(user, userResp);
        userResp.userId = user.id
        userResp.birthday = user.birthday?.let { DateUtil.formatDate(user.birthday)}
        if(null != user.deptid) {
           val dept =  deptService.getById(user.deptid)
            userResp.deptName = dept.simplename
        }
        return userResp
    }
}