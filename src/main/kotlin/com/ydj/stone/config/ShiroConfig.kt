package com.ydj.stone.config

import com.ydj.stone.config.properties.RedisProperties
import com.ydj.stone.config.properties.StoneProperties
import com.ydj.stone.core.interceptor.StoneUserFilter
import com.ydj.stone.core.shiro.ShiroDbRealm
import org.apache.shiro.cache.CacheManager
import org.apache.shiro.codec.Base64
import org.apache.shiro.session.mgt.SessionManager
import org.apache.shiro.spring.web.ShiroFilterFactoryBean
import org.apache.shiro.web.mgt.CookieRememberMeManager
import org.apache.shiro.web.mgt.DefaultWebSecurityManager
import org.apache.shiro.web.servlet.ShiroHttpSession
import org.apache.shiro.web.servlet.SimpleCookie
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager
import org.apache.shiro.web.session.mgt.ServletContainerSessionManager
import org.crazycake.shiro.RedisCacheManager
import org.crazycake.shiro.RedisManager
import org.crazycake.shiro.RedisSessionDAO
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.HashMap
import java.util.LinkedHashMap
import javax.servlet.Filter
import at.pollux.thymeleaf.shiro.dialect.ShiroDialect



/**
 * shiro权限管理的配置
 */
@Configuration
class ShiroConfig {

    /**
     * 安全管理器
     */
    @Bean
    fun securityManager (rememberMeManager: CookieRememberMeManager,
                         redisProperties: RedisProperties,
                         sessionManager: SessionManager): DefaultWebSecurityManager{
        val securityManager = DefaultWebSecurityManager()
        securityManager.setRealm(this.shiroDbRealm())
        securityManager.cacheManager = this.cacheManager(redisProperties)
        securityManager.rememberMeManager = rememberMeManager
        securityManager.sessionManager = sessionManager
        return securityManager
    }


    /**
     * spring session管理器（多机环境）
     */
    @Bean
    @ConditionalOnProperty(prefix = "stone", name = arrayOf("spring-session-open"), havingValue = "true")
    fun servletContainerSessionManager() =ServletContainerSessionManager()

    /**
     * session管理器(单机环境)
     */
    @Bean
    @ConditionalOnProperty(prefix = "stone", name = arrayOf("spring-session-open"), havingValue = "false")
    fun defaultWebSessionManager(stoneProperties: StoneProperties, redisProperties: RedisProperties): DefaultWebSessionManager {
        val sessionManager = DefaultWebSessionManager()
        sessionManager.setCacheManager(this.cacheManager(redisProperties))
        sessionManager.sessionDAO = redisSessionDAO(redisProperties)
        sessionManager.sessionValidationInterval = stoneProperties.sessionValidationInterval!! * 1000L
        sessionManager.globalSessionTimeout = stoneProperties.sessionInvalidateTime!! * 1000L
        sessionManager.isDeleteInvalidSessions = true
        sessionManager.isSessionValidationSchedulerEnabled = true
        val cookie = SimpleCookie(ShiroHttpSession.DEFAULT_SESSION_ID_NAME)
        cookie.name = "shiroCookie"
        cookie.isHttpOnly = true
        sessionManager.sessionIdCookie = cookie
        return sessionManager
    }

    /**
     * 项目自定义的Realm
     */
    @Bean
    fun shiroDbRealm() = ShiroDbRealm()

    fun cacheManager(redisProperties: RedisProperties): RedisCacheManager {
        var redisCacheManager = RedisCacheManager()
        redisCacheManager.redisManager = redisManager(redisProperties)
        return redisCacheManager
    }

    fun redisManager(redisProperties: RedisProperties): RedisManager {
        var redisManager = RedisManager()
        redisManager.host = redisProperties.host
        redisManager.port = redisProperties.port ?: 0
        return redisManager
    }

    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    fun redisSessionDAO(redisProperties: RedisProperties): RedisSessionDAO {
        val redisSessionDAO = RedisSessionDAO()
        redisSessionDAO.redisManager = redisManager(redisProperties)
        return redisSessionDAO
    }

    /**
     * rememberMe管理器, cipherKey生成见`Base64Test.java`
     */
    @Bean
    fun rememberMeManager(rememberMeCookie: SimpleCookie): CookieRememberMeManager {
        val manager = CookieRememberMeManager()
        manager.cipherKey = Base64.decode("Z3VucwAAAAAAAAAAAAAAAA==")
        manager.cookie = rememberMeCookie
        return manager
    }

    /**
     * 记住密码Cookie
     */
    @Bean
    fun rememberMeCookie(): SimpleCookie {
        val simpleCookie = SimpleCookie("rememberMe")
        simpleCookie.isHttpOnly = true
        simpleCookie.maxAge = 7 * 24 * 60 * 60//7天
        return simpleCookie
    }

    /**
     * Shiro的过滤器链
     */
    @Bean
    fun shiroFilter(securityManager: DefaultWebSecurityManager): ShiroFilterFactoryBean {
        val shiroFilter = ShiroFilterFactoryBean()
        shiroFilter.securityManager = securityManager
        /**
         * 默认的登陆访问url
         */
        shiroFilter.loginUrl = "/login"
        /**
         * 登陆成功后跳转的url
         */
        shiroFilter.successUrl = "/"
        /**
         * 没有权限跳转的url
         */
        shiroFilter.unauthorizedUrl = "/global/error"

        /**
         * 覆盖默认的user拦截器(默认拦截器解决不了ajax请求 session超时的问题,若有更好的办法请及时反馈作者)
         */
        val myFilters = HashMap<String, Filter>()
        myFilters["user"] = StoneUserFilter()
        shiroFilter.filters = myFilters

        /**
         * 配置shiro拦截器链
         *
         * anon  不需要认证
         * authc 需要认证
         * user  验证通过或RememberMe登录的都可以
         *
         * 当应用开启了rememberMe时,用户下次访问时可以是一个user,但不会是authc,因为authc是需要重新认证的
         *
         * 顺序从上到下,优先级依次降低
         *
         * api开头的接口，走rest api鉴权，不走shiro鉴权
         *
         */
        val hashMap = LinkedHashMap<String, String>()
        hashMap["/static/**"] = "anon"
        hashMap["/lib/**"] = "anon"
        hashMap["/common/**"] = "anon"
        hashMap["/layui/**"] = "anon"
        hashMap["/system/**"] = "anon"
        hashMap["/gunsApi/**"] = "anon"
        hashMap["/login"] = "anon"
        hashMap["/global/sessionError"] = "anon"
        hashMap["/kaptcha"] = "anon"
        hashMap["/loginCheck"] = "anon"
        hashMap["/**"] = "user"
        shiroFilter.filterChainDefinitionMap = hashMap
        return shiroFilter
    }

    @Bean
    fun shiroDialect(): ShiroDialect {
        return ShiroDialect()
    }
}