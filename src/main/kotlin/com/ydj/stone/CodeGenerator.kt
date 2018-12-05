package com.ydj.stone

import com.baomidou.mybatisplus.generator.AutoGenerator
import com.baomidou.mybatisplus.generator.config.DataSourceConfig
import com.baomidou.mybatisplus.generator.config.GlobalConfig
import com.baomidou.mybatisplus.generator.config.PackageConfig
import com.baomidou.mybatisplus.generator.config.StrategyConfig
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy
import com.baomidou.mybatisplus.generator.config.TemplateConfig



/**
 * mybatis-plus 代码自动生成器
 */
class CodeGenerator {


}

fun main(args:Array<String>){
    //代码生成器
    var mpg = AutoGenerator()

    var gc = GlobalConfig()
    val projectPath = System.getProperty("user.dir")
    gc.outputDir = "c://work/stone" + "/src/main/kotlin"
    gc.author = "yidj"
    gc.isOpen = false
    gc.isKotlin= true
    mpg.setGlobalConfig(gc)

    //数据源配置
    var dsc = DataSourceConfig()
    dsc.url = "jdbc:mysql://144.34.222.214:3306/stone?useUnicode=true&characterEncoding=utf-8"
    dsc.driverName = "com.mysql.jdbc.Driver"
    dsc.username = "stone"
    dsc.password = "Stone@2018"
    mpg.dataSource = dsc

    //包配置
    var pc = PackageConfig()
    pc.moduleName = "system"
    pc.parent = "com.ydj.stone.modular"
    mpg.packageInfo = pc

    //策略配置
    var strategy = StrategyConfig()
    strategy.naming = NamingStrategy.underline_to_camel
    strategy.columnNaming = NamingStrategy.underline_to_camel
    strategy.isRestControllerStyle = false
    strategy.setTablePrefix("sys")

    mpg.strategy = strategy

    //模板
    val tc = TemplateConfig()
    tc.controller = null
    mpg.template = tc
    mpg.execute()

}