package org.jeecg.modules.oasystem.receipt.service.impl;

import org.jeecg.modules.oasystem.receipt.entity.ReceiptMain;
import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import org.jeecg.modules.oasystem.receipt.mapper.ReceiptListMapper;
import org.jeecg.modules.oasystem.receipt.mapper.ReceiptMainMapper;
import org.jeecg.modules.oasystem.receipt.service.IReceiptMainService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 物资验收
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Service
public class ReceiptMainServiceImpl extends ServiceImpl<ReceiptMainMapper, ReceiptMain> implements IReceiptMainService {

	@Autowired
	private ReceiptMainMapper receiptMainMapper;
	@Autowired
	private ReceiptListMapper receiptListMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(ReceiptMain receiptMain, List<ReceiptList> receiptListList) {
		receiptMainMapper.insert(receiptMain);
		if(receiptListList!=null && receiptListList.size()>0) {
			for(ReceiptList entity:receiptListList) {
				//外键设置
				entity.setReceiptMainId(receiptMain.getId());
				receiptListMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(ReceiptMain receiptMain,List<ReceiptList> receiptListList) {
		receiptMainMapper.updateById(receiptMain);
		
		//1.先删除子表数据
		receiptListMapper.deleteByMainId(receiptMain.getId());
		
		//2.子表数据重新插入
		if(receiptListList!=null && receiptListList.size()>0) {
			for(ReceiptList entity:receiptListList) {
				//外键设置
				entity.setReceiptMainId(receiptMain.getId());
				receiptListMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		receiptListMapper.deleteByMainId(id);
		receiptMainMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			receiptListMapper.deleteByMainId(id.toString());
			receiptMainMapper.deleteById(id);
		}
	}
	
}
