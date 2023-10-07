package org.jeecg.modules.oasystem.orderapplication.service;

import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 电商采购月度申请表
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
public interface IOrderApplicationMainService extends IService<OrderApplicationMain> {

	/**
	 * 添加一对多
	 *
	 * @param orderApplicationMain
	 * @param orderApplicationListList
	 */
	public void saveMain(OrderApplicationMain orderApplicationMain,List<OrderApplicationList> orderApplicationListList) ;
	
	/**
	 * 修改一对多
	 *
   * @param orderApplicationMain
   * @param orderApplicationListList
	 */
	public void updateMain(OrderApplicationMain orderApplicationMain,List<OrderApplicationList> orderApplicationListList);

	/**
	 * 审核
	 * @param auditorType
	 * @param orderApplicationMain
	 * @param orderApplicationListList
	 * @return 返回申请状态
	 */
	public String auditMain(String auditorType, OrderApplicationMain orderApplicationMain,List<OrderApplicationList> orderApplicationListList);

	/**
	 * 修改申请状态
	 * @param orderApplicationMain
	 */
	public void updateApplicationStatus(OrderApplicationMain orderApplicationMain);

	/**
	 * 通过sys_org_code返回相应的部门名
	 * @param sysOrgCode
	 * @return departmentName
	 */
	public String getDepartmentNameBySysOrgCode(String sysOrgCode);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
