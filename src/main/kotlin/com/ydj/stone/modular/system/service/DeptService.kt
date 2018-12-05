package com.ydj.stone.modular.system.service;

import com.ydj.stone.modular.system.entity.Dept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ydj.stone.core.common.node.ZTreeNode
import com.ydj.stone.core.reqres.response.ZTreeResponse
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface DeptService : IService<Dept>{

    /**
     * 删除部门
     */
    fun deleteDept(deptId: Int?)

    /**
     * 获取ztree的节点列表
     */
    fun tree(pId: Int?): List<ZTreeResponse>

    /**
     * 获取所有部门列表
     */
    fun list(@Param("condition") condition: String): List<Map<String, Any>>

    /**
     * 根据部门名称查询
     */
    fun queryByLikeName(name: String?): List<ZTreeResponse>

}
