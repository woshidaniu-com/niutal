/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.woshidaniu.common.MessageKey;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.dao.entities.CsszModel;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;

/**
 * @类名 CsszController.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 上午8:48:19
 * @功能描述 在线咨询-参数设置
 * 
 */

@Controller("zxzxCsszAction")
@RequestMapping("/zxzx/jcsz")
public class CsszController extends BaseController{
	
	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;
	
	/**
	 * 参数设置
	 * @return
	 */
	@RequestMapping("/cssz.zf")
	public String cssz(CsszModel model, HttpServletRequest request){
		Map<String,String> configMap = csszService.getConfig();
		if(configMap != null){
			String kg = configMap.get(ZxzxConstant.CSSZ_KG_DM);
			String kssj = configMap.get(ZxzxConstant.CSSZ_KSSJ_DM);
			String jssj = configMap.get(ZxzxConstant.CSSZ_JSSJ_DM);
			String zxms = configMap.get(ZxzxConstant.CSSZ_ZXMS_DM);
			String dlms = configMap.get(ZxzxConstant.CSSZ_DLMS_DM);
			String loginUrl = configMap.get(ZxzxConstant.CSSZ_LOGIN_URL_DM);
			String authUrl = configMap.get(ZxzxConstant.CSSZ_AUTH_URL_DM);
			String rmzxjsfs = configMap.get(ZxzxConstant.CSSZ_RMZXJSFS_DM);
			String rmzxjssjd = configMap.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM);
			String rmzxxsts = configMap.get(ZxzxConstant.CSSZ_RMZXXSTS_DM);
			String lxr = configMap.get(ZxzxConstant.CSSZ_LXR_DM);
			String dh = configMap.get(ZxzxConstant.CSSZ_DH_DM);
			String dzyx = configMap.get(ZxzxConstant.CSSZ_DZYX_DM);
			String dz = configMap.get(ZxzxConstant.CSSZ_DZ_DM);
			
			model.setKg(kg);
			model.setKssj(kssj);
			model.setJssj(jssj);
			model.setZxms(zxms);
			model.setDlms(dlms);
			model.setLoginUrl(loginUrl);
			model.setAuthUrl(authUrl);
			model.setRmzxjsfs(rmzxjsfs);
			model.setRmzxjssjd(rmzxjssjd);
			model.setRmzxxsts(rmzxxsts);
			model.setLxr(lxr);
			model.setDh(dh);
			model.setDzyx(dzyx);
			model.setDz(dz);
			
			String sfxswdwt = configMap.get(ZxzxConstant.CSSZ_SFXSWDWT);
			String sfxswytw = configMap.get(ZxzxConstant.CSSZ_SFXSWYTW);
			String sfyxwdlcxwt = configMap.get(ZxzxConstant.CSSZ_SFYXWDLCXWT);
			
			model.setSfxswdwt(sfxswdwt);
			model.setSfxswytw(sfxswytw);
			model.setSfyxwdlcxwt(sfyxwdlcxwt);
			
			request.setAttribute("model", model);
		}
		
		return "/zxzx/jcsz/cssz";
	}
	
	/**
	 * 参数设置-保存
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/csszBc.zf")
	public Object csszBc(CsszModel model, HttpServletRequest request){
		User sessionUser = getUser();
		Object message;
		try{
			Map<String,String> configMap = new HashMap<String, String>();
			configMap.put(ZxzxConstant.CSSZ_KG_DM, model.getKg());
			configMap.put(ZxzxConstant.CSSZ_KSSJ_DM, model.getKssj());
			configMap.put(ZxzxConstant.CSSZ_JSSJ_DM, model.getJssj());
			configMap.put(ZxzxConstant.CSSZ_ZXMS_DM, model.getZxms());
			configMap.put(ZxzxConstant.CSSZ_DLMS_DM, model.getDlms());
			configMap.put(ZxzxConstant.CSSZ_LOGIN_URL_DM, model.getLoginUrl());
			configMap.put(ZxzxConstant.CSSZ_AUTH_URL_DM, model.getAuthUrl());
			configMap.put(ZxzxConstant.CSSZ_RMZXJSFS_DM, model.getRmzxjsfs());
			if("2".equals(model.getRmzxjsfs())){
				configMap.put(ZxzxConstant.CSSZ_RMZXJSSJD_DM, model.getRmzxjssjd());
			}
			configMap.put(ZxzxConstant.CSSZ_RMZXXSTS_DM, model.getRmzxxsts());
			configMap.put(ZxzxConstant.CSSZ_LXR_DM, model.getLxr());
			configMap.put(ZxzxConstant.CSSZ_DH_DM, model.getDh());
			configMap.put(ZxzxConstant.CSSZ_DZYX_DM, model.getDzyx());
			configMap.put(ZxzxConstant.CSSZ_DZ_DM, model.getDz());
			
			configMap.put(ZxzxConstant.CSSZ_SFXSWDWT, model.getSfxswdwt());
			configMap.put(ZxzxConstant.CSSZ_SFXSWYTW, model.getSfxswytw());
			configMap.put(ZxzxConstant.CSSZ_SFYXWDLCXWT, model.getSfyxwdlcxwt());
			
			boolean updateResult = csszService.updateConfig(configMap);
			message = (updateResult ? MessageKey.SAVE_SUCCESS : MessageKey.SAVE_FAIL).getJson();
			if (updateResult) {
				baseLog.insert(sessionUser, getMessage("log.message.ywmc",new String[] { "在线咨询-基础设置", "niutal_ZXZX_CSSZB" }),"在线咨询", "参数设置维护");
			}
			return message;
		} catch (Exception e) {
			logException(e);
			return MessageKey.SYSTEM_ERROR.getJson();
		}
	}

	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}

}
