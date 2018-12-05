package com.ydj.stone.core.common.node

/**
 * jquery ztree 插件的节点
 */
class ZTreeNode {

    /**
     * 节点id
     */
    var id : Long ? = null

    /**
     * 父节点id
     */
    var pId : Long ? = null

    /**
     * 节点名称
     */
    var name : String ? = null

    /**
     * 是否打开节点
     */
    var open : Boolean ? = false

    /**
     * 是否被选中
     */
    var checked : Boolean ? = false



}