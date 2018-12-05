package com.ydj.stone.config

import com.google.code.kaptcha.impl.DefaultKaptcha
import com.google.code.kaptcha.util.Config
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class KaptchaConfig {

    /**
     * 验证码生成相关
     */
    @Bean
    fun kaptcha(): DefaultKaptcha {
        val properties = Properties()
        properties["kaptcha.border"] = "no"
        properties["kaptcha.border.color"] = "105,179,90"
        properties["kaptcha.textproducer.font.color"] = "blue"
        properties["kaptcha.image.width"] = "125"
        properties["kaptcha.image.height"] = "45"
        properties["kaptcha.textproducer.font.size"] = "45"
        properties["kaptcha.session.key"] = "code"
        properties["kaptcha.textproducer.char.length"] = "4"
        properties["kaptcha.textproducer.font.names"] = "宋体,楷体,微软雅黑"
        val config = Config(properties)
        val defaultKaptcha = DefaultKaptcha()
        defaultKaptcha.config = config
        return defaultKaptcha
    }
}