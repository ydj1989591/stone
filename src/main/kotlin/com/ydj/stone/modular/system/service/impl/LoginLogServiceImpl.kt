package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.ydj.stone.modular.system.entity.LoginLog;
import com.ydj.stone.modular.system.mapper.LoginLogMapper;
import com.ydj.stone.modular.system.service.LoginLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录记录 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class LoginLogServiceImpl : ServiceImpl<LoginLogMapper, LoginLog>(), LoginLogService {

    override fun getLoginLogs(page: Page<LoginLog>, beginTime: String, endTime: String, logName: String, orderByField: String, asc: Boolean): List<Map<String, Any>> {
        return this.baseMapper.getLoginLogs(page, beginTime, endTime, logName, orderByField, asc)
    }
}
