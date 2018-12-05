package com.ydj.stone.core.aop

import com.ydj.stone.config.properties.StoneProperties
import com.ydj.stone.core.enums.BizExceptionEnum
import com.ydj.stone.core.exception.InvalidKaptchaException
import com.ydj.stone.core.exception.ServiceException
import com.ydj.stone.core.log.LogManager
import com.ydj.stone.core.log.factory.LogTaskFactory
import com.ydj.stone.core.reqres.response.ErrorResponseData
import com.ydj.stone.core.shiro.ShiroKit
import com.ydj.stone.core.util.HttpContext
import org.apache.shiro.authc.AuthenticationException
import org.apache.shiro.authc.CredentialsException
import org.apache.shiro.authc.DisabledAccountException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.reflect.UndeclaredThrowableException

@ControllerAdvice
@Order(-1)
class GlobalExceptionHandler {

    private val log = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @Autowired
    lateinit var stoneProperties: StoneProperties
    /**
     * 拦截业务异常
     */
    @ExceptionHandler(ServiceException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun bussiness(e: ServiceException): ErrorResponseData{
        return ErrorResponseData(1,"")
    }

    /**
     * 用户未登录异常
     */
    @ExceptionHandler(AuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun unAuth(e: AuthenticationException, model: Model): String{
        log.error("用户未登录", e)
        model.addAttribute("kaptchaOnOff", stoneProperties.kaptchaOpen)
        return "login"
    }

    /**
     * 账号密码错误异常
     */
    @ExceptionHandler(CredentialsException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun accountLocked(e: CredentialsException?, model:Model):String{
        val username = HttpContext.getRequest().getParameter("username")
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "账号密码错误", HttpContext.getIp()))
        log.error("账号密码错误")
        model.addAttribute("tips", "账号密码错误")
        model.addAttribute("kaptchaOnOff", stoneProperties.kaptchaOpen)
        return "login"
    }

    /**
     * 验证码错误异常
     */
    @ExceptionHandler(InvalidKaptchaException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun credentials(e: InvalidKaptchaException, model: Model): String{
        val username = HttpContext.getRequest().getParameter("username")
        LogManager.me().executeLog(LogTaskFactory.loginLog(username, "验证码错误", HttpContext.getIp()))
        model.addAttribute("tips", "验证码错误")
        log.error("验证码错误")
        model.addAttribute("kaptchaOnOff", stoneProperties.kaptchaOpen)
        return "login"
    }

    /**
     * 无权访问该资源异常
     */
    @ExceptionHandler(UndeclaredThrowableException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    fun credentials(e: UndeclaredThrowableException): ErrorResponseData{
        HttpContext.getRequest().setAttribute("tip", "权限异常")
        log.error("权限异常！", e)
        return ErrorResponseData(BizExceptionEnum.NO_PERMITION.code, BizExceptionEnum.NO_PERMITION.message)
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun notFount(e: RuntimeException): ErrorResponseData{
        LogManager.me().executeLog(LogTaskFactory.exceptionLog(ShiroKit.getUser()?.id, e))
        HttpContext.getRequest().setAttribute("tip", "服务器未知运行时异常")
        log.error("运行时异常：", e)
        return ErrorResponseData(BizExceptionEnum.SERVER_ERROR.code, BizExceptionEnum.SERVER_ERROR.message)
    }
}