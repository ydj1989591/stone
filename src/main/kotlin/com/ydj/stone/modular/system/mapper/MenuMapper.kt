package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydj.stone.core.common.node.MenuNode
import com.ydj.stone.core.common.node.ZTreeNode
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 菜单表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface MenuMapper : BaseMapper<Menu>{

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


