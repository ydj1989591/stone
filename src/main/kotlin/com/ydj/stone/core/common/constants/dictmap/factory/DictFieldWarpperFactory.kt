package com.ydj.stone.core.common.constants.dictmap.factory

import com.ydj.stone.core.common.factory.ConstantFactory
import com.ydj.stone.core.common.factory.IConstantFactory
import com.ydj.stone.core.enums.BizExceptionEnum
import com.ydj.stone.core.exception.ServiceException

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 */
class DictFieldWarpperFactory {

    companion object {
        fun createFieldWarpper(parameter: Any, methodName: String): Any {
            val constantFactory = ConstantFactory.me()
            try {
                val method = IConstantFactory::class.java!!.getMethod(methodName, parameter.javaClass)
                return method.invoke(constantFactory, parameter)
            } catch (e: Exception) {
                try {
                    val method = IConstantFactory::class.java!!.getMethod(methodName, Int::class.java)
                    return method.invoke(constantFactory, Integer.parseInt(parameter.toString()))
                } catch (e1: Exception) {
                    throw ServiceException(BizExceptionEnum.ERROR_WRAPPER_FIELD)
                }

            }

        }
    }
}