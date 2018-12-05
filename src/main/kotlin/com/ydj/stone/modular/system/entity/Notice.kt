package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 通知表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_notice")
class Notice : Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 标题
     */
    var title: String? = null
    /**
     * 类型
     */
    var type: Integer? = null
    /**
     * 内容
     */
    var content: String? = null
    /**
     * 创建时间
     */
    var createtime: LocalDateTime? = null
    /**
     * 创建人
     */
    var creater: Integer? = null


    override fun toString(): String {
        return "Notice{" +
        "id=" + id +
        ", title=" + title +
        ", type=" + type +
        ", content=" + content +
        ", createtime=" + createtime +
        ", creater=" + creater +
        "}"
    }
}
