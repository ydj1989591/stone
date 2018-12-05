package com.ydj.stone.core.interceptor

import com.ydj.stone.core.shiro.ShiroKit
import org.apache.shiro.web.filter.AccessControlFilter
import org.apache.shiro.web.util.WebUtils
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

class StoneUserFilter : AccessControlFilter() {

    /**
     * Returns `true` if the request is a
     * [loginRequest][.isLoginRequest] or
     * if the current [subject][.getSubject]
     * is not `null`, `false` otherwise.
     *
     * @return `true` if the request is a
     * [loginRequest][.isLoginRequest] or
     * if the current [subject][.getSubject]
     * is not `null`, `false` otherwise.
     */
    override fun isAccessAllowed(request: ServletRequest, response: ServletResponse, mappedValue: Any?): Boolean {
        if (isLoginRequest(request, response)) {
            return true
        } else {
            val subject = getSubject(request, response)
            // If principal is not null, then the user is known and should be allowed access.
            return subject.principal != null
        }
    }

    /**
     * This default implementation simply calls
     * [saveRequestAndRedirectToLogin][.saveRequestAndRedirectToLogin]
     * and then immediately returns `false`, thereby preventing the chain from continuing so the redirect may
     * execute.
     */
    @Throws(Exception::class)
    override fun onAccessDenied(request: ServletRequest, response: ServletResponse): Boolean {
        val httpServletRequest = WebUtils.toHttp(request)
        val httpServletResponse = WebUtils.toHttp(response)

        /**
         * 如果是ajax请求则不进行跳转
         */
        if (httpServletRequest.getHeader("x-requested-with") != null && httpServletRequest.getHeader("x-requested-with").equals("XMLHttpRequest", ignoreCase = true)) {
            httpServletResponse.setHeader("sessionstatus", "timeout")
            return false
        } else {

            /**
             * 第一次点击页面
             */
            val referer = httpServletRequest.getHeader("Referer")
            if (referer == null) {
                saveRequestAndRedirectToLogin(request, response)
                return false
            } else {

                /**
                 * 从别的页面跳转过来的
                 */
                if (ShiroKit.getSession().getAttribute("sessionFlag") == null) {
                   // httpServletRequest.setAttribute("tips", "session超时")
                    httpServletRequest.getRequestDispatcher("/login").forward(request, response)
                    return false
                } else {
                    saveRequestAndRedirectToLogin(request, response)
                    return false
                }
            }
        }
    }
}