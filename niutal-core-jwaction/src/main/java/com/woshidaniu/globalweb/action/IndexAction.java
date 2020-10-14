package com.woshidaniu.globalweb.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.util.TextProviderHelper;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.log.LoginType;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.service.BaseLog;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.entities.AncdModel;
import com.woshidaniu.entities.IndexModel;
import com.woshidaniu.entities.XwglModel;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.service.impl.LogEngineImpl;
import com.woshidaniu.service.svcinterface.IAncdService;
import com.woshidaniu.service.svcinterface.IIndexService;
import com.woshidaniu.service.svcinterface.IJsgnmkService;
import com.woshidaniu.service.svcinterface.ILoginService;
import com.woshidaniu.service.svcinterface.IWdyyService;
import com.woshidaniu.service.svcinterface.IXtszService;
import com.woshidaniu.service.svcinterface.IXwglService;
import com.woshidaniu.shiro.realm.AccountRealm;
import com.woshidaniu.shiro.realm.DefaultAccountRealm;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.file.DirectoryUtils;
import com.woshidaniu.util.xml.BaseDataReader;
import com.woshidaniu.web.Parameter;
import com.woshidaniu.web.Parameters;
import com.woshidaniu.web.context.WebContext;

@SuppressWarnings("serial")
public class IndexAction extends CommonBaseAction implements ModelDriven<IndexModel> {

	@Resource
	private IIndexService indexService;
	@Resource
	private IWdyyService  wdyyService;
	@Resource
	private IXwglService  xwglService;
	@Resource
	private IAncdService  ancdService;
	@Resource
	private IXtszService  xtszService;
	@Resource
	private IJsgnmkService jsgnmkService;
	@Resource
	private ILoginService loginService;
	protected IndexModel model = new IndexModel();
	protected BaseLog baseLog = LogEngineImpl.getInstance();
    protected String url;
	protected String curmkdm;
    
	/**
	 * 
	 * 方法描述: 首页的加载(教师，学生) 参数 @return 参数说明 返回类型 String 返回类型
	 * 
	 * @throws
	 */
	public String initMenu() {
		try {
		
			User user= getUser();
			
			//登录成功;记录登录方式标记；1：页面登录；2：单点登录；3：票据登录（通过握手秘钥等参数认证登录）
			String loginType = String.valueOf(getSession().getAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY)));
	        //页面登录：验证密码强度
			if(LoginType.INNER.getKey().equals(loginType)){
	        	//密码强度验证  强度小于等于1,则为弱密码,跳转首页
	            if(StringUtils.isEmpty(user.getYhmmdj()) || Integer.valueOf(user.getYhmmdj()) <= 1){
	        		return "indexPage";
	            }
	        }
		
