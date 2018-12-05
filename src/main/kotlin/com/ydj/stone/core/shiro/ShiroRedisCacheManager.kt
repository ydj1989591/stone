package com.ydj.stone.core.shiro

import org.apache.shiro.cache.AbstractCacheManager
import org.apache.shiro.cache.Cache
import org.apache.shiro.cache.CacheException
import org.springframework.data.redis.core.RedisTemplate

class ShiroRedisCacheManager() : AbstractCacheManager() {

    private var redisTemplate: RedisTemplate<Array<Byte>, Array<Byte>>? =null

    constructor(redisTemplate: RedisTemplate<Array<Byte>, Array<Byte>>):this(){
        this.redisTemplate = redisTemplate
    }

    @Throws(CacheException::class)
    override fun createCache(name: String?) = null
}