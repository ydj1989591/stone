package com.ydj.stone.core.enums

/**
 * 管理员的状态
 */
enum class ManagerStatus(val code : Int, val message:String) {
    OK(1, "启用"), FREEZED(2, "冻结"), DELETED(3, "被删除");

    companion object {

        fun valueOf(value: Int?): String {
            if (value == null) {
                return ""
            } else {
                for (ms in ManagerStatus.values()) {
                    if (ms.code == value) {
                        return ms.message
                    }
                }
                return ""
            }
        }
    }
}

/**
 * 菜单的状态
 */
enum class MenuStatus(val code : Int, val message:String){
    ENABLE(1, "启用"),
    DISABLE(0, "禁用");

    companion object {
        fun valueOf(status: Int?): String {
            if (status == null) {
                return ""
            } else {
                for (s in MenuStatus.values()) {
                    if (s.code == status) {
                        return s.message
                    }
                }
                return ""
            }
        }
    }
}

/**
 * 是或者否的枚举
 */
enum class YesOrNotEnum(val flag: Boolean, val desc: String, val code: Int){
    Y(true, "是", 1),

    N(false, "否", 0);

    companion object {
        fun valueOf(status: Int?): String {
            if (status == null) {
                return ""
            } else {
                for (s in YesOrNotEnum.values()) {
                    if (s.code == status) {
                        return s.desc
                    }
                }
                return ""
            }
        }
    }
}