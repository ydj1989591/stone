package com.ydj.stone.core.common.constants.cache

/**
 * 所有缓存名称的集合
 */
interface Cache {
    companion object {
        /**
         * 常量缓存
         */
       const val CONSTANT = "CONSTANT"
    }
}

/**
 * 缓存标识前缀集合,常用在ConstantFactory类中
 */
interface CacheKey {

    companion object {

        /**
         * 角色名称(多个)
         */
        const val ROLES_NAME = "roles_name_"

        /**
         * 角色名称(单个)
         */
        const val SINGLE_ROLE_NAME = "single_role_name_"

        /**
         * 角色英文名称
         */
        const val SINGLE_ROLE_TIP = "single_role_tip_"

        /**
         * 部门名称
         */
        const val DEPT_NAME = "dept_name_"
    }

}