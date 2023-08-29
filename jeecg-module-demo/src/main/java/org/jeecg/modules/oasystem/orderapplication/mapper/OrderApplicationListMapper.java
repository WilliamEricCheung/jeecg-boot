package org.jeecg.modules.oasystem.orderapplication.mapper;

import java.util.List;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 电商采购月度申请表采购物资具体要求
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
public interface OrderApplicationListMapper extends BaseMapper<OrderApplicationList> {

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
   * @return List<OrderApplicationList>
   */
	public List<OrderApplicationList> selectByMainId(@Param("mainId") String mainId);
}
