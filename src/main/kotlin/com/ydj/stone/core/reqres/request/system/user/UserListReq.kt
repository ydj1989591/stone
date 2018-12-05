package com.ydj.stone.core.reqres.request.system.user

import com.ydj.stone.core.reqres.request.PageReq

/**
 * 用户列表传参
 */
class UserListReq : PageReq() {

    /**
     * 用户Id
     */
    var userId : Int? = null

    /**
     * 用户姓名
     */
    var name : String? = null

    /**
     * 账号
     */
    var account: String? = null

    /**
     * 电子邮件
     */
    var email: String? = null
    /**
     * 电话
     */
    var phone: String? = null

}