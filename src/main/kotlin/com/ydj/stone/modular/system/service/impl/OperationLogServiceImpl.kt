package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.modular.system.entity.OperationLog;
import com.ydj.stone.modular.system.mapper.OperationLogMapper;
import com.ydj.stone.modular.system.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class OperationLogServiceImpl : ServiceImpl<OperationLogMapper, OperationLog>(), OperationLogService {

    override fun getOperationLogs(page: Page<OperationLog>, beginTime: String, endTime: String, logName: String, s: String, orderByField: String, asc: Boolean): List<Map<String, Any>> {
        return this.baseMapper.getOperationLogs(page, beginTime, endTime, logName, s, orderByField, asc)
    }
}
