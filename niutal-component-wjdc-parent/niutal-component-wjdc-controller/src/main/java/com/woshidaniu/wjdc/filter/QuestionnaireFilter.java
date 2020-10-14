/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.filter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.log.User;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.WebContext;
import com.woshidaniu.wjdc.dao.entites.WjglModel;
import com.woshidaniu.wjdc.dao.entites.YwgnModel;
import com.woshidaniu.wjdc.service.svcinterface.IWjglService;
import com.woshidaniu.wjdc.service.svcinterface.IYwgnService;

/**
 * @author 康康（1571）
 * 
 * 
 * 当在正式环境时,建议使用httpSession缓存,减少查询数据库的时间,降低数据库压力,提高响应速度,如下配置项所示：
 * niutal.wjdc.filterUseSessonCacheStoreGnlj=true
 * niutal.wjdc.filterUseSessonCacheStoreUnFinishWjList=true
 * 
 * 
 * 当在开发环境时,建议不使用httpSession缓存,快速配置需要的问卷和检测拦截生效，避免重复登录浪费开发时间,如下配置项所示：
 * niutal.wjdc.filterUseSessonCacheStoreGnlj=false
 * niutal.wjdc.filterUseSessonCacheStoreUnFinishWjList=false
 * 
 * 
 * 当不需要拦截问卷调查,可配置禁用此拦截器,如下配置项所示：
 * niutal.wjdc.filterEnable=false
 * 
 * 
 * 拦截生效依赖的基础表:
 * niutal_WJDC_YWGNB(问卷调查-业务功能表)
 * niutal_WJDC_YWGNBDB(问卷业务绑定表)
 */
public class QuestionnaireFilter implements Filter {

	protected static final Logger log = LoggerFactory.getLogger(QuestionnaireFilter.class);
	//缓存登录的一个用户的当时的问卷调查功能路径
	protected static String YWGN_SESSION_KEY = QuestionnaireFilter.class.getName()+"_ywgn_session_key";
	//是否使用session缓存功能路径配置
	private static final String FILTER_USE_SESSION_CACHE_STORE_GNLJ_KEY = "niutal.wjdc.filterUseSessonCacheStoreGnlj";
	//是否使用session缓存功能路径和是否缓存已经不需要回答问题
	private static final String FILTER_USE_SESSION_CACHE_STORE_UNFINISH_WJLIST_KEY = "niutal.wjdc.filterUseSessonCacheStoreUnFinishWjList";
	//没有完成的问卷列表的session的key
	public static final String UN_FINISH_WJLIST_SESSION_KEY = QuestionnaireFilter.class.getName()+"_"+FILTER_USE_SESSION_CACHE_STORE_UNFINISH_WJLIST_KEY;
	//是否启用此filter
	private static final String FILTER_ENABLE_KEY = "niutal.wjdc.filterEnable";
	// 是否启用此过滤器
	protected boolean enable = true;
	//是否使用session缓存
	protected boolean useSessionCacheStoreGnlj = true;
	//是否使用session缓存没有完成的问卷列表
	protected boolean useSessionCacheStoreUnFinishWjList = true;
	
	//汇总信息记录-start
	protected AtomicInteger totalFilterCount = new AtomicInteger(0);
	protected AtomicInteger totalHitCount = new AtomicInteger(0);
	protected AtomicInteger totalDatabaseQueryCount = new AtomicInteger(0);
	//汇总信息记录-end
	
	/**
	 * @description	： 初始化当前问卷调查过滤器配置
	 * @param config
	 * @throws ServletException
	 */
	@Override
	public void init(FilterConfig config) throws ServletException {
		{
			//是否启用
			String val = MessageUtil.getText(FILTER_ENABLE_KEY);
			if("false".equals(val)) {
				this.enable = false;
			}
			log.info("是否启用问卷调查过滤器,配置key=[{}],value=[{}],是否启用:[{}]",FILTER_ENABLE_KEY,val,this.enable);
		}
		
		{
			//是否缓存功能路径到session
			String val = MessageUtil.getText(FILTER_USE_SESSION_CACHE_STORE_GNLJ_KEY);
			if("false".equals(val)) {
				this.useSessionCacheStoreGnlj = false;
			}
			log.info("是否启用在session内缓存问卷调查功能路径配置,配置key=[{}],value=[{}],是否启用:[{}]",FILTER_USE_SESSION_CACHE_STORE_GNLJ_KEY,val,this.useSessionCacheStoreGnlj);
		}
		
		{
			//是否缓存没有完成的问卷列表到session
			String val = MessageUtil.getText(FILTER_USE_SESSION_CACHE_STORE_UNFINISH_WJLIST_KEY);
			if("false".equals(val)) {
				this.useSessionCacheStoreUnFinishWjList = false;
			}
			log.info("是否启用在session内缓存没有完成的问卷调查列表,配置key=[{}],value=[{}],是否启用:[{}]",FILTER_USE_SESSION_CACHE_STORE_UNFINISH_WJLIST_KEY,val,this.useSessionCacheStoreUnFinishWjList);
		}
	}
	
