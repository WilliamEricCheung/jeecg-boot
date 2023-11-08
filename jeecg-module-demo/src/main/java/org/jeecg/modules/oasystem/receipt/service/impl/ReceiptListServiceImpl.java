package org.jeecg.modules.oasystem.receipt.service.impl;

import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import org.jeecg.modules.oasystem.receipt.mapper.ReceiptListMapper;
import org.jeecg.modules.oasystem.receipt.service.IReceiptListService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 物品到货验明细
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class ReceiptListServiceImpl extends ServiceImpl<ReceiptListMapper, ReceiptList> implements IReceiptListService {
	
	@Autowired
	private ReceiptListMapper receiptListMapper;
	
	@Override
	public List<ReceiptList> selectByMainId(String mainId) {
		return receiptListMapper.selectByMainId(mainId);
	}
}
