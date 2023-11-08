package org.jeecg.modules.oasystem.receipt.service;

import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 物品到货验明细
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface IReceiptListService extends IService<ReceiptList> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<ReceiptList>
	 */
	public List<ReceiptList> selectByMainId(String mainId);
}
