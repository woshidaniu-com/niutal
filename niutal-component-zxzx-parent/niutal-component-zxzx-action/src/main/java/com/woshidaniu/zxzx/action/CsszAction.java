/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.zxzx.ZxzxConstant;
import com.woshidaniu.zxzx.service.svcinterface.ICsszService;

/**
 * @类名 CsszAction.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月19日 上午8:48:19
 * @功能描述 在线咨询-参数设置
 * 
 */

@Controller("zxzxCsszAction")
@Scope("prototype")
public class CsszAction extends BaseAction{

	private static final long serialVersionUID = 3910002383688133301L;
	
	private String kg;
	
	private String kssj;
	
	private String jssj;
	
	private String zxms;
	
	private String rmzxjsfs;
	
	private String rmzxjssjd;
	
	private String rmzxxsts;
	
	private String lxr;
	
	private String dh;
	
	private String dzyx;
	
	private String dz;
	
	private Map<String,String> configMap;

	private BaseLog baseLog = LogEngineImpl.getInstance();
	
	@Autowired
	@Qualifier("zxzxCsszService")
	private ICsszService csszService;
	
	/**
	 * 参数设置
	 * @return
	 */
	public String cssz(){
		configMap = csszService.getConfig();
		if(configMap != null){
			kg = configMap.get(ZxzxConstant.CSSZ_KG_DM);
			kssj = configMap.get(ZxzxConstant.CSSZ_KSSJ_DM);
			jssj = configMap.get(ZxzxConstant.CSSZ_JSSJ_DM);
			zxms = configMap.get(ZxzxConstant.CSSZ_ZXMS_DM);
			rmzxjsfs = configMap.get(ZxzxConstant.CSSZ_RMZXJSFS_DM);
			rmzxjssjd = configMap.get(ZxzxConstant.CSSZ_RMZXJSSJD_DM);
			rmzxxsts = configMap.get(ZxzxConstant.CSSZ_RMZXXSTS_DM);
			lxr = configMap.get(ZxzxConstant.CSSZ_LXR_DM);
			dh = configMap.get(ZxzxConstant.CSSZ_DH_DM);
			dzyx = configMap.get(ZxzxConstant.CSSZ_DZYX_DM);
			dz = configMap.get(ZxzxConstant.CSSZ_DZ_DM);
		}
		return "cssz";
	}
	
	/**
	 * 参数设置-保存
	 * @return
	 */
	public String csszBc(){
		User sessionUser = getUser();
		String message = "";
		try{
			configMap = new HashMap<String, String>();
			configMap.put(ZxzxConstant.CSSZ_KG_DM, kg);
			configMap.put(ZxzxConstant.CSSZ_KSSJ_DM, kssj);
			configMap.put(ZxzxConstant.CSSZ_JSSJ_DM, jssj);
			configMap.put(ZxzxConstant.CSSZ_ZXMS_DM, zxms);
			configMap.put(ZxzxConstant.CSSZ_RMZXJSFS_DM, rmzxjsfs);
			if("2".equals(rmzxjsfs)){
				configMap.put(ZxzxConstant.CSSZ_RMZXJSSJD_DM, rmzxjssjd);
			}
			configMap.put(ZxzxConstant.CSSZ_RMZXXSTS_DM, rmzxxsts);
			configMap.put(ZxzxConstant.CSSZ_LXR_DM, lxr);
			configMap.put(ZxzxConstant.CSSZ_DH_DM, dh);
			configMap.put(ZxzxConstant.CSSZ_DZYX_DM, dzyx);
			configMap.put(ZxzxConstant.CSSZ_DZ_DM, dz);
			boolean updateResult = csszService.updateConfig(configMap);
			message = getText(updateResult ? "I99001" : "I99002");
			if (updateResult) {
				baseLog.insert(sessionUser, getText("log.message.ywmc",new String[] { "在线咨询-基础设置", "niutal_ZXZX_CSSZB" }),"在线咨询", "参数设置维护");
			}
		} catch (Exception e) {
			logException(e);
			message = getText("S00001");
		}
		getValueStack().set(DATA, message);
		return DATA;
	}
	
	public String getKg() {
		return kg;
	}

	public void setKg(String kg) {
		this.kg = kg;
	}

	public String getKssj() {
		return kssj;
	}

	public void setKssj(String kssj) {
		this.kssj = kssj;
	}

	public String getJssj() {
		return jssj;
	}

	public void setJssj(String jssj) {
		this.jssj = jssj;
	}

	public String getZxms() {
		return zxms;
	}

	public void setZxms(String zxms) {
		this.zxms = zxms;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public ICsszService getCsszService() {
		return csszService;
	}

	public void setCsszService(ICsszService csszService) {
		this.csszService = csszService;
	}

	public String getRmzxjsfs() {
		return rmzxjsfs;
	}

	public void setRmzxjsfs(String rmzxjsfs) {
		this.rmzxjsfs = rmzxjsfs;
	}


	public String getRmzxjssjd() {
		return rmzxjssjd;
	}

	public void setRmzxjssjd(String rmzxjssjd) {
		this.rmzxjssjd = rmzxjssjd;
	}

	public String getRmzxxsts() {
		return rmzxxsts;
	}

	public void setRmzxxsts(String rmzxxsts) {
		this.rmzxxsts = rmzxxsts;
	}

	public String getLxr() {
		return lxr;
	}

	public void setLxr(String lxr) {
		this.lxr = lxr;
	}

	public String getDh() {
		return dh;
	}

	public void setDh(String dh) {
		this.dh = dh;
	}

	public String getDzyx() {
		return dzyx;
	}

	public void setDzyx(String dzyx) {
		this.dzyx = dzyx;
	}

	public String getDz() {
		return dz;
	}

	public void setDz(String dz) {
		this.dz = dz;
	}

}
