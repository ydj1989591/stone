package com.ydj.stone.config.properties

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement


@EnableTransactionManagement
@Configuration
class MybatisPlusConfig {

    /**
     * 分页插件
     */
    @Bean
    fun paginationInterceptor() = PaginationInterceptor()
}