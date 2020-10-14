package com.woshidaniu.globalweb.action;

import com.opensymphony.xwork2.Action;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.service.svcinterface.IBmdmService;
import com.woshidaniu.service.svcinterface.IYhglService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * @author xiaokang
 * 
 * 通用数据选择action
 *
 */
@Controller
@Scope("prototype")
public class CommonSelectAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4528404133420492014L;
	
	/* 用户管理接口*/
	@Autowired
	private IYhglService yhglService;
	/*部门管理接口*/
	@Autowired
	private IBmdmService bmdmService;
	
	
	/**
	 * 选择用户
	 * @return
	 */
	public String selectUser(){
//		String q_type = getRequest().getParameter("q_type");
//		String q_bmdm = getRequest().getParameter("q_bmdm");
//		if(StringUtils.equals("1", q_type)){
//			if(StringUtils.isNotBlank(q_bmdm)){
//				YhglModel yhModel = new YhglModel();
//				yhModel.setJgdm(q_bmdm);
//				List<Map<String, String>> cxYhByJgdm = yhglService.cxYhMapByJgdm(yhModel);
//				getValueStack().set(DATA, cxYhByJgdm);
//			}
//			return DATA;
//		}
//		//查詢所有機構代碼
//		Map<String,String> paramMap = new HashMap<String,String>();
//		if(StringUtils.isNotBlank(q_bmdm)){
//			paramMap.put("bmdm", q_bmdm);
//		}
//		List<Map<String, String>> bmList = bmdmService.getModelMapList(paramMap);
//		getValueStack().set("bmList", bmList);
		return Action.SUCCESS;
	}


	public IYhglService getYhglService() {
		return yhglService;
	}


	public void setYhglService(IYhglService yhglService) {
		this.yhglService = yhglService;
	}


	public IBmdmService getBmdmService() {
		return bmdmService;
	}


	public void setBmdmService(IBmdmService bmdmService) {
		this.bmdmService = bmdmService;
	}
	
}
