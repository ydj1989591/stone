package com.ydj.stone.modular.system.service.impl;

import cn.hutool.core.convert.Convert
import com.ydj.stone.modular.system.entity.Role;
import com.ydj.stone.modular.system.mapper.RoleMapper;
import com.ydj.stone.modular.system.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydj.stone.core.common.node.ZTreeNode
import com.ydj.stone.modular.system.entity.Relation
import com.ydj.stone.modular.system.mapper.RelationMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class RoleServiceImpl : ServiceImpl<RoleMapper, Role>(), RoleService {

    @Autowired
    lateinit var roleMapper: RoleMapper

    @Autowired
    lateinit var relationMapper: RelationMapper

    @Transactional
    override fun setAuthority(roleId: Int?, ids: String) {

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId)

        // 添加新的权限
        for (id in Convert.toLongArray(ids.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())) {
            val relation = Relation()
            relation.roleid =roleId
            relation.menuid = id
            this.relationMapper.insert(relation)
        }
    }

    @Transactional
    override fun delRoleById(roleId: Int?) {
        //删除角色
        this.roleMapper.deleteById(roleId)

        // 删除该角色所有的权限
        this.roleMapper.deleteRolesById(roleId)
    }

    override fun selectRoles(condition: String): List<Map<String, Any>> {
        return this.baseMapper.selectRoles(condition)
    }

    override fun deleteRolesById(roleId: Int?): Int {
        return this.baseMapper.deleteRolesById(roleId)
    }

    override fun roleTreeList(): List<ZTreeNode> {
        return this.baseMapper.roleTreeList()
    }

    override fun roleTreeListByRoleId(roleId: Array<String>): List<ZTreeNode> {
        return this.baseMapper.roleTreeListByRoleId(roleId)
    }
}
