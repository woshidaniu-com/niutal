package com.woshidaniu.pwdmgr.api.strategy.def;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.pwdmgr.api.PwdMgrKey;
import com.woshidaniu.pwdmgr.api.model.BindCaptcha;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.api.model.BindField;
import com.woshidaniu.pwdmgr.api.model.ResultData;
import com.woshidaniu.pwdmgr.api.provider.CaptchaProvider;
import com.woshidaniu.pwdmgr.api.provider.DataOutputProvider;
import com.woshidaniu.pwdmgr.api.strategy.PwdRetakeStrategyAdapter;
/**
 * 
 *@类名称		： PwdCaptchaRetakeStrategyAdapter.java
 *@类描述		：验证码方式找回密码策略Adapter
 *@创建人		：kangzhidong
 *@创建时间	：2017年4月7日 下午2:33:02
 *@修改人		：
 *@修改时间	：
 *@版本号	:v1.0
 */
public abstract class PwdCaptchaRetakeStrategyAdapter extends PwdRetakeStrategyAdapter {

	protected Logger LOG = LoggerFactory.getLogger(PwdCaptchaRetakeStrategyAdapter.class);
	protected static ConcurrentMap<String, Pattern> COMPLIED_PATTERN = new ConcurrentHashMap<String, Pattern>();
	
	/**
	 * 验证码生成服务提供者
	 */
	protected CaptchaProvider captchaProvider;
	/**
	 * 验证码输出对象提供者
	 */
	protected DataOutputProvider captchaOutput;
	
	protected Pattern getPattern(String regexp) {
		if (StringUtils.isNotEmpty(regexp)) {
			Pattern ret = COMPLIED_PATTERN.get(regexp);
			if (ret != null) {
				return ret;
			}
			ret = Pattern.compile(regexp);
			Pattern existing = COMPLIED_PATTERN.putIfAbsent(regexp, ret);
			if (existing != null) {
				ret = existing;
			}
			return ret;
		}
		return null;
	}
	
	@Override
	public ResultData advice(BindData data) {
		//第1步：从数据提供者获取账户相关信息
		BaseMap rtMap = getAccountProvider().input(data);
		if (null == rtMap){
			//如果没有根据参数获取到数据，则返回提醒信息
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_INPUT_NULL, rtMap);
		}
		
		//第2步：必要字段检查
		int index = 0;
		do {
			BindField field = bindFields[index];
			Object field_val = rtMap.get(field.getName());
			if(field.isRequired() && (!rtMap.containsKey(field.getName()) || StringUtils.isEmpty(field_val) )){
				return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_FILED_REQUIRED, new Object[]{ getAccountProvider().name() , field.getDesc() }, rtMap);
			}
			//扩展别名值，解决不同系统参数名称不同问题
			if(!StringUtils.isEmpty(field.getAlias())){
				rtMap.put(field.getAlias(), field_val);
			}
			index ++;
		} while ( index < bindFields.length);
		
		//第3步：提取、验证（ 不通过则创建）、存储验证码
		BindCaptcha captcha = getCaptchaProvider().input(data);
		//-1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
		if(! "1".equals(getCaptchaProvider().valid(data, captcha))){
			//验证码失效,从新生成、存储
			captcha = getCaptchaProvider().gen(data);
			try {
				if(!getCaptchaProvider().store(data,captcha)){
					//验证码没有存储
					return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_CAPTCHA_STORE_FAIL, new Object[]{ getCaptchaProvider().name() , captcha.getCaptcha() }, rtMap);
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
				//验证码存储失败
				return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_CAPTCHA_STORE_ERROR, new Object[]{ getCaptchaProvider().name()}, rtMap);
			}
		}
		data.getMap().put(PwdMgrKey.CAPTCHA, captcha.getCaptcha());
		
		//第4步：发送验证码
		for (BindField bindField : bindFields) {
			//原key、原值
			String key = bindField.getName();
			String value = String.valueOf(rtMap.get(key));
			//格式化的表达式
			String regex = bindField.getRegex();
			String replacement = bindField.getReplacement();
			
			//默认添加原值
			data.getMap().put(key, value);
			if(StringUtils.isNotEmpty(regex) && StringUtils.isNotEmpty(replacement)){
				data.getMap().put(key + "_format", value.replaceAll(regex, replacement));
				rtMap.put(key + "_format", value.replaceAll(regex, replacement));
			}
			
			//扩展别名值，解决不同系统参数名称不同问题
			if(!StringUtils.isEmpty(bindField.getAlias())){
				data.getMap().put(bindField.getAlias(), value );
				if(StringUtils.isNotEmpty(regex) && StringUtils.isNotEmpty(replacement)){
					//data.getMap().put(bindField.getAlias(), value.replaceAll(regex, replacement));
					data.getMap().put(bindField.getAlias() + "_format", value.replaceAll(regex, replacement));
					rtMap.put(bindField.getAlias() + "_format", value.replaceAll(regex, replacement));
				}
			}
		}
		
		//验证码的唯一ID;用于检测验证码的安全性
		rtMap.put("uuid", captcha.getUuid());
		
		//模拟成功逻辑
		//return ResultData.to(PwdMgrKey.SUCCESS, PwdMgrKey.PWD_RETAKE_CAPTCHA_OUTPUT_SUCCESS,  new Object[]{ getCaptchaOutput().name() }, rtMap);
		
		try {
			getCaptchaOutput().output(data);
			return ResultData.to(PwdMgrKey.SUCCESS, PwdMgrKey.PWD_RETAKE_CAPTCHA_OUTPUT_SUCCESS,  new Object[]{ getCaptchaOutput().name() }, rtMap);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
			return ResultData.to(PwdMgrKey.ERROR, PwdMgrKey.PWD_RETAKE_CAPTCHA_OUTPUT_ERROR,  new Object[]{ getCaptchaOutput().name() }, rtMap);
		}
	}
	

	@Override
	public ResultData retake(BindData data) {
		//第1步：提取、验证 验证码
		BindCaptcha captcha = getCaptchaProvider().input(data);
		Map<String, String> map = data.getMap();
		//-1 : 验证码对象为空；, 0 : 验证码过期, 1：验证码在有效期内
		if(! "1".equals(getCaptchaProvider().valid(data, captcha))){
			return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_CAPTCHA_EXPIRE, new Object[]{ getCaptchaProvider().name()}, map);
		}
		String uuid = data.getMap().get(PwdMgrKey.UUID);
		String captcha_code = data.getMap().get(PwdMgrKey.CAPTCHA);
		//对比提交的验证码和存储的验证码
		if(captcha.getCaptcha().equals(captcha_code) && captcha.getUuid().equals(uuid)){
			//验证码的唯一ID;用于检测验证码的安全性
			map.put("uuid", captcha.getUuid());
			return ResultData.to(PwdMgrKey.SUCCESS, PwdMgrKey.PWD_RETAKE_CAPTCHA_VERIFI_PASS,  new Object[]{ getCaptchaOutput().name() }, map);
		}
		return ResultData.to(PwdMgrKey.FAIL, PwdMgrKey.PWD_RETAKE_CAPTCHA_VERIFI_UNPASS,  new Object[]{ getCaptchaOutput().name() }, map);
	}

	public DataOutputProvider getCaptchaOutput() {
		return captchaOutput;
	}

	public void setCaptchaOutput(DataOutputProvider captchaOutput) {
		this.captchaOutput = captchaOutput;
	}

	public CaptchaProvider getCaptchaProvider() {
		return captchaProvider;
	}

	public void setCaptchaProvider(CaptchaProvider captchaProvider) {
		this.captchaProvider = captchaProvider;
	}
	
}