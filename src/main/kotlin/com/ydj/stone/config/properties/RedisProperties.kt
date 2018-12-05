package com.ydj.stone.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = RedisProperties.PREFIX)
class RedisProperties {

    companion object {
        const val PREFIX = "spring.redis"
    }

    var host:String ? = ""

    var port:Int ? = 0

}