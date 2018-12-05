package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_menu")
class Menu : Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Long? = null
    /**
     * 菜单编号
     */
    var code: String? = null
    /**
     * 菜单父编号
     */
    var pcode: String? = null
    /**
     * 当前菜单的所有父菜单编号
     */
    var pcodes: String? = null
    /**
     * 菜单名称
     */
    var name: String? = null
    /**
     * 菜单图标
     */
    var icon: String? = null
    /**
     * url地址
     */
    var url: String? = null
    /**
     * 菜单排序号
     */
    var num: Integer? = null
    /**
     * 菜单层级
     */
    var levels: Integer? = null
    /**
     * 是否是菜单（1：是  0：不是）
     */
    var ismenu: Integer? = null
    /**
     * 备注
     */
    var tips: String? = null
    /**
     * 菜单状态 :  1:启用   0:不启用
     */
    var status: Integer? = null
    /**
     * 是否打开:    1:打开   0:不打开
     */
    var isopen: Integer? = null
    /**
     * 顶部菜单Id
     */
    var topMenuId: Integer? = null

    override fun toString(): String {
        return "Menu{" +
        "id=" + id +
        ", code=" + code +
        ", pcode=" + pcode +
        ", pcodes=" + pcodes +
        ", name=" + name +
        ", icon=" + icon +
        ", url=" + url +
        ", num=" + num +
        ", levels=" + levels +
        ", ismenu=" + ismenu +
        ", tips=" + tips +
        ", status=" + status +
        ", isopen=" + isopen +
        ", topMenuId=" + topMenuId +
        "}"
    }
}
