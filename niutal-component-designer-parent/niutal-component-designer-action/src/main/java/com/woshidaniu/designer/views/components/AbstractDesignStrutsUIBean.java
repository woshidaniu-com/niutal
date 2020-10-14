package com.woshidaniu.designer.views.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.plus.components.AbstractStrutsUIBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.cache.CacheName;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.factory.SessionFactory;
import com.woshidaniu.common.utils.WebUtils;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignReportModel;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementQueryService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncService;
import com.woshidaniu.designer.service.svcinterface.IDesignToolbarButtonService;
import com.woshidaniu.designer.service.svcinterface.IDesignWidgetResourceService;
import com.woshidaniu.designer.utils.FuncCacheKeyUtils;
import com.woshidaniu.service.common.ICommonSqlService;
import com.woshidaniu.service.svcinterface.IJcsjService;
import com.woshidaniu.service.utils.FixedStringUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.util.xml.BaseDataReader;

/**
 * 
 *@类名称:AbstractStrutsUIBean.java
 *@类描述：
 *@创建人：kangzhidong
 *@创建时间：2015-4-23 下午03:04:56
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
public abstract class AbstractDesignStrutsUIBean extends AbstractStrutsUIBean  {

	protected Logger LOG = LoggerFactory.getLogger(AbstractDesignStrutsUIBean.class);
	protected ApplicationContext applicationContext = null;
	protected CacheManager cacheManager;
	protected String cacheName;
	protected IDesignFuncService designFuncService;
	protected IDesignToolbarButtonService designToolbarButtonService;
	protected IDesignWidgetResourceService designWidgetResourceService;
	protected IDesignFuncElementQueryService designFuncElementQueryService;
	protected IJcsjService jcsjService;
	protected ICommonSqlService commonSqlService;
	protected boolean cacheStart = false;
	
	public AbstractDesignStrutsUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		
		this.applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getSession().getServletContext());
		//判断是否使用缓存
		if(isCacheable()){
			cacheManager = this.applicationContext.getBean(CacheManager.class);
			cacheStart = cacheManager!=null && cacheManager.getCache(getCacheName()) != null;
		}
		//从spring容器获取service
		designFuncService = (IDesignFuncService) (designFuncService == null ? ServiceFactory.getService("designFuncService") : designFuncService);
		jcsjService = (IJcsjService) (jcsjService == null ? ServiceFactory.getService("jcsjService") : jcsjService);
		commonSqlService = (ICommonSqlService) (commonSqlService == null ? ServiceFactory.getService("commonSqlService") : commonSqlService);
		
		designToolbarButtonService = (IDesignToolbarButtonService) (designToolbarButtonService == null ? ServiceFactory.getService("designToolbarButtonService") : designToolbarButtonService);
		designWidgetResourceService = (IDesignWidgetResourceService) (designWidgetResourceService == null ? ServiceFactory.getService("designWidgetResourceService") : designWidgetResourceService);
		designFuncElementQueryService  = (IDesignFuncElementQueryService) (designFuncElementQueryService == null ? ServiceFactory.getService("designFuncElementQueryService") : designFuncElementQueryService);
	}
	
	/**
	 * setDefaultUITheme()方法默认是将配置文件中 struts.ui.theme 对应的值作为默认值；这里我们重写该方法使其始终为指定的字符，
	 * 在调用getTheme()如果标签未设置theme属性，该指定值将会作为默认值
	 * @see org.apache.struts2.components.UIBean#getTheme()
	 */
	@Override
	public void setDefaultUITheme(String theme) {
        this.defaultUITheme = "design";
    }
	
	/**
	 * 
	 *@描述：功能代码+操作代码 查询 对应的功能设计信息
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-28上午11:32:46
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignFuncModel getFuncModel(){
		String stackKey = getStackKey("funcModel");
		Object tmp = getStack().findValue(stackKey);
		DesignFuncModel funcModel = tmp != null ? (DesignFuncModel) tmp: null;
		if(BlankUtils.isBlank(funcModel)){
			//判断逻辑
			if(!BlankUtils.isBlank(func_code) && !BlankUtils.isBlank(designFuncService)){
				//判断是否使用缓存
				if(isCacheable()){
					//尝试从缓存中获取缓存的文件对象
					if( cacheStart ){
						String autoKey1 = getCacheKey("funcModel");
						Cache cache = this.getCache();
						ValueWrapper valueWrapper = cache.get(autoKey1);
						if( valueWrapper != null){
							funcModel = (DesignFuncModel) valueWrapper.get();
						}
						if(BlankUtils.isBlank(funcModel)){
							//缓存过期重新查询
							funcModel = designFuncService.getFuncModel(getFunc_code(), getOpt_code());
							if(!BlankUtils.isBlank(funcModel)){
								cache.put(autoKey1, funcModel );
							}
						}
					}else{
						funcModel = designFuncService.getFuncModel(getFunc_code(), getOpt_code());
					}
				}else{
					funcModel = designFuncService.getFuncModel(getFunc_code(), getOpt_code());
				}
			}
			getStack().set(stackKey, funcModel);
		}
		if(!BlankUtils.isBlank(funcModel)){
			//funcModel.setFunc_editable(StringUtils.getSafeStr(stack.findString("func_editable"), "0"));
		}
		return funcModel;
	}
	
	/**
	 * 
	 *@描述：查询当前自定义功能绑定的自定义报表信息对象
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-4上午09:08:05
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	public DesignReportModel getReportModel(){
		String stackKey = getStackKey("funcReport");
		Object tmp = getStack().findValue(stackKey);
		DesignReportModel funcReport = tmp != null ? (DesignReportModel) tmp: null;
		if(BlankUtils.isBlank(funcReport)){
			DesignFuncModel funcModel = getFuncModel();
			//判断逻辑
			if(!BlankUtils.isBlank(funcModel)  && !BlankUtils.isBlank(designFuncService)){
				//判断是否使用缓存
				if(isCacheable()){
					//尝试从缓存中获取缓存的文件对象
					if( cacheStart ){
						String autoKey0 = getCacheKey("funcReport");
						Cache cache = this.getCache();
						ValueWrapper valueWrapper = cache.get(autoKey0);
						if( valueWrapper != null){
							funcReport = (DesignReportModel) valueWrapper.get();
						}
						if(BlankUtils.isBlank(funcReport)){
							//缓存过期重新查询
							funcReport = designFuncService.getFuncReportModel(funcModel.getFunc_guid());
							//缓存一天
							cache.put(autoKey0 , funcReport);
						}
					}else{
						funcReport = designFuncService.getFuncReportModel(funcModel.getFunc_guid());
					}
				}else{
					funcReport = designFuncService.getFuncReportModel(funcModel.getFunc_guid());
				}
				getStack().set(stackKey, funcReport);
			}
		}
		return funcReport;
	}
	
	/**
	 * 
	 *@描述：查询当前自定义功能的功能按钮
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-4下午02:47:05
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	@SuppressWarnings("unchecked")
	public List<DesignToolbarButtonModel> getFuncButtonList(){
		String stackKey = getStackKey("button_list");
		Object tmp = getStack().findValue(stackKey);
		List<DesignToolbarButtonModel> button_list = tmp != null ? (List<DesignToolbarButtonModel>) tmp: null;
		if(BlankUtils.isBlank(button_list)){
			if(!BlankUtils.isBlank(getFunc_code()) && !BlankUtils.isBlank(designToolbarButtonService)){
				button_list =  designToolbarButtonService.getToolbarButtonList(getFunc_code());
				getStack().set(stackKey, button_list);
			}
		}
		return button_list;
	}
	
	/**
	 * 
	 *@描述：查询当前功能自定义的js,css资源
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-1上午09:52:28
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	@SuppressWarnings("unchecked")
	public List<DesignWidgetResourceModel> getFuncFileResources(){
		String stackKey = getStackKey("file_resource_list");
		Object tmp = getStack().findValue(stackKey);
		List<DesignWidgetResourceModel> file_resource_list = tmp != null ? (List<DesignWidgetResourceModel>) tmp: null;
		if(BlankUtils.isBlank(file_resource_list)){
			DesignFuncModel funcModel = getFuncModel();
			//判断逻辑
			if(!BlankUtils.isBlank(funcModel)  && !BlankUtils.isBlank(designWidgetResourceService)){
				//判断是否使用缓存
				if(isCacheable()){
					//尝试从缓存中获取缓存的文件对象
					if( cacheStart ){
						String autoKey = getCacheKey("file_resource_list");
						Cache cache = this.getCache();
						ValueWrapper valueWrapper = cache.get(autoKey);
						if( valueWrapper != null){
							file_resource_list = (List<DesignWidgetResourceModel>) valueWrapper.get();
						}
						if(BlankUtils.isBlank(file_resource_list)){
							//缓存过期重新查询
							file_resource_list = designWidgetResourceService.getFuncFileResourceList(funcModel);
							//缓存一周
							cache.put(autoKey, file_resource_list);
						}
					}else{
						file_resource_list = designWidgetResourceService.getFuncFileResourceList(funcModel);
					}
					
				}else{
					file_resource_list = designWidgetResourceService.getFuncFileResourceList(funcModel);
				}
				getStack().set(stackKey, file_resource_list);
			}
		}
		return file_resource_list;
	}
	
	@SuppressWarnings("unchecked")
	protected void initFieldModel(int colmun,DesignFuncElementQueryFieldModel fieldModel) {
		
		if(fieldModel != null){
	  
			fieldModel.setField_class("1".equalsIgnoreCase(fieldModel.getField_chosen()) ? fieldModel.getField_class() + " chosen-select " : fieldModel.getField_class());
			//字段元素外层单元div占比
			fieldModel.setField_div_width(String.valueOf(Double.valueOf(Math.ceil(12/colmun)).intValue()));
			//字段标题占比
			String label_width = StringUtils.getSafeStr(fieldModel.getField_name_width(), "4");
			fieldModel.setField_name_width(label_width);
			//字段元素本身占比：field_element_width  = 12 - field_name_width
			fieldModel.setField_element_width(String.valueOf(12 - Integer.parseInt(label_width)));
			/**
			 * 字段值来源： 
			 * 程序设置 	：此时如果field_list值不为空且field_shape=2，需要开发者在进入页面前指定field_index对应的对象结果
			 * 数据库 		：格式如 SQL:查询SQL 例如 SQL:select id as key,name as value from table_name
			 * XML数据 	：格式如 XML:(baseData.xml)文件中的id 例如 XML:isValid Spring集合对象 ：格式如
			 * Spring	:文件中的id 例如 Spring:field_list 
			 * 基础数据		:基础数据类型  例如 Base:0009 
			 * 固定值 		：格式如 Fixed:固定值1,固定值2,...(多个用,隔开) 例如 Fixed:aaa,bbb,ccc 或 Fixed:1#aaa,2#bbb,3#ccc
			 */
			String field_source  = String.valueOf(fieldModel.getField_source());
			//字段数据取值索引
			String field_list = fieldModel.getField_list();
			//字段值对象
			Object field_obj = null;
			
			if(field_source.startsWith("APP:")){
				//从struts上下文取值
				field_obj = super.findValue(field_list);
			}else if(field_source.startsWith("SQL:")){
				if(!BlankUtils.isBlank(field_source)){
					//截取出SQL语句
					String sql = field_source.substring("SQL:".length());
					//根据SQL查询数据
					field_obj =  designFuncElementQueryService.getFieldDataList(sql);
				}
			}else if(field_source.startsWith("Base:")){
				if(!BlankUtils.isBlank(field_source)){
					//解析出基础数据类型
					String lxdm = field_source.substring("Base:".length());
					//根据基础数据类型查询数据
					field_obj =  jcsjService.getJcsjList(lxdm);
				}
			}else if(field_source.startsWith("XML:")){
				//截取出XML配置中标签ID
				String xmlID = field_source.substring("XML:".length());
				//获取XMl配置中对象集合
				field_obj = BaseDataReader.getCachedOptionList(StringUtils.getSafeStr(xmlID, field_list));
			}else if(field_source.startsWith("Spring:")){
				//截取出Spring配置中标签ID
				String beanID = field_source.substring("Spring:".length());
				//从Spring上下文中获取集合对象
				field_obj = ServiceFactory.getService(StringUtils.getSafeStr(beanID, field_list));
			}else if(field_source.startsWith("Fixed:")){
				//截取出固定值字符串中的内容部分
				String fixedStr = field_source.substring("Fixed:".length());
				//解析固定值集合
				field_obj = FixedStringUtils.getFixedList(fixedStr,":");
			}
			//字段展示形态;默认 1,可选值 ：1：select,2：input,3：textarea
			if(field_obj != null && "1".equalsIgnoreCase(fieldModel.getField_shape())){
				//判断是否集合类型对象
				if(!Collection.class.isAssignableFrom(field_obj.getClass())){
					field_obj = Arrays.asList(ObjectUtils.toObjectArray(field_obj));
				}
				List<PairModel> field_data_list = null;
				//不是固定值时
				if(field_source.startsWith("Fixed:")){
					field_data_list = (List<PairModel>) field_obj;
				}else{
					field_data_list = new ArrayList<PairModel>();
					String key,value = null;
					for (Object fieldObj : ((List)field_obj)) {
						if(fieldObj == null){
							continue;
						}
						//如果是map直接取值
						if(fieldObj instanceof Map){
							key 	= StringUtils.getSafeStr(((Map)fieldObj).get(fieldModel.getField_listKey())) ;
							value 	= StringUtils.getSafeStr(((Map)fieldObj).get(fieldModel.getField_listValue())) ;
						}else{
							key 	= StringUtils.getSafeStr(ReflectionUtils.getField(fieldModel.getField_listKey() , fieldObj)) ;
							value 	= StringUtils.getSafeStr(ReflectionUtils.getField(fieldModel.getField_listValue() , fieldObj)) ;
						}
						//添加一个option数据
						field_data_list.add(new PairModel(key,value));
					}
				}
				fieldModel.setField_data_list(field_data_list);
			}
		}
	}
	
	/**
	 * 覆写evaluateExtraParams（）方法，在UIBean初始化后会调用这个方法来初始化设定参数，
	 * 如addParameter方法，会在freemarker里的parameters里加入一个key value。
	 * 这里要注意findString，还有相关的findxxxx方法，它们是已经封装好了的解释ognl语法的工具，具体可看一下UIBean的api doc
	 */
	@Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();
		this.addParameter("jsVersion", MessageUtil.getText("niutal.jsVersion"));
		this.addParameter("cssVersion", MessageUtil.getText("niutal.cssVersion"));
    }
	
	/**
	 * 
	 *@描述：初始化自定义功能的，用户功能操作权限
	 *@创建人:kangzhidong
	 *@创建时间:2015-6-4下午02:56:15
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected void evaluateSession() {
		
		List<DesignToolbarButtonModel> button_list = getFuncButtonList();
		if(!BlankUtils.isBlank(button_list)){
			String actionPath =  null;
			List<String> czdmList  = new ArrayList<String>();
			for(int i=0;i<button_list.size();i++){
				DesignToolbarButtonModel _model  = button_list.get(i);
				czdmList.add(_model.getOpt_code());
				if(i==0){
					actionPath = StringUtils.getSafeStr(_model.getButton_href(),"");
					actionPath = actionPath.substring(0,actionPath.indexOf("_"));
				}
			}
			SessionFactory.getSession().setAttribute(WebUtils.getFuncSessionKey(actionPath, SessionFactory.getUser().getJsdm()), czdmList);
		}
	}
	
	/**
	 * 
	 *@描述：根据【 功能代码 + 操作代码 + 模板名称  + 后缀】生成MD5加密后的key
	 *@创建人:kangzhidong
	 *@创建时间:2015-4-28上午10:01:21
	 *@param suffix
	 *@return
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected String getCacheKey(String suffix) {
		return FuncCacheKeyUtils.getCacheKey(getFunc_code(), getOpt_code(), suffix);
	}
	
	@Override
	public String getStylePath() {
		//String stylePath = MessageUtil.getText("system.stylePath");
		//String reportPath = MessageUtil.getText("system.reportLocation");
		return MessageUtil.getText("system.stylePath");
	}

	public CacheManager getCacheManager() {
		return cacheManager;
	}
	
	public String getCacheName() {
		return cacheName == null ? CacheName.CACHE_TAGS : cacheName;
	}
	
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	
	public Cache getCache() {
		return getCacheManager().getCache(getCacheName());
	}
	
	
}
