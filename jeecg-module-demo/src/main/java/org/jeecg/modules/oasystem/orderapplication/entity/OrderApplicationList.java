package org.jeecg.modules.oasystem.orderapplication.entity;

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
 * @Description: 电商采购月度申请表采购物资具体要求
 * @Author: jeecg-boot
 * @Date:   2023-08-29
 * @Version: V1.0
 */
@ApiModel(value="order_application_list对象", description="电商采购月度申请表采购物资具体要求")
@Data
@TableName("order_application_list")
public class OrderApplicationList implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**申请人所属部门编码*/
    @ApiModelProperty(value = "申请人所属部门")
    private java.lang.String sysOrgCode;
    /**申请部门*/
    @ApiModelProperty(value = "申请部门")
    private java.lang.String departmentName;
	/**申请人*/
    @ApiModelProperty(value = "申请人")
    private java.lang.String createBy;
	/**申请日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请日期")
    private java.util.Date createTime;
	/**更新人*/
//	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private java.lang.String updatedBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**品名*/
	@Excel(name = "品名", width = 15)
    @ApiModelProperty(value = "品名")
    private java.lang.String materialName;
	/**生产厂家或品牌*/
	@Excel(name = "生产厂家或品牌", width = 15)
    @ApiModelProperty(value = "生产厂家或品牌")
    private java.lang.String brandName;
	/**规格及型号*/
	@Excel(name = "规格及型号", width = 15)
    @ApiModelProperty(value = "规格及型号")
    private java.lang.String specification;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private java.lang.String unit;
	/**库存*/
	@Excel(name = "库存", width = 15)
    @ApiModelProperty(value = "库存")
    private java.lang.Integer storage;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private java.lang.Integer number;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private java.lang.Double price;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private java.lang.Double totalPrice;
	/**用途及使用场所*/
	@Excel(name = "用途及使用场所", width = 15)
    @ApiModelProperty(value = "用途及使用场所")
    private java.lang.String purpose;
	/**电商平台物资编码*/
	@Excel(name = "电商平台物资编码", width = 15)
    @ApiModelProperty(value = "电商平台物资编码")
    private java.lang.String materialCode;
	/**部门主管意见*/
	@Excel(name = "部门主管意见", width = 15, dicCode = "opinion")
    @ApiModelProperty(value = "部门主管意见")
    private java.lang.String managerOpinion;
	/**分管领导意见*/
	@Excel(name = "分管领导意见", width = 15, dicCode = "opinion")
    @ApiModelProperty(value = "分管领导意见")
    private java.lang.String leaderOpinion;
	/**申请主表外键*/
    @ApiModelProperty(value = "申请主表外键")
    private java.lang.String applicationMainId;
}
