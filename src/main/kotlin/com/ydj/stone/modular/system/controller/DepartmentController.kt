package com.ydj.stone.modular.system.controller

import com.ydj.stone.core.reqres.request.system.dept.DeptTreeReq
import com.ydj.stone.core.reqres.response.ResponseData
import com.ydj.stone.core.reqres.response.SuccessResponseData
import com.ydj.stone.core.reqres.response.ZTreeResponse
import com.ydj.stone.modular.system.command.DepartmentCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@RequestMapping(value = "/department")
@Controller
class DepartmentController {

    @Autowired
    private lateinit var departmentCommand: DepartmentCommand

    /**
     * 查询部门树
     */
    @ResponseBody
    @RequestMapping(value = "/queryTreeList", method = arrayOf(RequestMethod.POST))
    fun queryTreeList(@RequestBody req: DeptTreeReq):List<ZTreeResponse>{

        return departmentCommand.queryTreeList(req.name)
    }


}