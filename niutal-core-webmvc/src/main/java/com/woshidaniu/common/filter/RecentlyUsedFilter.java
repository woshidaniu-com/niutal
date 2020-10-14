package com.woshidaniu.common.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.cache.core.Cache;
import com.woshidaniu.common.log.User;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.servlet.filter.cache.CacheSupportedFilter;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：最近使用过滤器
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2017年1月13日上午10:41:19
 */
public class RecentlyUsedFilter extends CacheSupportedFilter {
	
	private static final String REDIRECT_TO_LOGIN_KEY = "niutal.RecentlyUsedFilter.isSessionExpireRedirectToLogin";
	
	private static final Logger log = LoggerFactory.getLogger(RecentlyUsedFilter.class);

	private static final int USED_MENU_MAX = 6;
	
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		 //获取功能模块代码
		String gnmkdm = WebUtils.getFuncCode(WebUtils.toHttp(servletRequest));
		//访问功能菜单，处理业务逻辑
		if (!StringUtils.isNull(gnmkdm)){
			Cache<String,List<RecentlyUsedMenu>> info = getCacheManager().getCache(getCacheName());
//			Map<String,List<RecentlyUsedMenu>> info = new HashMap<String,List<RecentlyUsedMenu>>();
			
			User user = WebContext.getUser();
			if (user == null){
				boolean isRedirectToLogin = Boolean.parseBoolean(MessageUtil.getText(REDIRECT_TO_LOGIN_KEY));
				if(isRedirectToLogin){
					String redirectUri = "/sec/index.zf";
					log.debug("access menu by gnmkdm :[{}] session expire , redirect to uri :[{}]",gnmkdm,redirectUri);
					WebUtils.issueRedirect(WebUtils.toHttp(servletRequest), WebUtils.toHttp(servletResponse), redirectUri);
					return;
				}else{
					return;					
				}
			};
			
			RecentlyUsedMenu menu = new RecentlyUsedMenu();
			menu.setGnmkdm(gnmkdm);
			menu.setLastTime(new Date());
			
			if (info.get(user.getYhm()) != null){
				List<RecentlyUsedMenu> menuList = info.get(user.getYhm());
				addUsedMenu(menuList, menu);
				info.put(user.getYhm(), menuList);
			} else {
				List<RecentlyUsedMenu> menuList = new ArrayList<RecentlyUsedMenu>();
				menuList.add(menu);
				info.put(user.getYhm(), menuList);
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	
	private void addUsedMenu(List<RecentlyUsedMenu> list,RecentlyUsedMenu menu){
		boolean isUsed = false;
		for (RecentlyUsedMenu usedMenu : list){
			if (usedMenu.getGnmkdm().equals(menu.getGnmkdm())){
				usedMenu.setCount(usedMenu.getCount()+1);
				usedMenu.setLastTime(new Date());
				isUsed = true;
			}
		}
		
		if (!isUsed){
			list.add(menu);
		}
		//按最近使用时间排序 
		Collections.sort(list,new Comparator<RecentlyUsedMenu>() {
			public int compare(RecentlyUsedMenu o1, RecentlyUsedMenu o2) {
				return o2.getLastTime().compareTo(o1.getLastTime());
			}
		});
		
		if (list.size() > USED_MENU_MAX){
			list.remove(list.size()-1);
		}
		 
	}
	
}
