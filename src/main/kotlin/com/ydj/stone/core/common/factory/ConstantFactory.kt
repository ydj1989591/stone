package com.ydj.stone.core.common.factory

import cn.hutool.core.convert.Convert
import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.conditions.Wrapper
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.core.common.constants.cache.Cache
import com.ydj.stone.core.common.constants.cache.CacheKey
import com.ydj.stone.core.enums.ManagerStatus
import com.ydj.stone.core.enums.MenuStatus
import com.ydj.stone.core.log.LogObjectHolder
import com.ydj.stone.core.util.SpringContextHolder
import com.ydj.stone.core.util.ToolUtil
import com.ydj.stone.core.util.ValidateUtil
import com.ydj.stone.modular.system.entity.Dept
import com.ydj.stone.modular.system.entity.Dict
import com.ydj.stone.modular.system.entity.Menu
import com.ydj.stone.modular.system.mapper.*
import org.springframework.cache.annotation.Cacheable
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import java.util.ArrayList

/**
 * 常量的生产工厂
 */
@Component
@DependsOn("springContextHolder")
open class ConstantFactory  : IConstantFactory{
    private val roleMapper = SpringContextHolder.getBean(RoleMapper::class.java)
    private val deptMapper = SpringContextHolder.getBean(DeptMapper::class.java)
    private val dictMapper = SpringContextHolder.getBean(DictMapper::class.java)
    private val userMapper = SpringContextHolder.getBean(UserMapper::class.java)
    private val menuMapper = SpringContextHolder.getBean(MenuMapper::class.java)
    private val noticeMapper = SpringContextHolder.getBean(NoticeMapper::class.java)

    companion object {

        fun me(): ConstantFactory {
            return SpringContextHolder.getBean("constantFactory")
        }
    }

    /**
     * 根据用户id获取用户名称
     *
     * @author stylefeng
     * @Date 2017/5/9 23:41
     */
    override fun getUserNameById(userId: Int?): String? {
        val user = userMapper.selectById(userId)
        return if (user != null) {
            user!!.name
        } else {
            "--"
        }
    }

    /**
     * 根据用户id获取用户账号
     *
     * @author stylefeng
     * @date 2017年5月16日21:55:371
     */
    override fun getUserAccountById(userId: Int?): String? {
        val user = userMapper.selectById(userId)
        return if (user != null) {
            user!!.account
        } else {
            "--"
        }
    }

    /**
     * 通过角色ids获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.ROLES_NAME + "'+#roleIds")
    override fun getRoleName(roleIds: String): String {
        if (ValidateUtil.isEmpty(roleIds)) {
            return ""
        }
        val roles = Convert.toIntArray(roleIds)
        val sb = StringBuilder()
        for (role in roles) {
            val roleObj = roleMapper.selectById(role)
            if (ValidateUtil.isNotEmpty(roleObj) && ValidateUtil.isNotEmpty(roleObj.name)) {
                sb.append(roleObj.name).append(",")
            }
        }
        return StrUtil.removeSuffix(sb.toString(), ",")
    }

    /**
     * 通过角色id获取角色名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_NAME + "'+#roleId")
    override fun getSingleRoleName(roleId: Int?): String {
        if (0 == roleId) {
            return "--"
        }
        val roleObj = roleMapper.selectById(roleId)
        return if (ValidateUtil.isNotEmpty(roleObj) && ValidateUtil.isNotEmpty(roleObj.name)) {
            roleObj.name?: ""
        } else ""
    }

    /**
     * 通过角色id获取角色英文名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.SINGLE_ROLE_TIP + "'+#roleId")
    override fun getSingleRoleTip(roleId: Int?): String {
        if (0 == roleId) {
            return "--"
        }
        val roleObj = roleMapper.selectById(roleId)
        return if (ValidateUtil.isNotEmpty(roleObj) && ValidateUtil.isNotEmpty(roleObj.name)) {
            roleObj.tips ?:""
        } else ""
    }

    /**
     * 获取部门名称
     */
    @Cacheable(value = Cache.CONSTANT, key = "'" + CacheKey.DEPT_NAME + "'+#deptId")
    override fun getDeptName(deptId: Int?): String {
        val dept = deptMapper.selectById(deptId)
        return if (ValidateUtil.isNotEmpty(dept) && ValidateUtil.isNotEmpty(dept.fullname)) {
            dept.fullname ?: ""
        } else ""
    }

