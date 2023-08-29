package org.jeecg.modules.oasystem.orderapplication.vo;

import java.util.List;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 电商采购月度申请表
 * @Author: jeecg-boot
 * @Date:   2023-08-28
 * @Version: V1.0
 */
@Data
@ApiModel(value="order_application_mainPage对象", description="电商采购月度申请表")
public class OrderApplicationMainPage {

	/**编号*/
	@ApiModelProperty(value = "编号")
    private String id;
	/**申请部门*/
	@ApiModelProperty(value = "申请部门")
    private String sysOrgCode;
	/**申请时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "申请时间")
    private Date createTime;
	/**经办人*/
	@ApiModelProperty(value = "经办人")
    private String createBy;
	/**更新时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新时间")
    private Date updateTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private String updateBy;
	/**部门主管*/
	@Excel(name = "部门主管", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@ApiModelProperty(value = "部门主管")
    private String managerName;
	/**分管领导*/
	@Excel(name = "分管领导", width = 15, dictTable = "sys_user", dicText = "realname", dicCode = "username")
    @Dict(dictTable = "sys_user", dicText = "realname", dicCode = "username")
	@ApiModelProperty(value = "分管领导")
    private String leaderName;
	/**申请理由*/
	@Excel(name = "申请理由", width = 15)
	@ApiModelProperty(value = "申请理由")
    private String reason;
	/**申请状态*/
	@Excel(name = "申请状态", width = 15)
	@ApiModelProperty(value = "申请状态")
    private String applicationStatus;

	@ExcelCollection(name="电商采购月度申请表采购物资具体要求")
	@ApiModelProperty(value = "电商采购月度申请表采购物资具体要求")
	private List<OrderApplicationList> orderApplicationListList;

}
