package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.modular.system.entity.TopMenu;
import com.ydj.stone.modular.system.mapper.TopMenuMapper;
import com.ydj.stone.modular.system.service.TopMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 顶部菜单表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-11-19
 */
@Service
open class TopMenuServiceImpl : ServiceImpl<TopMenuMapper, TopMenu>(), TopMenuService {

    override fun listAll(): MutableList<TopMenu> {
        val queryWrapper = QueryWrapper<TopMenu>();
        queryWrapper.eq("status", 1).eq("is_valid",1).orderByAsc("sort");
        return list(queryWrapper)
    }

}
