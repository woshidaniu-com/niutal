package com.woshidaniu.web.filter.userBehavior;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.woshidaniu.cache.core.Cache;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.web.WebUtils;
import com.woshidaniu.web.servlet.filter.cache.CacheSupportedFilter;

/**
 * <p>
 *   <h3>niutal框架<h3>
 *   <br>说明：bootstrap table 用户行为记录过滤器,基于cookie.</br>
 *   <br>如果用户没有登录，忽略跳过。</br> 
 *   <br>更新缓存，并设置新的cookie到response中</br>
 *   <br></br>
 *	 <br>class：com.woshidaniu.web.filter.userBehavior.BootstrapTableInteractionFilter.java
 * <p>
 * @author <a href="#">xiaokang[1036]<a>
 * @version 2016年8月15日上午8:50:29
 */
public class BootstrapTableInteractionFilter extends CacheSupportedFilter{

	/**
	 * 查询的表名
	 */
	protected static final String TABLE_ID_NAME = "cookieIdTable";
	
	/**
	 * 是否是bstable查询标志
	 */
	protected static final String BS_TABLE_QUERY = "send_by_bootstrap_table";
	
	protected static final String BS_TABLE_COOKIE_QUERY_REQUEST = "cookie_query_by_bootstrap_table";
	
	protected static final String BS_TABLE_COOKIE_CACHE_REQUEST = "cookie_cache_by_bootstrap_table";
	
	/**
	 * 所选列
	 */
	protected static final String TABLE_COLUMNS_PATTERN = ".bs.table.columns";
	
	/**
	 * 每页条数
	 */
	protected static final String TABLE_PAGELIST_PATTERN = ".bs.table.pageList";
	
	/**
	 * 第几页
	 */
	protected static final String TABLE_PAGENUMNER_PATTERN = ".bs.table.pageNumber";
	
	/**
	 * 排序列
	 */
	protected static final String TABLE_SORTNAME_PATTERN = ".bs.table.sortName";
	
	/**
	 * 排序方式
	 */
	protected static final String TABLE_SORTORDER_PATTERN = ".bs.table.sortOrder";
	
	/**
	 * 搜索字符
	 */
	protected static final String TABLE_SEARCHTEXT_PATTERN = ".bs.table.searchText";
	
