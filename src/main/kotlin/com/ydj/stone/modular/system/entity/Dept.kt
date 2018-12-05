package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_dept")
class Dept : Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    /**
     * 排序
     */
    var num: Int? = null
    /**
     * 父部门id
     */
    var pid: Int? = null
    /**
     * 父级ids
     */
    var pids: String? = null
    /**
     * 简称
     */
    var simplename: String? = null
    /**
     * 全称
     */
    var fullname: String? = null
    /**
     * 提示
     */
    var tips: String? = null
    /**
     * 版本（乐观锁保留字段）
     */
    var version: Int? = null


    override fun toString(): String {
        return "Dept{" +
        "id=" + id +
        ", num=" + num +
        ", pid=" + pid +
        ", pids=" + pids +
        ", simplename=" + simplename +
        ", fullname=" + fullname +
        ", tips=" + tips +
        ", version=" + version +
        "}"
    }
}
