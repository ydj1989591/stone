package com.ydj.stone.modular.system.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 报销表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_expense")
class Expense : Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 报销金额
     */
    var money: BigDecimal? = null
    /**
     * 描述
     */
    var desc: String? = null
    /**
     * 创建时间
     */
    var createtime: LocalDateTime? = null
    /**
     * 状态: 1.待提交  2:待审核   3.审核通过 4:驳回
     */
    var state: Integer? = null
    /**
     * 用户id
     */
    var userid: Integer? = null
    /**
     * 流程定义id
     */
    @TableField("processId")
    var processId: String? = null


    override fun toString(): String {
        return "Expense{" +
        "id=" + id +
        ", money=" + money +
        ", desc=" + desc +
        ", createtime=" + createtime +
        ", state=" + state +
        ", userid=" + userid +
        ", processId=" + processId +
        "}"
    }
}
