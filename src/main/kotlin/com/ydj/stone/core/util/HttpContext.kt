package com.ydj.stone.core.util

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 快捷获取HttpServletRequest,HttpServletResponse
 */
class HttpContext {

    companion object {
        /**
         * 获取请求的ip地址
         */
        fun getIp():String{
            val request = this.getRequest()
            return if (null == request) "127.0.0.1" else request.remoteHost
        }

        /**
         * 获取当前请求的Request对象
         */
        fun getRequest():HttpServletRequest{
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            return requestAttributes?.let { requestAttributes.request }
        }

        /**
         * 获取当前请求的Response对象
         */
        fun getResponse(): HttpServletResponse?{
            val requestAttributes = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
            return requestAttributes?.let { requestAttributes.response }
        }

        /**
         * 获取所有请求的值
         */
        fun getRequestParameters(): MutableMap<String, String>{
            var values = mutableMapOf<String,String>()
            val request = this.getRequest()
            if(null == request) return values

            val enums = request.parameterNames
            while (enums.hasMoreElements()){
                val parameterName = enums.nextElement() as String
                values.put(parameterName, request.getParameter(parameterName))
            }
            return values

        }
    }
}