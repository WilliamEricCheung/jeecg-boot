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
 * @Date:   2023-08-28
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
    private String id;
	/**申请人所属部门*/
    @ApiModelProperty(value = "申请人所属部门")
    private String sysOrgCode;
	/**申请人*/
    @ApiModelProperty(value = "申请人")
    private String createBy;
	/**申请日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "申请日期")
    private Date createTime;
	/**更新人*/
	@Excel(name = "更新人", width = 15)
    @ApiModelProperty(value = "更新人")
    private String updatedBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**品名*/
	@Excel(name = "品名", width = 15)
    @ApiModelProperty(value = "品名")
    private String materialName;
	/**生产厂家或品牌*/
	@Excel(name = "生产厂家或品牌", width = 15)
    @ApiModelProperty(value = "生产厂家或品牌")
    private String brandName;
	/**规格及型号*/
	@Excel(name = "规格及型号", width = 15)
    @ApiModelProperty(value = "规格及型号")
    private String specification;
	/**单位*/
	@Excel(name = "单位", width = 15)
    @ApiModelProperty(value = "单位")
    private String unit;
	/**数量*/
	@Excel(name = "数量", width = 15)
    @ApiModelProperty(value = "数量")
    private Integer number;
	/**单价*/
	@Excel(name = "单价", width = 15)
    @ApiModelProperty(value = "单价")
    private Double price;
	/**总价*/
	@Excel(name = "总价", width = 15)
    @ApiModelProperty(value = "总价")
    private Double totalPrice;
	/**用途及使用场所*/
	@Excel(name = "用途及使用场所", width = 15)
    @ApiModelProperty(value = "用途及使用场所")
    private String purpose;
	/**电商平台物资编码*/
	@Excel(name = "电商平台物资编码", width = 15)
    @ApiModelProperty(value = "电商平台物资编码")
    private String materialCode;
	/**部门主管意见*/
	@Excel(name = "部门主管意见", width = 15)
    @ApiModelProperty(value = "部门主管意见")
    private String managerOpinion;
	/**分管领导意见*/
	@Excel(name = "分管领导意见", width = 15)
    @ApiModelProperty(value = "分管领导意见")
    private String leaderOpinion;
	/**申请主表外键*/
    @ApiModelProperty(value = "申请主表外键")
    private String applicationMainId;
}
