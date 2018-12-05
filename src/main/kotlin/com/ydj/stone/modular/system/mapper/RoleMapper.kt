package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydj.stone.core.common.node.ZTreeNode
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface RoleMapper : BaseMapper<Role>{

    /**
     * 根据条件查询角色列表
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    fun selectRoles(@Param("condition") condition: String): List<Map<String, Any>>

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    fun deleteRolesById(@Param("roleId") roleId: Int?): Int

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
     fun roleTreeList(): List<ZTreeNode>

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    fun roleTreeListByRoleId(roleId: Array<String>): List<ZTreeNode>
}
