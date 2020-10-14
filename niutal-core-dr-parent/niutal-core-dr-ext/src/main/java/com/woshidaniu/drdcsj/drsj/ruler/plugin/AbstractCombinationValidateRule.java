/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.ruler.plugin;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrpzModel;
/**
 * @author kzd
 */
public abstract class AbstractCombinationValidateRule implements CombinationValidateRule {
	
	@Override
	public abstract void destroy() ;

	@Override
	public abstract void init() ;

	@Override
	public abstract boolean[] validate(String[] drlmcs, Map<String, List<String>> drlValues) ;
	
	private HttpServletRequest request;
	
	private Map<String,String> initialMap; 
	
	private DrpzModel drpzModel;
	
	private List<DrlpzModel> drlpzModelList;
	
	protected HttpServletRequest getServletRequest(){
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public DrpzModel getDrpzModel() {
		return drpzModel;
	}

	public void setDrpzModel(DrpzModel drpzModel) {
		this.drpzModel = drpzModel;
	}

	public List<DrlpzModel> getDrlpzModelList() {
		return drlpzModelList;
	}

	public void setDrlpzModelList(List<DrlpzModel> drlpzModelList) {
		this.drlpzModelList = drlpzModelList;
	}

	public Map<String, String> getInitialMap() {
		return initialMap;
	}

	public void setInitialMap(Map<String, String> initialMap) {
		this.initialMap = initialMap;
	}
	
}
