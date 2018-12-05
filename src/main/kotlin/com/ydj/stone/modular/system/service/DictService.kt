package com.ydj.stone.modular.system.service;

import com.ydj.stone.modular.system.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 字典表 服务类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface DictService : IService<Dict>{
    /**
     * 添加字典
     */
    fun addDict(dictCode: String, dictName: String, dictTips: String, dictValues: String)

    /**
     * 编辑字典
     */
    fun editDict(dictId: Int?, dictCode: String, dictName: String, dictTips: String, dicts: String)

    /**
     * 删除字典
     */
    fun delteDict(dictId: Int?)

    /**
     * 根据编码获取词典列表
     */
    fun selectByCode(@Param("code") code: String): List<Dict>

    /**
     * 根据父类编码获取词典列表
     */
    fun selectByParentCode(@Param("code") code: String): List<Dict>

    /**
     * 查询字典列表
     */
    fun list(@Param("condition") conditiion: String): List<Map<String, Any>>
}
