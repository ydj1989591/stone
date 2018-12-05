package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.OperationLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface OperationLogMapper : BaseMapper<OperationLog>{

    /**
     * 获取操作日志
     */
    fun getOperationLogs(@Param("page") page: Page<OperationLog>,
                         @Param("beginTime") beginTime: String,
                         @Param("endTime") endTime: String,
                         @Param("logName") logName: String,
                         @Param("logType") logType: String,
                         @Param("orderByField") orderByField: String,
                         @Param("isAsc") isAsc: Boolean): List<Map<String, Any>>
}
