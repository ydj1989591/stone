package com.ydj.stone.modular.system.service.impl;

import com.ydj.stone.modular.system.entity.Relation;
import com.ydj.stone.modular.system.mapper.RelationMapper;
import com.ydj.stone.modular.system.service.RelationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和菜单关联表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class RelationServiceImpl : ServiceImpl<RelationMapper, Relation>(), RelationService {

}
