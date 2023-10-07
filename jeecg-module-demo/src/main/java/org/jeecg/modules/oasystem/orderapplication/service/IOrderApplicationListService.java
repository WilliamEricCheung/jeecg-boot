package org.jeecg.modules.oasystem.orderapplication.service;

import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 电商采购月度申请表采购物资具体要求
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
public interface IOrderApplicationListService extends IService<OrderApplicationList> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<OrderApplicationList>
	 */
	public List<OrderApplicationList> selectByMainId(String mainId);

	/**
	 * 通过主表id更新子表审核状态数据
	 *
	 * @param mainId 主表id
	 */
	public void revokeAllByMainId(String mainId);
}
