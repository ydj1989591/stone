package com.ydj.stone.modular.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.modular.system.entity.OperationLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 操作日志 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface OperationLogService : IService<OperationLog>{
    /**
     * 获取操作日志列表
     */
    fun getOperationLogs(page: Page<OperationLog>, beginTime: String, endTime: String, logName: String, s: String, orderByField: String, asc: Boolean): List<Map<String, Any>>
}
