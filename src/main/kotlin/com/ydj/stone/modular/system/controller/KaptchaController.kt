package com.ydj.stone.modular.system.controller

import com.google.code.kaptcha.Constants
import com.google.code.kaptcha.Producer
import com.ydj.stone.config.properties.StoneProperties
import com.ydj.stone.core.util.FileUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.io.IOException
import javax.imageio.ImageIO
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * 验证码生成
 */
@Controller
@RequestMapping("/kaptcha")
class KaptchaController {

    val LOGGER: Logger = LoggerFactory.getLogger(KaptchaController::class.java)
    @Autowired
    lateinit var stoneProperties: StoneProperties

    @Autowired
    lateinit var producer: Producer

    /**
     * 生成验证码
     */
    @RequestMapping("")
    fun index(request: HttpServletRequest, response: HttpServletResponse) {
        val startTime = System.currentTimeMillis()
        val session = request.session

        response.setDateHeader("Expires", 0)

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate")

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0")

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache")

        // return a jpeg
        response.contentType = "image/jpeg"

        // create the text for the image
        val capText = producer.createText()

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText)
        LOGGER.info("***KAPTCHA***：{}", capText)

        // create the image with the text
        val bi = producer.createImage(capText)
        var out: ServletOutputStream? = null
        try {
            out = response.outputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // write the data out
        try {
            ImageIO.write(bi, "jpg", out!!)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        try {
            try {
                out!!.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } finally {
            try {
                out!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        val endTime = System.currentTimeMillis();
        LOGGER.info("消耗时间：{}", endTime-startTime)
    }

    /**
     * 返回图片
     *
     * @author stylefeng
     * @Date 2017/5/24 23:00
     */
    @RequestMapping("/{pictureId}")
    fun renderPicture(@PathVariable("pictureId") pictureId: String, response: HttpServletResponse) {
        val path = stoneProperties.fileUploadPath + pictureId
        try {
            val bytes = FileUtil.toByteArray(path)
            response.outputStream.write(bytes)
        } catch (e: Exception) {
            //如果找不到图片就返回一个默认图片
            try {
                response.sendRedirect("/static/img/girl.gif")
            } catch (e1: IOException) {
                e1.printStackTrace()
            }

        }

    }
}