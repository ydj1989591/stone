package com.ydj.stone.modular.system.service;

import com.ydj.stone.modular.system.entity.Notice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 通知表 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface NoticeService : IService<Notice>{
    /**
     * 获取通知列表
     */
    fun list(condition: String): List<Map<String, Any>>
}
