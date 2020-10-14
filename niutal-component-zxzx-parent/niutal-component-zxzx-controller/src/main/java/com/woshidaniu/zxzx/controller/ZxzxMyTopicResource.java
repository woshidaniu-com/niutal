/**
 * 
 */
package com.woshidaniu.zxzx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.UniqID;
import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.query.QueryModel;
import com.woshidaniu.zxzx.auth.ZxzxAuthentication;
import com.woshidaniu.zxzx.dao.entities.ZxwtModel;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;
import com.woshidaniu.zxzx.service.svcinterface.IZxwtService;

/**
 * <p>
 * <h3>niutal框架
 * <h3>说明：TODO
 * <p>
 * 
 * @className:com.woshidaniu.zxzx.controller.ZxzxMyTopicResource.java
 * @author <a href="mailto:337836629@qq.com">zhidong.kang[1036]<a>
 * @version 2017年6月21日下午12:44:48
 */
@Controller
@RequestMapping("/zxzx/web/topic/auth")
public class ZxzxMyTopicResource extends ZxzxTopicResource {
	
	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}

	public ZxzxAuthentication getZxzxAuthentication() {
		return zxzxAuthentication;
	}

	public void setZxzxAuthentication(ZxzxAuthentication zxzxAuthentication) {
		this.zxzxAuthentication = zxzxAuthentication;
	}

	public IZxwtService getZxwtService() {
		return zxwtService;
	}

	public void setZxwtService(IZxwtService zxwtService) {
		this.zxwtService = zxwtService;
	}

	@Autowired
	@Qualifier("zxzxZxwtService")
	private IZxwtService zxwtService;

	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;

	// 用于校验用户登录
	@Autowired
	protected ZxzxAuthentication zxzxAuthentication;

	@ResponseBody
	@RequestMapping(value = "/my-topic/list/json", method = RequestMethod.GET)
	public Object getMyTopicsJson(ZxwtModel model) {
		if (getZxzxAuthentication().isAuthenticated()) {
			QueryModel queryModel = model.getQueryModel();
			model.setZxr(getZxzxAuthentication().getPrinciple());
			List<ZxwtModel> myTopics = getZxwtService().getPagedListMytopicWeb(model);
			queryModel.setItems(myTopics);
			return queryModel;
		} else {
			return getUnAuthJSON();
		}
	}

	@ResponseBody
	@RequestMapping(value = "/my-topic/new", method = RequestMethod.POST)
	public Object newTopic(ZxwtModel model) {
		
		Object message = null;
		Map<String, Object> openStatus = csszService.isOpen(zxzxAuthentication.isAuthenticated());
		boolean isopen = (Boolean) openStatus.get("isOpen");
		String zxms = (String) openStatus.get("zxms");
		if (!isopen) {
			message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I006");
			return message;
		} else {
			// start
			if (null == model.getBkdm() || model.getBkdm().trim().equals("")) {
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I008");
				return message;
			}
			if (null == model.getZxnr() || model.getZxnr().trim().equals("")) {
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I010");
				return message;
			}

			ZxwtModel inertModel = new ZxwtModel();
			inertModel.setZxid(UniqID.getInstance().getUniqIDHash());
			inertModel.setBkdm(model.getBkdm());
			inertModel.setkzdt(model.getkzdt());
			
			String zxnr = model.getZxnr();
			inertModel.setZxnr(zxnr);
			
			inertModel.setZxsj(DateUtils.format(new Date()));
			inertModel.setSfgk("1");
			inertModel.setSfnm(model.getSfnm());
			//如果是免登陆咨询并且用户没登陆，就设置为匿名
			if("0".equals(zxms) && (!zxzxAuthentication.isAuthenticated())){
				inertModel.setSfnm("1");
			}
			inertModel.setZxr(zxzxAuthentication.isAuthenticated()?zxzxAuthentication.getPrinciple():null);
			inertModel.setDjl(0);
			boolean insert;
			try {
				insert = zxwtService.insert(inertModel);
				if (insert) {
					message = getJSONMessage(MESSAGE_STATUS_SUCCESS, "ZXZX_I011");
				} else {
					message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I012");
				}
			} catch (Exception e) {
				message = getJSONMessage(MESSAGE_STATUS_FAIL, "ZXZX_I013");
				logException(e);
			}
			return message;

			// end
		}

	}

	@ResponseBody
	@RequestMapping(value = "/my-topic/{zxid}/del", method = RequestMethod.POST)
	public Object delTopic(@PathVariable String zxid) {
		List<String> deletData = null;
		Object message;
		try {
			if (zxid != null && zxzxAuthentication.isAuthenticated()) {
				deletData = new ArrayList<String>();
				deletData.add(zxid);
				boolean batchDeleteWeb = zxwtService.batchDeleteWeb(deletData, zxzxAuthentication.getPrinciple());
				message = (batchDeleteWeb ? MessageKey.DELETE_SUCCESS : MessageKey.DELETE_FAIL).getJson();
			} else {
				message = MessageKey.PARAMATER_ERROR.getJson();
			}
		}catch (Exception e) {
			message = MessageKey.DELETE_FAIL.getJson();
		}
		
		return message;
	}

	@ResponseBody
	@RequestMapping(value = "/my-topic/{zxid}/get", method = RequestMethod.GET)
	public Object getTopic(@PathVariable String zxid) {
		ZxwtModel model = null;
		if (zxid != null && zxzxAuthentication.isAuthenticated()) {
			model = zxwtService.getModel(zxid);
		}
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/my-topic/edit", method = RequestMethod.POST)
	public Object editTopic(ZxwtModel model) {
		Object message;
		try {
			if (model.getZxid() != null && zxzxAuthentication.isAuthenticated()) {
				String zxnr = model.getZxnr();
				model.setZxnr(zxnr);
				zxwtService.updateWeb(model);
			}
			message = MessageKey.SAVE_SUCCESS.getJson();
		} catch (Exception e) {
			message = MessageKey.SAVE_FAIL.getJson();
			logException(e);
		}
		return message;
	}
}
