package com.ydj.stone.modular.system.controller

import com.ydj.stone.core.enums.CoreExceptionEnum
import com.ydj.stone.core.exception.ServiceException
import com.ydj.stone.core.reqres.response.SuccessResponseData
import com.ydj.stone.core.util.HttpContext
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.io.*
import javax.servlet.http.Cookie

/**
 * 控制器基类，封装一些控制器常用的方法
 */
open class BaseController {

    protected val REDIRECT = "redirect:"
    protected val FORWARD = "forward:"

    companion object {
        protected val SUCCESS_TIP = SuccessResponseData()
    }

    protected fun getHttpServletRequest() = HttpContext.getRequest()

    protected fun getHttpServletResponse() = HttpContext.getResponse()

    protected fun getSession() = getHttpServletRequest().getSession()

    protected fun getSession(flag: Boolean) = getHttpServletRequest().getSession(flag)

    protected fun getPara(name: String) = getHttpServletRequest().getParameter(name)

    protected fun setAttr(name:String, value: Any) = getHttpServletRequest().setAttribute(name, value)

    /**
     * 删除cookie
     */
    protected fun deleteCookieByName(cookieName: String){
        val cookies = this.getHttpServletRequest().cookies
        for (cookie in cookies) {
            if (cookie.name == cookieName) {
                val temp = Cookie(cookie.name, "")
                temp.maxAge = 0
                this.getHttpServletResponse()!!.addCookie(temp)
            }
        }
    }

    /**
     * 删除所有cookie
     */
    protected fun deleteAllCookie(){
        val cookies = this.getHttpServletRequest().cookies
        for (cookie in cookies) {
            val temp = Cookie(cookie.name, "")
            temp.maxAge = 0
            this.getHttpServletResponse()!!.addCookie(temp)
        }
    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected fun renderFile(fileName: String, filePath: String): ResponseEntity<InputStreamResource> {
        try {
            val inputStream = FileInputStream(filePath)
            return renderFile(fileName, inputStream)
        } catch (e: FileNotFoundException) {
            throw ServiceException(CoreExceptionEnum.FILE_READING_ERROR)
        }

    }

    /**
     * 返回前台文件流
     *
     * @author fengshuonan
     * @date 2017年2月28日 下午2:53:19
     */
    protected fun renderFile(fileName: String, fileBytes: ByteArray): ResponseEntity<InputStreamResource> {
        return renderFile(fileName, ByteArrayInputStream(fileBytes))
    }

    /**
     * 返回前台文件流
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return
     * @author 0x0001
     */
    protected fun renderFile(fileName: String, inputStream: InputStream): ResponseEntity<InputStreamResource> {
        val resource = InputStreamResource(inputStream)
        var dfileName: String? = null
        try {
            dfileName = String(fileName.toByteArray(charset("gb2312")), Charsets.ISO_8859_1)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        headers.setContentDispositionFormData("attachment", dfileName)
        return ResponseEntity(resource, headers, HttpStatus.CREATED)
    }
}