package org.jeecg.modules.oasystem.receipt.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 物资验收
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@ApiModel(value="receipt_main对象", description="物资验收")
@Data
@TableName("receipt_main")
public class ReceiptMain implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**签收单位*/
	@Excel(name = "签收单位", width = 15)
    @ApiModelProperty(value = "签收单位")
    private java.lang.String receiver;
	/**供应单位*/
	@Excel(name = "供应单位", width = 15)
    @ApiModelProperty(value = "供应单位")
    private java.lang.String provider;
	/**账号或发票号*/
	@Excel(name = "账号或发票号", width = 15)
    @ApiModelProperty(value = "账号或发票号")
    private java.lang.String receiptNumber;
	/**电商采购月度申请主表ID*/
	@Excel(name = "电商采购月度申请主表ID", width = 15)
    @ApiModelProperty(value = "电商采购月度申请主表ID")
    private java.lang.String orderApplicationMainId;
	/**库别*/
	@Excel(name = "库别", width = 15)
    @ApiModelProperty(value = "库别")
    private java.lang.String repository;
}
