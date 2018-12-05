package com.ydj.stone.modular.system.service.impl;

import com.ydj.stone.modular.system.entity.Test;
import com.ydj.stone.modular.system.mapper.TestMapper;
import com.ydj.stone.modular.system.service.TestService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class TestServiceImpl : ServiceImpl<TestMapper, Test>(), TestService {

}
