package com.woshidaniu.zxzx.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.dao.entities.YhglModel;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.zxzx.dao.entities.YhbkModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.service.svcinterface.IYhbkService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;

/**
 * 
 * @类名 YhbkAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月23日 下午6:05:19
 * @功能描述 在线咨询-用户板块
 *
 */
@Controller("zxzxYhbkAction")
@Scope("prototype")
public class YhbkAction extends BaseAction implements ModelDriven<YhbkModel>{

	private static final long serialVersionUID = -3719291752289690919L;

	private YhbkModel model = new YhbkModel();
	
	@Autowired
	@Qualifier("zxzxYhbkService")
	private IYhbkService service;
	
	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;
	
	@Autowired
	private IBmdmService bmdmService;
	
	/**
	 * 查询用户板块信息
	 * @return
	 */
	public String cxYhbk(){
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<YhbkModel> pagedList = service.getPagedList(getModel());
				queryModel.setItems(pagedList);
				getValueStack().set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		return SUCCESS;
	}
	
	/**
	 * 查询版块用户信息
	 * @return
	 */
	public String cxBkyhxxAjax(){
		try{
			QueryModel queryModel = model.getQueryModel();
			List<YhglModel> pagedList = service.getPagedListYfpYhxx(getModel());
			queryModel.setItems(pagedList);
			getValueStack().set(DATA, queryModel);
		} catch(Exception ex){
			logException(ex);
		}
		return DATA;
	}
	
	/**
	 * 分配用户板块信息
	 * @return
	 */
	public String fpBkyh(){
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<YhglModel> pagedList = service.getPagedListYfpYhxx(getModel());
				queryModel.setItems(pagedList);
				getValueStack().set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		ValueStack vs = getValueStack();
		List<kzdkModel> kzdkList = kzdkService.getModelList(new kzdkModel());
		List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
		vs.set("jgdmsList", jgdmsList);
		vs.set("kzdkList", kzdkList);
		return SUCCESS;
	}
	
	/**
	 * 增加板块用户
	 * @return
	 */
	public String zjBkyh(){
		if("query".equals(model.getDoType())){
			try{
				QueryModel queryModel = model.getQueryModel();
				List<YhglModel> pagedList = service.getPagedListWfpYhxx(getModel());
				queryModel.setItems(pagedList);
				getValueStack().set(DATA, queryModel);
			} catch(Exception ex){
				logException(ex);
			}
			return DATA;
		}
		ValueStack vs = getValueStack();
		List<BmdmModel> jgdmsList = bmdmService.getModelList(new BmdmModel());
		vs.set("jgdmsList", jgdmsList);
		return SUCCESS;
	}
	
	/**
	 * 增加保存版块用户
	 * @return
	 */
	public String zjBcBkyh(){
		String message = null;
		if(model.getBkdm() == null){
			message = getText("I99002M", new String[]{"参数错误【版块代码为空】"});
		}else{
			List<YhbkModel> modelList = new ArrayList<YhbkModel>();
			String[] zghs = model.getZgh().split(",");
			for (String s : zghs) {
				YhbkModel queryModel = new YhbkModel();
				queryModel.setBkdm(model.getBkdm());
				queryModel.setZgh(s);
				modelList.add(queryModel);
			}
			boolean result = service.batchInsert(modelList);
			message = getText(result?"I99001":"I99002");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	/**
	 * 删除保存版块用户
	 * @return
	 */
	public String scBcBkyh(){
		String message = null;
		if(model.getBkdm() == null){
			message = getText("I99002M", new String[]{"参数错误【版块代码为空】"});
		}else{
			List<YhbkModel> modelList = new ArrayList<YhbkModel>();
			String[] zghs = model.getZgh().split(",");
			for (String s : zghs) {
				YhbkModel queryModel = new YhbkModel();
				queryModel.setBkdm(model.getBkdm());
				queryModel.setZgh(s);
				modelList.add(queryModel);
			}
			boolean result = service.batchDelete(modelList);
			message = getText(result?"I99005":"I99006");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	@Override
	public YhbkModel getModel() {
		return model;
	}
	public IYhbkService getService() {
		return service;
	}
	public void setService(IYhbkService service) {
		this.service = service;
	}
}
