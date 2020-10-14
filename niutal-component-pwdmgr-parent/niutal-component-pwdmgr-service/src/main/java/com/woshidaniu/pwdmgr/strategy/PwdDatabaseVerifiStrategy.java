package com.woshidaniu.pwdmgr.strategy;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.pwdmgr.api.PwdMgrKey;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.ResultData;
import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
import com.woshidaniu.pwdmgr.api.strategy.PwdVerifiStrategy;
import com.woshidaniu.pwdmgr.dao.entities.VerifiModel;
import com.woshidaniu.pwdmgr.service.svcinterface.UserAccountService;
import com.woshidaniu.pwdmgr.service.svcinterface.VerifiService;

/**
 * 
 *@类名称		： PwdDatabaseVerifiStrategy.java
 *@类描述		：基于Oracle数据库存储的账号核实字段验证策略实现
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月12日 下午3:34:57
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public class PwdDatabaseVerifiStrategy implements PwdVerifiStrategy {

	protected Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	@Resource
	protected UserAccountService accountService;
	@Resource
	protected VerifiService verifiService;
	
	@Override
	public String name() {
		return PwdStrategy.DEFAULT_STRATEGY;
	}

	@Override
	public ResultData verifi(BindData data) {
		
		BaseMap rtMap = null; 
		String verfiType = data.getVerifiType();
		
		if(PwdStrategy.PWD_RETAKE_BY_EMAIL.equals(verfiType)) {//邮箱
			rtMap = this.accountService.getUserAccountByEmail(data);
			
		}else if(PwdStrategy.PWD_RETAKE_BY_PHONE.equals(verfiType)) {//手机
			rtMap = this.accountService.getUserAccountByPhone(data);
			
		}else if(PwdStrategy.DEFAULT_STRATEGY.equals(verfiType)){//默认
			//使用用户名查询用户账号的应该放弃,防止被恶意暴力枚举攻击
			//rtMap = this.accountService.getUserAccount(data);
		}else {
			//不支持的验证类型，直接返回错误
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_INPUT_NULL, Collections.EMPTY_MAP);
		}
		//第一步：验证用户是否存在
		if(null == rtMap){
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_INPUT_NULL, rtMap);
		}
		//第二步：验证附加信息
		try {
			
			//查询启用的身份校验字段
			VerifiModel verifiModel = new VerifiModel();
			verifiModel.setStatus("1");
			List<VerifiModel> verifiList = this.verifiService.getModelList(verifiModel);
			//第二步-1：通过数据库存储的字段来审查页面提交的字段防止伪造异常请求
			for (VerifiModel verifi : verifiList) {
				//需要提供的验证信息没有提供
				if(!data.getMap().containsKey(verifi.getName())){
					return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_VERIFI_REQUIRED , rtMap);
				}
			}
			//第二步-2：对提交的信息进行对比
			for (String key : data.getMap().keySet()) {
				//获取相应字段原值（有可能未定义）
				String origin = (String) rtMap.get(key);
				//获取对应字段参数值
				String from = data.getMap().get(key);
				//对比字段可能为空的情况处理
				if(StringUtils.isEmpty(from) || StringUtils.isEmpty(origin)){
					return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_VERIFI_REQUIRED, rtMap);
				}
				if( !from.equals(origin)) {
					return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_VERIFI_UNPASS , rtMap);
				}
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_VERIFI_ERROR , new Object[] { e.getMessage() }, rtMap);
		}
		return ResultData.to(PwdMgrKey.SUCCESS, PwdMgrKey.PWD_RETAKE_VERIFI_PASS , rtMap);
	}
}
