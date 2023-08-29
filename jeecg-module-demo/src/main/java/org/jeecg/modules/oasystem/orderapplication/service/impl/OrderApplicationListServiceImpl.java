package org.jeecg.modules.oasystem.orderapplication.service.impl;

import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import org.jeecg.modules.oasystem.orderapplication.mapper.OrderApplicationListMapper;
import org.jeecg.modules.oasystem.orderapplication.service.IOrderApplicationListService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 电商采购月度申请表采购物资具体要求
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
@Service
public class OrderApplicationListServiceImpl extends ServiceImpl<OrderApplicationListMapper, OrderApplicationList> implements IOrderApplicationListService {
	
	@Autowired
	private OrderApplicationListMapper orderApplicationListMapper;
	
	@Override
	public List<OrderApplicationList> selectByMainId(String mainId) {
		return orderApplicationListMapper.selectByMainId(mainId);
	}
}
