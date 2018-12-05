package com.ydj.stone.modular.system.controller

import cn.hutool.json.JSONUtil
import com.alibaba.fastjson.JSONObject
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.core.reqres.response.ResponseData
import com.ydj.stone.core.reqres.response.SuccessResponseData
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.modular.system.command.MenuCommand
import com.ydj.stone.modular.system.entity.TopMenu
import com.ydj.stone.modular.system.service.MenuService
import com.ydj.stone.modular.system.service.TopMenuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * 菜单controller
 */
@RequestMapping("/menu")
@RestController
class MenuController {

    @Autowired
    private lateinit var menuService: MenuService;
    @Autowired
    private lateinit var topMenuService: TopMenuService;
    @Autowired
    private lateinit var menuCommand: MenuCommand

    /**
     * 获取菜单列表(首页用)
     */
    @ResponseBody
    @RequestMapping(value = "/menuTreeList",method = arrayOf(RequestMethod.POST))
    fun menuTreeList() :ResponseData {
        val list =  menuService.menuTreeList()
        return SuccessResponseData(list)
    }

    /**
     * 返回顶部菜单列表
     */
    @RequestMapping(value = "/queryTopMenuList", method = arrayOf(RequestMethod.POST))
    fun queryTopMenuList():ResponseData{
        return SuccessResponseData(topMenuService.listAll())
    }

    /**
     * 查询索引的菜单
     */
    @RequestMapping(value = "/queryMenuList", method = arrayOf(RequestMethod.POST))
    fun queryMenuList():ResponseData{
        return SuccessResponseData(menuCommand.queryMenuList(ShiroKit.getUser()?.roleList!!))

    }
}