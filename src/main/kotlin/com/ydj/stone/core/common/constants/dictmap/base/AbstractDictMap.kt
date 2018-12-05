package com.ydj.stone.core.common.constants.dictmap.base

/**
 * 字典映射抽象类
 */
abstract class AbstractDictMap {

    protected var dictory = mutableMapOf<String,String>()
    protected var fieldWarpperDictory =  mutableMapOf<String,String>()

    init {
        put("id", "主键id")
        init()
        initBeWrapped()
    }

    /**
     * 初始化字段英文名称和中文名称对应的字典
     *
     * @author stylefeng
     * @Date 2017/5/9 19:39
     */
    abstract fun init()

    /**
     * 初始化需要被包装的字段(例如:性别为1:男,2:女,需要被包装为汉字)
     *
     * @author stylefeng
     * @Date 2017/5/9 19:35
     */
    protected abstract fun initBeWrapped()

    operator fun get(key: String): String {
        return this.dictory[key] ?: ""
    }

    fun put(key: String, value: String) {
        this.dictory[key] = value
    }

    fun getFieldWarpperMethodName(key: String): String {
        return this.fieldWarpperDictory[key] ?: ""
    }

    fun putFieldWrapperMethodName(key: String, methodName: String) {
        this.fieldWarpperDictory[key] = methodName
    }
}