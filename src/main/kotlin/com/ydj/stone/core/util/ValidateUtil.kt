package com.ydj.stone.core.util

/**
 * 空参数校验工具类
 */
open class ValidateUtil {

    companion object {

        /**
         * 对象是否不为空(新增)
         */
        fun isNotEmpty(o: Any?):Boolean{
            return !isEmpty(o)
        }

        /**
         * 对象是否为空
         */
         fun isEmpty(o: Any?) : Boolean{
            if(o == null) return true

            if(o is String){
                if(o.toString().trim().equals("")) return true
            } else if(o is List<*>){
                if(o.size == 0) return true
            } else if(o is MutableList<*>){
                if (o.size == 0) return true
            }else if(o is Map<*, *>){
                if (o.size == 0) return true
            }else if(o is MutableMap<*, *>){
                if(o.size == 0) return true
            }else if(o is Set<*>){
                if(o.size == 0) return true
            }else if(o is MutableSet<*>){
                if(o.size == 0) return true
            }else if(o is Array<*>){
                if(o.size == 0 ) return true
            }
            return false
        }

        /**
         * 对象组中是否存在空对象
         */
        fun isOneEmpty(vararg os: Any):Boolean{
            for(o in os){
                if(isEmpty(o)){
                    return true
                }
            }
            return false
        }

        /**
         * 对象组中是否全是空对象
         */
        fun isAllEmpty(vararg os: Any):Boolean{
            for(o in os){
                if(isNotEmpty(o)){
                    return false
                }
            }
            return true
        }
    }
}