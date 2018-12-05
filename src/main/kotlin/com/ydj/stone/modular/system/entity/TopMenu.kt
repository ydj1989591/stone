package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.*

/**
 * <p>
 * 顶部菜单表
 * </p>
 *
 * @author yidj
 * @since 2018-11-19
 */
@TableName("sys_top_menu")
class TopMenu : Serializable {

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 编码
     */
    var code: String? = null
    /**
     * 名称
     */
    var name: String? = null
    /**
     * icon图标
     */
    var icon: String? = null
    /**
     * 状态 1启用 2禁用
     */
    var status: Integer? = null
    /**
     * 排序
     */
    var sort: Integer? = null
    /**
     * 是否可用 1可用 0删除
     */
    var isValid: Integer? = null
    /**
     * 创建时间
     */
    var createTime: Date? = null
    /**
     * 创建人Id
     */
    var createUserId: Integer? = null
    /**
     * 最后更新时间
     */
    var updateTime: Date? = null
    /**
     * 最后更新人ID
     */
    var updateUserId: Integer? = null


    override fun toString(): String {
        return "TopMenu{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", icon=" + icon +
        ", status=" + status +
        ", sort=" + sort +
        ", isValid=" + isValid +
        ", createTime=" + createTime +
        ", createUserId=" + createUserId +
        ", updateTime=" + updateTime +
        ", updateUserId=" + updateUserId +
        "}"
    }
}
