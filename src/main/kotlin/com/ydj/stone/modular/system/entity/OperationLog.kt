package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.*

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_operation_log")
class OperationLog : Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 日志类型
     */
    var logtype: String? = null
    /**
     * 日志名称
     */
    var logname: String? = null
    /**
     * 用户id
     */
    var userid: Int? = null
    /**
     * 类名称
     */
    var classname: String? = null
    /**
     * 方法名称
     */
    var method: String? = null
    /**
     * 创建时间
     */
    var createtime: Date? = null
    /**
     * 是否成功
     */
    var succeed: String? = null
    /**
     * 备注
     */
    var message: String? = null


    override fun toString(): String {
        return "OperationLog{" +
        "id=" + id +
        ", logtype=" + logtype +
        ", logname=" + logname +
        ", userid=" + userid +
        ", classname=" + classname +
        ", method=" + method +
        ", createtime=" + createtime +
        ", succeed=" + succeed +
        ", message=" + message +
        "}"
    }
}
