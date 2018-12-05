package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.time.LocalDateTime
import java.util.*

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@TableName("sys_user")
class User : Serializable {

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    var id: Int? = null
    /**
     * 头像
     */
    var avatar: String? = null
    /**
     * 账号
     */
    var account: String? = null
    /**
     * 密码
     */
    var password: String? = null
    /**
     * md5密码盐
     */
    var salt: String? = null
    /**
     * 名字
     */
    var name: String? = null
    /**
     * 生日
     */
    var birthday: Date? = null
    /**
     * 性别（1：男 2：女）
     */
    var sex: Int? = null
    /**
     * 电子邮件
     */
    var email: String? = null
    /**
     * 电话
     */
    var phone: String? = null
    /**
     * 角色id
     */
    var roleid: String? = null
    /**
     * 部门id
     */
    var deptid: Int? = null
    /**
     * 状态(1：启用  2：冻结  3：删除）
     */
    var status: Int? = null
    /**
     * 创建时间
     */
    var createtime: Date? = null
    /**
     * 保留字段
     */
    var version: Int? = null


    override fun toString(): String {
        return "User{" +
        "id=" + id +
        ", avatar=" + avatar +
        ", account=" + account +
        ", password=" + password +
        ", salt=" + salt +
        ", name=" + name +
        ", birthday=" + birthday +
        ", sex=" + sex +
        ", email=" + email +
        ", phone=" + phone +
        ", roleid=" + roleid +
        ", deptid=" + deptid +
        ", status=" + status +
        ", createtime=" + createtime +
        ", version=" + version +
        "}"
    }
}
