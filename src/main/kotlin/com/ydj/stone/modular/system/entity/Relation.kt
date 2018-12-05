package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_relation")
class Relation : Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    /**
     * 菜单id
     */
    var menuid: Long? = null
    /**
     * 角色id
     */
    var roleid: Int? = null


    override fun toString(): String {
        return "Relation{" +
        "id=" + id +
        ", menuid=" + menuid +
        ", roleid=" + roleid +
        "}"
    }
}
