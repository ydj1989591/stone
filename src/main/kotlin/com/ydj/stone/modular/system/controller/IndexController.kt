package com.ydj.stone.modular.system.controller

import com.google.code.kaptcha.Constants
import com.ydj.stone.config.properties.StoneProperties
import com.ydj.stone.core.exception.InvalidKaptchaException
import com.ydj.stone.core.log.LogManager
import com.ydj.stone.core.log.factory.LogTaskFactory
import com.ydj.stone.core.reqres.response.ResponseData
import com.ydj.stone.core.reqres.response.SuccessResponseData
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.core.util.HttpContext
import org.apache.commons.lang3.StringUtils
import org.apache.shiro.authc.UsernamePasswordToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/")
class IndexController : BaseController(){

    @Autowired
    lateinit var stoneProperties: StoneProperties
    /**
     * 主页
     */
    @RequestMapping("/")
    fun blackboad()  = "index"

    /**
     * 登录页
     */
    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.GET))
    fun login(model: Model) :String{

        if (ShiroKit.isAuthenticated() || ShiroKit.getUser() != null) {
            return REDIRECT + "/"
        } else {
            model.addAttribute("kaptchaOnOff", stoneProperties.kaptchaOpen)
            return "login"
        }
    }

    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.POST))
    fun loginCheck(): String{
        val username = super.getPara("username")?.trim()
        val password = super.getPara("password")?.trim()
        val remember = super.getPara("remember")?.trim()

        HttpContext.getRequest()
        if(stoneProperties.kaptchaOpen!!){
            val kaptcha = super.getPara("kaptcha")?.trim()
            val code = super.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY) as? String
            if(!StringUtils.equalsIgnoreCase(kaptcha, code)){
                throw InvalidKaptchaException()
            }
        }

        val currentUser = ShiroKit.getSubject()
        val token = UsernamePasswordToken(username, password!!.toCharArray())
        if(StringUtils.equals(remember, "on") )  token.isRememberMe  else token.isRememberMe = false

        currentUser.login(token)
        val shiroUser = ShiroKit.getUser()
        super.getSession().setAttribute("shiroUse", shiroUser)
        super.getSession().setAttribute("username", shiroUser?.account)

        LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser?.id!!, HttpContext.getIp()))

        ShiroKit.getSession().setAttribute("sessionFlag", true)

        return REDIRECT + "/"


    }

    @ResponseBody
    @RequestMapping(value = "/testLayer", method = arrayOf(RequestMethod.POST))
    fun testLayer(): ResponseData{
        Thread.sleep(3000L)
        return SuccessResponseData()
    }
}