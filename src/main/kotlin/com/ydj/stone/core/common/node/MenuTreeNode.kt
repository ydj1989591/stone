package com.ydj.stone.core.common.node

/**
 * 菜单树
 */
class MenuTreeNode {

    /**
     * 菜单标题
     */
    var title : String? = ""

    /**
     * 图标
     */
    var icon : String? = ""

    /**
     * 跳转路径
     */
    var href: String? = ""

    /**
     * 是否展开
     */
    var spread: Boolean = false

    /**
     * 子菜单
     */
    var children: MutableList<MenuTreeNode>?=null




}