	/**
	 * Cookie 最大时间
	 */
	protected static int COOKIE_MAX_AGE = 24 * 60 * 60 * 30;
	
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = WebUtils.toHttp(request);
		HttpServletResponse httpResponse = WebUtils.toHttp(response);
		if(WebContext.getUser() == null){
			return false;
		}
		if(!isBsTableQuery(httpRequest)){
			return false;
		}
		if(isBsTableCookieCache(httpRequest)){
			doCache(httpRequest, httpResponse);
		}
		if(isBsTableCookieQuery(httpRequest)){
			doQueryCache(httpRequest, httpResponse);
		}
		return false;
	}

	protected boolean doCache(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
		String tableId = httpRequest.getParameter(TABLE_ID_NAME);
		if(tableId == null)
			return false;
		
		Cache<String, InteractionBehavior> cache = resolveCache();
		if(cache == null)
			return false;
		Cookie c1 = getCookie(tableId + TABLE_COLUMNS_PATTERN, httpRequest);
		Cookie c2 = getCookie(tableId + TABLE_PAGELIST_PATTERN, httpRequest);
		Cookie c3 = getCookie(tableId + TABLE_SORTNAME_PATTERN, httpRequest);
		Cookie c4 = getCookie(tableId + TABLE_SORTORDER_PATTERN, httpRequest);
		Cookie c5 = getCookie(tableId + TABLE_PAGENUMNER_PATTERN, httpRequest);
		Cookie c6 = getCookie(tableId + TABLE_SEARCHTEXT_PATTERN, httpRequest);
		String columns = c1 == null ? null : c1.getValue();
		String pageList = c2 == null ? null : c2.getValue();
		String sortName = c3 == null ? null : c3.getValue();
		String sortOrder = c4 == null ? null : c4.getValue();
		String pageNumber = c5 == null ? null : c5.getValue();
		String searchText = c6 == null ? null : c6.getValue();
		InteractionBehavior ib = new InteractionBehavior(columns, pageList, pageNumber, sortName, sortOrder, searchText);
		cache.put(WebContext.getUser().getYhm() + "_" + tableId, ib);
		return true;
	}
	
	
	/**
	 * 
	 * <p>方法说明：判断是否是bsTable的查询请求<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年10月14日下午4:38:41<p>
	 */
	protected boolean isBsTableQuery(HttpServletRequest request){
		return WebUtils.isTrue(request, BS_TABLE_QUERY) && WebUtils.isAjaxRequest(request);
	}
	
	/**
	 * 
	 * <p>方法说明：判断是否是bsTable的cookie信息查询请求<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年10月15日下午3:16:34<p>
	 */
	protected boolean isBsTableCookieQuery(HttpServletRequest request){
		return WebUtils.isTrue(request, BS_TABLE_COOKIE_QUERY_REQUEST) && WebUtils.isAjaxRequest(request);
	}
	
	/**
	 * 
	 * <p>方法说明：判断是否是bsTable的cookie信息缓存请求<p>
	 * <p>作者：a href="#">xiaokang[1036]<a><p>
	 * <p>时间：2016年10月15日下午3:16:34<p>
	 */
	protected boolean isBsTableCookieCache(HttpServletRequest request){
		return WebUtils.isTrue(request, BS_TABLE_COOKIE_CACHE_REQUEST) && WebUtils.isAjaxRequest(request);
	}
	
	protected Cache<String, InteractionBehavior> resolveCache(){
		if(getCacheManager() == null || getCacheName() == null){
			return null;
		}
		return getCacheManager().getCache(getCacheName());
	}
	
	protected boolean doQueryCache(HttpServletRequest httpRequest, HttpServletResponse httpResponse){
		String tableId = httpRequest.getParameter(TABLE_ID_NAME);
		if(tableId == null)
			return false;
		Cache<String, InteractionBehavior> cache = resolveCache();
		if(cache == null)
			return false;
		
		InteractionBehavior ib = cache.get(WebContext.getUser().getYhm() + "_" + tableId);
		JSONObject responseObj = new JSONObject();
		if(ib != null){
			Cookie columnCookie = createCookie(tableId + TABLE_COLUMNS_PATTERN, ib.columns,COOKIE_MAX_AGE);
			Cookie pageListCookie = createCookie(tableId + TABLE_PAGELIST_PATTERN, ib.pageList,COOKIE_MAX_AGE);
			Cookie sortNameCookie = createCookie(tableId + TABLE_SORTNAME_PATTERN, ib.sortName,COOKIE_MAX_AGE);
			Cookie sortOrderCookie = createCookie(tableId + TABLE_SORTORDER_PATTERN, ib.sortOrder,COOKIE_MAX_AGE);
			Cookie pageNumberCookie = createCookie(tableId + TABLE_PAGENUMNER_PATTERN, ib.pageNumber,COOKIE_MAX_AGE);
			Cookie searchTextCookie = createCookie(tableId + TABLE_SEARCHTEXT_PATTERN, ib.searchText,COOKIE_MAX_AGE);
			if(columnCookie != null){
				httpResponse.addCookie(columnCookie);
				responseObj.put(columnCookie.getName(), columnCookie.getValue());
			}
			if(pageListCookie != null){
				httpResponse.addCookie(pageListCookie);
				responseObj.put(pageListCookie.getName(), pageListCookie.getValue());
			}
			if(sortNameCookie != null){
				httpResponse.addCookie(sortNameCookie);	
				responseObj.put(sortNameCookie.getName(), sortNameCookie.getValue());
			}
			if(sortOrderCookie != null){
				httpResponse.addCookie(sortOrderCookie);
				responseObj.put(sortOrderCookie.getName(), sortOrderCookie.getValue());
			}
			if(pageNumberCookie != null){
				httpResponse.addCookie(pageNumberCookie);
				responseObj.put(pageNumberCookie.getName(), pageNumberCookie.getValue());
			}
			if(searchTextCookie != null){
				httpResponse.addCookie(searchTextCookie);
				responseObj.put(searchTextCookie.getName(), searchTextCookie.getValue());
			}
		}
		try {
			httpResponse.setContentType("");
			httpResponse.setCharacterEncoding("UTF-8");  
			httpResponse.setContentType("application/json; charset=utf-8"); 
			httpResponse.getWriter().write(responseObj.toString());
			httpResponse.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	
	protected Cookie createCookie(String cookieName, String cookieVal, int maxAge){
		if(cookieVal == null || "".equals(cookieName.trim())){
			return null;
		}
		Cookie cookie = new Cookie(cookieName, cookieVal);
		cookie.setMaxAge(maxAge);
		return cookie;
	}
	
	protected Cookie getCookie(String cookieName, HttpServletRequest httpRequest){
		Cookie[] cookies = httpRequest.getCookies();
		
		for (Cookie cookie : cookies) {
			if(cookie.getName().equalsIgnoreCase(cookieName))
				return cookie;
		}
		
		return null;
	}
	
	static class InteractionBehavior implements Serializable{
		private static final long serialVersionUID = -3874922054737541560L;
		String columns;
		String pageList;
		String pageNumber;
		String sortName;
		String sortOrder;
		String searchText;
		
		InteractionBehavior(String columns, 
							String pageList, 
							String pageNumber, 
							String sortName,
							String sortOrder, 
							String searchText) {
			this.columns = columns;
			this.pageList = pageList;
			this.pageNumber = pageNumber;
			this.sortName = sortName;
			this.sortOrder = sortOrder;
			this.searchText = searchText;
		}
	}
	
}