    /**
     * 获取菜单的名称们(多个)
     */
    override fun getMenuNames(menuIds: String): String {
        val menus = Convert.toIntArray(menuIds)
        val sb = StringBuilder()
        for (menu in menus) {
            val menuObj = menuMapper.selectById(menu)
            if (ValidateUtil.isNotEmpty(menuObj) && ValidateUtil.isNotEmpty(menuObj.name)) {
                sb.append(menuObj.name).append(",")
            }
        }
        return StrUtil.removeSuffix(sb.toString(), ",")
    }

    /**
     * 获取菜单名称
     */
    override fun getMenuName(menuId: Long?): String {
        if (ValidateUtil.isEmpty(menuId)) {
            return ""
        } else {
            val menu = menuMapper.selectById(menuId)
            return if (menu == null) {
                ""
            } else {
                menu!!.name ?: ""
            }
        }
    }

    /**
     * 获取菜单名称通过编号
     */
    override fun getMenuNameByCode(code: String): String {
        if (ValidateUtil.isEmpty(code)) {
            return ""
        } else {
            val queryWrapper = QueryWrapper<Menu>()
            queryWrapper.eq("code", code)
            val menu = menuMapper.selectOne(queryWrapper)
            return if (menu == null) {
                ""
            } else {
                menu!!.name ?: ""
            }
        }
    }

    /**
     * 获取字典名称
     */
    override fun getDictName(dictId: Int?): String {
        if (ValidateUtil.isEmpty(dictId)) {
            return ""
        } else {
            val dict = dictMapper.selectById(dictId)
            return if (dict == null) {
                ""
            } else {
                dict!!.name?: ""
            }
        }
    }

    /**
     * 获取通知标题
     */
    override fun getNoticeTitle(dictId: Int?): String {
        if (ValidateUtil.isEmpty(dictId)) {
            return ""
        } else {
            val notice = noticeMapper.selectById(dictId)
            return if (notice == null) {
                ""
            } else {
                notice!!.title?:""
            }
        }
    }

    /**
     * 根据字典名称和字典中的值获取对应的名称
     */
    override fun getDictsByName(name: String, `val`: Int?): String {
        val queryWrapper = QueryWrapper<Dict>()
        queryWrapper.eq("name",name)
        val dict = dictMapper.selectOne(queryWrapper)
        if (dict == null) {
            return ""
        } else {
            var wrapper = QueryWrapper<Dict>()
            wrapper = wrapper.eq("pid", dict!!.id)
            val dicts = dictMapper.selectList(wrapper)
            for (item in dicts) {
                if (item.name != null && (item.name)!!.equals(`val`)) {
                    return item.name ?: ""
                }
            }
            return ""
        }
    }

    /**
     * 获取性别名称
     */
    override fun getSexName(sex: Int?): String {
        return getDictsByName("性别", sex)
    }

    /**
     * 获取用户登录状态
     */
    override fun getStatusName(status: Int?): String {
        return ManagerStatus.valueOf(status)
    }

    /**
     * 获取菜单状态
     */
    override fun getMenuStatusName(status: Int?): String {
        return MenuStatus.valueOf(status)
    }

    /**
     * 查询字典
     */
    override fun findInDict(id: Int?): List<Dict>? {
        if (ValidateUtil.isEmpty(id)) {
            return null
        } else {
            val wrapper = QueryWrapper<Dict>()
            val dicts = dictMapper.selectList(wrapper.eq("pid", id))
            return dicts

        }
    }

    /**
     * 获取被缓存的对象(用户删除业务)
     */
    override fun getCacheObject(para: String): String {
        return LogObjectHolder.me().`object`.toString()
    }

    /**
     * 获取子部门id
     */
    override fun getSubDeptId(deptid: Int?): MutableList<Int> {
        var wrapper = QueryWrapper<Dept>()
        wrapper = wrapper.like("pids", "%[$deptid]%")
        val depts = this.deptMapper.selectList(wrapper)

        val deptids = mutableListOf<Int>()

        if (depts != null && depts!!.size > 0) {
            for (dept in depts) {
                deptids.add(dept.id?: 0)
            }
        }

        return deptids
    }

    /**
     * 获取所有父部门id
     */
    override fun getParentDeptIds(deptid: Int?): List<Int> {
        val dept = deptMapper.selectById(deptid)
        val pids = dept.pids
        val split = pids?.let { pids.split(",".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()}
        val parentDeptIds = ArrayList<Int>()
        for (s in split!!) {
            parentDeptIds.add(Integer.valueOf(StrUtil.removeSuffix(StrUtil.removePrefix(s, "["), "]")))
        }
        return parentDeptIds
    }

}