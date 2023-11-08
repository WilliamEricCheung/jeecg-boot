package org.jeecg.modules.oasystem.receipt.service;

import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import org.jeecg.modules.oasystem.receipt.entity.ReceiptMain;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 物资验收
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface IReceiptMainService extends IService<ReceiptMain> {

	/**
	 * 添加一对多
	 *
	 * @param receiptMain
	 * @param receiptListList
	 */
	public void saveMain(ReceiptMain receiptMain,List<ReceiptList> receiptListList) ;
	
	/**
	 * 修改一对多
	 *
   * @param receiptMain
   * @param receiptListList
	 */
	public void updateMain(ReceiptMain receiptMain,List<ReceiptList> receiptListList);
	
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
