package com.woshidaniu.globalweb.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;

/**
 * 自定义导出
 * @author Penghui.Qu
 *
 */
@Controller
@RequestMapping(value = "/niutal/dc/export")
public class ExportController extends BaseController{
	@Autowired
	@Qualifier("exportExcel")
	private IExportService exportService;
	
	/**
	 * 获取导出配置信息
	 * @return
	 */
	@RequestMapping("/exportConfig.zf")
	public String exportConfig(HttpServletRequest request,ExportModel model){
		User user = getUser();
		model.setZgh(user.getYhm());
		model.setJs(user.getJsdm());
		List<ExportConfigPhModel> phList = exportService.getConfigPhList(model);
		List<ExportConfigModel> configList = exportService.getExportPublicConfig(model);
		request.setAttribute("configList", configList);
		request.setAttribute("phList", phList);
		request.setAttribute("dcclbh", model.getDcclbh());
		return "/dc/exportConfig";
	}
	
	@RequestMapping("/zjExportConfig.zf")
	public String zjExportConfig(){
		return "/dc/zjExportConfig";
	}
	
	/**
	 * 获取偏好设置的字段列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/cxConfigPhZdList.zf")
	public Object cxConfigPhZdList(HttpServletRequest request,String id){
		User user = getUser();
		String zgh = user.getYhm();
		List<ExportConfigModel> configPhZdList = exportService.getConfigPhZdList(id, zgh);
		return configPhZdList;
	}
	
	/**
	 * 保存自定义导出配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/saveCustomConfig.zf")
	public Object saveCustomConfig(ExportModel model){
		User user = getUser();
		model.setZgh(user.getYhm());
		try {
			return exportService.saveExportConfig2(model);
		} catch (Exception e) {
			logException(e);
		}
		return MessageKey.SAVE_FAIL.getJson();
	}
	
	/**
	 * 删除自定义导出配置
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteCustomConfig.zf")
	public Object deleteCustomConfig(ExportModel model){
		boolean isSuccess = false;
		try {
			isSuccess = exportService.deleteExportConfig(model);
		} catch (Exception e) {
			logException(e);
		}
		return isSuccess ? MessageKey.DELETE_SUCCESS.getJson() :MessageKey.DELETE_FAIL.getJson();
	}
	

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}
	
}
