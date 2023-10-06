package org.jeecg.modules.oasystem.orderapplication.service.impl;

import org.jeecg.modules.oasystem.orderapplication.constant.OrderApplicationConstant;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import org.jeecg.modules.oasystem.orderapplication.mapper.OrderApplicationListMapper;
import org.jeecg.modules.oasystem.orderapplication.mapper.OrderApplicationMainMapper;
import org.jeecg.modules.oasystem.orderapplication.service.IOrderApplicationMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 电商采购月度申请表
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
@Service
public class OrderApplicationMainServiceImpl extends ServiceImpl<OrderApplicationMainMapper, OrderApplicationMain> implements IOrderApplicationMainService {

	@Autowired
	private OrderApplicationMainMapper orderApplicationMainMapper;
	@Autowired
	private OrderApplicationListMapper orderApplicationListMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(OrderApplicationMain orderApplicationMain, List<OrderApplicationList> orderApplicationListList) {
		orderApplicationMainMapper.insert(orderApplicationMain);
		if(orderApplicationListList!=null && orderApplicationListList.size()>0) {
			for(OrderApplicationList entity:orderApplicationListList) {
				//外键设置
				entity.setApplicationMainId(orderApplicationMain.getId());
				orderApplicationListMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(OrderApplicationMain orderApplicationMain,List<OrderApplicationList> orderApplicationListList) {
		//1.先删除子表数据
		orderApplicationListMapper.deleteByMainId(orderApplicationMain.getId());
		
		//2.子表数据重新插入
		if(orderApplicationListList!=null && orderApplicationListList.size()>0) {
			for(OrderApplicationList entity:orderApplicationListList) {
				//外键设置
				entity.setApplicationMainId(orderApplicationMain.getId());
				orderApplicationListMapper.insert(entity);
			}
		}
		orderApplicationMainMapper.updateById(orderApplicationMain);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void auditMain(String auditorType, OrderApplicationMain orderApplicationMain, List<OrderApplicationList> orderApplicationListList) {
		//1.先删除子表数据
		orderApplicationListMapper.deleteByMainId(orderApplicationMain.getId());

		//2.子表数据重新插入
		if(orderApplicationListList!=null && orderApplicationListList.size()>0) {

			int disagrees = 0;
			int agrees = 0;
			int total = orderApplicationListList.size();

			for(OrderApplicationList entity:orderApplicationListList) {
				//外键设置
				entity.setApplicationMainId(orderApplicationMain.getId());
				orderApplicationListMapper.insert(entity);
				//判断同意情况
				if (auditorType.equals(OrderApplicationConstant.AUDITOR_TYPE_MANAGER)) {
					if (OrderApplicationConstant.DISAGREE.equals(entity.getManagerOpinion())) {
						disagrees++;
					}else {
						agrees++;
					}
				}else {
					if (OrderApplicationConstant.DISAGREE.equals(entity.getLeaderOpinion())) {
						disagrees++;
					}else {
						agrees++;
					}
				}
			}

			if (auditorType.equals(OrderApplicationConstant.AUDITOR_TYPE_MANAGER)) {
				if (disagrees == total) {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.MANAGER_CONFIRMED_NONE);
				} else if (agrees == total) {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.MANAGER_CONFIRMED_ALL);
				} else {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.MANAGER_CONFIRMED_PART);
				}
			} else {
				if (disagrees == total) {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.LEADER_CONFIRMED_NONE);
				} else if (agrees == total) {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.LEADER_CONFIRMED_ALL);
				} else {
					orderApplicationMain.setApplicationStatus(OrderApplicationConstant.LEADER_CONFIRMED_PART);
				}
			}
			orderApplicationMainMapper.updateById(orderApplicationMain);
		}
	}

	@Override
	public void updateApplicationStatus(OrderApplicationMain orderApplicationMain) {
		orderApplicationMainMapper.updateById(orderApplicationMain);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		orderApplicationListMapper.deleteByMainId(id);
		orderApplicationMainMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			orderApplicationListMapper.deleteByMainId(id.toString());
			orderApplicationMainMapper.deleteById(id);
		}
	}
	
}
