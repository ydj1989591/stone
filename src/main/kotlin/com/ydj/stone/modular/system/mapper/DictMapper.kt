package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 字典表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface DictMapper : BaseMapper<Dict>{

    /**
     * 根据编码获取词典列表
     */
    fun selectByCode(@Param("code") code: String): List<Dict>

    /**
     * 查询字典列表
     */
    fun list(@Param("condition") conditiion: String): List<Map<String, Any>>

    /**
     * 根据父类编码获取词典列表
     */
    fun selectByParentCode(@Param("code") code: String): List<Dict>
}
