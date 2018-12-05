package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.core.reqres.request.system.user.UserListReq
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 管理员表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface UserMapper : BaseMapper<User>{
    /**
     * 修改用户状态
     */
    fun setStatus(@Param("userId") userId: Int?, @Param("status") status: Int): Int

    /**
     * 修改密码
     */
    fun changePwd(@Param("userId") userId: Int?, @Param("pwd") pwd: String): Int

    /**
     * 根据条件查询用户列表
     */
    fun selectUsers(@Param("name") name: String, @Param("beginTime") beginTime: String, @Param("endTime") endTime: String, @Param("deptid") deptid: Int?): List<Map<String, Any>>

    /**
     * 设置用户的角色
     */
    fun setRoles(@Param("userId") userId: Int?, @Param("roleIds") roleIds: String): Int

    /**
     * 通过账号获取用户
     */
    fun getByAccount(@Param("account") account: String): User

    /**
     * 查询用户列表
     */
    fun selectUserList(page: Page<User>, @Param("req") list: UserListReq): Page<User>

    /**
     * 根据用户Id查询用户
     */
    fun selectUserById(@Param("userId") userId: Int): User
}
