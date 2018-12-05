package com.ydj.stone.modular.system.service.impl;

import cn.hutool.core.util.StrUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.ydj.stone.modular.system.entity.Dict;
import com.ydj.stone.modular.system.mapper.DictMapper;
import com.ydj.stone.modular.system.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ydj.stone.core.enums.BizExceptionEnum
import com.ydj.stone.core.exception.ServiceException
import com.ydj.stone.core.util.ValidateUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional
import java.util.ArrayList
import java.util.HashMap

/**
 * <p>
 * 字典表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class DictServiceImpl : ServiceImpl<DictMapper, Dict>(), DictService {

    @Autowired
    lateinit var dictMapper: DictMapper

    @Transactional
    override fun addDict(dictCode: String, dictName: String, dictTips: String, dictValues: String) {
        //判断有没有该字典
        val dicts = dictMapper.selectList(QueryWrapper<Dict>().eq("code", dictCode).eq("pid", 0))
        if (dicts != null && dicts.size > 0) {
            throw ServiceException(BizExceptionEnum.DICT_EXISTED)
        }

        //解析dictValues
        val items = parseKeyValue(dictValues)

        //添加字典
        val dict = Dict()
        dict.name = dictName
        dict.code = dictCode
        dict.tips = dictTips
        dict.num = 0
        dict.pid = 0

        this.dictMapper.insert(dict)

        //添加字典条目
        for (item in items) {
            val code = item.get(MUTI_STR_CODE)
            val name = item.get(MUTI_STR_NAME)
            val num = item.get(MUTI_STR_NUM)
            val itemDict = Dict()
            itemDict.pid = dict.id
            itemDict.code = code
            itemDict.name = name

            try {
                itemDict.num = Integer.valueOf(num)
            } catch (e: NumberFormatException) {
                throw ServiceException(BizExceptionEnum.DICT_MUST_BE_NUMBER)
            }

            this.dictMapper.insert(itemDict)
        }
    }

    @Transactional
    override fun editDict(dictId: Int?, dictCode: String, dictName: String, dictTips: String, dicts: String) {
        //删除之前的字典
        this.delteDict(dictId)

        //重新添加新的字典
        this.addDict(dictCode, dictName, dictTips, dicts)
    }

    @Transactional
    override fun delteDict(dictId: Int?) {
        //删除这个字典的子词典
        var dictEntityWrapper= QueryWrapper<Dict>()
        dictEntityWrapper = dictEntityWrapper.eq("pid", dictId)
        dictMapper.delete(dictEntityWrapper)

        //删除这个词典
        dictMapper.deleteById(dictId)
    }

    override fun selectByCode(code: String): List<Dict> {
        return this.baseMapper.selectByCode(code)
    }

    override fun selectByParentCode(code: String): List<Dict> {
        return this.baseMapper.selectByParentCode(code)
    }


    override fun list(conditiion: String): List<Map<String, Any>> {
        return this.baseMapper.list(conditiion)
    }


    /**
     * 每个条目之间的分隔符
     */
    val ITEM_SPLIT = ";"

    /**
     * 属性之间的分隔符
     */
    val ATTR_SPLIT = ":"

    /**
     * 拼接字符串的id
     */
    val MUTI_STR_ID = "ID"

    /**
     * 拼接字符串的CODE
     */
    val MUTI_STR_CODE = "CODE"

    /**
     * 拼接字符串的NAME
     */
    val MUTI_STR_NAME = "NAME"

    /**
     * 拼接字符串的NUM
     */
    val MUTI_STR_NUM = "NUM"
    /**
     * 解析一个组合字符串(例如:  "1:启用;2:禁用;3:冻结"  这样的字符串)
     *
     * @author fengshuonan
     * @Date 2017/4/27 16:44
     */
    fun parseKeyValue(mutiString: String): List<Map<String, String>> {
        if (ValidateUtil.isEmpty(mutiString)) {
            return ArrayList()
        } else {
            val results = ArrayList<Map<String, String>>()
            val items = StrUtil.split(StrUtil.removeSuffix(mutiString, ITEM_SPLIT), ITEM_SPLIT)
            for (item in items) {
                val attrs = item.split(ATTR_SPLIT.toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                val itemMap = HashMap<String, String>()
                itemMap[MUTI_STR_CODE] = attrs[0]
                itemMap[MUTI_STR_NAME] = attrs[1]
                itemMap[MUTI_STR_NUM] = attrs[2]
                results.add(itemMap)
            }
            return results
        }
    }
}
