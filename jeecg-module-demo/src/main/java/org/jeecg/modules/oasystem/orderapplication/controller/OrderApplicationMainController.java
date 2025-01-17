package org.jeecg.modules.oasystem.orderapplication.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.jeecg.common.api.CommonAPI;
import org.jeecg.common.api.dto.message.MessageDTO;
import org.jeecg.common.aspect.annotation.PermissionData;
import org.jeecg.common.constant.enums.MessageTypeEnum;
import org.jeecg.common.system.api.ISysBaseAPI;
import org.jeecg.common.util.DateUtils;
import org.jeecg.modules.message.entity.SysMessageTemplate;
import org.jeecg.modules.oasystem.orderapplication.constant.OrderApplicationConstant;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.service.ISysDepartService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationList;
import org.jeecg.modules.oasystem.orderapplication.entity.OrderApplicationMain;
import org.jeecg.modules.oasystem.orderapplication.vo.OrderApplicationMainPage;
import org.jeecg.modules.oasystem.orderapplication.service.IOrderApplicationMainService;
import org.jeecg.modules.oasystem.orderapplication.service.IOrderApplicationListService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @Description: 电商采购月度申请表
 * @Author: jeecg-boot
 * @Date: 2023-08-29
 * @Version: V1.0
 */
@Api(tags = "电商采购月度申请表")
@RestController
@RequestMapping("/orderapplication/orderApplicationMain")
@Slf4j
public class OrderApplicationMainController {
    @Autowired
    private IOrderApplicationMainService orderApplicationMainService;
    @Autowired
    private IOrderApplicationListService orderApplicationListService;
    @Autowired
    private ISysBaseAPI sysBaseApi;
    @Autowired
    private CommonAPI commonAPI;

