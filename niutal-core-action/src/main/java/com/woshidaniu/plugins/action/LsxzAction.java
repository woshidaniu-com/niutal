package com.woshidaniu.plugins.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.dao.entities.LsxzModel;
import com.woshidaniu.service.svcinterface.ILsxzService;

/**
 * 选择老师信息
 * @author Jiangdong.Yi
 *
 */
@Controller
@Scope("prototype")
public class LsxzAction extends BaseAction implements ModelDriven<LsxzModel>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LsxzModel model=new LsxzModel();
	
	@Autowired
	private ILsxzService lsxzService;
	private final String DOSEARCH_SEARCH="search";//查询跳转标记
	public LsxzModel getModel() {
		return model;
	}
	public void setLsxzService(ILsxzService lsxzService) {
		this.lsxzService = lsxzService;
	}
	
	/**
	 * 查询老师信息
	 * @return
	 */
	public String cxLsxz(){
		ValueStack vs=getValueStack();
		try {
			QueryModel queryModel = model.getQueryModel();
			if (QUERY.equals(model.getDoType()) && model.getDoSearch() == null){
				queryModel.setItems(new ArrayList<LsxzModel>());
				vs.set(DATA, queryModel);
				return DATA;
			}else if(QUERY.equals(model.getDoType()) && DOSEARCH_SEARCH.equals(model.getDoSearch())){
				queryModel.setItems(lsxzService.getPagedList(model));
				vs.set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
		}
		return "cxLsxz";
	}
	
	/**
	 * 查询单个老师信息
	 * @return
	 */
	public String ckLsxzAjax(){
		ValueStack vs=getValueStack();
		try{
			LsxzModel lsxzModel=lsxzService.getLsxzByZgh(model.getZgh());
			vs.set(DATA, lsxzModel);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 查询老师选择列表
	 * @return
	 */
	public String cxLsxzAjax(){
		String ids = getRequest().getParameter("ids");
		ValueStack vs=getValueStack();
		try{
			List<LsxzModel> list=lsxzService.getModelList(ids);
			vs.set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 查询老师选择列表   ajax方式，
	 * @return
	 */
	public String cxLsxzListAjax(){
		ValueStack vs=getValueStack();
		try{
			List<LsxzModel> list=lsxzService.getLsxxList(model);
			vs.set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
}
