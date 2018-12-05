package com.ydj.stone.core.util

import cn.hutool.core.date.DateUtil
import cn.hutool.core.util.StrUtil
import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap
import com.ydj.stone.core.common.constants.dictmap.factory.DictFieldWarpperFactory
import java.beans.PropertyDescriptor
import java.lang.reflect.Method
import java.util.*

/**
 * 对比两个对象的变化的工具类
 */
class Contrast {

    companion object {
        //记录每个修改字段的分隔符
        val separator = ";;;"

        /**
         * 比较两个对象,并返回不一致的信息
         *
         * @author stylefeng
         * @Date 2017/5/9 19:34
         */
        fun contrastObj(pojo1: Any, pojo2: Any): String {
            var str = ""
            try {
                val clazz = pojo1.javaClass
                val fields = pojo1.javaClass.declaredFields
                var i = 1
                for (field in fields) {
                    if ("serialVersionUID" == field.name) {
                        continue
                    }
                    val pd = PropertyDescriptor(field.name, clazz)
                    val getMethod = pd.readMethod
                    var o1: Any? = getMethod.invoke(pojo1)
                    val o2 = getMethod.invoke(pojo2)
                    if (o1 == null || o2 == null) {
                        continue
                    }
                    if (o1 is Date) {
                        o1 = DateUtil.formatDate(o1 as Date?)
                    }
                    if (o1!!.toString() != o2.toString()) {
                        if (i != 1) {
                            str += separator
                        }
                        str += "字段名称" + field.name + ",旧值:" + o1 + ",新值:" + o2
                        i++
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return str
        }

        /**
         * 比较两个对象pojo1和pojo2,并输出不一致信息
         *
         * @author stylefeng
         * @Date 2017/5/9 19:34
         */
        @Throws(IllegalAccessException::class, InstantiationException::class)
        fun contrastObj(dictClass: Class<*>, key: String, pojo1: Any, pojo2: Map<String, String>): String {
            val dictMap = dictClass.newInstance() as AbstractDictMap
            var str = parseMutiKey(dictMap, key, pojo2) + separator
            try {
                val clazz = pojo1.javaClass
                val fields = pojo1.javaClass.declaredFields
                var i = 1
                for (field in fields) {
                    if ("serialVersionUID" == field.name) {
                        continue
                    }
                    val pd = PropertyDescriptor(field.name, clazz)
                    val getMethod = pd.readMethod
                    var o1: Any? = getMethod.invoke(pojo1)
                    var o2: Any? = pojo2[StrUtil.lowerFirst(getMethod.name.substring(3))]
                    if (o1 == null || o2 == null) {
                        continue
                    }
                    if (o1 is Date) {
                        o1 = DateUtil.formatDate(o1 as Date?)
                    } else if (o1 is Int) {
                        o2 = Integer.parseInt(o2.toString())
                    }
                    if (o1!!.toString() != o2.toString()) {
                        if (i != 1) {
                            str += separator
                        }
                        val fieldName = dictMap.get(field.name)
                        val fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(field.name)
                        if (fieldWarpperMethodName != null) {
                            val o1Warpper = DictFieldWarpperFactory.createFieldWarpper(o1, fieldWarpperMethodName)
                            val o2Warpper = DictFieldWarpperFactory.createFieldWarpper(o2, fieldWarpperMethodName)
                            str += "字段名称:$fieldName,旧值:$o1Warpper,新值:$o2Warpper"
                        } else {
                            str += "字段名称:$fieldName,旧值:$o1,新值:$o2"
                        }
                        i++
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return str
        }

        /**
         * 比较两个对象pojo1和pojo2,并输出不一致信息
         *
         * @author stylefeng
         * @Date 2017/5/9 19:34
         */
        @Throws(IllegalAccessException::class, InstantiationException::class)
        fun contrastObjByName(dictClass: Class<*>, key: String, pojo1: Any, pojo2: Map<String, String>): String {
            val dictMap = dictClass.newInstance() as AbstractDictMap
            var str = parseMutiKey(dictMap, key, pojo2) + separator
            try {
                val clazz = pojo1.javaClass
                val fields = pojo1.javaClass.declaredFields
                var i = 1
                for (field in fields) {
                    if ("serialVersionUID" == field.name) {
                        continue
                    }
                    var prefix = "get"
                    var prefixLength = 3
                    if (field.type.name == "java.lang.Boolean") {
                        prefix = "is"
                        prefixLength = 2
                    }
                    var getMethod: Method? = null
                    try {
                        getMethod = clazz.getDeclaredMethod(prefix + StrUtil.upperFirst(field.name))
                    } catch (e: java.lang.NoSuchMethodException) {
                        System.err.println("this className:" + clazz.name + " is not methodName: " + e.message)
                        continue
                    }

                    var o1: Any? = getMethod!!.invoke(pojo1)
                    var o2: Any? = pojo2[StrUtil.lowerFirst(getMethod.name.substring(prefixLength))]
                    if (o1 == null || o2 == null) {
                        continue
                    }
                    if (o1 is Date) {
                        o1 = DateUtil.formatDate(o1 as Date?)
                    } else if (o1 is Int) {
                        o2 = Integer.parseInt(o2.toString())
                    }
                    if (o1!!.toString() != o2.toString()) {
                        if (i != 1) {
                            str += separator
                        }
                        val fieldName = dictMap.get(field.name)
                        val fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(field.name)
                        if (fieldWarpperMethodName != null) {
                            val o1Warpper = DictFieldWarpperFactory.createFieldWarpper(o1, fieldWarpperMethodName)
                            val o2Warpper = DictFieldWarpperFactory.createFieldWarpper(o2, fieldWarpperMethodName)
                            str += "字段名称:$fieldName,旧值:$o1Warpper,新值:$o2Warpper"
                        } else {
                            str += "字段名称:$fieldName,旧值:$o1,新值:$o2"
                        }
                        i++
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return str
        }

        /**
         * 解析多个key(逗号隔开的)
         *
         * @author stylefeng
         * @Date 2017/5/16 22:19
         */
        fun parseMutiKey(dictMap: AbstractDictMap, key: String, requests: Map<String, String>): String {
            val sb = StringBuilder()
            if (key.contains(",")) {
                val keys = key.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                for (item in keys) {
                    val fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(item)
                    val value = requests[item]
                    if (fieldWarpperMethodName != null) {
                        val valueWarpper = DictFieldWarpperFactory.createFieldWarpper(value!!, fieldWarpperMethodName)
                        sb.append(dictMap.get(item) + "=" + valueWarpper + ",")
                    } else {
                        sb.append(dictMap.get(item) + "=" + value + ",")
                    }
                }
                return StrUtil.removeSuffix(sb.toString(), ",")
            } else {
                val fieldWarpperMethodName = dictMap.getFieldWarpperMethodName(key)
                val value = requests[key]
                if (fieldWarpperMethodName != null) {
                    val valueWarpper = DictFieldWarpperFactory.createFieldWarpper(value!!, fieldWarpperMethodName)
                    sb.append(dictMap.get(key) + "=" + valueWarpper)
                } else {
                    sb.append(dictMap.get(key) + "=" + value)
                }
                return sb.toString()
            }
        }

    }
}