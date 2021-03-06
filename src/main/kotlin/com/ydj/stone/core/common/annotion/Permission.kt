package com.ydj.stone.core.common.annotion

import java.lang.annotation.Inherited

/**
 * 权限注解 用于检查权限 规定访问权限
 */
@Inherited
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class Permission (

        /**
         *
         * 角色英文名称
         *
         * 使用注解时加上这个值表示限制只有某个角色的才可以访问对应的资源
         *
         * 常用在某些资源限制只有超级管理员角色才可访问
         */
        val values: Array<String> = arrayOf()
)