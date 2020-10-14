/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 ZxzzWebAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月25日 下午7:39:10
 * @功能描述 
 * 
 */
@Controller("zxzxWebAction")
@Scope("prototype")
public class ZxzzWebAction extends BaseAction implements ModelDriven<ZxwtModel>,ValidationAware{
	
	private static final long serialVersionUID = -6718266484239464777L;
	
	private ZxwtModel model = new ZxwtModel();
	
	private String yzm;
	
	private String webSearchBkdmValue;
	
	private String webSearchValue;
	
	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService zxwtService;
	
	@Autowired
	@Qualifier("zxkzdkxxService")
	private IkzdkService kzdkService;
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;
	
	@Autowired
	@Qualifier("zxzxCjwtService")
	private ICjwtService cjwtService;
	
	/**
	 * 首页
	 * @return
	 */
	public String index(){
		ValueStack vs = getValueStack();
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		//搜索版块
		//List<kzdkModel> searchkzdkList = new ArrayList<kzdkModel>();
		
//		if(webBkdmValues != null && webBkdmValues.length > 0){
//			for (String bkdm : webBkdmValues) {
//				for (kzdkModel kzdkModel : kzdkList) {
//					if(kzdkModel.getBkdm().equals(bkdm)){
//						searchkzdkList.add(kzdkModel);
//						break;
//					}
//				}
//			}
//		}
		
		//查询约束
		if(webSearchBkdmValue != null && (!"".equals(webSearchBkdmValue.trim()))
				){
			model.setBkdm(webSearchBkdmValue);
		}	
		if(webSearchValue != null){
			model.setWebSearch(webSearchValue);
		}
		//table值
		List<ZxwtModel> zxwtList = zxwtService.getPagedListWeb(model);
		
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SecurityUtils.getSubject().isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getText(message.toString()));
		}
		vs.set("zxwtList", zxwtList);
		vs.set("kzdkList", kzdkList);
