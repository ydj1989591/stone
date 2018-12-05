package com.ydj.stone.modular.system.command

import com.ydj.stone.core.reqres.response.ZTreeResponse
import com.ydj.stone.modular.system.service.DeptService
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DepartmentCommand {

    @Autowired
    private lateinit var deptService: DeptService

    fun queryTreeList(name: String?): List<ZTreeResponse>{
        val allZTreeResponseList = deptService.tree(null)//查询所有的部门

        val zTreeResponseList = allZTreeResponseList.filter { StringUtils.containsIgnoreCase(it.name, name ) }
        if(StringUtils.isBlank(name)) return zTreeResponseList



        val zTreeList = mutableSetOf<ZTreeResponse>()

        zTreeList.addAll(zTreeResponseList)

        zTreeResponseList?.forEach{
            zTreeList.addAll(queryTreeList(it.id!!, allZTreeResponseList))
        }
        return zTreeList.toList()
    }

    fun queryTreeList(pid: Int, allList: List<ZTreeResponse>): List<ZTreeResponse>{
        val zTreeResponseList = allList.filter { it.pId == pid }

        val zTreeList = mutableSetOf<ZTreeResponse>()

        zTreeList.addAll(zTreeResponseList)

        zTreeResponseList?.forEach{
            zTreeList.addAll(queryTreeList(it.id!!, allList))
        }
        return zTreeList.toList()
    }
}