package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.Dept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ydj.stone.core.common.node.ZTreeNode
import com.ydj.stone.core.reqres.response.ZTreeResponse
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 部门表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface DeptMapper : BaseMapper<Dept>{

    /**
     * 获取ztree的节点列表
     */
    fun tree(@Param("pId") pId: Int?): List<ZTreeResponse>

    /**
     * 获取所有部门列表
     */
    fun list(@Param("condition") condition: String): List<Map<String, Any>>

    /**
     * 根据部门名称获取节点列表
     */
    fun queryByLikeName(@Param("name") name: String?): List<ZTreeResponse>
}
