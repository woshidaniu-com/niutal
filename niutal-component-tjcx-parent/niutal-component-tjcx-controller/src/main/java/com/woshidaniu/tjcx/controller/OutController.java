package com.woshidaniu.tjcx.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.security.algorithm.DesBase64Codec;
import com.woshidaniu.tjcx.dao.entites.OutLoginModel;
import com.woshidaniu.tjcx.service.impl.Constants;


/**
 * 
 * @系统名称: 统计报表系统
 * @模块名称: 单点登录接口
 * @类功能描述: 
 * @作者： ligl
 * @时间： 2013-6-27 下午04:42:23
 * @版本： V1.0
 * @修改记录:
 */
@Controller
@RequestMapping("/tjcx")
public class OutController extends BaseController {
	
	//private BaseLog baseLog = LogEngineImpl.getInstance();

	/**
	 * 单点登录，参数param，
	 * 解密后多个参数以&相连，参数名=参数值
	 * yhm用户名；gnbh功能编号;timestamp时间戳
	 * @return
	 */
	@RequestMapping("/login.zf")
	public String login(HttpServletRequest request,String param) {
		HttpSession session = request.getSession();
		OutLoginModel model = new OutLoginModel();
		DesBase64Codec dbEncrypt = new DesBase64Codec();
		
		String yhm2 = null;
		String gnbh = null;
		String gnmk = null;
		String menu = null;
		String jmlx = null;
		String sqlQx = null;
		String kzszid = null;
		
		try {
			jmlx = request.getParameter("jmlx");
			if(jmlx != null && jmlx.equals("mw")){
				yhm2 = request.getParameter("yhm");
				gnbh = request.getParameter("gnbh");
				gnmk = request.getParameter("gnmk");
				menu = request.getParameter("menu");
				sqlQx = request.getParameter("sqlQx");
				kzszid = request.getParameter("kzszid");
			}else{				
				String param2 = dbEncrypt.decrypt(param.getBytes());
				String[] params = param2.split("&");
				HashMap<String,String> paramMap = new HashMap<String, String>();
				for (int i = 0; i < params.length; i++) {
					if(params != null){
						String[] strs = params[i].split("=");
						if(strs == null || strs.length < 2 ){
							continue;
						}
						paramMap.put(strs[0], strs[1]);
					}
				}
				yhm2 = paramMap.get("yhm");
				gnbh = paramMap.get("gnbh");
				gnmk = paramMap.get("gnmk");
				menu = paramMap.get("menu");
				sqlQx = paramMap.get("sqlQx");
				kzszid = paramMap.get("kzszid");

			}
			
			if(sqlQx != null && !sqlQx.trim().equals("")){
				sqlQx = java.net.URLDecoder.decode(sqlQx, "GBK");				
				session.setAttribute(Constants.TJCX_SQL_QX_XG, sqlQx);//
			}
			
			if(kzszid != null && !kzszid.trim().equals("")){
				model.setKzszid(kzszid);
				gnbh = "kzxq";
			}
			
			session.setAttribute(Constants.TJCX_GNMK_BS, gnmk);//功能模块
			session.setAttribute(Constants.TJCX_CURRENT_MENU, menu);//界面当前位置
			/*User user = getUser();
			baseLog.login(
					user,
					MessageUtil.getText("log.message.ywmc", new String[] { "登陆系统",
							"xg_xtgl_yhb" }),
					"系统管理",
					MessageUtil.getText("log.message.czms", new String[] { "登陆系统", "用户名",
							user.getYhm() }));*/
			
			
			if(StringUtils.equalsIgnoreCase("tjbb", gnbh)){
				return "redirect:/niutal/tjcx/tjbb/tjbb.zf?layout=default";
			}
			
			if(StringUtils.equalsIgnoreCase("tjcx", gnbh)){
				return "redirect:/niutal/tjcx/tjcx/tjcx.zf?layout=default";
			}
			
			if(StringUtils.equalsIgnoreCase("kzxq", gnbh)){
				return "redirect:/niutal/tjcx/kzsz/kzsz.zf?layout=default&kzszid="+kzszid;
			}
			return "redirect:/xtgl/index/initMenu.zf";
		} catch (Exception e) {
			logException(e);
		}
		return null;
	}
}
