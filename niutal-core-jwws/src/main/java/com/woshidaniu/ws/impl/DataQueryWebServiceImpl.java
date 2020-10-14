package com.woshidaniu.ws.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.jws.WebParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.daointerface.IDataQueryWebServiceDao;
import com.woshidaniu.daointerface.ILoginDao;
import com.woshidaniu.entities.LoginModel;
import com.woshidaniu.entities.webservice.DbsyModel;
import com.woshidaniu.entities.webservice.JskbModel;
import com.woshidaniu.entities.webservice.ParamModel;
import com.woshidaniu.entities.webservice.TzggModel;
import com.woshidaniu.entities.webservice.XscjModel;
import com.woshidaniu.entities.webservice.XskbModel;
import com.woshidaniu.entities.webservice.YhxxModel;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.context.WebContext;
import com.woshidaniu.ws.svcinterface.IDataQueryWebService;
import com.woshidaniu.ws.utils.PropertiesUtils;
import com.woshidaniu.ws.utils.ToolUtil;
import com.woshidaniu.xmlhub.utils.XStreamUtils;


/**
 * 
 *@类名称:DataQueryWebServiceImpl.java
 *@类描述：
 *@创建时间：2015-3-31 上午08:55:02
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public class DataQueryWebServiceImpl implements IDataQueryWebService {
	
	private static final Logger LOG = LoggerFactory.getLogger(DataQueryWebServiceImpl.class);
	
	private static final int DEFAULT_NOTICE_NUM = 6;
	
	@Resource
	private IDataQueryWebServiceDao dao;
	
	private ILoginDao loginDao = (ILoginDao)ServiceFactory.getService("loginDao");
	
	private LoginModel login;
	
	/**
	 *@描述：获取学生成绩信息
	 *@param yhm 用户名
	 *@param num 获取记录数
	 *@param sign 验证签名
	 *@return num个表单
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-28上午09:57:14	 
	 */
	@Override
	public String getXscjList( @WebParam(name="yhm") String yhm,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");
			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}
			
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}
		
		initUser(yhm);//获取用户数据
		List<XscjModel> list = new ArrayList<XscjModel>();		
		try {
			if( num == 0 ) {
				num = DEFAULT_NOTICE_NUM;
			}			
			ParamModel param = new ParamModel();
			param.setNum( num );
			param.setYhm(yhm);
			param.setYhid(login.getYhlybid());
			list = dao.getXscjList(param);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
			
		Map<String, Class> map = new HashMap<String, Class>();
		
		map.put( "noticeList", List.class );
		map.put( "notice", XscjModel.class );
		
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml( list, map );
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	/**
	 * 获取通知公告
	 * @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getTzggList( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,
			@WebParam(name="num") Integer num, @WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");
			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}
			
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}    				
		//判断系统用户类型
		if("xs".equals(yhlx)){
			yhlx = "1";
		}else if("js".equals(yhlx)){
			yhlx = "2";
		}else{
			yhlx = "3";
		}
		List<TzggModel> list = new ArrayList<TzggModel>();
		
		try {
			if( num == 0 ) {
				num = DEFAULT_NOTICE_NUM;
			}			
			ParamModel param = new ParamModel();
			param.setNum( num );
			param.setYhm(yhm);
			param.setYhlx(yhlx);
			list = dao.getTzggList(param);
			
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
			
		Map<String, Class> map = new HashMap<String, Class>();
		
		map.put( "noticeList", List.class );
		map.put( "notice", TzggModel.class );
		
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml( list, map );
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	/**
	 *@描述：获取学生课表信息
	 *@param yhm 用户名
	 *@param num 获取记录数
	 *@param sign 验证签名
	 *@return num个表单
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-28上午09:57:14	 
	 */
	@Override
	public String getXskbList( @WebParam(name="yhm") String yhm,
			@WebParam(name="num") Integer num, @WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}			
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}
		
		initUser(yhm);//获取用户数据
		List<XskbModel> list = new ArrayList<XskbModel>();
		
		try {
			if( num == 0 ) {
				num = DEFAULT_NOTICE_NUM;
			}			
			ParamModel param = new ParamModel();
			param.setNum( num );
			param.setYhm(yhm);
			param.setYhid(login.getYhlybid());
			list = dao.getXskbList(param);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
			
		Map<String, Class> map = new HashMap<String, Class>();		
		map.put( "noticeList", List.class );
		map.put( "notice", XskbModel.class );
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml( list, map );
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	/**
	 *@描述：获取教师课表信息
	 *@param yhm 用户名
	 *@param num 获取记录数
	 *@param sign 验证签名
	 *@return num个表单
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-28上午09:57:14	 
	 */
	@Override
	public String getJskbList( @WebParam(name="yhm") String yhm,
			@WebParam(name="num") Integer num, @WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}			
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}
		initUser(yhm);//获取用户数据
		List<JskbModel> list = new ArrayList<JskbModel>();
		
		try {
			if( num == 0 ) {
				num = DEFAULT_NOTICE_NUM;
			}			
			ParamModel param = new ParamModel();
			param.setNum( num );
			param.setYhm(yhm);	
			param.setYhid(login.getYhlybid());
			list = dao.getJskbList(param);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
			
		Map<String, Class> map = new HashMap<String, Class>();		
		map.put( "noticeList", List.class );
		map.put( "notice", JskbModel.class );
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml(list, map);
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	/**
	 * 获取用户信息
	 * @param yhm 用户名
	 * @param yhlx 用户类型
	 * @param sign 验证签名
	 */
	public String getYhxxIndex( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,
			@WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");
			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}
			
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}
		
		List<YhxxModel> list = new ArrayList<YhxxModel>();
		String systemPath = MessageUtil.getText("system.systemPath") + WebContext.getServletContext().getContextPath();
		String stylePath = MessageUtil.getText("system.stylePath");
		StringBuffer yhxx = new StringBuffer();
		Map<String,String> yhxxMap = new HashMap<String,String>();
		try {	
			yhxx.append("<div class=\"index_grxx\"><div class=\"media\"><a class=\"pull-left\" href=\"#\">");		
			yhxxMap.put("yhlx", yhlx);
			yhxxMap.put("yhm", yhm);
			if(yhlx.equalsIgnoreCase("teacher")){
				int countJzgzp = dao.countJzgzp(yhm);
				if(countJzgzp>0){
					yhxx.append("<img class=\"media-object\" data-src=\"holder.js/110x110\" alt=\"110x110\" src=\""+systemPath+"/photo/photo_cxJzgzp.html?jgh="+yhm+"\" style=\"width: 110px; height: 110px;\">");
				}else{
					yhxx.append("<img class=\"media-object\" data-src=\"holder.js/110x110\" alt=\"110x110\" src=\""+stylePath+"images/user_logo.jpg\" style=\"width: 110px; height: 110px;\">");
				}
			}
			if(yhlx.equalsIgnoreCase("student")){
				String xh_id=dao.getXh_id(yhm);
				yhxxMap.put("xh_id", xh_id);
				int countXsRxqzp = dao.countXsRxqzp(yhm);
				int countXsRxhzp = dao.countXsRxhzp(yhm);
				if(countXsRxhzp>0){
					yhxx.append("<img class=\"media-object\" data-src=\"holder.js/110x110\" alt=\"110x110\" src=\""+systemPath+"/photo/photo_cxXszp.html?xh_id="+xh_id+"&zplx=rxhzp\" style=\"width: 110px; height: 110px;\">");
				}else if(countXsRxqzp>0){
					yhxx.append("<img class=\"media-object\" data-src=\"holder.js/110x110\" alt=\"110x110\" src=\""+systemPath+"/photo/photo_cxXszp.html?xh_id="+xh_id+"&zplx=rxhzp\" style=\"width: 110px; height: 110px;\">");
				}else{
					yhxx.append("<img class=\"media-object\" data-src=\"holder.js/110x110\" alt=\"110x110\" src=\""+stylePath+"images/user_logo.jpg\" style=\"width: 110px; height: 110px;\">");
				}
			}
			Map<String,String> yhMap = dao.getYhxxIndex(yhxxMap);
			yhxx.append("</a><div class=\"media-body\"><h4 class=\"media-heading\">"+(yhMap.get("XM")==null?"":yhMap.get("XM"))+"</h4>");
			if(yhlx.equalsIgnoreCase("teacher")){
				yhxx.append("<p>"+(yhMap.get("JGMC")==null?"":yhMap.get("JGMC"))+"</p>");
				if(!yhm.equalsIgnoreCase("admin")){
					yhxx.append("<p class=\"fs1\">"+(yhMap.get("ZCMC")==null?"":yhMap.get("ZCMC"))+"</p>");
				}
			}
			
			if(yhlx.equalsIgnoreCase("student")){
				Object sxxf = yhMap.get("SXXF");
				if(sxxf.equals("0")){
					yhMap.put("BL","100");
				}
				Object bl= yhMap.get("BL");
				if(Integer.parseInt(bl.toString())>100){
					yhMap.put("BL","100");
				}
				yhxx.append("<p>"+(yhMap.get("JGMC")==null?"":yhMap.get("JGMC"))+" "+(yhMap.get("BJ")==null?"":yhMap.get("BJ"))+"</p>");
				yhxx.append("<p class=\"fs1\">已获学分"+((Object)yhMap.get("YHXF")).toString()+"分</p>");
				yhxx.append("<div class=\"progress\"><div class=\"progress-bar progress-bar-success\" role=\"progressbar\" aria-valuenow=\"40\" aria-valuemin=\"0\" aria-valuemax=\"100\" style=\"width: "+((Object)yhMap.get("BL")).toString()+"%\">");
				yhxx.append("<span class=\"sr-only\">"+((Object)yhMap.get("BL")).toString()+"% Complete (success)</span></div><a href=\"#\">详细</a></div>");
				yhxx.append("<p class=\"fs2\">毕业所需学分 "+((Object)yhMap.get("SXXF")).toString()+"分</p>");
			}
			yhxx.append("</div></div></div>");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
		YhxxModel yhxxModel = new YhxxModel();
		yhxxModel.setYhxx(yhxx.toString());
		list.add(yhxxModel);
		Map<String, Class> map = new HashMap<String, Class>();
		map.put( "noticeList", List.class );
		map.put( "notice", YhxxModel.class );
		
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml( list, map );
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	/**
	 *@描述：待办事宜
	 *@param yhm 用户名
	 *@param num 获取记录数
	 *@param sign 验证签名
	 *@return num个表单
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-22上午09:57:14	 
	 */
	@Override
	public String getDbsyList( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,
			@WebParam(name="num") Integer num, @WebParam(name="sign") String sign ){
		try {
			//获取加密密钥
			Properties properties = PropertiesUtils.loadProperties("Key.properties");			
			String key = (String) properties.get("key");			
			if( ToolUtil.isNull( yhm ) || ToolUtil.isNull( sign ) ) {
				return ToolUtil.getErrorInfo("002", "传入参数错误");
			}
			String code = ToolUtil.eCode( key + yhm );
			System.out.println(code);
			if( ToolUtil.eCode( key + yhm ).equalsIgnoreCase( sign ) == false ) {
				return ToolUtil.getErrorInfo("001", "未授权访问");
			}
		} catch (IOException e) {
			LOG.error( "not find key.proerties" );
			return ToolUtil.getErrorInfo( "103", "服务端加密密钥未查找到" );
		}
		initUser(yhm);//获取用户数据
		
		String sso_user_key = Parameters.getGlobalString(Parameter.SESSION_SSO_USER_KEY);
    	String sso_role_key = Parameters.getGlobalString(Parameter.SESSION_SSO_ROLE_KEY);
    	
		WebContext.getSession().setAttribute(sso_user_key, yhm);
		WebContext.getSession().setAttribute(sso_role_key, yhlx);
		List<DbsyModel> list = new ArrayList<DbsyModel>();
		
		try {
			if( num == 0 ) {
				num = DEFAULT_NOTICE_NUM;
			}			
			ParamModel param = new ParamModel();
			param.setNum( num );
			param.setYhm(yhm);
			param.setYhid(login.getYhlybid());
			list = dao.getDbsyListByScope(param);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error at result");
			return ToolUtil.getErrorInfo( "101", "数据查询错误" );
		}
			
		Map<String, Class> map = new HashMap<String, Class>();		
		map.put( "noticeList", List.class );
		map.put( "notice", DbsyModel.class );
		if( list != null && list.size() > 0 ) {
			return XStreamUtils.collectionToXml( list, map );
		} else {
			return ToolUtil.getErrorInfo( "102", "数据查询为空" );
		}
	}
	
	//获取用户数据
	private void initUser(String yhm){		
		login = new LoginModel();
		login.setYhm(yhm);
		login = loginDao.getModel(login);
	}	

	public IDataQueryWebServiceDao getDao() {
		return dao;
	}
	public void setDao(IDataQueryWebServiceDao dao) {
		this.dao = dao;
	}

	public void setLogin(LoginModel login) {
		this.login = login;
	}

	public LoginModel getLogin() {
		return login;
	}
	
	
//	public static void main(String[] a){
//		List<TzggModel> list = new ArrayList<TzggModel>();
//		for(int i=0;i<5;i++){
//			TzggModel model = new TzggModel();
//			model.setXwbh("xxx测试新闻"+i);
//			model.setXwbt("xxx测试"+i);
//			model.setUrl("111" + i);
//			list.add(model);
//		}
//		Map<String, Class> map = new HashMap<String, Class>();		
//		map.put( "noticeList", List.class );
//		map.put( "notice", TzggModel.class );
//		String result = new XmlUtil().ListToXml( list, map );
//		System.out.println(result);
//	}
}
