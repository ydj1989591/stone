package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.modular.system.entity.User;
import com.ydj.stone.modular.system.mapper.UserMapper;
import com.ydj.stone.modular.system.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydj.stone.core.reqres.request.system.user.UserListReq
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class UserServiceImpl : ServiceImpl<UserMapper, User>(), UserService {

    override fun setStatus(userId: Int?, status: Int): Int {
        return this.baseMapper.setStatus(userId, status)
    }

    override fun changePwd(userId: Int?, pwd: String): Int {
        return this.baseMapper.changePwd(userId, pwd)
    }

    override fun selectUsers( name: String, beginTime: String, endTime: String, deptid: Int?): List<Map<String, Any>> {
        return this.baseMapper.selectUsers(name, beginTime, endTime, deptid)
    }

    override fun setRoles(userId: Int?, roleIds: String): Int {
        return this.baseMapper.setRoles(userId, roleIds)
    }

    override fun getByAccount(account: String): User {
        return this.baseMapper.getByAccount(account)
    }

    override fun selectUserList(page: Page<User>, req: UserListReq): Page<User> {
        return this.baseMapper.selectUserList(page, req)
    }

    override fun selectUserById(userId: Int): User {
        return this.baseMapper.selectUserById(userId)
    }
}