			//通过指定唯一角色，从而切换界面功能
			Map<String, Object> param = new HashMap<String, Object>();
			List<String> jsxx 		  = new ArrayList<String>();
			// 第一次进入，角色代码为空
			if (StringUtils.isEmpty(model.getJsdm())) {
				//按照角色权限范围由大到小默认选择第一个角色即权限最大的角色
				jsxx.add(user.getJsdms().get(0));
				model.setJsdm(user.getJsdms().get(0));
			}else{
				//已经选择了角色代码;
				jsxx.add(model.getJsdm());
				switchRole(model.getJsdm());
			}
			//设置用户信息到session中
			getSession().setAttribute("jsxx", jsxx);
			
			
			String login_role = model.getJsdm();
			user.setJsdm(login_role);
			user.setJslxdm(getIndexService().getJslxdm(login_role));
			getSession().setAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY), user);
		    //设置登录用户的登录角色信息，在拦截器中会用于判断角色切换后的权限判断
			getSession().setAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_KEY), login_role);
			//获取本次登录中前次使用角色和本次登录角色信息，在拦截器中会用于判断角色切换后的权限判断
	        String pre_role = String.valueOf(getSession().getAttribute(Parameters.getGlobalString(Parameter.SESSION_ROLE_PRE_KEY)));
	        //判断本次登录中前次使用角色和本次登录角色是否相同
	        if(!pre_role.equals(login_role)){
	        	//查询当前角色的角色功能代码集合
	            user.setJsgnmkdmList(getJsgnmkService().cxJsGnmkdmList(login_role));
	        }
	        
			// 根据角色查询功能菜单
			param.put("jsdm", login_role);
			param.put("model", model);
			param.put("list", jsxx);
			//统一菜单查询接口
			getValueStack().set("RoleMenu", getAncdService().getMenu(user.getYhm(),  model.getJsdm()));
			getValueStack().set("languageList", BaseDataReader.getCachedBaseDataList("languageList"));
			getValueStack().set("login_type", getSession().getAttribute(Parameters.getGlobalString(Parameter.LOGIN_TYPE_KEY)));
			
			//该方法不受拦截器控制，手动进行国际化语言切换
			LocaleUtils.interceptLocale(getRequest(),getValueStack());
			return "teapage";
			
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	
	//切换角色
	protected void switchRole(String jsdm) {
		try {
			if(StringUtils.isNotBlank(jsdm) && (!StringUtils.equals(jsdm, getUser().getJsdm()))){
				//切换当前的角色信息
				getUser().setJsdm(jsdm);
				//刷新shiro缓存
				AccountRealm shiroRealm = ServiceFactory.getService(DefaultAccountRealm.class);
				shiroRealm.clearAuthorizationCache();
				//刷新shiro缓存
			}
		} catch (Exception e) {
			logException(e);
		}
	}
	
	
	/**
	 * 
	 *@描述		：子页面中转
	 *@创建人		: kangzhidong
	 *@创建时间	: Aug 8, 20168:36:04 AM
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public String cxFuncIndex() {
		return SUCCESS;
	}
	
	/**
	 * 
	 * @description:查询当前用户可见新闻和通知
	 * @author kangzhidong
	 * @date 2014-3-24
	 * @return
	 */
	public String cxNews() {
		try {
			User user = getUser();
			XwglModel xwglModel = new XwglModel();
			xwglModel.setYhm(user.getYhm());
			
			if(user.isStudent()){
				xwglModel.setYhlx("1");
			}else if(user.isTeacher()){
				xwglModel.setYhlx("2");
			}else{
				xwglModel.setYhlx("3");
			}
			List<XwglModel> newsList =  xwglService.getGrtzList(xwglModel);//查询首页新闻
			this.setProperty("newsList",newsList);
			//getEmptyList(limit, newsList.size());
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		
		return "cxNews";
	}
	
	/**
	 * 
	 *@描述：根据yhm显示用户首页信息，可嵌入其他系统
	 *@创建人:wjy
	 *@创建时间:2014-9-16下午04:05:15
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxYhxxIndex() {
		try {
			User user = getUser();
			String yhm  = user.getYhm();
			String yhlx = user.getYhlx();
			Map<String,String> yhxxMap = new HashMap<String,String>();
			yhxxMap.put("yhlx", yhlx);
			yhxxMap.put("yhm", yhm);
			if(yhlx.equalsIgnoreCase("teacher")){
				try {
					this.setProperty("hasJzgzp",getIndexService().hasJzgzp(yhm));
				} catch (Exception e) {
					logStackException(e);
					this.setProperty("hasJzgzp",false);
				}
			}
			if(yhlx.equalsIgnoreCase("student")){
				String xh_id = user.getYhlybid();
				yhxxMap.put("xh_id", xh_id);
				this.setProperty("xh_id",xh_id);
				try {
					this.setProperty("hasXsRxqzp",getIndexService().hasXsRxqzp(yhm));
					this.setProperty("hasXsRxhzp",getIndexService().hasXsRxhzp(yhm));
				} catch (Exception e) {
					logStackException(e);
					this.setProperty("hasXsRxqzp",false);
					this.setProperty("hasXsRxhzp",false);
				}
			}
			Map<String,String> map = getIndexService().getYhxxIndex(yhxxMap);
			if(!BlankUtils.isBlank(map) && yhlx.equalsIgnoreCase("student")){
				Object sxxf = map.get("SXXF");
				if(sxxf.equals("0")){
					map.put("BL","100");
				}
				Object bl= map.get("BL");
				if(Integer.parseInt(bl.toString())>100){
					map.put("BL","100");
				}
			}
			this.setProperty("map",BlankUtils.isBlank(map) ? new HashMap<String,String>() : map);
			this.setProperty("yhlx",yhlx);
			this.setProperty("yhm",yhm);
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
		if(getRequest().getParameter("xt")!=null&&getRequest().getParameter("xt").equalsIgnoreCase("jw")){
			return "cxYhxxJwIndex";
		}
		return "cxYhxxIndex";
	}
	
	/**
	 * 
	 *@描述：查询用户功能
	 *@创建人:majun
	 *@创建时间:2014-7-10下午05:32:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 * @throws UnsupportedEncodingException 
	 */
	public String cxGnPage() throws UnsupportedEncodingException{
		User user  		     = getUser();
		if(BlankUtils.isBlank(user)){
			return Result.EX_SESSION_OUT;
		}
		AncdModel ancdModel  = new AncdModel();
		List<AncdModel> list = new ArrayList<AncdModel>();
		//功能代码为空，跳入错误页面
		if(BlankUtils.isBlank(model.getGnmkdm())){
			getValueStack().set(Result.MESSAGE, getText("W-N000000-01001",new String[]{"gnmkdm"}));
			return Result.EX_WARN;
		}
		if(BlankUtils.isBlank(model.getDyym()) || (!BlankUtils.isBlank(model.getDyym()) && "0".equals(model.getSfgnym()) ) ){
			ancdModel.setGnmkdm(model.getGnmkdm());
			ancdModel.setYhm(user.getYhm());
			ancdModel.setJsdm(user.getJsdm());
			list  =  getIndexService().cxYhgnList(ancdModel);
			if(list.size()>0){
				getValueStack().set("xslx", list.get(0).getXslx());
				if(list.size()==1){
					AncdModel model = list.get(0);
					getValueStack().set("gnmkdm", model.getGnmkdm());
					getValueStack().set("dyym", model.getDyym());
					getValueStack().set("gnmkmc", model.getGnmkmc());
				}else{
					AncdModel model = list.get(1);
					getValueStack().set("gnmkdm", model.getGnmkdm());
					getValueStack().set("dyym", model.getDyym());
					getValueStack().set("gnmkmc", model.getGnmkmc());
				}
			}else{
				return Result.EX_NON_ACCESS;
			}
		}else{
			String dyym = model.getDyym();
			ancdModel.setDyym(dyym);
			ancdModel.setGnmkdm(model.getGnmkdm());
			String localeName = MessageUtil.getLocaleText(WebContext.getLocale(), model.getGnmkdm());
			ancdModel.setGnmkmc(StringUtils.getSafeStr(localeName, URLDecoder.decode(model.getGnmkmc(), "utf-8")));
			list.add(ancdModel);
			getValueStack().set("xslx", 0);
		}
		getValueStack().set(ZFtalParameters.getGlobalString(ZFtalParameter.REQUEST_FUNC_USERKEY) , user.getYhm());
		getValueStack().set("gnList", list);
		getValueStack().set("languageList", BaseDataReader.getCachedBaseDataList("languageList"));
		return "gnPage";
	}
	
	/**
	 * 
	 *@描述：浏览器验证跳转
	 *@创建人:majun
	 *@创建时间:2014-8-13下午07:34:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxBrowser(){
		return Result.RD_BROWSER;
	}
	
	/**
	 * 
	 *@描述：浏览器验证跳转
	 *@创建人:majun
	 *@创建时间:2014-8-13下午07:34:39
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 * @throws IOException 
	 */
	public String xzBrowser() throws IOException{
		 InputStream fis = null;
		try {
			String wabappPath  =  System.getProperty("user.dir").replace("bin", "webapps");
			String browserType =  getRequest().getParameter("browserType");
			String filePath    = "";
			String downloadUrl = "";
			if("chrome".equals(browserType)){
				filePath  = wabappPath+"/browserFile/ChromeSetup.rar";
				fileName = "ChromeSetup.rar";
				downloadUrl = "http://www.google.cn/intl/zh-CN/chrome/browser/";
			}else if("firefox".equals(browserType)){
				filePath  = wabappPath+"/browserFile/FirefoxSetup.rar";
				fileName = "FirefoxSetup.rar";
				downloadUrl = "http://www.firefox.com.cn/download/";
			}else if("safari".equals(browserType)){
				filePath  = wabappPath+"/browserFile/SafariSetup.rar";
				fileName = "SafariSetup.rar";
				downloadUrl = "http://rj.baidu.com/soft/detail/12966.html?ald";
			}else if("ie9".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE9Setup.rar";
				fileName = "IE9Setup.rar";
				downloadUrl = "http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/";
			}else if("ie10".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE10Setup.rar";
				fileName = "IE10Setup.rar";
				downloadUrl = "http://rj.baidu.com/soft/detail/14917.html?ald";
			}else if("ie11".equals(browserType)){
				filePath  = wabappPath+"/browserFile/IE11Setup.rar";
				fileName = "IE11Setup.rar";
				downloadUrl = "http://windows.microsoft.com/zh-cn/internet-explorer/ie-11-worldwide-languages/";
			}else if("360".equals(browserType)){
				filePath  = wabappPath+"/browserFile/360Setup.rar";
				fileName = "360Setup.rar";
				downloadUrl = "http://se.360.cn/";
			}else if("baidu".equals(browserType)){
				filePath  = wabappPath+"/browserFile/BaiduSetup.rar";
				fileName = "BaiduSetup.rar";
				downloadUrl = "https://liulanqi.baidu.com/";
			}
			// 以流的形式下载文件。
	        file = new File(DirectoryUtils.getResolvePath(filePath));
	        getValueStack().set("downloadUrl", downloadUrl);
	        if(!file.exists()){
	        	return "browserDownLoad";
	        }
			// 以流的形式下载文件。
	        fis = new BufferedInputStream(new FileInputStream(file));
	        bytes = new byte[fis.available()];
		} catch (Exception e) {
			logException(e);
			return Result.EX_BROWSER;
		} finally {
			IOUtils.closeQuietly(fis);
        }
		//该方法不受拦截器控制，手动进行国际化语言切换
		LocaleUtils.interceptLocale(getRequest(),getValueStack());
		return Result.BYTE;
	}
	/***
	 * 
	 *@描述：查询按钮
	 *@创建人:majun
	 *@创建时间:2014-7-9下午01:51:35
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param session
	 *@param gnmkdm
	 *@return
	 */
	public static String cxButtons(String gnmkdm,String ...czdms){
		
		gnmkdm = BlankUtils.isBlank(gnmkdm) ? WebContext.getRequest().getParameter("gnmkdm") : gnmkdm;
		
		HttpSession  session   = WebContext.getSession();

		User user  			   = (User) session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY));
		IAncdService service   = (IAncdService)ServiceFactory.getService("ancdService");
		List<AncdModel>  list  = service.cxButtonsList(user, gnmkdm);
		
		StringBuffer buttons   = new StringBuffer();
		buttons.append("<div class=\"btn-toolbar\" role=\"toolbar\" style=\"float:right;\">");
		buttons.append("<div class=\"btn-group\" id=\"but_ancd\">");
		if(!BlankUtils.isBlank(list)){
			String path =  null;
			List<String> czdmList  = new ArrayList<String>();
			for(int i=0;i<list.size();i++){
				AncdModel _model  = list.get(i);
				if("1".equals(_model.getSfxs())){
					if(!BlankUtils.isBlank(czdms)){
						for (String czdm : czdms) {
							if( _model.getCzdm().equals(czdm)){
								buttons.append(_model.getButton());
								break;
							}
						}
					}else{
						buttons.append(_model.getButton());
					}
				}
				czdmList.add(_model.getCzdm());
				if(i==0){
					path = _model.getPath();
				}
			}
			
			session.setAttribute(WebUtils.getFuncSessionKey(path, user.getJsdm()), czdmList);
		}else{
			ValueStack stack = ActionContext.getContext().getValueStack();
			buttons.append(TextProviderHelper.getText("i18n.unauthorized", "", stack));
		}
		buttons.append(" </div> </div>");
		 
		return buttons.toString();
	}
	
	/***
	 * 
	 *@描述：查询报表按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-8上午10:23:51
	 *@param gnmkdm
	 *@param czdms
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public static String cxReportButtons(String gnmkdm,String ...czdms){
		//<li><a id="last" class="btn btn-default btn-sm" role="button"><span class='glyphicon glyphicon-fast-forward'/> 末页</a></li>
		HttpSession  session   = SessionFactory.getSession();
		User user  			   = (User) session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY));
		IAncdService service  = (IAncdService)ServiceFactory.getService("ancdService");
		List<AncdModel>  list  = service.cxButtonGroupList(user, gnmkdm);
		
		StringBuffer buttons   = new StringBuffer();
		if(!BlankUtils.isBlank(list)){
			for(int i=0;i<list.size();i++){
				AncdModel _model  = list.get(i);
				//不显示的报表按钮
				if("0".equals(_model.getSfxs()) && _model.getCzdm().startsWith("report_") ){
					if(!BlankUtils.isBlank(czdms)){
						for (String czdm : czdms) {
							if( _model.getCzdm().equals(czdm)){
								buttons.append("<li><a id=\"btn_" + _model.getCzdm() + "\" class=\"btn btn-default btn-sm\" role=\"button\"><span class=\""+_model.getAnys()+"\"/> " + _model.getCzmc() + "</a></li>");
								break;
							}
						}
					}else{
						buttons.append("<li><a id=\"btn_" + _model.getCzdm() + "\" class=\"btn btn-default btn-sm\" role=\"button\"><span class=\""+_model.getAnys()+"\"/> " + _model.getCzmc() + "</a></li>");
					}
				}
			}
		}		 
		return buttons.toString();
	}
	
	public static String cxLinks(String ...gnmkdms){
		if(BlankUtils.isBlank(gnmkdms)){
			return "";
		}
		IAncdService service   = (IAncdService)ServiceFactory.getService("ancdService");
		List<AncdModel>  list  = service.cxLinkList(gnmkdms);
		StringBuffer links   = new StringBuffer();
		if(!BlankUtils.isBlank(list)){
			String stylePath = MessageUtil.getText("system.stylePath");
			/*links.append("<table class=\"table list-unstyled\" style=\"text-align:center\"><tbody>");
			for (int i = 0; i < list.size(); i++) {
				AncdModel ancdModel = list.get(i);
				if(i%2 == 0){
					links.append("<tr>");
				}
				links.append("<td class=\"col-md-6 col-sm-6 col-xs-6\">");
				links.append("<a onclick=\"clickMenu('");
				links.append(ancdModel.getGnmkdm()).append("','").append(ancdModel.getDyym()).append("','");
				links.append(ancdModel.getGnmkmc()).append("'); return false;\" href=\"#\" target=\"_blank\">");
				links.append("<img src=\"").append(stylePath);
				if(!BlankUtils.isBlank(ancdModel.getTblj())){
					links.append(ancdModel.getTblj());
				}else{
					links.append("images/ico/ico_but2.png");
				}
				links.append("\" alt=\"").append(ancdModel.getGnmkmc()).append("\"/>");
				if(!"zh_CN".equalsIgnoreCase(LocaleUtils.getLocaleKey())){
					links.append("<h6 style=\"margin-top: 5px;color:#333;\">").append(ancdModel.getGnmkmc()).append("</h6>");
				}else{
					links.append("<h5 style=\"margin-top: 5px;color:#333;\">").append(ancdModel.getGnmkmc()).append("</h5>");
				}
				links.append("</a></td>");
				if(i > 0 && i%2 == 1){
					links.append("</tr>");
				}
			}
			links.append("</tbody></table>");
			*/
			for (AncdModel ancdModel : list) {
				links.append("<li class=\"col-lg-4 col-md-4 col-sm-6 col-xs-4\">");
				links.append("<a class=\"nav-blank\" ");
				//功能代码
				links.append(" data-gnmkdm=\"").append(ancdModel.getGnmkdm()).append("\"");
				//功能名称
				links.append(" data-gnmkmc=\"").append(ancdModel.getGnmkmc()).append("\"");
				//请求地址
				links.append(" data-dyym=\"").append(ancdModel.getDyym()).append("\"");
				//是否功能页面
				links.append(" data-sfgnym=\"1\" target=\"_blank\">");
				/*links.append("<a onclick=\"clickMenu('");
				links.append(ancdModel.getGnmkdm()).append("','").append(ancdModel.getDyym()).append("','");
				links.append(ancdModel.getGnmkmc()).append("'); return false;\"target=\"_blank\">");*/
				links.append("<img src=\"").append(stylePath);
				if(!BlankUtils.isBlank(ancdModel.getTblj())){
					links.append(ancdModel.getTblj());
				}else{
					links.append("/assets/images/ico/ico_but2.png");
				}
				links.append("\" alt=\"").append(ancdModel.getGnmkmc()).append("\"/>");
				if(!"zh_CN".equalsIgnoreCase(LocaleUtils.getLocaleKey())){
					links.append("<h6>").append(ancdModel.getGnmkmc()).append("</h6>");
				}else{
					links.append("<h5>").append(ancdModel.getGnmkmc()).append("</h5>");
				}
				links.append("</a></li>");
			}
			
		}
		return links.toString();
	}
	
	public static boolean hasAncd(String gnmkdm,String ...czdms ) {
		HttpSession  session   = SessionFactory.getSession();
		User user  			   = (User) session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY));
		IAncdService ancdService   = (IAncdService)ServiceFactory.getService("ancdService");
		List<AncdModel>  list  = ancdService.cxButtonGroupList( user, gnmkdm);
		if(!BlankUtils.isBlank(list) && BlankUtils.isBlank(czdms)){
			return true;
		}else if(!BlankUtils.isBlank(list)&&!BlankUtils.isBlank(czdms)){
			boolean isHasAllAncd = false;
			for (String czdm : czdms) {
				boolean isHasAncd = false;
				for(AncdModel _model  : list){
					if( _model.getCzdm().equals(czdm)){
						isHasAncd = true;
						break;
					}
				}
				isHasAllAncd = isHasAncd;
				if(!isHasAncd){
					break;
				}
			}
			return isHasAllAncd;
		}else{
			return false;
		}
	}
	
	public IXtszService getXtszService() {
		return xtszService;
	}

	public void setXtszService(IXtszService xtszService) {
		this.xtszService = xtszService;
	}

	/**
	 * 
	 *@描述：仅仅查询按钮部分html,方便页面扩展
	 *@创建人:kangzhidong
	 *@创建时间:2014-11-11下午04:43:37
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@param gnmkdm
	 *@param czdms
	 *@return
	 */
	public static String cxButtonLIGroups(String gnmkdm,String ...czdms){
		return cxButtonGroups(gnmkdm, "li", czdms);
	}
	
	public static String cxButtonTRGroups(String gnmkdm,String ...czdms){
		return cxButtonGroups(gnmkdm, "tr", czdms);
	}

	public static String cxButtonTDGroups(String gnmkdm,String ...czdms){
		return cxButtonGroups(gnmkdm, "td", czdms);
	}
	
	public static String cxButtonGroups(String gnmkdm,String tagName,String ...czdms){
		HttpSession  session   = SessionFactory.getSession();
		User user  			   = (User) session.getAttribute(Parameters.getGlobalString(Parameter.SESSION_USER_KEY));
		IAncdService service  = (IAncdService)ServiceFactory.getService("ancdService");
		List<AncdModel>  list  = service.cxButtonGroupList(user, gnmkdm);
		StringBuffer buttons   = new StringBuffer();
		if(!BlankUtils.isBlank(list)){
			tagName = tagName.toLowerCase();
			for(int i=0;i<list.size();i++){
				AncdModel _model  = list.get(i);
				if("1".equals(_model.getSfxs())){
					if(!BlankUtils.isBlank(czdms)){
						for (String czdm : czdms) {
							if( _model.getCzdm().equals(czdm)){
								if("li".equalsIgnoreCase(tagName)){
									buttons.append("<li class=\"list-group-item btn_" + _model.getCzdm() + "\" >" + _model.getCzmc() + "</li>");
								}else if("tr".equalsIgnoreCase(tagName)){
									buttons.append("<tr><td class=\"tr-td-item btn_" + _model.getCzdm() + "\">" + _model.getCzmc() + "</td></tr>");
								}else{
									buttons.append("<"+tagName+" class=\""+tagName+"-item btn_" + _model.getCzdm() + "\">" + _model.getCzmc() + "</td>");
								}
								break;
							}
						}
					}else{
						if("li".equalsIgnoreCase(tagName)){
							buttons.append("<li class=\"list-group-item btn_" + _model.getCzdm() + "\" >" + _model.getCzmc() + "</li>");
						}else if("tr".equalsIgnoreCase(tagName)){
							buttons.append("<tr class=\"tr-item\"><td class=\"tr-td-item btn_" + _model.getCzdm() + "\">" + _model.getCzmc() + "</td></tr>");
						}else{
							buttons.append("<"+tagName+" class=\""+tagName+"-item btn_" + _model.getCzdm() + "\">" + _model.getCzmc() + "</td>");
						}
					}
				}
			}
		}
		return buttons.toString();
	}
	
	
	/**
	 *@描述：访客进入
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-14上午10:10:02
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String fkIndex(){
		List<AncdModel> list = getIndexService().cxFkgnList();		
		getValueStack().set("gnList", list);
		return "fkIndex";
	}
	
	/**
	 *@描述：访客进入具体功能
	 *@创建人:"huangrz"
	 *@创建时间:2014-8-14上午10:40:11
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxFkGnPage(){
		return "cxFkGnPage";
	}
 
	
	public String center() {
		return "center";
	}

	public String sessionOut() {
		return Action.LOGIN;
	}

	public IndexModel getModel() {
		return model;
	}

	public void setModel(IndexModel model) {
		this.model = model;
	}

	public IIndexService getIndexService() {
		return indexService;
	}

	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public IWdyyService getWdyyService() {
		return wdyyService;
	}

	public void setWdyyService(IWdyyService wdyyService) {
		this.wdyyService = wdyyService;
	}

	public IXwglService getXwglService() {
		return xwglService;
	}

	public void setXwglService(IXwglService xwglService) {
		this.xwglService = xwglService;
	}
	
	public IJsgnmkService getJsgnmkService() {
		return jsgnmkService;
	}

	public void setJsgnmkService(IJsgnmkService jsgnmkService) {
		this.jsgnmkService = jsgnmkService;
	}

	public IAncdService getAncdService() {
		return ancdService;
	}

	public void setAncdService(IAncdService ancdService) {
		this.ancdService = ancdService;
	}

	public ILoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(ILoginService loginService) {
		this.loginService = loginService;
	}

	public String getCurmkdm() {
		return curmkdm;
	}

	public void setCurmkdm(String curmkdm) {
		this.curmkdm = curmkdm;
	}

}
