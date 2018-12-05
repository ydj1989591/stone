package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.LoginLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 登录记录 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface LoginLogMapper : BaseMapper<LoginLog>{

    /**
     * 获取登录日志
     */
    fun getLoginLogs(@Param("page") page: Page<LoginLog>,
                     @Param("beginTime") beginTime: String,
                     @Param("endTime") endTime: String,
                     @Param("logName") logName: String,
                     @Param("orderByField") orderByField: String,
                     @Param("isAsc") isAsc: Boolean): List<Map<String, Any>>
}
