package com.ydj.stone.modular.system.controller

import com.ydj.stone.core.enums.ManagerStatus
import com.ydj.stone.core.reqres.request.system.user.UserListReq
import com.ydj.stone.core.reqres.request.system.user.UserReq
import com.ydj.stone.core.reqres.response.ErrorResponseData
import com.ydj.stone.core.reqres.response.ResponseData
import com.ydj.stone.core.reqres.response.SuccessResponseData
import com.ydj.stone.modular.system.command.UserCommand
import com.ydj.stone.modular.system.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

/**
 * 用户管理
 */
@RequestMapping(value = "/user")
@Controller
class UserController {
    private val LOGGER = LoggerFactory.getLogger(UserController::class.java)

    @Autowired
    private lateinit var userCommand: UserCommand

    @Autowired
    private lateinit var userService: UserService
    /**
     * 跳转到用户管理列表页
     */
    @RequestMapping(value = "/toList", method = arrayOf(RequestMethod.GET))
    fun toList() = "/system/user/user_list"

    /**
     * 跳转到用户新增/编辑页面
     */
    @RequestMapping(value = "/toEdit", method = arrayOf(RequestMethod.GET))
    fun toEdit(@RequestParam(value = "userId", required = false) userId: Int?, mv: Model): String {
        userId?.let {
            val user = userCommand.queryUser(userId)
        mv.addAttribute("user", user)
        }
        return "/system/user/user_edit"
    }

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping(value = "/list", method = arrayOf(RequestMethod.POST))
    fun list(@RequestBody req: UserListReq): ResponseData {

        return SuccessResponseData(userCommand.list(req))
    }

    /**
     * 添加/编辑
     */
    @ResponseBody
    @RequestMapping(value = "/edit", method = arrayOf(RequestMethod.POST))
    fun edit(@RequestBody req: UserReq): ResponseData{
        userCommand.edit(req)
        return SuccessResponseData(true)
    }

    /**
     * 校验有户名
     */
    @ResponseBody
    @RequestMapping(value = "/checkAccount", method = arrayOf(RequestMethod.POST))
    fun checkAccount(@RequestBody req: UserReq): ResponseData{
        return if (req.account == null) {
            LOGGER.error("[checkAccount] account counld not be null")
            ErrorResponseData(500, "用户名不能为空")
        } else {
            SuccessResponseData(userCommand.checkAccount(req.account!!, req.userId))
        }
    }

    /**
     * 删除用户
     */
    @ResponseBody
    @RequestMapping(value = "/deleteUser", method = arrayOf(RequestMethod.POST))
    fun deleteUser(@RequestBody req: UserReq): ResponseData{
        return if(req.userId == null){
            ErrorResponseData(500, "用户Id不能为空")
        }else{
            userCommand.changeUserStatus(req.userId!!, ManagerStatus.DELETED.code)
            SuccessResponseData(true)
        }
    }

    /**
     * 冻结/解冻用户
     */
    @ResponseBody
    @RequestMapping(value = "/useableUser", method = arrayOf(RequestMethod.POST))
    fun useableUser(@RequestBody req: UserReq): ResponseData{
        return if(req.userId == null || req.status == null){
            ErrorResponseData(500, "参数错误")
        }else{
            userCommand.changeUserStatus(req.userId!!, req.status!!)
            SuccessResponseData(true)
        }
    }
}