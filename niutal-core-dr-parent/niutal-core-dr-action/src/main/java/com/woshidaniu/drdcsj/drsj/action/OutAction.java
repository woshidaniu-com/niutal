package com.woshidaniu.drdcsj.drsj.action;

import java.util.HashMap;

import org.springframework.stereotype.Controller;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.security.algorithm.DesBase64Codec;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：TODO
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月3日下午5:47:36
 */
@Controller("drOutAction")
public class OutAction  extends BaseAction{

	private static final long serialVersionUID = 4434097970993600063L;
	//输入参数
	private String param;
	//仅导入模块使用;
	private String drmkdm;
	
	/**
	 * 单点登录，参数param，
	 * 解密后多个参数以&相连，参数名=参数值
	 * yhm用户名；gnbh功能编号;timestamp时间戳
	 * @return
	 */
	public String login() {
		DesBase64Codec dbEncrypt = new DesBase64Codec();
		String yhm = null,gnbh = null;
		try {
			if(param == null){
				this.getValueStack().set(DATA, "调用外部登陆失败,参数错误！");
				return DATA;
			}
			String accessParam = dbEncrypt.decrypt(param.getBytes());
			String[] params = accessParam.split("&");
			HashMap<String,String> paramMap = new HashMap<String, String>();
			for (int i = 0; i < params.length; i++) {
				if(params != null){
					String[] paramsArr = params[i].split("=");
					if(paramsArr != null && paramsArr.length > 1){
						paramMap.put(paramsArr[0], paramsArr[1]);
					}
				}
			}
			//用户名参数
			yhm = paramMap.get("yhm");
			//功能编号参数
			gnbh = paramMap.get("gnbh");
			//导入模块代码(仅导入模块使用)
			drmkdm = paramMap.get("drmkdm");
			return gnbh;
		} catch (Exception e) {
			this.getValueStack().set(DATA, "用户参数：[" + yhm + "] 非法：无法获取用户信息！");
			return DATA;
		}

	}
	
	/**
	 * 系统初始化
	 */
	public void initialize() {
	}
	
	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getDrmkdm() {
		return drmkdm;
	}

	public void setDrmkdm(String drmkdm) {
		this.drmkdm = drmkdm;
	}
	
}
