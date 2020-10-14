package com.woshidaniu.tjcx.action;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.opensymphony.xwork2.ModelDriven;
import com.woshidaniu.common.action.BaseAction;
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
public class OutAction extends BaseAction implements ModelDriven<OutLoginModel> {
	private static final long serialVersionUID = -2481866698635373788L;
	private OutLoginModel model = new OutLoginModel();

	//private BaseLog baseLog = LogEngineImpl.getInstance();

	private String param;

	public void initialize() {
	}

	/**
	 * 单点登录，参数param，
	 * 解密后多个参数以&相连，参数名=参数值
	 * yhm用户名；gnbh功能编号;timestamp时间戳
	 * @return
	 */
	public String login() {
		HttpSession session = getSession();
		DesBase64Codec dbEncrypt = new DesBase64Codec();
		
		String yhm2 = null;
		String gnbh = null;
		String gnmk = null;
		String menu = null;
		String jmlx = null;
		String sqlQx = null;
		String kzszid = null;
		
		try {
			HttpServletRequest request = getRequest();
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
					getText("log.message.ywmc", new String[] { "登陆系统",
							"xg_xtgl_yhb" }),
					"系统管理",
					getText("log.message.czms", new String[] { "登陆系统", "用户名",
							user.getYhm() }));*/
			return gnbh;
		} catch (Exception e) {
			this.getValueStack().set(DATA, "用户参数：[" + yhm2 + "] 非法：无法获取用户信息！");
			return DATA;
		}

	}

	public OutLoginModel getModel() {
		return model;
	}

	public void setModel(OutLoginModel model) {
		this.model = model;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
}
