package com.woshidaniu.wjdc.action;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.util.encrypt.DBEncrypt;

@Controller
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class OutAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private BaseLog baseLog = LogEngineImpl.getInstance();

	private String param;


	/**
	 * 方法名: login 方法描述: 登录 修改时间：2011-12-20 上午10:49:38 参数 @return 返回类型 String
	 * 
	 * @throws
	 */
	public String login() {
		DBEncrypt dbEncrypt = new DBEncrypt();
		String yhm2 = null;
		String gnbh = null;
		try {
			String param2 = dbEncrypt.dCode(param.getBytes());
			String[] params = param2.split("&");
			HashMap<String,String> paramMap = new HashMap<String, String>();
			for (int i = 0; i < params.length; i++) {
				if(params != null){
					String[] paramsArr = params[i].split("=");
					if(paramsArr != null && paramsArr.length > 1){
						paramMap.put(paramsArr[0], paramsArr[1]);
					}
				}
			}
			yhm2 = paramMap.get("yhm");
			gnbh = paramMap.get("gnbh");
			/**
			 * 问卷答卷的话需要设置wjid参数
			 */
			if(StringUtils.equals("yhdj", gnbh)){
				getValueStack().set("wjid", paramMap.get("wjid"));
			}
			
			User user = getUser();
			baseLog.login(
					user,
					getText("log.message.ywmc", new String[] { "登陆系统",
							"xg_xtgl_yhb" }),
					"系统管理",
					getText("log.message.czms", new String[] { "登陆系统", "用户名",
							user.getYhm() }));
			return gnbh;
		} catch (Exception e) {
			logException(e);
			this.getValueStack().set(DATA, "用户参数：[" + yhm2 + "] 非法：无法获取用户信息！");
			return DATA;
		}

	}

	public BaseLog getBaseLog() {
		return baseLog;
	}

	public void setBaseLog(BaseLog baseLog) {
		this.baseLog = baseLog;
	}

	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

}
