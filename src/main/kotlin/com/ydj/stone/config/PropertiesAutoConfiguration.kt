package com.ydj.stone.config

import com.ydj.stone.config.properties.AppNameProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

/**
 * 默认的配置
 */
@Configuration
class PropertiesAutoConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.application.name")
    fun appNameProperties(): AppNameProperties{
        return AppNameProperties();
    }
}