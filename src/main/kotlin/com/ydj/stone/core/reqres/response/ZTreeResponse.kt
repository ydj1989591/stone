package com.ydj.stone.core.reqres.response

class ZTreeResponse {

    var id: Int? = 0

    var pId: Int? = 0

    var name: String? = ""

    var open: Boolean? = false
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ZTreeResponse

        if (id != other.id) return false
        if (pId != other.pId) return false
        if (name != other.name) return false
        if (open != other.open) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id ?: 0
        result = 31 * result + (pId ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (open?.hashCode() ?: 0)
        return result
    }


}