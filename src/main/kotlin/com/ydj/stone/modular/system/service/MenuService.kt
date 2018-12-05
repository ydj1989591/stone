package com.ydj.stone.modular.system.service;

import com.ydj.stone.modular.system.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydj.stone.core.common.node.MenuNode
import com.ydj.stone.core.common.node.ZTreeNode
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface MenuService : IService<Menu>{
    /**
     * 删除菜单
     *
     * @author stylefeng
     * @Date 2017/5/5 22:20
     */
    fun delMenu(menuId: Long?)

    /**
     * 删除菜单包含所有子菜单
     *
     * @author stylefeng
     * @Date 2017/6/13 22:02
     */
    fun delMenuContainSubMenus(menuId: Long?)

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    fun selectMenus(@Param("condition") condition: String, @Param("level") level: String): List<Map<String, Any>>

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    fun getMenuIdsByRoleId(@Param("roleId") roleId: Int?): List<Long>

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    fun menuTreeList(): List<ZTreeNode>

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    fun menuTreeListByMenuIds(menuIds: List<Long>): List<ZTreeNode>

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    fun deleteRelationByMenu(menuId: Long?): Int

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    fun getResUrlsByRoleId(roleId: Int?): List<String>

    /**
     * 根据角色获取菜单
     *
     * @param roleIds
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    fun getMenusByRoleIds(roleIds: List<Int>): List<MenuNode>
}
