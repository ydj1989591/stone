package com.ydj.stone.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.modular.system.entity.LoginLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 登录记录 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface LoginLogService : IService<LoginLog>{
    /**
     * 获取登录日志列表
     */
    fun getLoginLogs(page: Page<LoginLog>, beginTime: String, endTime: String, logName: String, orderByField: String, asc: Boolean): List<Map<String, Any>>
}
