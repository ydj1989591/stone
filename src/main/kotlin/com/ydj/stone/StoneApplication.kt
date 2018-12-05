package com.ydj.stone

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@EnableCaching
@SpringBootApplication
@MapperScan("com.ydj.stone.modular.system.mapper")
class StoneApplication

fun main(args: Array<String>) {
    runApplication<StoneApplication>(*args)
}
