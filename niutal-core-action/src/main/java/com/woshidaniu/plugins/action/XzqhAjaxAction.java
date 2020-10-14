package com.woshidaniu.plugins.action;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.system.cache.CacheLogic;
import com.woshidaniu.common.system.emum.BaseGBDataEnum;
import com.woshidaniu.dao.entities.GBXzqModel;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.service.svcinterface.IXzqhService;

/**
 * 行政区划加载通用类
 * @author Penghui.Qu
 *
 */
@Controller
@Scope("prototype")
public class XzqhAjaxAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	@Autowired
	private IXzqhService xzqhService;
	
	public void setXzqhService(IXzqhService xzqhService) {
		this.xzqhService = xzqhService;
	}

	/**
	 * 加载省
	 * @return
	 */
	public String getShengList(){
		try {
			ValueStack vs=getValueStack();
			List<SjbzModel> shengList = xzqhService.getShengList();
			vs.set(DATA, shengList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 加载省
	 * @return
	 */
	public String likeQueryShengList(){
		try {
			List<SjbzModel> shengList = xzqhService.likeQueryShengList(this.getQueryMap(getRequest()));
			getValueStack().set(DATA, shengList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}

	/**
	 * 加载市
	 * @return
	 */
	public String getShiList(){
		
		String sheng = getRequest().getParameter("parentValue");
		try {
			ValueStack vs=getValueStack();
			String v_xzq = getRequest().getParameter("v_xzq");
			boolean f = true;
			if(StringUtils.isNotBlank(v_xzq) && StringUtils.equals("0", v_xzq)){
				f = false;
			}
			List<SjbzModel> shiList = xzqhService.getShiList(sheng, f);
			vs.set(DATA, shiList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	/**
	 * 加载市
	 * @return
	 */
	public String likeQueryShiList(){
		try {
			List<SjbzModel> shengList = xzqhService.likeQueryShiList(this.getQueryMap(getRequest()));
			getValueStack().set(DATA, shengList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 加载县
	 * @return
	 */
	public String getXianList(){
		String shi = getRequest().getParameter("parentValue");
		try {
			ValueStack vs=getValueStack();
			List<SjbzModel> xianList = xzqhService.getXianList(shi);
			vs.set(DATA, xianList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 获取行政区号名称合并列表 ajax
	 * @return
	 */
	public String getXzqhMcHbListAjax(){
		ValueStack vs=getValueStack();
		try {
			List<GBXzqModel> xzqhList =  (List<GBXzqModel>) CacheLogic.getGBCache(BaseGBDataEnum.XING_ZHENG_QU.getValue());   //内容取至缓存
			//List<SjbzModel> xzqhList = xzqhService.getXzqhMcHbList();
			vs.set(DATA, xzqhList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	/**
	 * 模糊查询行政区号名称 ajax
	 * @return
	 */
	public String likeQueryXzqhMcHbListAjax(){
		String keyword = getRequest().getParameter("keyword");
		String showsize = getRequest().getParameter("showsize");
		if(StringUtils.isEmpty(keyword)){
			keyword = "";
		}
		if(StringUtils.isEmpty(showsize)){
			showsize = "10";
		}
		
		ValueStack vs=getValueStack();
		try {
			HashMap<String,Object> mp = new HashMap<String,Object>();
			mp.put("keyword", keyword);
			mp.put("showsize", showsize);
			List<SjbzModel> xzqhList = xzqhService.likeQueryXzqhMcHbList(mp);
			vs.set(DATA, xzqhList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
	private HashMap<String,Object> getQueryMap(HttpServletRequest request){
		HashMap<String,Object> mp = new HashMap<String,Object>();
		String keyword = getRequest().getParameter("keyword");
		String showsize = getRequest().getParameter("showsize");
		String parentValue = getRequest().getParameter("parentValue");
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
		return mp;
	}
	
	/**
	 * 加载国家
	 * @return
	 */
	public String likeQueryGuoJiaListAjax(){
		String keyword = getRequest().getParameter("keyword");
		String showsize = getRequest().getParameter("showsize");
		if(StringUtils.isEmpty(keyword)){
			keyword = "";
		}
		if(StringUtils.isEmpty(showsize)){
			showsize = "10";
		}
		
		ValueStack vs=getValueStack();
		try {
			HashMap<String,Object> mp = new HashMap<String,Object>();
			mp.put("keyword", keyword);
			mp.put("showsize", showsize);
			List<SjbzModel> xzqhList = xzqhService.likeQueryGuoJiaList(mp);
			vs.set(DATA, xzqhList);
		} catch (Exception e) {
			logException(e);
		}
		return DATA;
	}
	
}