	@Override
	public void destroy() {
		//展示汇总信息
		log.info("问卷调查过滤器拦截汇总,totalFilterCount[{}],totalHitCount[{}],totalDatabaseQueryCount[{}]",this.totalFilterCount,this.totalHitCount,this.totalDatabaseQueryCount);
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		this.totalFilterCount.incrementAndGet();
		
		User user = WebContext.getUser();
		if (user != null && enable) {

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			try {
				this.doFilterProcess(req, resp, user);
			} catch (ServletException e) {
				throw e;
			} catch (IOException e) {
				throw e;
			}catch (Exception e){
				//避免影响其他过滤器调用和controller等组件的使用
				log.error("用户答卷过滤器处理异常", e);
			}
		}
		chain.doFilter(request, response);
	}
	/**
	 * @description ： 处理
	 * @param req
	 * @param resp
	 * @param user
	 * @throws IOException
	 * @throws ServletException 
	 * @throws Exception
	 */
	protected void doFilterProcess(HttpServletRequest req, HttpServletResponse resp, User user)	throws IOException, ServletException {

		String requestUrl = req.getRequestURI();
		String userid = user.getYhm();

		List<YwgnModel> ywgnModels = null;
		if(this.useSessionCacheStoreGnlj) {
			ywgnModels = this.getYwgnModelsFromSession(req, resp, user);
			if(CollectionUtils.isEmpty(ywgnModels)) {
				return;
			}
		}else {
			ywgnModels = this.getYwgnModels();
			
			//展示当前数据库配置
			if(CollectionUtils.isEmpty(ywgnModels)) {
				log.debug("当前数据库未配置任何拦击功能路径");
				return;
			}else {
				for(int i=0;i<ywgnModels.size();i++) {
					YwgnModel ywgnModel = ywgnModels.get(i);
					log.debug("当前数据库配置拦击功能路径:{}",ywgnModel);	
				}
			}
		}
		
		// 是否包含当前请求路径
		for (YwgnModel ywgnModel : ywgnModels) {
			// 需要处理的路径
			String gnlj = ywgnModel.getGnlj();
			
			if (requestUrl.equals(gnlj) || requestUrl.contains(gnlj)) {
				
				List<WjglModel> wjglModelList = this.getUnfinishedWjList(userid,req);
				
				if (CollectionUtils.isNotEmpty(wjglModelList)) {//取第一个未回答的问卷,此问卷优先级数字最低，优先级最高
					WjglModel wjglModel = wjglModelList.get(0);
					this.totalHitCount.incrementAndGet();
					log.debug("用户[{}]跳转回答问卷,ID[{}],名称[{}],问卷优先级[{}],被拦截路径[{}]", user.getYhm(),wjglModel.getWjid(),wjglModel.getWjmc(), wjglModel.getWjyxj(),requestUrl);							
					// 跳转答卷
					req.setAttribute("url", requestUrl);
					req.getRequestDispatcher("/wjdc/wjgl/yhdj.zf?wjid=" + wjglModel.getWjid()).forward(req, resp);
					return;
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private List<WjglModel> getUnfinishedWjList(String userid,HttpServletRequest req) {
		if(this.useSessionCacheStoreUnFinishWjList) {
			HttpSession session = req.getSession();
			Object maybeExistList = session.getAttribute(UN_FINISH_WJLIST_SESSION_KEY);
			if(maybeExistList == null) {
				this.totalDatabaseQueryCount.incrementAndGet();
				IWjglService wjglService = ServiceFactory.getService(IWjglService.class);
				List<WjglModel> wjglModelList = wjglService.getUnfinshedWjList(userid);
				session.setAttribute(UN_FINISH_WJLIST_SESSION_KEY, wjglModelList);
				return wjglModelList;
			}else {
				List<WjglModel> wjglModelList = (List<WjglModel>)maybeExistList;
				return wjglModelList;
			}
		}else {
			this.totalDatabaseQueryCount.incrementAndGet();
			IWjglService wjglService = ServiceFactory.getService(IWjglService.class);
			List<WjglModel> wjglModelList = wjglService.getUnfinshedWjList(userid);
			return wjglModelList;
		}
	}
	
	/**
	 * @description	： 查询获得所有具有试题关联的功能配置列表
	 * @return
	 */
	protected List<YwgnModel> getYwgnModels() {
		this.totalDatabaseQueryCount.incrementAndGet();
		IYwgnService ywgnService = ServiceFactory.getService(IYwgnService.class);
		List<YwgnModel> ywgnModels = ywgnService.getAllYwgnList();
		return ywgnModels;
	}
	/**
	 * @description	： 查询获得所有具有试题关联的功能配置列表
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected List<YwgnModel> getYwgnModelsFromSession(HttpServletRequest req, HttpServletResponse resp, User user) {
		HttpSession session = req.getSession();
		List<YwgnModel> ywgnModels = (List<YwgnModel>) session.getAttribute(YWGN_SESSION_KEY);
		if(ywgnModels == null) {
			ywgnModels = this.getYwgnModels();
			session.setAttribute(YWGN_SESSION_KEY,ywgnModels);
		}
		return ywgnModels;
	}
}
