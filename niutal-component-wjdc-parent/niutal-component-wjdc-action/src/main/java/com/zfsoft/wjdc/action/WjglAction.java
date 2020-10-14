package com.woshidaniu.wjdc.action;

import java.util.HashMap;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.interceptor.WjdcInterceptor;
import com.woshidaniu.wjdc.service.svcinterface.IWjBaseService;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class WjglAction extends BaseAction implements ModelDriven<WjglModel>{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private IWjglService service;
	@Autowired
	private IWjBaseService baseService;
	private WjglModel model=new WjglModel();
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	/**
	 * 查询问卷信息列表
	 * 
	 * @return
	 */
	public String cxWjxx() {

		if (QUERY.equals(model.getDoType())) {
			QueryModel queryModel = model.getQueryModel();
			try {
				queryModel.setItems(service.getPagedList(model));
			} catch (Exception e) {
				logException(e);
			}
			getValueStack().set(DATA, queryModel);
			return DATA;
		}
		try {
			getValueStack().set("wjlxList", baseService.getWjlxList());
			getValueStack().set("wjztList", baseService.getWjztList());
		} catch (Exception e) {
			logException(e);
		}
		return "cxWjxx";
	}
	
	/**
	 * 增加问卷信息
	 * @return
	 */
	public String zjWjxx(){
		try{
			getValueStack().set("wjlxList",baseService.getWjlxList());
			//发布类型列表
			getValueStack().set("wjfblxList", baseService.getWjfblxList());
		}catch (Exception e) {
			logException(e);
		}
		return "zjWjxx";
	}
	
	/**
	 * 增加保存问卷信息
	 * @return
	 */
	public String zjBcWjxx(){
		User user = getUser();

		try {
			model.setWjid(UniqID.getInstance().getUniqIDHash());
			model.setWjzt("草稿");
			model.setCjr(user.getYhm());
			boolean result = service.insert(model);

			if (result) {
				baseLog.insert(
						user,
						getText("log.message.ywmc", new String[] { "问卷管理",
								"ZFXG_WJDC_WJXXB" }),
						"问卷信息管理",
						getText("log.message.czms", new String[] { "新增问卷",
								"问卷名称", model.getWjmc() }));
			}

			String key = result ? "I99001" : "I99002";
			getValueStack().set("result", getText(key));
		} catch (Exception e) {
			logException(e);
		}
		return zjWjxx();
	}
	
	/**
	 * 修改问卷信息
	 * @return
	 */
	public String xgWjxx(){
		try{
			WjglModel wjglModel=service.getModel(model);
			BeanUtils.copyProperties(model, wjglModel);
			//发布类型列表
			getValueStack().set("wjfblxList", baseService.getWjfblxList());
			getValueStack().set("wjlxList",baseService.getWjlxList());
		}catch (Exception e) {
			logException(e);
		}
		return "xgWjxx";
	}
	
	/**
	 * 修改保存问卷信息
	 * @return
	 */
	public String xgBcWjxx(){
		User user = getUser();

		try {
			boolean result = service.update(model);

			if (result) {
				baseLog.update(
						user,
						getText("log.message.ywmc", new String[] { "问卷管理",
								"ZFXG_WJDC_WJXXB" }),
						"问卷信息管理",
						getText("log.message.czms", new String[] { "修改问卷",
								"问卷id", model.getWjid() }));
			}

			String key = result ? "I99003" : "I99004";
			getValueStack().set("result", getText(key));

		} catch (Exception e) {
			logException(e);
		}
		return xgWjxx();
	}
	
	/**
	 * 删除问卷信息
	 * @return
	 */
	public String scWjxx(){
		User user = getUser();
		String id = getRequest().getParameter("ids");
		try {
			String message = service.scWjxx(id);
			getValueStack().set(DATA, message);
			
			if("删除成功！".equals(message)){
				baseLog.insert(
						user,
						getText("log.message.ywmc", new String[] { "问卷管理","ZFXG_WJDC_WJXXB" }),
						"问卷管理",
						getText("log.message.czms", new String[] { "删除问卷信息",
								"问卷id", id }));
			}
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 获取问卷相关信息
	 * @return
	 */
	public String getWjxgxx(){
		String id = getRequest().getParameter("ids");
		try {
			HashMap<String, String> wjxgxx = service.getWjxgxx(id);
			getValueStack().set(DATA, wjxgxx);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**获取问卷类型列表*/
	public String getWjlxList(){
		try{
			getValueStack().set(DATA,baseService.getWjlxList());
		}catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**更新问卷状态*/
	public String updateWjzt(){
		try {
			if(model.getWjzt()!=null&&!"".equals(model.getWjzt())){
				boolean result=service.updateWjzt(model);
				String key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
			}
			WjglModel wjglModel=service.getModel(model);
			BeanUtils.copyProperties(model, wjglModel);
			getValueStack().set("wjztList", baseService.getWjztList());
		} catch (Exception e) {
			logException(e);
		}
		WjdcInterceptor.initWjysgnPath();
		return "updateWjzt";
	}
	
	/**问卷功能约束*/
	public String wjgnys(){
		try {
			if("update".equals(model.getDoType())){
				boolean result=service.updateWjgnys(model);
				String key = result ? "I99001" : "I99002";
				getValueStack().set("result", getText(key));
				WjdcInterceptor.initWjysgnPath();
			}
			getValueStack().set("wjgnysList", service.getWjgnysList(model));
		} catch (Exception e) {
			logException(e);
		}
		return "wjgnys";
	}

	public IWjglService getService() {
		return service;
	}

	public void setService(IWjglService service) {
		this.service = service;
	}

	public WjglModel getModel() {
		return model;
	}

	public void setModel(WjglModel model) {
		this.model = model;
	}

	public IWjBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IWjBaseService baseService) {
		this.baseService = baseService;
	}

}
