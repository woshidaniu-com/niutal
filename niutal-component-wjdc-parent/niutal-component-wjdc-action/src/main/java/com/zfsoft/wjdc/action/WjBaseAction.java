package com.woshidaniu.wjdc.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.wjdc.dao.entites.WjpzModel;
import com.woshidaniu.wjdc.interceptor.WjdcInterceptor;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;

/**
 * 问卷基础ACTION
 * @author Administrator
 *
 */
@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjBaseAction extends BaseAction implements ModelDriven<WjpzModel> {

	private WjpzModel model = new WjpzModel();
	
	@Autowired
	private IWjBaseService iWjBaseService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 查询条件字段列表值加载
	 * @return
	 */
	public String getCxzdOption() {
		try {
			List<HashMap<String, String>> list = iWjBaseService.getZdoptionList(model); 
			getValueStack().set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 检查问卷对象数据是否存在
	 * @return
	 */
	public String jcWjdxsj() {
		Map<String, String> map = iWjBaseService.formatMap(getRequest().getParameterMap());
		map.put("bm", getRequest().getParameter("bm"));
		try {
			boolean result = iWjBaseService.getWjdxbsjByTj(map);
			getValueStack().set(DATA, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DATA;
	}
	
	/**
	 * 初始化问卷约束功能路径
	 * @return
	 */
	public String initWjysgnPath(){
		WjdcInterceptor.initWjysgnPath();
		return null;
	}
	
	public WjpzModel getModel() {
		return model;
	}

	public IWjBaseService getiWjBaseService() {
		return iWjBaseService;
	}

	public void setiWjBaseService(IWjBaseService iWjBaseService) {
		this.iWjBaseService = iWjBaseService;
	}


}
