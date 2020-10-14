package com.woshidaniu.plugins.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.dao.entities.XsbmModel;
import com.woshidaniu.service.svcinterface.IXsbmService;

/**
 * 年级、学院、层次、专业、班级加载
 * @author Penghui.Qu
 *
 */
@Controller
@Scope("prototype")
public class XsbmAjaxAction extends BaseAction implements ModelDriven<XsbmModel>{

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IXsbmService xsbmService;
	private XsbmModel model = new XsbmModel();

	public void setXsbmService(IXsbmService xsbmService) {
		this.xsbmService = xsbmService;
	}

	public XsbmModel getModel() {
		return model;
	}
	
	
	/**
	 * 加载学院列表
	 * @return
	 */
	public String getXyList(){
		
		try {
			List<XsbmModel> xyList = xsbmService.getXyList();
			getValueStack().set(DATA, xyList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	
	/**
	 * 加载专业列表
	 * @return
	 */
	public String getZyList(){
		
		try {
			List<XsbmModel> zyList = xsbmService.getZyList(model);
			getValueStack().set(DATA, zyList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 加载专业列表
	 * @return
	 */
	public String likeQueryZyList(){
		ValueStack vs=getValueStack();
		try {
			List<SjbzModel> xzqhList = xsbmService.likeQueryZyList(getLikeQueryMap(getRequest()));
			vs.set(DATA, xzqhList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	
	/**
	 * 加载班级列表
	 * @return
	 */
	public String getBjList(){
		
		try {
			List<XsbmModel> bjList = xsbmService.getBjList(model);
			getValueStack().set(DATA, bjList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 模糊查询加载班级列表
	 * @return
	 */
	public String likeQueryBjList(){
		ValueStack vs=getValueStack();
		try {
			List<SjbzModel> bjList = xsbmService.likeQueryBjList(getLikeQueryMap(getRequest()));
			vs.set(DATA, bjList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	
	/**
	 * 加载年级列表
	 * @return
	 */
	public String getNjList(){
		try {
			List<XsbmModel> njList = xsbmService.getNjList();
			getValueStack().set(DATA, njList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 加载层次列表
	 * @return
	 */
	public String getCcList(){
		
		try {
			List<XsbmModel> ccList = xsbmService.getCcList();
			getValueStack().set(DATA, ccList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	public String getBmListByXlcc(){
		try {
			List<XsbmModel> bmList = xsbmService.getBmListByXlcc(model);
			getValueStack().set(DATA, bmList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 根据学历层次加载学院列表
	 * @return
	 * @throws Exception
	 */
	public String likeQueryBmListByXlcc(){
		ValueStack vs=getValueStack();
		try {
			List<SjbzModel> xzqhList = xsbmService.likeQueryBmListByXlcc(getLikeQueryMap(getRequest()));
			vs.set(DATA, xzqhList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	private HashMap<String,Object> getLikeQueryMap(HttpServletRequest request){
		HashMap<String,Object> mp = new HashMap<String,Object>();
		String keyword = getRequest().getParameter("keyword");
		String showsize = getRequest().getParameter("showsize");
		String parentValue = getRequest().getParameter("parentValue");
		String secondParentValue = getRequest().getParameter("secondParentValue");
		if(StringUtils.isEmpty(keyword)){
			keyword = "";
		}
		if(StringUtils.isEmpty(showsize)){
			showsize = "10";
		}
		if(StringUtils.isEmpty(parentValue)){
			parentValue = "";
		}
		mp.put("keyword", keyword);
		mp.put("showsize", showsize);
		mp.put("parentValue", parentValue);
		mp.put("secondParentValue", secondParentValue);
		return mp;
	}
}
