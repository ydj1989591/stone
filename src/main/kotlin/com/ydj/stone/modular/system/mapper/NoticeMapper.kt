package com.ydj.stone.modular.system.mapper;

import com.ydj.stone.modular.system.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param

/**
 * <p>
 * 通知表 Mapper 接口
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
interface NoticeMapper : BaseMapper<Notice>{

    /**
     * 获取通知列表
     */
    fun list(@Param("condition") condition: String): List<Map<String, Any>>
}
