package com.ydj.stone.modular.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.modular.system.entity.Dept;
import com.ydj.stone.modular.system.mapper.DeptMapper;
import com.ydj.stone.modular.system.service.DeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydj.stone.core.common.node.ZTreeNode
import com.ydj.stone.core.reqres.response.ZTreeResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class DeptServiceImpl : ServiceImpl<DeptMapper, Dept>(), DeptService {

    @Autowired
    lateinit var deptMapper: DeptMapper

    @Transactional
    override fun deleteDept(deptId: Int?) {
        val dept = deptMapper.selectById(deptId)

        var wrapper = QueryWrapper<Dept>()
        wrapper = wrapper.like("pids", "%[" + dept.id + "]%")
        val subDepts = deptMapper.selectList(wrapper)
        subDepts?.let {
                 val subDeptIdList = subDepts.map{it.id}
            deptMapper.deleteBatchIds(subDeptIdList)
        }
        deptMapper.deleteById(deptId)
    }

    override fun tree(pId: Int?): List<ZTreeResponse> {
        return this.baseMapper.tree(pId)
    }

    override fun list(condition: String): List<Map<String, Any>> {
        return this.baseMapper.list(condition)
    }

    override fun queryByLikeName(name: String?): List<ZTreeResponse> {
        return this.baseMapper.queryByLikeName(name)
    }
}
