package com.ydj.stone.modular.system.service.impl;

import com.ydj.stone.modular.system.entity.Notice;
import com.ydj.stone.modular.system.mapper.NoticeMapper;
import com.ydj.stone.modular.system.service.NoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通知表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class NoticeServiceImpl : ServiceImpl<NoticeMapper, Notice>(), NoticeService {

    override fun list(condition: String): List<Map<String, Any>> {
        return this.baseMapper.list(condition)
    }
}
