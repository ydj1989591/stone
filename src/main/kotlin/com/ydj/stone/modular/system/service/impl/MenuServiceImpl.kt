package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.modular.system.entity.Menu;
import com.ydj.stone.modular.system.mapper.MenuMapper;
import com.ydj.stone.modular.system.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydj.stone.core.common.node.MenuNode
import com.ydj.stone.core.common.node.ZTreeNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class MenuServiceImpl : ServiceImpl<MenuMapper, Menu>(), MenuService {

    @Autowired
    lateinit var menuMapper: MenuMapper

    @Transactional
    override fun delMenu(menuId: Long?) {

        //删除菜单
        this.menuMapper.deleteById(menuId)

        //删除关联的relation
        this.menuMapper.deleteRelationByMenu(menuId)
    }

    @Transactional
    override fun delMenuContainSubMenus(menuId: Long?) {

        val menu = menuMapper.selectById(menuId)

        //删除当前菜单
        delMenu(menuId)

        //删除所有子菜单
        var wrapper= QueryWrapper<Menu>()
        wrapper = wrapper.like("pcodes", "%[" + menu.code + "]%")
        val menus = menuMapper.selectList(wrapper)
        for (temp in menus) {
            delMenu(temp.id)
        }
    }

    override fun selectMenus(condition: String, level: String): List<Map<String, Any>> {
        return this.baseMapper.selectMenus(condition, level)
    }

    override fun getMenuIdsByRoleId(roleId: Int?): List<Long> {
        return this.baseMapper.getMenuIdsByRoleId(roleId)
    }

    override fun menuTreeList(): List<ZTreeNode> {
        return this.baseMapper.menuTreeList()
    }

    override fun menuTreeListByMenuIds(menuIds: List<Long>): List<ZTreeNode> {
        return this.baseMapper.menuTreeListByMenuIds(menuIds)
    }

    override fun deleteRelationByMenu(menuId: Long?): Int {
        return this.baseMapper.deleteRelationByMenu(menuId)
    }

    override fun getResUrlsByRoleId(roleId: Int?): List<String> {
        return this.baseMapper.getResUrlsByRoleId(roleId)
    }

    override fun getMenusByRoleIds(roleIds: List<Int>): List<MenuNode> {
        return this.baseMapper.getMenusByRoleIds(roleIds)
    }
}
