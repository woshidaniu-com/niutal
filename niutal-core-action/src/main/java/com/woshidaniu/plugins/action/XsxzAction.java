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
import com.woshidaniu.dao.entities.XsxzModel;
import com.woshidaniu.service.svcinterface.IXsxzService;

/**
 * 选择学生信息
 * @author Jiangdong.Yi
 *
 */
@Controller
@Scope("prototype")
public class XsxzAction extends BaseAction implements ModelDriven<XsxzModel>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private XsxzModel model=new XsxzModel();
	
	@Autowired
	private IXsxzService xsxzService;
	
	private final String DOSEARCH_SEARCH="search";//查询跳转标记
	public XsxzModel getModel() {
		return model;
	}
	public void setXsxzService(IXsxzService xsxzService) {
		this.xsxzService = xsxzService;
	}
	
	/**
	 * 查询学生信息
	 * @return
	 */
	public String cxXsxz(){
		ValueStack vs=getValueStack();
		try {
			QueryModel queryModel = model.getQueryModel();
			if (QUERY.equals(model.getDoType()) && model.getDoSearch() == null){
				queryModel.setItems(new ArrayList<XsxzModel>());
				vs.set(DATA, queryModel);
				return DATA;
			}else if(QUERY.equals(model.getDoType()) && DOSEARCH_SEARCH.equals(model.getDoSearch())){
				queryModel.setItems(xsxzService.getPagedList(model));
				vs.set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
		}
		return "cxXsxz";
	}
	
	/**
	 * 查询单个学生信息
	 * @return
	 */
	public String ckXsxzAjax(){
		ValueStack vs=getValueStack();
		try{
			XsxzModel xsxzModel=xsxzService.getModel(model);
			List<XsxzModel> list=new ArrayList<XsxzModel>();
			list.add(xsxzModel);
			vs.set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 查询单个学生信息
	 * @return
	 */
	public String ckXsxzOneAjax(){
		ValueStack vs=getValueStack();
		try{
			XsxzModel xsxzModel=xsxzService.getModel(model);
			vs.set(DATA, xsxzModel);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 查询学生选择列表
	 * @return
	 */
	public String cxXsxzAjax(){
		String ids = getRequest().getParameter("ids");
		ValueStack vs=getValueStack();
		try{
			List<XsxzModel> list=xsxzService.getModelList(ids);
			vs.set(DATA, list);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 查询学生信息  带数据范围的
	 * @return
	 */
	public String cxXsxzSjfw(){
		ValueStack vs=getValueStack();
		try {
			QueryModel queryModel = model.getQueryModel();
			if (QUERY.equals(model.getDoType()) && model.getDoSearch() == null){
				queryModel.setItems(new ArrayList<XsxzModel>());
				vs.set(DATA, queryModel);
				return DATA;
			}else if(QUERY.equals(model.getDoType()) && DOSEARCH_SEARCH.equals(model.getDoSearch())){
				queryModel.setItems(xsxzService.getPagedByScope(model));
				vs.set(DATA, queryModel);
				return DATA;
			}
		} catch (Exception e) {
			logException(e);
		}
		return "cxXsxzSjfw";
	}
	
	
}
