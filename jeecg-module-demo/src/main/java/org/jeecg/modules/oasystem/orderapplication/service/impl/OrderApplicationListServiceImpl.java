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

	@Override
	public void revokeAllByMainId(String mainId) {
		List<OrderApplicationList> list = selectByMainId(mainId);
		//1.先删除子表数据
		orderApplicationListMapper.deleteByMainId(mainId);
		//2.子表数据重新插入
		if(list!=null && list.size()>0) {
			for (OrderApplicationList entity : list) {
				entity.setCurrentOpinion("-1");
				entity.setManagerOpinion("-1");
				entity.setLeaderOpinion("-1");
				// 子表插入
				orderApplicationListMapper.insert(entity);
			}
		}
	}
}