    /**
     * 分页列表查询
     *
     * @param orderApplicationMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-分页列表查询")
    @ApiOperation(value = "电商采购月度申请表-分页列表查询", notes = "电商采购月度申请表-分页列表查询")
    @GetMapping(value = "/list")
    @PermissionData(pageComponent = "demo/orderapplication/OrderApplicationMainList")
    public Result<IPage<OrderApplicationMain>> queryPageList(OrderApplicationMain orderApplicationMain,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        QueryWrapper<OrderApplicationMain> queryWrapper = QueryGenerator.initQueryWrapper(orderApplicationMain, req.getParameterMap());
        Page<OrderApplicationMain> page = new Page<OrderApplicationMain>(pageNo, pageSize);
        IPage<OrderApplicationMain> pageList = orderApplicationMainService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param orderApplicationMainPage
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-添加")
    @ApiOperation(value = "电商采购月度申请表-添加", notes = "电商采购月度申请表-添加")
    @RequiresPermissions("orderapplication:order_application_main:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody OrderApplicationMainPage orderApplicationMainPage) {
        OrderApplicationMain orderApplicationMain = new OrderApplicationMain();
        BeanUtils.copyProperties(orderApplicationMainPage, orderApplicationMain);
        // 设置默认申请状态
        orderApplicationMain.setApplicationStatus(OrderApplicationConstant.APPLICANT_NOT_SUBMITTED);
        // 同步设置部门名
        orderApplicationMain.setDepartmentName(orderApplicationMainService.getDepartmentNameBySysOrgCode(orderApplicationMain.getSysOrgCode()));
        // 设置update_by
        orderApplicationMain.setUpdateBy(orderApplicationMain.getCreateBy());
        orderApplicationMainService.saveMain(orderApplicationMain, orderApplicationMainPage.getOrderApplicationListList());
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param orderApplicationMainPage
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-编辑")
    @ApiOperation(value = "电商采购月度申请表-编辑", notes = "电商采购月度申请表-编辑")
    @RequiresPermissions("orderapplication:order_application_main:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody OrderApplicationMainPage orderApplicationMainPage) {
        OrderApplicationMain orderApplicationMain = new OrderApplicationMain();
        BeanUtils.copyProperties(orderApplicationMainPage, orderApplicationMain);
        OrderApplicationMain orderApplicationMainEntity = orderApplicationMainService.getById(orderApplicationMain.getId());
        if (orderApplicationMainEntity == null) {
            return Result.error("未找到对应数据");
        }
        orderApplicationMainService.updateMain(orderApplicationMain, orderApplicationMainPage.getOrderApplicationListList());
        return Result.OK("编辑成功!");
    }

    @AutoLog(value = "电商采购月度申请表-审核")
    @ApiOperation(value = "电商采购月度申请表-审核", notes = "电商采购月度申请表-审核")
    @RequiresPermissions("orderapplication:order_application_main:audit")
    @RequestMapping(value = "/audit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<?> audit(@RequestBody OrderApplicationMainPage orderApplicationMainPage) {
        OrderApplicationMain orderApplicationMain = new OrderApplicationMain();
        BeanUtils.copyProperties(orderApplicationMainPage, orderApplicationMain);
        OrderApplicationMain orderApplicationMainEntity = orderApplicationMainService.getById(orderApplicationMain.getId());
        if (orderApplicationMainEntity == null) {
            return Result.error("未找到对应数据");
        }
        // 获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String auditorType = null;
        String receiver2 = null;
        if (sysUser.getUsername().equals(orderApplicationMainEntity.getManagerUsername())) {
            auditorType = OrderApplicationConstant.AUDITOR_TYPE_MANAGER;
            receiver2 = orderApplicationMain.getLeaderUsername();
        } else if (sysUser.getUsername().equals(orderApplicationMainEntity.getLeaderUsername())) {
            auditorType = OrderApplicationConstant.AUDITOR_TYPE_LEADER;
            receiver2 = orderApplicationMain.getManagerUsername();
        } else {
            return Result.error("{}无权审核该申请！", sysUser.getRealname());
        }
        String applicationStatus = orderApplicationMainService.auditMain(auditorType, orderApplicationMain, orderApplicationMainPage.getOrderApplicationListList());
        String sender = sysUser.getRealname();
        String departmentName = orderApplicationMain.getDepartmentName();
        // 收件人1为申请人，收件人2为经理或者领导（根据auditorType来判断）
        String receiver1 = orderApplicationMain.getCreateBy();
        String receiver = receiver1 + "," + receiver2;
        log.info("departmentName: {}", departmentName);
        String createBy = commonAPI.getUserByName(receiver1).getRealname();
        String createTime = DateUtils.formatDate(orderApplicationMain.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        String reason = orderApplicationMain.getReason();
        return sendAuditMessage(sender, receiver, "物资采购申请审核状态更新", applicationStatus,
                departmentName, createBy, createTime, reason);
    }

    /**
     * 通过id提交
     *
     * @param id
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-通过id提交")
    @ApiOperation(value = "电商采购月度申请表-通过id提交", notes = "电商采购月度申请表-通过id提交")
    @RequiresPermissions("orderapplication:order_application_main:submit")
    @PostMapping(value = "/submit")
    public Result<?> submit(@RequestParam(name = "id", required = true) String id) {
        OrderApplicationMain orderApplicationMain = orderApplicationMainService.getById(id);
        if (orderApplicationMain == null) {
            return Result.error("未找到对应数据");
        }
        orderApplicationMain.setApplicationStatus(OrderApplicationConstant.APPLICANT_SUBMITTED);
        orderApplicationMainService.updateApplicationStatus(orderApplicationMain);
        // 消息提示 https://help.jeecg.com/java/java/msgpush.html
        // 获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String sender = sysUser.getRealname();
        // 仅提醒经理
        String receiver = orderApplicationMain.getManagerUsername();
        String departmentName = orderApplicationMain.getDepartmentName();
        String createBy = commonAPI.getUserByName(orderApplicationMain.getCreateBy()).getRealname();
        String createTime = DateUtils.formatDate(orderApplicationMain.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
        String reason = orderApplicationMain.getReason();
        return sendAuditMessage(sender, receiver, "新的物资采购申请审核", OrderApplicationConstant.APPLICANT_SUBMITTED,
                departmentName, createBy, createTime, reason);
    }

    /**
     * 通过id撤回
     *
     * @param id
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-通过id撤回")
    @ApiOperation(value = "电商采购月度申请表-通过id撤回", notes = "电商采购月度申请表-通过id撤回")
    @RequiresPermissions("orderapplication:order_application_main:revoke")
    @PostMapping(value = "/revoke")
    public Result<String> revoke(@RequestParam(name = "id", required = true) String id) {
        OrderApplicationMain orderApplicationMain = orderApplicationMainService.getById(id);
        if (orderApplicationMain == null) {
            return Result.error("未找到对应数据");
        }
        // 重置子表审核状态
        orderApplicationListService.revokeAllByMainId(id);
        // 更新主表申请状态
        orderApplicationMain.setApplicationStatus(OrderApplicationConstant.APPLICANT_REVOKED);
        orderApplicationMainService.updateApplicationStatus(orderApplicationMain);
        return Result.OK("撤回成功!");
    }

    /**
     * 批量撤回
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-批量撤回")
    @ApiOperation(value = "电商采购月度申请表-批量撤回", notes = "电商采购月度申请表-批量撤回")
    @RequiresPermissions("orderapplication:order_application_main:revokeBatch")
    @PostMapping(value = "/revokeBatch")
    public Result<String> revokeBatch(@RequestParam(name = "ids", required = true) String ids) {
        String[] list = ids.split(",");
        for (String id: list) {
            OrderApplicationMain orderApplicationMain = orderApplicationMainService.getById(id);
            if (orderApplicationMain == null) {
                return Result.error("未找到对应数据");
            }
            // 重置子表审核状态
            orderApplicationListService.revokeAllByMainId(id);
            // 更新主表申请状态
            orderApplicationMain.setApplicationStatus(OrderApplicationConstant.APPLICANT_REVOKED);
            orderApplicationMainService.updateApplicationStatus(orderApplicationMain);
        }
        return Result.OK("批量撤回成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-通过id删除")
    @ApiOperation(value = "电商采购月度申请表-通过id删除", notes = "电商采购月度申请表-通过id删除")
    @RequiresPermissions("orderapplication:order_application_main:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        orderApplicationMainService.delMain(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "电商采购月度申请表-批量删除")
    @ApiOperation(value = "电商采购月度申请表-批量删除", notes = "电商采购月度申请表-批量删除")
    @RequiresPermissions("orderapplication:order_application_main:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.orderApplicationMainService.delBatchMain(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功！");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "电商采购月度申请表-通过id查询")
    @ApiOperation(value = "电商采购月度申请表-通过id查询", notes = "电商采购月度申请表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<OrderApplicationMain> queryById(@RequestParam(name = "id", required = true) String id) {
        OrderApplicationMain orderApplicationMain = orderApplicationMainService.getById(id);
        if (orderApplicationMain == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(orderApplicationMain);

    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @AutoLog(value = "电商采购月度申请表采购物资具体要求通过主表ID查询")
    @ApiOperation(value = "电商采购月度申请表采购物资具体要求主表ID查询", notes = "电商采购月度申请表采购物资具体要求-通主表ID查询")
    @GetMapping(value = "/queryOrderApplicationListByMainId")
    public Result<List<OrderApplicationList>> queryOrderApplicationListListByMainId(@RequestParam(name = "id", required = true) String id) {
        List<OrderApplicationList> orderApplicationListList = orderApplicationListService.selectByMainId(id);
        return Result.OK(orderApplicationListList);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param orderApplicationMain
     */
    @RequiresPermissions("orderapplication:order_application_main:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, OrderApplicationMain orderApplicationMain) {
        // Step.1 组装查询条件查询数据
        QueryWrapper<OrderApplicationMain> queryWrapper = QueryGenerator.initQueryWrapper(orderApplicationMain, request.getParameterMap());
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        //配置选中数据查询条件
        String selections = request.getParameter("selections");
        if (oConvertUtils.isNotEmpty(selections)) {
            List<String> selectionList = Arrays.asList(selections.split(","));
            queryWrapper.in("id", selectionList);
        }
        //Step.2 获取导出数据
        List<OrderApplicationMain> orderApplicationMainList = orderApplicationMainService.list(queryWrapper);

        // Step.3 组装pageList
        List<OrderApplicationMainPage> pageList = new ArrayList<OrderApplicationMainPage>();
        for (OrderApplicationMain main : orderApplicationMainList) {
            OrderApplicationMainPage vo = new OrderApplicationMainPage();
            BeanUtils.copyProperties(main, vo);
            List<OrderApplicationList> orderApplicationListList = orderApplicationListService.selectByMainId(main.getId());
            vo.setOrderApplicationListList(orderApplicationListList);
            pageList.add(vo);
        }

        // Step.4 AutoPoi 导出Excel
        ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
        mv.addObject(NormalExcelConstants.FILE_NAME, "电商采购月度申请表列表");
        mv.addObject(NormalExcelConstants.CLASS, OrderApplicationMainPage.class);
        mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("电商采购月度申请表数据", "导出人:" + sysUser.getRealname(), "电商采购月度申请表"));
        mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
        return mv;
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("orderapplication:order_application_main:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 获取上传文件对象
            MultipartFile file = entity.getValue();
            ImportParams params = new ImportParams();
            params.setTitleRows(2);
            params.setHeadRows(1);
            params.setNeedSave(true);
            try {
                List<OrderApplicationMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), OrderApplicationMainPage.class, params);
                for (OrderApplicationMainPage page : list) {
                    OrderApplicationMain po = new OrderApplicationMain();
                    BeanUtils.copyProperties(page, po);
                    orderApplicationMainService.saveMain(po, page.getOrderApplicationListList());
                }
                return Result.OK("文件导入成功！数据行数:" + list.size());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return Result.error("文件导入失败:" + e.getMessage());
            } finally {
                try {
                    file.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return Result.OK("文件导入失败！");
    }

    /**
     * 发送审核消息
     *
     * @param sender   消息发布人
     * @param receiver 用英文小写逗号分隔，例如：system,admin
     * @return
     */
    private Result<SysMessageTemplate> sendAuditMessage(String sender, String receiver, String title, String templateCode,
                                                        String departmentName, String createBy, String createTime, String reason) {
        Result<SysMessageTemplate> result = new Result<SysMessageTemplate>();
        try {
            // 构建消息模板内容参数
            Map<String, Object> data = new HashMap<>();
            data.put("department", departmentName);
            data.put("create_by", createBy);
            data.put("create_time", createTime);
            data.put("reason", reason);
            MessageDTO md = new MessageDTO();
            md.setFromUser(sender);
            md.setTitle(title);
            md.setTemplateCode(templateCode);
            md.setToUser(receiver);
            md.setType(MessageTypeEnum.XT.getType());
            md.setData(data);
            sysBaseApi.sendTemplateMessage(md);
            return result.success("提交成功！");
        } catch (Exception e) {
            log.error("发送消息出错，{}", e.getMessage());
            return result.error500("提交失败，发送站内消息出错！");
        }
    }

}
