package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.*

/**
 * <p>
 * 登录记录
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_login_log")
class LoginLog : Serializable {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Integer? = null
    /**
     * 日志名称
     */
    var logname: String? = null
    /**
     * 管理员id
     */
    var userid: Int? = null
    /**
     * 创建时间
     */
    var createtime: Date? = null
    /**
     * 是否执行成功
     */
    var succeed: String? = null
    /**
     * 具体消息
     */
    var message: String? = null
    /**
     * 登录ip
     */
    var ip: String? = null


    override fun toString(): String {
        return "LoginLog{" +
        "id=" + id +
        ", logname=" + logname +
        ", userid=" + userid +
        ", createtime=" + createtime +
        ", succeed=" + succeed +
        ", message=" + message +
        ", ip=" + ip +
        "}"
    }
}
