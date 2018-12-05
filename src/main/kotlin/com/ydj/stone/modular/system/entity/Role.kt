package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_role")
class Role : Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 序号
     */
    var num: Integer? = null
    /**
     * 父角色id
     */
    var pid: Integer? = null
    /**
     * 角色名称
     */
    var name: String? = null
    /**
     * 部门名称
     */
    var deptid: Integer? = null
    /**
     * 提示
     */
    var tips: String? = null
    /**
     * 保留字段(暂时没用）
     */
    var version: Integer? = null


    override fun toString(): String {
        return "Role{" +
        "id=" + id +
        ", num=" + num +
        ", pid=" + pid +
        ", name=" + name +
        ", deptid=" + deptid +
        ", tips=" + tips +
        ", version=" + version +
        "}"
    }
}
