package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 字典表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_dict")
class Dict : Serializable {

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
     * 父级字典
     */
    var pid: Int? = null
    /**
     * 名称
     */
    var name: String? = null
    /**
     * 提示
     */
    var tips: String? = null
    /**
     * 值
     */
    var code: String? = null


    override fun toString(): String {
        return "Dict{" +
        "id=" + id +
        ", num=" + num +
        ", pid=" + pid +
        ", name=" + name +
        ", tips=" + tips +
        ", code=" + code +
        "}"
    }
}
