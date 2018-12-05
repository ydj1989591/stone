package com.ydj.stone.core.common.annotion

import com.ydj.stone.core.common.constants.dictmap.base.AbstractDictMap
import com.ydj.stone.core.common.constants.dictmap.base.SystemDict
import java.lang.annotation.Inherited
import java.lang.annotation.RetentionPolicy
import kotlin.reflect.KClass

@Inherited
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class BussinessLog  (
        /**
         * 业务的名称,例如:"修改菜单"
         */
        val value : String = "" ,

        /**
         * 被修改的实体的唯一标识,例如:菜单实体的唯一标识为"id"
         */
        val key : String = "id",

        /**
         * 字典(用于查找key的中文名称和字段的中文名称)
          */
        val dict : KClass<out AbstractDictMap> = SystemDict::class

)





