package org.jeecg.modules.oasystem.receipt.mapper;

import java.util.List;
import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 物品到货验明细
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
public interface ReceiptListMapper extends BaseMapper<ReceiptList> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<ReceiptList>
   */
	public List<ReceiptList> selectByMainId(@Param("mainId") String mainId);
}
