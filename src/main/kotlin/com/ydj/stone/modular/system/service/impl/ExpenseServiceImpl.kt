package com.ydj.stone.modular.system.service.impl;

import com.ydj.stone.modular.system.entity.Expense;
import com.ydj.stone.modular.system.mapper.ExpenseMapper;
import com.ydj.stone.modular.system.service.ExpenseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 报销表 服务实现类
 * </p>
 *
 * @author yidj
 * @since 2018-10-26
 */
@Service
open class ExpenseServiceImpl : ServiceImpl<ExpenseMapper, Expense>(), ExpenseService {

}
