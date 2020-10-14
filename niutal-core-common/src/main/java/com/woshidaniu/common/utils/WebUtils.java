package com.woshidaniu.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import com.woshidaniu.basemodel.PairModel;
import com.woshidaniu.basicutils.Assert;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.security.algorithm.MD5Codec;
import com.woshidaniu.web.utils.WebRequestUtils;

/**
 * 
 *@类名称		： WebUtils.java
 *@类描述		：
 *@创建人		：kangzhidong
 *@创建时间	：Mar 7, 2017 10:00:17 AM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public final class WebUtils extends com.woshidaniu.web.utils.WebUtils {

	public static List<PairModel> getPairParameters(ServletRequest request,String ...filters) {
		Assert.notNull(request, "request must not be null");
		//解析请求的参数
		Map<String, String> requestMap = WebRequestUtils.getParameters(request,filters);
		List<PairModel> requestPair = new ArrayList<PairModel>();
		for (String key : requestMap.keySet()) {
			requestPair.add(new PairModel(key, requestMap.get(key)));
		}
		return requestPair;
	}
	
	/**
	 * 
	 *@描述：根据路径和角色代码生成：访问功能查询功能按钮时，系统会把功能操作代码放入session中
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-23下午04:38:35
	 *@param path
	 *@param jsdm
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String getFuncSessionKey(String path,String jsdm){
		if(path.endsWith("_")){
			return MD5Codec.encrypt(path+"czdm_"+jsdm);
		}else{
			return MD5Codec.encrypt(path+"_czdm_"+jsdm);
		}
	}
	 	
	/**
	 *@描述		： 获取功能模块代码:主页打开页面使用gnmkdm参数名称，子页面传递功能代码使用gnmkdmKey参数名称
	 *@创建人		: kangzhidong
	 *@创建时间	: Dec 21, 20169:14:45 AM
	 *@param request
	 *@return
	 */
	public static String getFuncCode(HttpServletRequest request){
		Object gnmkdmKey = request.getParameter("gnmkdm");
	    	   gnmkdmKey = BlankUtils.isBlank(gnmkdmKey) ?  request.getAttribute("gnmkdm") : gnmkdmKey;
	    if(BlankUtils.isBlank(gnmkdmKey) || BlankUtils.isBlank(gnmkdmKey.toString())){
	    	   gnmkdmKey = request.getParameter("gnmkdmKey");
			   gnmkdmKey = BlankUtils.isBlank(gnmkdmKey) ?  request.getAttribute("gnmkdmKey") : gnmkdmKey;
	    }
        return gnmkdmKey == null ? null : gnmkdmKey.toString();
	}
	
	public static String getSessionUser(HttpServletRequest request){
		//获取当前会话中传递过来的参数
		String sessionUserKey = ZFtalParameters.getGlobalString(ZFtalParameter.REQUEST_FUNC_USERKEY);
		Object sessionUser = request.getParameter(sessionUserKey);
			   sessionUser = BlankUtils.isBlank(sessionUser) ?  request.getAttribute(sessionUserKey) : sessionUser;
        return sessionUser == null ? null : sessionUser.toString();
	}
	
}
