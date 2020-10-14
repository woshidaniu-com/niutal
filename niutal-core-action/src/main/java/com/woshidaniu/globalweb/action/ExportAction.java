package com.woshidaniu.globalweb.action;


import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigPhModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 自定义导出
 * @author Penghui.Qu
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ExportAction extends BaseAction implements ModelDriven<ExportModel>{

	private static final long serialVersionUID = 1L;
	private static final String EXPORT_CONFIG = "exportConfig";
	
	private ExportModel model = new ExportModel();
	@Autowired
	@Qualifier("exportExcel")
	private IExportService exportService;

	public void setExportService(IExportService exportService) {
		this.exportService = exportService;
	}

	public ExportModel getModel() {
		return model;
	}
	
	/**
	 * 获取导出配置信息
	 * @return
	 */
	public String exportConfig(){
		
		User user = getUser();
		model.setZgh(user.getYhm());
		//查询偏好设置
		List<ExportConfigPhModel> phList = exportService.getConfigPhList(model);
		//查询导出列
		List<ExportConfigModel> configList = exportService.getConfigList(model);
//		List<ExportConfigModel> configList = exportService.getExportPublicConfig(model.getDcclbh());
		//根据显示顺序排序
		//Collections.sort(configList,new ExportComparator());
		
		ValueStack stack = getValueStack();
		stack.set("configList", configList);
		stack.set("phList", phList);
		return EXPORT_CONFIG;
	}
	
	public String zjExportConfig(){
		return "zjExportConfig";
	}
	
	/**
	 * 获取偏好设置的字段列表
	 * @return
	 */
	public String cxConfigPhZdList(){
		User user = getUser();
		String zgh = user.getYhm();
		String id = getRequest().getParameter("id");
		List<ExportConfigModel> configPhZdList = exportService.getConfigPhZdList(id, zgh);
		ValueStack valueStack = getValueStack();
		valueStack.set(DATA, configPhZdList);
		return DATA;
	}
	
	/**
	 * 保存自定义导出配置
	 * @return
	 */
	public String saveCustomConfig(){
		
		User user = getUser();
		model.setZgh(user.getYhm());
		boolean isSuccess = exportService.saveExportConfig(model);
		ValueStack stack = getValueStack();
		stack.set(DATA, isSuccess);
		return DATA;
	}
	
	/**
	 * 删除自定义导出配置
	 * @return
	 */
	public String deleteCustomConfig(){
		boolean isSuccess = exportService.deleteExportConfig(model);
		ValueStack stack = getValueStack();
		stack.set(DATA, isSuccess);
		return DATA;
	}
	
	
}
