package org.jeecg.modules.oasystem.receipt.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.jeecg.modules.oasystem.receipt.entity.ReceiptList;
import org.jeecg.modules.oasystem.receipt.entity.ReceiptMain;
import org.jeecg.modules.oasystem.receipt.vo.ReceiptMainPage;
import org.jeecg.modules.oasystem.receipt.service.IReceiptMainService;
import org.jeecg.modules.oasystem.receipt.service.IReceiptListService;
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
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 物资验收
 * @Author: jeecg-boot
 * @Date:   2023-11-08
 * @Version: V1.0
 */
@Api(tags="物资验收")
@RestController
@RequestMapping("/receipt/receiptMain")
@Slf4j
public class ReceiptMainController {
	@Autowired
	private IReceiptMainService receiptMainService;
	@Autowired
	private IReceiptListService receiptListService;
	
	/**
	 * 分页列表查询
	 *
	 * @param receiptMain
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "物资验收-分页列表查询")
	@ApiOperation(value="物资验收-分页列表查询", notes="物资验收-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<ReceiptMain>> queryPageList(ReceiptMain receiptMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<ReceiptMain> queryWrapper = QueryGenerator.initQueryWrapper(receiptMain, req.getParameterMap());
		Page<ReceiptMain> page = new Page<ReceiptMain>(pageNo, pageSize);
		IPage<ReceiptMain> pageList = receiptMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param receiptMainPage
	 * @return
	 */
	@AutoLog(value = "物资验收-添加")
	@ApiOperation(value="物资验收-添加", notes="物资验收-添加")
    @RequiresPermissions("receipt:receipt_main:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody ReceiptMainPage receiptMainPage) {
		ReceiptMain receiptMain = new ReceiptMain();
		BeanUtils.copyProperties(receiptMainPage, receiptMain);
		receiptMainService.saveMain(receiptMain, receiptMainPage.getReceiptListList());
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param receiptMainPage
	 * @return
	 */
	@AutoLog(value = "物资验收-编辑")
	@ApiOperation(value="物资验收-编辑", notes="物资验收-编辑")
    @RequiresPermissions("receipt:receipt_main:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody ReceiptMainPage receiptMainPage) {
		ReceiptMain receiptMain = new ReceiptMain();
		BeanUtils.copyProperties(receiptMainPage, receiptMain);
		ReceiptMain receiptMainEntity = receiptMainService.getById(receiptMain.getId());
		if(receiptMainEntity==null) {
			return Result.error("未找到对应数据");
		}
		receiptMainService.updateMain(receiptMain, receiptMainPage.getReceiptListList());
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "物资验收-通过id删除")
	@ApiOperation(value="物资验收-通过id删除", notes="物资验收-通过id删除")
    @RequiresPermissions("receipt:receipt_main:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		receiptMainService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "物资验收-批量删除")
	@ApiOperation(value="物资验收-批量删除", notes="物资验收-批量删除")
    @RequiresPermissions("receipt:receipt_main:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.receiptMainService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "物资验收-通过id查询")
	@ApiOperation(value="物资验收-通过id查询", notes="物资验收-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<ReceiptMain> queryById(@RequestParam(name="id",required=true) String id) {
		ReceiptMain receiptMain = receiptMainService.getById(id);
		if(receiptMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(receiptMain);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "物品到货验明细通过主表ID查询")
	@ApiOperation(value="物品到货验明细主表ID查询", notes="物品到货验明细-通主表ID查询")
	@GetMapping(value = "/queryReceiptListByMainId")
	public Result<List<ReceiptList>> queryReceiptListListByMainId(@RequestParam(name="id",required=true) String id) {
		List<ReceiptList> receiptListList = receiptListService.selectByMainId(id);
		return Result.OK(receiptListList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param receiptMain
    */
    @RequiresPermissions("receipt:receipt_main:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, ReceiptMain receiptMain) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<ReceiptMain> queryWrapper = QueryGenerator.initQueryWrapper(receiptMain, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<ReceiptMain> receiptMainList = receiptMainService.list(queryWrapper);

      // Step.3 组装pageList
      List<ReceiptMainPage> pageList = new ArrayList<ReceiptMainPage>();
      for (ReceiptMain main : receiptMainList) {
          ReceiptMainPage vo = new ReceiptMainPage();
          BeanUtils.copyProperties(main, vo);
          List<ReceiptList> receiptListList = receiptListService.selectByMainId(main.getId());
          vo.setReceiptListList(receiptListList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "物资验收列表");
      mv.addObject(NormalExcelConstants.CLASS, ReceiptMainPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("物资验收数据", "导出人:"+sysUser.getRealname(), "物资验收"));
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
    @RequiresPermissions("receipt:receipt_main:importExcel")
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
              List<ReceiptMainPage> list = ExcelImportUtil.importExcel(file.getInputStream(), ReceiptMainPage.class, params);
              for (ReceiptMainPage page : list) {
                  ReceiptMain po = new ReceiptMain();
                  BeanUtils.copyProperties(page, po);
                  receiptMainService.saveMain(po, page.getReceiptListList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
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

}
