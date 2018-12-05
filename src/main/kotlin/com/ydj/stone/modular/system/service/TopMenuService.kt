package com.ydj.stone.modular.system.service;

import com.ydj.stone.modular.system.entity.TopMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 顶部菜单表 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-11-19
 */
interface TopMenuService : IService<TopMenu>{

    /**
     * 查询所有的顶部菜单
     */
    fun listAll(): MutableList<TopMenu>
}
