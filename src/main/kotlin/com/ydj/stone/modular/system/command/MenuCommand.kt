package com.ydj.stone.modular.system.command

import com.ydj.stone.core.common.node.MenuNode
import com.ydj.stone.core.common.node.MenuTreeNode
import com.ydj.stone.modular.system.service.MenuService
import com.ydj.stone.modular.system.service.TopMenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MenuCommand {

    @Autowired
    private lateinit var menuService: MenuService
    @Autowired
    private lateinit var topMenuService: TopMenuService

    /**
     * 查询菜单树
     */
    fun queryMenuList(roleIds: List<Int>):Map<String, MutableList<MenuTreeNode>>{
        val menuTreeNodeMap = mutableMapOf<String, MutableList<MenuTreeNode>>()

        val menuList = menuService.getMenusByRoleIds(roleIds)
        val menuTreeList = MenuNode.buildTitle(menuList as MutableList<MenuNode>)

        val map = menuTreeList.groupBy { it.topMenuCode }
        map.forEach{(k,v)->
            val menuTreeNodeList = transformTree(v)
            menuTreeNodeMap.put(k!!, menuTreeNodeList)
        }
        return menuTreeNodeMap
    }


    private fun transformTree(menuList: List<MenuNode>?): MutableList<MenuTreeNode>{
        val menuTreeNodeList = mutableListOf<MenuTreeNode>()
        menuList?.forEach {
            val menuTreeNode = MenuTreeNode()
            menuTreeNode.title = it.name
            menuTreeNode.href = it.url
            menuTreeNode.icon = it.icon
            menuTreeNode.spread = if(it.isOpen == 1) true else false
            menuTreeNode.children = it.children?.let{ transformTree(it as? MutableList<MenuNode>)}
            menuTreeNodeList.add(menuTreeNode)
        }
        return menuTreeNodeList
    }
}