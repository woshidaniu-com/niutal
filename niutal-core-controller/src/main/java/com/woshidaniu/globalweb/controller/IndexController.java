package com.woshidaniu.globalweb.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.controller.BaseController;
import com.woshidaniu.common.filter.RecentlyUsedMenu;
import com.woshidaniu.common.log.User;
import com.woshidaniu.dao.entities.JsglModel;
import com.woshidaniu.service.svcinterface.IJsglService;
import com.woshidaniu.service.svcinterface.ISystemConfigService;
import com.woshidaniu.util.base.MessageUtil;

/**
 * @author Penghui.Qu
 * 基础代码
 * @author 康康（1571）
 * 整理
 * nav-type-3类型
 * 高级查询版本设置
 */
@Controller
@RequestMapping(value = "/xtgl/index")
public class IndexController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);
	//高级查询版本配置key
	private static final String AdvancedSearchVersionKey = "niutal.advancedSearch.version";
	//菜单导航配置key
	private static final String NavTypeConfigKey = "niutal.nav.type";
	//菜单导航类型到页面的映射
	private static final Map<String,String> NavTypeToPageMapping = new HashMap<String,String>();
	//菜单类型描述
	private static final Map<String,String> NavTypeDesc = new HashMap<String,String>();
	
	static {
		NavTypeToPageMapping.put("nav-type-1", "/globalweb/index");
		NavTypeDesc.put("nav-type-1", "第一级菜单在顶部，其余菜单在左侧");
		
		NavTypeToPageMapping.put("nav-type-2", "/globalweb/index");
		NavTypeDesc.put("nav-type-2", "所有菜单在左侧");

		NavTypeToPageMapping.put("nav-type-3", "/globalweb/index-nav-type-3");
		NavTypeDesc.put("nav-type-3", "所有菜单在顶部");
	}
	//默认菜单导航类型
	private String systemNavType = "nav-type-1";
	@Autowired
	private IJsglService jsglService;
	
	@Autowired
	private ISystemConfigService systemConfitService;
	
	//初始化高级查询版本信息
	@PostConstruct
	public void initAdvancedSearchVersion(){
		String val = MessageUtil.getText(AdvancedSearchVersionKey);
		if(StringUtils.isNotEmpty(val)) {
			//设置版本
			com.woshidaniu.search.core.Version.setVersion(val);
		}
		String configedValue = com.woshidaniu.search.core.Version.getVersion();
		log.info("初始化高级查询版本,配置文件key:[{}],配置文件value:[{}],最终使用版本[{}]",AdvancedSearchVersionKey,val,configedValue);
	}
	
	//初始化菜单类型
	@PostConstruct
	public void init(){
		String configNavType = MessageUtil.getText(NavTypeConfigKey);
		if(StringUtils.isNotEmpty(configNavType)) {
			if(NavTypeToPageMapping.containsKey(configNavType)) {
				this.systemNavType = configNavType;
				log.info("系统配置文件的菜单类型:{},系统采用菜单类型:{},描述:{}",configNavType,this.systemNavType,NavTypeDesc.get(this.systemNavType));
			}else {
				log.info("系统配置文件的配置的菜单类型不符合系统预定义类型,系统采用默认菜单类型:{},描述:{}",this.systemNavType,NavTypeDesc.get(this.systemNavType));
			}
		}else {
			log.info("系统配置文件的没有配置菜单类型,系统采用默认菜单类型:{},描述:{}",this.systemNavType,NavTypeDesc.get(this.systemNavType));
		}
	}

	/**
	 * 初始化首页
	 * @return
	 */
	@RequestMapping(value = "/initMenu")
	public String initMenu(HttpServletRequest request , String gnmkdm,String navType) {
		try {
			User user = getUser();
			request.setAttribute("user", user);
			request.setAttribute("userCurrentRole", getUserCurrentRole());
			//String jsdm = user.getJsdm();
			if (user.getJsdms() != null && user.getJsdms().size() > 1) {
				List<JsglModel> cxJsxxZgh = jsglService.getJsxxListByZgh(user.getYhm());
				request.setAttribute("jsxxList", cxJsxxZgh);
			}
			
			//多角色上下文
			boolean isMutileRoleContext = this.systemConfitService.isEnableMutileRoleContext();
			request.setAttribute("isMutileRoleContext", isMutileRoleContext);
			
			//List<Menu> menuList =  jsglService.getGnqxByJsdm(user.getYhm() , jsdm);
			//request.setAttribute("menuList",menuList);
			
			ValueWrapper cache = getCacheManager().getCache(CacheName.USED_MENU).get(user.getYhm());
			if (cache != null){
				List<RecentlyUsedMenu> usedMenuList = (List<RecentlyUsedMenu>) cache.get();
				
				this.processXssForGnmkdm(usedMenuList);
				
				request.setAttribute("usedMenuList", usedMenuList);
			}
			if (StringUtils.isNotEmpty(gnmkdm)){
				request.setAttribute("gnmkdm", gnmkdm);
			}
			
			String pageNavType = this.systemNavType;
			//参数临时可变化菜单样式，仅供开发调试和演示
			if(StringUtils.isNotEmpty(navType)) {
				if(NavTypeToPageMapping.containsKey(navType)) {
					pageNavType = navType;
					if(!this.systemNavType.equals(navType)) {
						log.debug("展示临时菜单类型:{},描述:{}",pageNavType,NavTypeDesc.get(pageNavType));
					}
				}else {
					log.error("未定义的菜单导航类型:{}",navType);
				}
			}
			request.setAttribute("navType", pageNavType);
			
			String page = NavTypeToPageMapping.get(pageNavType);
			return page;
			
		} catch (Exception e) {
			logException(e);
			return ERROR_VIEW;
		}
	}

	/**
	 * @description	： 处理gnmkdm的xss注入问题
	 * @param usedMenuList
	 */
	private void processXssForGnmkdm(List<RecentlyUsedMenu> usedMenuList) {
		Iterator<RecentlyUsedMenu> it = usedMenuList.iterator();
		while(it.hasNext()) {
			RecentlyUsedMenu menu = it.next();
			String oldGnmkdm = menu.getGnmkdm();
			String newGnmkdm = org.owasp.encoder.Encode.forHtml(oldGnmkdm);
			menu.setGnmkdm(newGnmkdm);
		}
	}
}
