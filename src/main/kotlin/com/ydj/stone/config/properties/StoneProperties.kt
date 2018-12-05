package com.ydj.stone.config.properties

import com.ydj.stone.core.util.ToolUtil
import com.ydj.stone.core.util.ValidateUtil
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.io.File

@Component
@ConfigurationProperties(prefix = StoneProperties.PREFIX)
class StoneProperties {

    companion object {
        const val PREFIX = "stone"
    }

    var kaptchaOpen: Boolean? = false

    var swaggerOpen: Boolean? = false

    var fileUploadPath: String? = null

    var haveCreatePath: Boolean? = false

    var springSessionOpen: Boolean? = false

    /**
     * session 失效时间（默认为30分钟 单位：秒）
     */
     var sessionInvalidateTime: Long? = 30 * 60

    /**
     * session 验证失效时间（默认为15分钟 单位：秒）
     */
    var sessionValidationInterval: Long? = 15 * 60

    fun getFileUploadPath1(): String? {
        //如果没有写文件上传路径,保存到临时目录
        if (ValidateUtil.isEmpty(fileUploadPath)) {
            return ToolUtil.getTempPath()
        } else {
            //判断有没有结尾符,没有得加上
            if (!fileUploadPath!!.endsWith(File.separator)) {
                fileUploadPath = fileUploadPath + File.separator
            }
            //判断目录存不存在,不存在得加上
            if (!haveCreatePath!!) {
                val file = File(fileUploadPath)
                file.mkdirs()
                haveCreatePath = true
            }
            return fileUploadPath
        }
    }
}