//		vs.set("searchkzdkList", searchkzdkList);
		vs.set("zxzxConfig", openStatus.get("config"));
		vs.set("openStatus", openStatus);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String updateDjl(){
		zxwtService.clickEventRecord(model);
		return DATA;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public String top(){
		ValueStack vs = getValueStack();
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SecurityUtils.getSubject().isAuthenticated());
		
		Map<String, String> config = (Map<String, String>) openStatus.get("config");
		int CSSZ_RMZXJSFS_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXJSFS_DM));
		int CSSZ_RMZXJSSJD_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM)==null ? "0" : config.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM));
		int CSSZ_RMZXXSTS_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXXSTS_DM));
		
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getText(message.toString()));
		}
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		List<ZxwtModel> zxwtList = null;
		//table值
		zxwtList = zxwtService.getTopWeb(webSearchBkdmValue, CSSZ_RMZXJSFS_DM, CSSZ_RMZXJSSJD_DM, CSSZ_RMZXXSTS_DM);
		
		vs.set("zxwtList", zxwtList);
		vs.set("kzdkList", kzdkList);
		vs.set("zxzxConfig", openStatus.get("config"));
		vs.set("openStatus", openStatus);
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String faq(){
		ValueStack vs = getValueStack();
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		//table值
		CjwtModel queryModel = new CjwtModel();
		queryModel.setQueryModel(model.getQueryModel());
		List<CjwtModel> pagedListWeb = cjwtService.getPagedListWeb(queryModel);
		
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SecurityUtils.getSubject().isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getText(message.toString()));
		}
		vs.set("cjwtList", pagedListWeb);
		vs.set("kzdkList", kzdkList);
		vs.set("zxzxConfig", openStatus.get("config"));
		vs.set("openStatus", openStatus);
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String search(){
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String newTopic(){
		ValueStack vs = getValueStack();
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		vs.set("kzdkList", kzdkList);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String newTopicSubmit(){
		User user = getUser();
		ValueStack vs = getValueStack();
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		vs.set("kzdkList", kzdkList);
		boolean canInsert = Boolean.TRUE;
		Map<String, Object> openStatus = csszService.isOpen(SecurityUtils.getSubject().isAuthenticated());
		boolean isopen = (Boolean) openStatus.get("isOpen");
		if(!isopen){
			addActionError(getText("ZXZX_I006"));
			canInsert = Boolean.FALSE;
		}else{
			String zxr = null;
			if(user!=null && "student".equalsIgnoreCase(user.getYhlx())){
				//addActionError(getText("ZXZX_I007"));
				//canInsert = Boolean.FALSE;
				zxr = user.getYhm();
			}
			//start
			if(null == model.getBkdm() || model.getBkdm().trim().equals("")){
				addActionError(getText("ZXZX_I008"));
				canInsert = Boolean.FALSE;
			}
			if(null == model.getkzdt() || model.getkzdt().trim().equals("")){
				addActionError(getText("ZXZX_I009"));
				canInsert = Boolean.FALSE;
			}
			if(null == model.getZxnr() || model.getZxnr().trim().equals("")){
				addActionError(getText("ZXZX_I010"));
				canInsert = Boolean.FALSE;
			}
			
			if(canInsert){
				//验证码验证
				String sessionYzm = (String) getSession().getAttribute("KAPTCHA_SESSION_KEY");
				if(!(sessionYzm!=null && sessionYzm.equalsIgnoreCase(getYzm()))){
					addActionError(getText("I00001"));
				}else{
					ZxwtModel inertModel = new ZxwtModel();
					inertModel.setZxid(UniqID.getInstance().getUniqIDHash());
					inertModel.setBkdm(model.getBkdm());
					inertModel.setkzdt(model.getkzdt());
					inertModel.setZxnr(model.getZxnr());
					inertModel.setZxsj(DateUtils.format(new Date()));
					inertModel.setSfgk("1");
					inertModel.setZxr(zxr);
					inertModel.setDjl(0);
					boolean insert;
					try {
						insert = zxwtService.insert(inertModel);
						if(insert){
							addActionMessage(getText("ZXZX_I011"));
						}else{
							addActionError(getText("ZXZX_I012"));
						}
					} catch (Exception e) {
						addActionError(getText("ZXZX_I013"));
						logException(e);
					}
				}
			}
			//end
		}
		return "newTopic";
	}
	
	/**
	 * 
	 * @return
	 */
	public String myTopic(){
		User user = getUser();
		ValueStack vs = getValueStack();
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		
		//table值
		List<ZxwtModel> zxwtList = null;
		if(user!=null && user.getYhm() != null){
			model.setZxr(user.getYhm());
			zxwtList = zxwtService.getPagedListMytopicWeb(model);
		}
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SecurityUtils.getSubject().isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getText(message.toString()));
		}
		vs.set("zxwtList", zxwtList);
		vs.set("kzdkList", kzdkList);
		vs.set("zxzxConfig", openStatus.get("config"));
		vs.set("openStatus", openStatus);
		return SUCCESS;
	}

	/**
	 * 删除用户咨询
	 * @return
	 */
	public String deleteMyTopic(){
		User user = getUser();
		String zxid = model.getZxid();
		List<String> deletData = null;
		String message = "";
		if(zxid != null && user!=null){
			deletData = new ArrayList<String>();
			deletData.add(zxid);
			boolean batchDeleteWeb = zxwtService.batchDeleteWeb(deletData,user.getYhm());
			message = getText(batchDeleteWeb?"I99005":"I99006");
		}else{
			message = getText("I99006");
		}
		getValueStack().set(DATA,message);
		return DATA;
	}
	
	@Override
	public ZxwtModel getModel() {
		return model;
	}

	public String getYzm() {
		return yzm;
	}

	public void setYzm(String yzm) {
		this.yzm = yzm;
	}

	public IZxwtService getZxwtService() {
		return zxwtService;
	}

	public void setZxwtService(IZxwtService zxwtService) {
		this.zxwtService = zxwtService;
	}

	public IkzdkService getkzdkService() {
		return kzdkService;
	}

	public void setkzdkService(IkzdkService kzdkService) {
		this.kzdkService = kzdkService;
	}

	public void setModel(ZxwtModel model) {
		this.model = model;
	}
	
	public String getWebSearchBkdmValue() {
		return webSearchBkdmValue;
	}

	public void setWebSearchBkdmValue(String webSearchBkdmValue) {
		this.webSearchBkdmValue = webSearchBkdmValue;
	}

	public String getWebSearchValue() {
		return webSearchValue;
	}

	public void setWebSearchValue(String webSearchValue) {
		this.webSearchValue = webSearchValue;
	}

	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}

	public ICjwtService getCjwtService() {
		return cjwtService;
	}

	public void setCjwtService(ICjwtService cjwtService) {
		this.cjwtService = cjwtService;
	}
	
}
