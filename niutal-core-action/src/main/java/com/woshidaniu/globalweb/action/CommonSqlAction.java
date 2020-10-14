package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.dao.entities.BjdmModel;
import com.woshidaniu.dao.entities.BmdmModel;
import com.woshidaniu.dao.entities.ZydmModel;
import com.woshidaniu.service.common.ICommonSqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
* 
* 类名称：CommonSqlAction 
* 类描述： 公共Action
* 创建人：caozf 
* 创建时间：2012-7-17 上午08:41:27 
* 修改备注： 
* @version 
*
 */
@Controller
@Scope("prototype")
public class CommonSqlAction extends BaseAction  {

	private static final long serialVersionUID = 1L;
	
	private BjdmModel bjdmMode;
	
	@Autowired
	private ICommonSqlService commonSqlService;

	/**
	 * 按班级-查询所有/该学院下的专业代码
	 * @return
	 */
	public String cxZydm(){
		ValueStack vs = getValueStack();
		Map<String,String> map = new HashMap<String,String>();
		String bmdm_id = getRequest().getParameter("bmdm_id");
		map.put("bmdm_id_lsbm", bmdm_id);
		try {
			List<ZydmModel> zydms = commonSqlService.queryZydm(map);
			vs.set(DATA,zydms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 按专业-查询所有/该学院下的专业代码
	 * @return
	 */
	public String cxZydmByXy(){
		ValueStack vs = getValueStack();
		Map<String,String> map = new HashMap<String,String>();
		String bmdm_id = getRequest().getParameter("bmdm_zydm_id");
		map.put("bmdm_id_lsbm", bmdm_id);
		try {
			List<ZydmModel> zydms = commonSqlService.queryZydm(map);
			vs.set(DATA,zydms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	/**
	 * 查询所有学院代码
	 * @return
	 */
	public String cxAllXydm(){
		ValueStack vs = getValueStack();
		try {
			List<BmdmModel> bmdms = commonSqlService.queryAllXy();
			vs.set(DATA,bmdms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	/**
	 * 查询班级代码
	 * @return
	 */
	public String cxBjdm(){
		ValueStack vs = getValueStack();
		try {
			List<BjdmModel> bjdms = commonSqlService.queryBjdm(bjdmMode);
			vs.set(DATA,bjdms);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		return DATA;
	}
	
	
	public ICommonSqlService getCommonSqlService() {
		return commonSqlService;
	}

	public void setCommonSqlService(ICommonSqlService commonSqlService) {
		this.commonSqlService = commonSqlService;
	}

	public BjdmModel getBjdmMode() {
		return bjdmMode;
	}

	public void setBjdmMode(BjdmModel bjdmMode) {
		this.bjdmMode = bjdmMode;
	}
	
	
}

