package com.woshidaniu.globalweb.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.util.Assert;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.entities.PlxgModel;
import com.woshidaniu.service.svcinterface.IPlxgService;
import com.woshidaniu.util.request.WebRequestUtils;
import com.woshidaniu.util.xml.BaseDataReader;
/***
 * 
 *@类名称:PlxgAction.java
 *@类描述：数据批量修改action
 *@创建人：kangzhidong
 *@创建时间：2015-4-10 下午12:38:43
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class PlxgAction extends CommonBaseAction implements ModelDriven<PlxgModel>{
	
	protected PlxgModel model = new PlxgModel();
	protected List<PlxgModel> list = null;
	@Resource
	private IPlxgService service;
	
	/***
	 * 
	 *@描述：查询数据批量修改功能
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午03:01:08
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxPlxgIndex(){
		try {
			if (QUERY.equals(model.getDoType())) {
				QueryModel queryModel = model.getQueryModel();
				queryModel.setItems(getService().getPagedList(model));
				getValueStack().set(DATA, queryModel);
				return DATA;
			}
			this.setProperty("gnmkdmPairList", getService().getGnmkdmPairList());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return SUCCESS;
	}
	
	public String cxPlxg() {
		getValueStack().set("gnmkdm",model.getGnmkdm());
		getValueStack().set("modelList",getService().getModelList(model));
		return SUCCESS;
	}

	/**
	 * 
	 *@描述：启用/停用字段的批量修改
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-10下午02:43:19
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String szZdplxg() {
		try {
			for (PlxgModel element : list) {
				element.setGnmkdm(model.getGnmkdm());
			}
			getService().batchUpdate(list);
			getValueStack().set(DATA, getText("I99007",new String[]{"字段批量修改停用"}));
		} catch (Exception e) {
			logException(e);
			getValueStack().set(DATA, getText("I99008",new String[]{"字段批量修改停用"}));
		}
		return DATA;
	}
	
	/**
	 * 
	 *@描述：跳转到批量修改界面
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-13下午01:53:37
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public String cxPlxgSettings() {
		if(BlankUtils.isBlank(model.getGnmkdm())){
			getValueStack().set("msg","gnmkdm 不能为空 !");
			return ERROR;
		}
		getValueStack().set("modelList",getService().getBatchModelList(model));
		getValueStack().set("xgfsList",BaseDataReader.getCachedBaseDataList("xgfsList"));
		getValueStack().set("plxgURL", getRequest().getParameter("plxgURL"));
		//解析请求的参数
		getValueStack().set("requestMap", getPairParameters(getRequest()));
		return "batchUpdateData";
	}
	

	public List<PairModel> getPairParameters(ServletRequest request) {
		Assert.notNull(request, "Request must not be null");
		//解析请求的参数
		Map<String, String> requestMap = WebRequestUtils.getParameters(request);
		requestMap.remove("title");
		requestMap.remove("gnmkdm");
		requestMap.remove("gnmkdmKey");
		requestMap.remove("plxgURL");
		requestMap.remove(ZFtalParameters.getGlobalString(ZFtalParameter.REQUEST_FUNC_USERKEY));
		requestMap.remove("time");
		List<PairModel> params = new ArrayList<PairModel>();
		for (String key : requestMap.keySet()) {
			params.add(new PairModel(key, requestMap.get(key)));
		}
		return params;
	}
	

	public PlxgModel getModel() {
		model.setUser(getUser());
		return model;
	}

	public void setModel(PlxgModel model) {
		this.model = model;
	}

	public List<PlxgModel> getList() {
		return list;
	}

	public void setList(List<PlxgModel> list) {
		this.list = list;
	}

	public IPlxgService getService() {
		return service;
	}

	public void setService(IPlxgService service) {
		this.service = service;
	}
	
}
