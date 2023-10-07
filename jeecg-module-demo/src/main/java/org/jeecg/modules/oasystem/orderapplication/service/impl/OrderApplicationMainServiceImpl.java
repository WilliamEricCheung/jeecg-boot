package org.jeecg.modules.oasystem.orderapplication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.modules.oasystem.orderapplication.constant.OrderApplicationConstant;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import org.jeecg.modules.oasystem.orderapplication.mapper.OrderApplicationListMapper;
import org.jeecg.modules.oasystem.orderapplication.mapper.OrderApplicationMainMapper;
import org.jeecg.modules.oasystem.orderapplication.service.IOrderApplicationMainService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
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
	@Autowired
	private ISysDepartService sysDepartService;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(OrderApplicationMain orderApplicationMain, List<OrderApplicationList> orderApplicationListList) {
		orderApplicationMainMapper.insert(orderApplicationMain);
		if(orderApplicationListList!=null && orderApplicationListList.size()>0) {
			for(OrderApplicationList entity:orderApplicationListList) {
				//外键设置
				entity.setApplicationMainId(orderApplicationMain.getId());
				//设置departmentName
				entity.setDepartmentName(orderApplicationMain.getDepartmentName());
				//设置更新人和更新时间
				entity.setUpdateTime(orderApplicationMain.getUpdateTime());
				entity.setUpdatedBy(orderApplicationMain.getUpdateBy());
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
	public String auditMain(String auditorType, OrderApplicationMain orderApplicationMain, List<OrderApplicationList> orderApplicationListList) {
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
				//判断同意情况
				if (auditorType.equals(OrderApplicationConstant.AUDITOR_TYPE_MANAGER)) {
					entity.setManagerOpinion(entity.getCurrentOpinion());
					if (OrderApplicationConstant.DISAGREE.equals(entity.getManagerOpinion())) {
						disagrees++;
					}else {
						agrees++;
					}
				}else {
					entity.setLeaderOpinion(entity.getCurrentOpinion());
					if (OrderApplicationConstant.DISAGREE.equals(entity.getLeaderOpinion())) {
						disagrees++;
					}else {
						agrees++;
					}
				}
				// 子表插入
				orderApplicationListMapper.insert(entity);
			}
			// 更新主表审批同意情况
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
		return orderApplicationMain.getApplicationStatus();
	}

	@Override
	public void updateApplicationStatus(OrderApplicationMain orderApplicationMain) {
		orderApplicationMainMapper.updateById(orderApplicationMain);
	}

	@Override
	public String getDepartmentNameBySysOrgCode(String sysOrgCode) {
		LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
		query.eq(SysDepart::getOrgCode, sysOrgCode);
		SysDepart sysDepart = sysDepartService.getOne(query);
		return sysDepart.getDepartName();
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

	@Override
	public List<OrderApplicationList> selectByMainId(String mainId, String username) {
		List<OrderApplicationList> list = orderApplicationListMapper.selectByMainId(mainId);
		//1.先删除子表数据
		orderApplicationListMapper.deleteByMainId(mainId);
		OrderApplicationMain orderApplicationMain = getById(mainId);
		//2.子表数据重新插入
		if(list!=null && list.size()>0) {
			for (OrderApplicationList entity : list) {
				// 判断当前用户的角色
				if (username.equals(orderApplicationMain.getManagerUsername())) {
					// 部门主管
					entity.setCurrentOpinion(entity.getManagerOpinion());
				} else if (username.equals(orderApplicationMain.getLeaderUsername())) {
					// 分管领导
					entity.setCurrentOpinion(entity.getLeaderOpinion());
				} else {
					// 申请人或系统开发管理员
					entity.setCurrentOpinion(OrderApplicationConstant.NOTDECIDED);
				}
				orderApplicationListMapper.insert(entity);
			}
		}
		return list;
	}
	
}
