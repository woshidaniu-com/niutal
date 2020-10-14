/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.shiro.SubjectUtils;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.dao.entities.CjwtModel;
import com.woshidaniu.zxzx.dao.entities.kzdkModel;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICjwtService;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;
import com.woshidaniu.zxzx.service.svcinterface.IkzdkService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * @类名 ZxzzWebController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月25日 下午7:39:10
 * @功能描述 
 * 
 */
@Controller("zxzxWebAction")
@RequestMapping("/zxzx/web")
public class ZxzzWebController extends BaseController{
	
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
	@RequestMapping("/index.zf")
	public String index(ZxwtModel model, HttpServletRequest request){
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		
		//查询约束
		if(model.getWebSearchBkdmValue() != null && (!"".equals(model.getWebSearchBkdmValue().trim()))){
			model.setBkdm(model.getWebSearchBkdmValue());
		}
		if(model.getWebSearchValue() != null){
			model.setWebSearch(model.getWebSearchValue());
		}
		//table值
		List<ZxwtModel> zxwtList = zxwtService.getPagedListWeb(model);
		
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getMessage(message.toString()));
		}
		request.setAttribute("zxwtList", zxwtList);
		request.setAttribute("kzdkList", kzdkList);
		request.setAttribute("zxzxConfig", openStatus.get("config"));
		request.setAttribute("openStatus", openStatus);
		request.setAttribute("model", model);
		request.setAttribute("webSearchBkdmValue", model.getWebSearchBkdmValue());
		request.setAttribute("webSearchValue", model.getWebSearchValue());
		return "/zxzx/web_v_2/index";
	}
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateDjl.zf")
	public Object updateDjl(ZxwtModel model){
		zxwtService.clickEventRecord(model);
		return MessageKey.DO_SUCCESS.getJson();
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/top.zf")
	public String top(ZxwtModel model, HttpServletRequest request){
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		
		Map<String, String> config = (Map<String, String>) openStatus.get("config");
		int CSSZ_RMZXJSFS_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXJSFS_DM));
		int CSSZ_RMZXJSSJD_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM)==null ? "0" : config.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM));
		int CSSZ_RMZXXSTS_DM = Integer.parseInt(config.get(ZxzxConstant.CSSZ_RMZXXSTS_DM));
		
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getMessage(message.toString()));
		}
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		List<ZxwtModel> zxwtList = null;
		//table值
		zxwtList = zxwtService.getTopWeb(model.getWebSearchBkdmValue(), CSSZ_RMZXJSFS_DM, CSSZ_RMZXJSSJD_DM, CSSZ_RMZXXSTS_DM);
		
		request.setAttribute("zxwtList", zxwtList);
		request.setAttribute("kzdkList", kzdkList);
		request.setAttribute("zxzxConfig", openStatus.get("config"));
		request.setAttribute("openStatus", openStatus);
		request.setAttribute("model", model);
		return "/zxzx/web_v_2/top";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/faq.zf")
	public String faq(ZxwtModel model, HttpServletRequest request){
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		//table值
		CjwtModel queryModel = new CjwtModel();
		queryModel.setQueryModel(model.getQueryModel());
		List<CjwtModel> pagedListWeb = cjwtService.getPagedListWeb(queryModel);
		
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getMessage(message.toString()));
		}
		request.setAttribute("cjwtList", pagedListWeb);
		request.setAttribute("kzdkList", kzdkList);
		request.setAttribute("zxzxConfig", openStatus.get("config"));
		request.setAttribute("openStatus", openStatus);
		request.setAttribute("model", model);
		return "/zxzx/web_v_2/faq";
	}
	
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/search.zf")
	public String search(){
		return "/zxzx/web_v_2/search";
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/newTopic.zf")
	public String newTopic(HttpServletRequest request){
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		request.setAttribute("kzdkList", kzdkList);
		return "/zxzx/web_v_2/newTopic";
	}
	
	/**
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/newTopicSubmit.zf")
	public Object newTopicSubmit(ZxwtModel model, HttpServletRequest request){
		Object message = null;
		User user = getUser();
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		request.setAttribute("kzdkList", kzdkList);
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		boolean isopen = (Boolean) openStatus.get("isOpen");
		Map<String, String> config = (Map<String, String>) openStatus.get("config");
		if(!isopen){
			message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I006");
			return message;
		}else{
			if("1".equals(config.get("CSSZ_ZXMS_DM")) && user == null){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I015");
				return message;
			}
			String zxr = (user == null ? null : user.getYhm());
			if(user!=null){
				zxr = user.getYhm();
			}
			
			if("1".equals(config.get("CSSZ_ZXMS_DM")) && (!"student".equalsIgnoreCase(user.getYhlx()))){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I007");
				return message;
			}
			
			//start
			if(null == model.getBkdm() || model.getBkdm().trim().equals("")){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I008");
				return message;
			}
			if(null == model.getkzdt() || model.getkzdt().trim().equals("")){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I009");
				return message;
			}
			if(null == model.getZxnr() || model.getZxnr().trim().equals("")){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I010");
				return message;
			}
			//验证码验证
			String requestYzm = request.getParameter("yzm");
			String sessionYzm = (String) SubjectUtils.getSession().getAttribute("KAPTCHA_SESSION_KEY");
			if(!(sessionYzm!=null && sessionYzm.equalsIgnoreCase(requestYzm))){
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "I00001");
				return message;
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
						message = getJSONMessage(MESSAGE_STATUS_SUCCESS, "ZXZX_I011");
					}else{
						message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I012");
					}
				} catch (Exception e) {
					message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I013");
					logException(e);
				}
				return message;
			}
			//end
		}
		
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/myTopic.zf")
	public String myTopic(ZxwtModel model, HttpServletRequest request){
		User user = getUser();
		//热门版块
		List<kzdkModel> kzdkList = kzdkService.getModelListWeb(null);
		
		//table值
		List<ZxwtModel> zxwtList = null;
		if(user!=null && user.getYhm() != null){
			model.setZxr(user.getYhm());
			zxwtList = zxwtService.getPagedListMytopicWeb(model);
		}
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		Object message = openStatus.get("message");
		if(message != null){
			openStatus.put("messageValue", getMessage(message.toString()));
		}
		request.setAttribute("zxwtList", zxwtList);
		request.setAttribute("kzdkList", kzdkList);
		request.setAttribute("zxzxConfig", openStatus.get("config"));
		request.setAttribute("openStatus", openStatus);
		request.setAttribute("model", model);
		return "/zxzx/web_v_2/myTopic";
	}

	/**
	 * 删除用户咨询
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteMyTopic.zf")
	public Object deleteMyTopic(ZxwtModel model, HttpServletRequest request){
		User user = getUser();
		String zxid = model.getZxid();
		List<String> deletData = null;
		Object message;
		if(zxid != null && user!=null){
			deletData = new ArrayList<String>();
			deletData.add(zxid);
			boolean batchDeleteWeb = zxwtService.batchDeleteWeb(deletData,user.getYhm());
			message = (batchDeleteWeb?MessageKey.DELETE_SUCCESS:MessageKey.DELETE_FAIL).getJson();
		}else{
			message = MessageKey.PARAMATER_ERROR.getJson();
		}
		return message;
	}
	
	@RequestMapping("/contactUs.zf")
	public String contactUs(HttpServletRequest request){
		//设置信息
		Map<String, Object> openStatus = csszService.isOpen(SubjectUtils.isAuthenticated());
		request.setAttribute("zxzxConfig", openStatus.get("config"));
		return "/zxzx/web_v_2/contactUs";
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
