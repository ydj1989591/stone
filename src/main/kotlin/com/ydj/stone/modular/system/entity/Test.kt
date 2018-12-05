package com.ydj.stone.modular.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
class Test : Serializable {

    @TableId(value = "aaa", type = IdType.AUTO)
    var aaa: Integer? = null
    var bbb: String? = null


    override fun toString(): String {
        return "Test{" +
        "aaa=" + aaa +
        ", bbb=" + bbb +
        "}"
    }
}
