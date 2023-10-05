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
    private ISysDepartService sysDepartService;
    @Autowired
    private ISysBaseAPI sysBaseApi;

    /**
     * 分页列表查询
     *
     * @param orderApplicationMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "电商采购月度申请表-分页列表查询")
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
     * 根据部门编码获取部门信息
     *
     * @param orgCode
     * @return
     */
    @GetMapping("/getDepartName")
    public Result<String> getDepartName(@RequestParam(name = "orgCode") String orgCode) {
        Result<String> result = new Result<>();
        LambdaQueryWrapper<SysDepart> query = new LambdaQueryWrapper<>();
        query.eq(SysDepart::getOrgCode, orgCode);
        SysDepart sysDepart = sysDepartService.getOne(query);
        result.setSuccess(true);
        result.setResult(sysDepart.getDepartName());
        return result;
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
    public Result<String> audit(@RequestBody OrderApplicationMainPage orderApplicationMainPage) {
        OrderApplicationMain orderApplicationMain = new OrderApplicationMain();
        BeanUtils.copyProperties(orderApplicationMainPage, orderApplicationMain);
        OrderApplicationMain orderApplicationMainEntity = orderApplicationMainService.getById(orderApplicationMain.getId());
        if (orderApplicationMainEntity == null) {
            return Result.error("未找到对应数据");
        }
        //获取当前用户
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        String auditorType = null;
        if (sysUser.getUsername().equals(orderApplicationMainEntity.getManagerUsername())) {
            auditorType = OrderApplicationConstant.AUDITOR_TYPE_MANAGER;
        } else if (sysUser.getUsername().equals(orderApplicationMainEntity.getLeaderUsername())) {
            auditorType = OrderApplicationConstant.AUDITOR_TYPE_LEADER;
        } else {
            return Result.error("{}无权审核该申请！", sysUser.getRealname());
        }
        orderApplicationMainService.auditMain(auditorType, orderApplicationMain, orderApplicationMainPage.getOrderApplicationListList());
        return Result.OK("审核完毕！");
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
        orderApplicationMain.setApplicationStatus(OrderApplicationConstant.APPLICANT_SUBMITTED_WATING_FOR_MANAGER);
        orderApplicationMainService.updateApplicationStatus(orderApplicationMain);
        // 消息提示 https://help.jeecg.com/java/java/msgpush.html
        String receiver = orderApplicationMain.getManagerUsername() + "," + orderApplicationMain.getLeaderUsername();
        Map<String, Object> data = new HashMap<>();
        data.put("sys_org_code", orderApplicationMain.getSysOrgCode());
        data.put("create_by", orderApplicationMain.getCreateBy());
        data.put("create_time", DateUtils.formatDate(orderApplicationMain.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        data.put("reason", orderApplicationMain.getReason());
        return sendMessageByType(receiver, MessageTypeEnum.XT.getType(), data);
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
    //@AutoLog(value = "电商采购月度申请表采购物资具体要求通过主表ID查询")
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
     * @param receiver 用英文小写逗号分隔，例如：system,admin
     * @param type     标识发送系统推送或邮件类型
     * @param data     消息模板中例如${sys_org_code}自定义参数，用json形式保存
     * @return
     */
    private Result<SysMessageTemplate> sendMessageByType(String receiver, String type, Map<String, Object> data) {
        Result<SysMessageTemplate> result = new Result<SysMessageTemplate>();
        try {
            MessageDTO md = new MessageDTO();
            md.setFromUser("系统");
            md.setTitle("新的物资采购申请审批");
            md.setTemplateCode(OrderApplicationConstant.MESSAGE_TEMPLATE_CODE);
            md.setToUser(receiver);
            md.setType(type);
            md.setData(data);
            sysBaseApi.sendTemplateMessage(md);
			return result.success("提交成功！");
        } catch (Exception e) {
			log.error("发送消息出错，{}", e.getMessage());
			return result.error500("提交失败，发送站内消息出错！");
		}
    }

}
