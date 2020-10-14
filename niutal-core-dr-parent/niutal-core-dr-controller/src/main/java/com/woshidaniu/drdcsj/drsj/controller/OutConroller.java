package com.woshidaniu.drdcsj.drsj.controller;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.security.algorithm.DesBase64Codec;

/**
 * xiaokang[1036]
 */
@Controller
@RequestMapping("/dr/out")
public class OutConroller  extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(OutConroller.class);
	/**
	 * 系统初始化
	 */
	@PostConstruct
	public void initialize() {
		
	}
	
	/**
	 * 单点登录，参数param，
	 * 解密后多个参数以&相连，参数名=参数值
	 * yhm用户名；gnbh功能编号;timestamp时间戳
	 * @return
	 */
	@RequestMapping("/login.zf")
	public String login(HttpServletRequest request, String param, String drmkdm) {
		DesBase64Codec dbEncrypt = new DesBase64Codec();
		String yhm = null;
		try {
			if(param == null){
				return "调用外部登陆失败,参数错误！";
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
			//导入模块代码(仅导入模块使用)
			drmkdm = paramMap.get("drmkdm");
			return "redirect:/niutal/dr/import_showImport.html?source=woshidaniu&amp;closeBtn=no&amp;drmkdm="+drmkdm;
		} catch (Exception e) {
			log.error("",e);
			return "用户参数：[" + yhm + "] 非法：无法获取用户信息！";
		}
	}
}
