package com.woshidaniu.designer.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.Predicate;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.stereotype.Service;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.aop.annotations.After;
import com.woshidaniu.common.aop.annotations.Comment;
import com.woshidaniu.common.aop.annotations.Logger;
import com.woshidaniu.common.constant.GlobalCacheKeyConstant;
import com.woshidaniu.common.exception.BusinessRuntimeException;
import com.woshidaniu.common.query.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.daointerface.ICommonQueryDao;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncDao;
import com.woshidaniu.designer.dao.daointerface.IDesignFuncSQLScriptDao;
import com.woshidaniu.designer.dao.daointerface.IDesignReportDao;
import com.woshidaniu.designer.dao.daointerface.IDesignWidgetResourceDao;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignReportModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncService;
import com.woshidaniu.designer.utils.FuncCacheKeyUtils;
import com.woshidaniu.designer.utils.FuncMenuUtils;
import com.woshidaniu.designer.utils.FuncPageUtils;
import com.woshidaniu.util.local.LocaleUtils;
import com.woshidaniu.web.WebContext;

/**
 * 
 *@类名称: DesignFuncServiceImpl.java
 *@类描述：功能页面:功能设计操作SERVICE接口实现：为指定功能代码+操作代码维护操作功能数据
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:41:06
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@After
@Logger(model = "N03", business = "N0320")
@Service("designFuncService")
public class DesignFuncServiceImpl extends BaseServiceImpl<DesignFuncModel, IDesignFuncDao> implements IDesignFuncService {

	@Resource
	protected IDesignFuncSQLScriptDao sqlScriptDao;
	@Resource
	protected IDesignWidgetResourceDao resourceDao;
	@Resource
	protected IDesignReportDao reportDao;
	@Resource
	protected IDesignFuncDao dao;
	@Resource
	protected ICommonQueryDao queryDao;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		super.setDao(dao);
	}
	
	@Override
	public DesignFuncModel getFuncModel(String func_code, String opt_code) {
		return dao.getFuncModel(func_code, opt_code);
	}

	@Override
	public List<PairModel> getTopFuncList() {
		return dao.getTopFuncList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public DesignFuncMenuModel getFuncMenuModel(String func_code) {
		DesignFuncMenuModel funcMenuModel = dao.getSystemFuncModel(func_code);
		if(!BlankUtils.isBlank(funcMenuModel)){
			// 查询所有功能模块代码，操作模块代码
			List<DesignFuncMenuModel> funcMenuList = dao.getSystemFuncList(funcMenuModel.getFunc_code());
			// 用户所有功能操作代码列表
			List<DesignFuncMenuButtonModel> funcBtnList = null;
			//尝试从缓存中获取缓存的文件对象
			if( isCacheSupport() ){
				Cache cache = this.getCache();
				ValueWrapper valueWrapper = cache.get(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
				if( valueWrapper != null){
					funcBtnList =  (List<DesignFuncMenuButtonModel>) valueWrapper.get();
				}else{
					//缓存过期重新查询
					funcBtnList = dao.getSystemFuncOptList(LocaleUtils.getLocaleKey());
					//缓存一周
					cache.put(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY, funcBtnList);
				}
			}else{
				funcBtnList = dao.getSystemFuncOptList(LocaleUtils.getLocaleKey());
			}
			// 从一级功能菜单开始初始化各级别功能模块
			FuncMenuUtils.initMenuList(funcMenuModel, funcMenuList, funcBtnList);
		}
		/*
		 * jsgnmkModel.setJsdm(model.getJsdm()); //从一级功能菜单开始初始化各级别功能模块
		 * initJsgnmkList
		 * (gnmkczList,gnmkdmJbList,model.getSqrJsdm(),jsgnmkModel,2,max);
		 */
		// 去除无用属性
		funcMenuModel.setQueryList(null);
		funcMenuModel.setQueryModel(null);
		funcMenuModel.setTotalResult(null);
		return funcMenuModel;
	}

	@Override
	public DesignFuncMenuModel getFuncMenuModel(DesignFuncMenuModel model) {
		return dao.getFuncMenuModel(model);
	}
	
	@Override
	public DesignFuncMenuModel getSystemFuncModel(String func_code){
		return dao.getSystemFuncModel(func_code);
	}
	
	@Override
	@Comment
	public boolean insertMenu(DesignFuncMenuModel model) {
		model.setUser(WebContext.getUser());
		//增加自定义菜单信息
		dao.insertMenu(model);
		//清除缓存:这里是为了防止，刚删除的功能操作代码+操作代码对应的自定义功能再次被创建时遗留上次的缓存数据
		clearCache(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean insertFuncMenu(DesignFuncMenuModel model) {
		try{
			model.setUser(WebContext.getUser());
			model.setFunc_guid(getQueryDao().getSysGuid());
			model.setQuery_type(StringUtils.getSafeStr(model.getQuery_type(), "0"));
			model.setFunc_target("all".equalsIgnoreCase(model.getFunc_target())?"":model.getFunc_target());
			if(!"0".equals(model.getQuery_type())){
				model.setFunc_element_guid(getQueryDao().getSysGuid());
			}
			if("1".equals(model.getFunc_jqgridable())){
				model.setFunc_element_guid2(getQueryDao().getSysGuid());
			}
			
			model.setFunc_url("/design/viewFunc_cxDesignFuncPageIndex.html");
			//增加自定义功能对应功能菜单信息
			dao.insertFuncMenu(model);
			//构建自定义功能页面
			FuncPageUtils.buildFuncPage(model);
			//清除缓存:这里是为了防止，刚删除的功能操作代码+操作代码对应的自定义功能再次被创建时遗留上次的缓存数据
			clearCache(model);
			return true;
		} catch (Exception e) {
			throw new BusinessRuntimeException(e);
		}
	}

	@Override
	@Comment
	public boolean deleteFuncMenu(DesignFuncMenuModel model) {
		//查询要移除的自定义功能集合
		List<DesignFuncModel> removeList = dao.getModelList(model);
		//删除自定义功能对应功能菜单信息
		dao.deleteFuncMenu(model);
		if(!BlankUtils.isBlank(removeList)){
			for (DesignFuncModel removeFunc : removeList) {
				//删除自定义功能页面
				FuncPageUtils.deleteFuncPage(removeFunc);
				//清除缓存
				clearCache(removeFunc);
			}
		}
		return true;
	}

	@Override
	@Comment
	public boolean updateMenu(DesignFuncMenuModel model) {
		//更新自定义菜单信息
		dao.updateMenu(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean updateFuncMenu(DesignFuncMenuModel model) {
		try{
			model.setFunc_target("all".equalsIgnoreCase(model.getFunc_target())?"":model.getFunc_target());
			model.setFunc_url("/design/viewFunc_cxDesignFuncPageIndex.html");
			model.setQuery_type(StringUtils.getSafeStr(model.getQuery_type(), "0"));
			if(!"0".equals(model.getQuery_type()) && "1".equals(model.getFunc_type())){
				model.setFunc_element_guid(getQueryDao().getSysGuid());
			}
			//更新自定义功能对应功能菜单信息
			dao.updateFuncMenu(model);
			//清除缓存
			clearCache(model);
			//构建自定义功能页面
			FuncPageUtils.buildFuncPage(model);
			return true;
		} catch (Exception e) {
			throw new BusinessRuntimeException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DesignFuncMenuButtonModel> getSystemFuncOptList(final String func_code){
		// 用户所有功能操作代码列表
		List<DesignFuncMenuButtonModel> funcBtnList = null;
		//尝试从缓存中获取缓存的文件对象
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			ValueWrapper valueWrapper = cache.get(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
			if( valueWrapper != null){
				funcBtnList = CollectionUtils.findAll((List<DesignFuncMenuButtonModel>) valueWrapper.get(), new Predicate(){
					@Override
					public boolean evaluate(Object object) {
						DesignFuncMenuButtonModel buttonModel = (DesignFuncMenuButtonModel)object;
						return buttonModel != null && buttonModel.getFunc_code().equals(func_code);
					}
				});
			}else{
				//缓存过期重新查询
				funcBtnList = dao.getSystemFuncOptMoldelList(LocaleUtils.getLocaleKey(),func_code);
			}
		}else{
			funcBtnList = dao.getSystemFuncOptMoldelList(LocaleUtils.getLocaleKey(),func_code);
		}
		return funcBtnList;
	}

	@Override
	public DesignFuncMenuButtonModel getSystemFuncOptModel(String func_code,String opt_code){
		return dao.getSystemFuncOptModel(LocaleUtils.getLocaleKey(),func_code,opt_code);
	}
	
	@Override
	public DesignFuncMenuButtonModel getFuncOptModel(DesignFuncMenuButtonModel model) {
		return dao.getFuncOptModel(model);
	}
	
	@Override
	@Comment
	public boolean insertFuncOpt(DesignFuncMenuButtonModel model) {
		//增加自定义功能对应【操作按钮】信息
		model.setUser(WebContext.getUser());
		model.setFunc_code(model.getParent_func_code());
		dao.insertFuncOpt(model);
		//尝试从缓存中获取缓存的文件对象
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			cache.evict(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
		}
		return true;
	}

	@Override
	@Comment
	public boolean deleteFuncOpt(DesignFuncMenuButtonModel model) {
		//删除自定义功能对应【操作按钮】信息
		dao.deleteFuncOpt(model);
		//删除自定义功能页面
		FuncPageUtils.deleteFuncPage(model);
		//尝试从缓存中获取缓存的文件对象
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			cache.evict(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
		}
		return true;
	}

	@Override
	@Comment
	public boolean updateFuncOpt(DesignFuncMenuButtonModel model) {
		//更新自定义功能对应【操作按钮】信息
		dao.updateFuncOpt(model);
		//尝试从缓存中获取缓存的文件对象
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			cache.evict(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
		}
		return true;
	}
	
	@Override
	public DesignFuncMenuButtonModel getFuncOptLinkModel(DesignFuncMenuButtonModel model) {
		return dao.getFuncOptLinkModel(model);
	}
	
	@Override
	public List<DesignFuncMenuButtonModel> getFuncOptLinkList(String func_code){
		return dao.getFuncOptLinkList(func_code);
	}
	
	@Override
	@Comment
	public boolean insertFuncOptLink(DesignFuncMenuButtonModel model){
		try{
			model.setUser(WebContext.getUser());
			model.setFunc_guid(getQueryDao().getSysGuid());
			model.setQuery_type(StringUtils.getSafeStr(model.getQuery_type(), "0"));
			//增加【操作按钮】对应自定义功能信息
			dao.insertFuncOptLink(model);
			//系统自定义功能类型；数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'预览打印',4:'快速打印',5:'数据导出',6:'数据删除'
			if("1".equals(model.getFunc_type())||"2".equals(model.getFunc_type())||"3".equals(model.getFunc_type())){
				//构建自定义功能页面
				FuncPageUtils.buildFuncPage(model);
			}
			//清除缓存
			clearCache(model);
			return true;
		} catch (Exception e) {
			throw new BusinessRuntimeException(e);
		}
	}
	
	@Override
	@Comment
	public boolean updateFuncOptLink(DesignFuncMenuButtonModel model){
		model.setQuery_type(StringUtils.getSafeStr(model.getQuery_type(), "0"));
		//更新【操作按钮】对应自定义功能信息
		dao.updateFuncOptLink(model);
		//清除缓存
		clearCache(model);
		return true;
	}
	
	@Override
	@Comment
	public boolean deleteFuncOptLink(DesignFuncMenuButtonModel model){
		//删除【操作按钮】对应自定义功能信息
		dao.deleteFuncOptLink(model);
		//删除自定义功能页面
		FuncPageUtils.deleteFuncPage(model);
		//清除缓存
		clearCache(model);
		return true;
	}
	
	
	@Override
	public List<BaseMap> getOptList(){
		return dao.getOptList();
	}

	@Override
	public List<String> getFuncCodeListOfRole(String jsdm) {
		return dao.getFuncCodeListOfRole(jsdm);
	}

	@Override
	public File getSqlscriptFile(DesignFuncMenuButtonModel model) {
		try {
			Map<String, Object> rootMap = new HashMap<String, Object>();
			rootMap.put("func_code", model.getFunc_code());
			rootMap.put("opt_code", model.getOpt_code());
			rootMap.put("func_name", model.getFunc_name());
			//功能模块代码
			rootMap.put("func_menu_list", getSqlScriptDao().getFuncMenuList(model));
			//功能操作代码
			rootMap.put("func_btn_list", getSqlScriptDao().getFuncBtnList(model));
			//角色功能模块操作代码
			rootMap.put("func_jsMenu_list", getSqlScriptDao().getFuncRoleBtnList(model));
			//自定义功能菜单
			rootMap.put("func_list", getSqlScriptDao().getDesignFuncList(model));
			//自定义功能关联元素数据
			rootMap.put("func_element_list", getSqlScriptDao().getDesignFuncElementList(model));
			//元素关联查询条件数据
			rootMap.put("func_query_list", getSqlScriptDao().getDesignFuncQueryList(model));
			//元素关联查询条件字段数据
			List<BaseMap> func_query_field_list = getSqlScriptDao().getDesignFuncQueryFieldList(model);
			rootMap.put("func_query_field_list", func_query_field_list);
			if(!BlankUtils.isBlank(func_query_field_list)){
				rootMap.put("func_auto_query_field_list", getSqlScriptDao().getDesignFuncAutoQueryFieldList(model));
			}
			//自定义功能元素关联组件数据
			List<BaseMap> func_widget_list = getSqlScriptDao().getDesignFuncWidgetList(model);
			if(!BlankUtils.isBlank(func_widget_list)){
				for (BaseMap baseMap : func_widget_list) {
					if(baseMap!=null){
						String widgetSql = BlankUtils.isBlank(baseMap.get("widget_sql")) ? "" : baseMap.get("widget_sql").toString();
						if(widgetSql!=null && widgetSql.length() > 0 ){
							widgetSql = widgetSql.replaceAll("'", "''");
						}
						baseMap.put("widget_sql", widgetSql);
					}
				}
			}
			rootMap.put("func_widget_list",func_widget_list );
			//自定义功能元素关联JQGrid组件列数据
			rootMap.put("func_jqgrid_field_list", getSqlScriptDao().getDesignFuncGQGridFieldList(model));
			//元素关联字段数据
			List<BaseMap> func_field_list = getSqlScriptDao().getDesignFuncFieldList(model);
			rootMap.put("func_field_list", func_field_list);
			if(!BlankUtils.isBlank(func_field_list)){
				rootMap.put("func_auto_field_list", getSqlScriptDao().getDesignFuncAutoFieldList(model));
			}
			//自定义功能功能相关的js,css资源
			rootMap.put("func_resource_list", getSqlScriptDao().getDesignFuncResourceList(model));
			//自定义功能绑定的自定义报表
			rootMap.put("func_report_list", getSqlScriptDao().getDesignFuncReportList(model));
			
			File sqlscriptFile =  FuncPageUtils.buildFuncScript(model, rootMap);
			return sqlscriptFile;
		} catch (Exception e) {
			throw new BusinessRuntimeException(e);
		}
	}
	
	@Override
	@Comment
	public boolean updateFuncResource(DesignWidgetResourceModel resourceModel,List<DesignWidgetResourceModel> resourceList){
		DesignFuncModel funcModel = new DesignFuncModel();
		funcModel.setFunc_guid(resourceModel.getFunc_guid());
		//查询已有资源
		List<DesignWidgetResourceModel> oldList = resourceDao.getFuncResourceList(funcModel);
		//判断查询条件不为空
		if( !BlankUtils.isBlank(resourceList) && !BlankUtils.isBlank(oldList)){
			//删除移除的查询条件
			for (DesignWidgetResourceModel oldElement : oldList) {
				boolean isHas = false;
				for (DesignWidgetResourceModel newElement : resourceList) {
					newElement.setFunc_guid(resourceModel.getFunc_guid());
					if(!BlankUtils.isBlank(newElement.getResource_guid())  && oldElement.getResource_guid().equals(newElement.getResource_guid())){
						isHas = true;
						break;
					}
				}
				//历史查询条件被删除
				if(!isHas){
					resourceDao.delete(oldElement);
				}
			}
		}else if(!BlankUtils.isBlank(resourceList)){
			//没有查询条件设置,且有历史资源，则删除历史查询条件
			for (DesignWidgetResourceModel oldElement : oldList) {
				resourceDao.delete(oldElement);
			}
		}
		if(resourceModel!=null && (!BlankUtils.isBlank(resourceModel.getWidget_guid()) || !BlankUtils.isBlank(resourceModel.getResource_text())) ){
			resourceDao.insert(resourceModel);
		}
		return true;
	}

	protected void clearCache(DesignFuncModel removeFunc){
		//尝试从缓存中获取缓存的文件对象
		if( isCacheSupport() ){
			Cache cache = this.getCache();
			cache.evict(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
		}
		if(isCacheSupport() && !BlankUtils.isBlank(removeFunc)){
			Cache cache = this.getCache();
			//尝试从缓存中获取缓存的文件对象
			cache.evict(GlobalCacheKeyConstant.CZDM_LIST_CACHEKEY);
			cache.evict(FuncCacheKeyUtils.getCacheKey(removeFunc.getFunc_code(),removeFunc.getOpt_code(),"funcModel"));
			cache.evict(FuncCacheKeyUtils.getCacheKey(removeFunc.getFunc_code(),removeFunc.getOpt_code(),"funcReport"));
			cache.evict(FuncCacheKeyUtils.getCacheKey(removeFunc.getFunc_code(),removeFunc.getOpt_code(),"file_resource_list"));
		}
	}
	
	@Override
	public DesignReportModel getFuncReportModel(String func_guid){
		return getReportDao().getFuncReportModel(func_guid);
	}
	
	public IDesignFuncSQLScriptDao getSqlScriptDao() {
		return sqlScriptDao;
	}

	public void setSqlScriptDao(IDesignFuncSQLScriptDao sqlScriptDao) {
		this.sqlScriptDao = sqlScriptDao;
	}

	public IDesignWidgetResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(IDesignWidgetResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	public IDesignReportDao getReportDao() {
		return reportDao;
	}

	public void setReportDao(IDesignReportDao reportDao) {
		this.reportDao = reportDao;
	}

	public ICommonQueryDao getQueryDao() {
		return queryDao;
	}

	public void setQueryDao(ICommonQueryDao queryDao) {
		this.queryDao = queryDao;
	}
	
	
}
