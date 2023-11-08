package org.jeecg.modules.oasystem.receipt.entity;

import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.UnsupportedEncodingException;

/**
 * @Description: 物品到货验明细
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@ApiModel(value="receipt_list对象", description="物品到货验明细")
@Data
@TableName("receipt_list")
public class ReceiptList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**材料名称*/
	@Excel(name = "材料名称", width = 15)
    @ApiModelProperty(value = "材料名称")
    private java.lang.String materialName;
	/**规格型号*/
	@Excel(name = "规格型号", width = 15)
    @ApiModelProperty(value = "规格型号")
    private java.lang.String specification;
	/**计量单位*/
	@Excel(name = "计量单位", width = 15)
    @ApiModelProperty(value = "计量单位")
    private java.lang.String unit;
	/**应收*/
	@Excel(name = "应收", width = 15)
    @ApiModelProperty(value = "应收")
    private java.lang.Integer receivable;
	/**实收*/
	@Excel(name = "实收", width = 15)
    @ApiModelProperty(value = "实收")
    private java.lang.Integer receipt;
	/**购入单价*/
	@Excel(name = "购入单价", width = 15)
    @ApiModelProperty(value = "购入单价")
    private java.lang.Double price;
	/**购入金额*/
	@Excel(name = "购入金额", width = 15)
    @ApiModelProperty(value = "购入金额")
    private java.lang.Double totalPrice;
	/**目录单价*/
	@Excel(name = "目录单价", width = 15)
    @ApiModelProperty(value = "目录单价")
    private java.lang.Double libPrice;
	/**目录金额*/
	@Excel(name = "目录金额", width = 15)
    @ApiModelProperty(value = "目录金额")
    private java.lang.Double libPriceTotal;
	/**物品到货验收主表主键*/
    @ApiModelProperty(value = "物品到货验收主表主键")
    private java.lang.String receiptMainId;
}
