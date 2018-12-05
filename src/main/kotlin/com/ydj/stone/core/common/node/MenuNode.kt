package com.ydj.stone.core.common.node

import com.ydj.stone.core.enums.YesOrNotEnum
import java.util.*

/**
 * 菜单的节点
 */
class MenuNode() : Comparable<MenuNode> {

    /**
     * 重写排序比较接口，首先根据等级排序，然后更具排序字段排序
     */
    override fun compareTo(other: MenuNode): Int {
        val menuNode = other
        var num = menuNode.num
        var levels = menuNode.levels
        if (num == null) {
            num = 0
        }
        if (levels == null) {
            levels = 0
        }
        return if (this.levels?.compareTo(levels!!) == 0) {
            this.num!!.compareTo(num!!)
        } else {
            this.levels!!.compareTo(levels!!)
        }
    }

    /**
     * 节点id
     */
    var id: Long? = null

    /**
     * 父节点
     */
    var parentId: Long? = null

    /**
     * 节点名称
     */
    var name: String? = null

    /**
     * 按钮级别
     */
    var levels: Int? = null

    /**
     * 按钮级别
     */
    var ismenu: Int? = null

    /**
     * 按钮的排序
     */
    var num: Int? = null

    /**
     * 节点的url
     */
    var url: String? = null

    /**
     * 节点图标
     */
    var icon: String? = null
    /**
     * 是否打开
     */
    var isOpen: Int? = null

    /**
     * 顶部菜单id
     */
    var topMenuId: Int? = null

    /**
     * 顶部菜单code
     */
    var topMenuCode: String? = null

    /**
     * 子节点的集合
     */
    var children: List<MenuNode>? = null

    /**
     * 查询子节点时候的临时集合
     */
    val linkedList = ArrayList<MenuNode>()

    companion object {
        /**
         * 构建页面菜单列表
         */
        fun buildTitle(nodes: MutableList<MenuNode>): List<MenuNode> {
            if (nodes.size <= 0) {
                return nodes
            }
            //剔除非菜单
            nodes.removeIf { node -> node.ismenu!= YesOrNotEnum.Y.code }
            //对菜单排序，返回列表按菜单等级，序号的排序方式排列
            Collections.sort(nodes)
            return mergeList(nodes, nodes[nodes.size - 1].levels!!, null)
        }

        /**
         * 递归合并数组为子数组，最后返回第一层
         *
         * @param menuList
         * @param listMap
         * @return
         */
        private fun mergeList(menuList: MutableList<MenuNode>, rank: Int, listMap: Map<Long, List<MenuNode>>?): List<MenuNode> {
            //保存当次调用总共合并了多少元素
            var n: Int
            //保存当次调用总共合并出来的list
            val currentMap = mutableMapOf<Long, MutableList<MenuNode>>()
            //由于按等级从小到大排序，需要从后往前排序
            //判断该节点是否属于当前循环的等级,不等于则跳出循环
            n = menuList.size - 1
            while (n >= 0 && menuList[n].levels == rank) {
                //判断之前的调用是否有返回以该节点的id为key的map，有则设置为children列表。
                if (listMap != null && listMap[menuList[n].id] != null) {
                    menuList.get(n).children = listMap.get(menuList[n].id)
                }

                if (menuList[n].parentId != null && menuList[n].parentId != 0L) {
                    //判断当前节点所属的pid是否已经创建了以该pid为key的键值对，没有则创建新的链表
                    currentMap.computeIfAbsent(menuList[n].parentId!!) { k -> LinkedList() }
                    //将该节点插入到对应的list的头部
                    var menuNodeList  = currentMap.get(menuList[n].parentId)
                    menuNodeList?.add(0, menuList[n])
                }
                n--
            }
            return if (n < 0) {
                menuList
            } else {
                mergeList(menuList.subList(0, n + 1), menuList[n].levels!!, currentMap)
            }
        }
    }

}