package com.woshidaniu.designer.views.components;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.ArrayUtils;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.beanutils.reflection.ReflectionUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.common.global.GlobalXtszx;
import com.woshidaniu.common.log.User;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.designer.dao.entities.DesignZdybdFlszModel;
import com.woshidaniu.designer.dao.entities.DesignZdybdZddyModel;
import com.woshidaniu.designer.service.svcinterface.IDesignZdybdService;
import com.woshidaniu.designer.utils.ElementFlszPredicate;
import com.woshidaniu.designer.utils.ElementZddyComparator;
import com.woshidaniu.designer.utils.ElementZddyPredicate;
import com.woshidaniu.service.utils.FixedStringUtils;
import com.woshidaniu.struts2.provider.ITagDataProvider;
import com.woshidaniu.struts2.provider.TagDataPair;
import com.woshidaniu.web.WebContext;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-fields", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncFieldsPanelTag", 
		description="根据自定义功能数据自动生成页面body内容")
@SuppressWarnings("unchecked")
public class FuncFieldsPanel extends AbstractDesignStrutsUIBean {
	
	// 数据提供者对象名称：用于从Spring上下文获取该对象实例
 	protected String provider;
 	// 数据提供者实例
    protected ITagDataProvider dataProvider;
    
	//功能类型 1:增加;2:修改;3:查看
	protected String func_type;
	//显示模式   1：横排页签 ，2：竖排伸缩
	protected String display_mode;
	//对象值栈取值key
	protected String stackKey;
	//是否开启人员统一注册接口
	protected String sfkqrytyzchk;
	//姓名,性别,民族,出生日期,出生地,证件类型,证件号码,籍贯
	protected String[] strArr = {"xh","xbm","mzm","csrq","csd","zjlxm","zjhm","jg"};
	
	protected IDesignZdybdService designZdybdService;
	protected List<DesignZdybdFlszModel> func_flsz_list  = null;
	protected List<DesignZdybdZddyModel> func_zddy_list  = null;
	
	
	public FuncFieldsPanel(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
        //从spring容器获取service
        designZdybdService = (IDesignZdybdService) (designZdybdService == null ? ServiceFactory.getService("designZdybdService") : designZdybdService);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-fields";
	}
	
	/**
	 * 添加扩展参数
	 */
	protected void evaluateExtraParams() {
		//调用父级方法初始化参数:如果使用父级引用对象，一定要执行此代码
		super.evaluateExtraParams();
        if (null != func_type) {
        	this.setFunc_code(findString(func_type));
        	this.addParameter("func_type", getFunc_type());
        }
        if (null != display_mode) {
        	this.setOpt_code(findString(display_mode));
        	this.addParameter("display_mode", getDisplay_mode());
        }
	}

	protected Map<String, Object> parseMap(String params ) {
		if(!BlankUtils.isBlank(params)){
			return JSONObject.parseObject(params, HashMap.class);
		}
		return null;
	}
	
	/**
	 * 
	 *@描述：进行数据预处理
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7上午10:37:17
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected void processData() {
		//判断页面元素关联字段是否存在，则进一步处理组件数据
		if(!BlankUtils.isBlank(func_zddy_list)){
			/*
			 * 1.固定值，格式为：1:男,2:女 
			 * 2:数据库取值,“表名:代码,名称”,
			 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型；
			 */
			String szlx = null;
			String szz = null;
			String zd = null;
			String zdlx = null;
			String kdbl = null;
			String listKey = "key";
			String listValue = "value";
			StringBuilder yzlxBuilder = null;
			
			//当前页面元素个字段值所属对象：对象可能是map或者model对象
			Object stack_obj = null;
			if(!BlankUtils.isBlank(getStackKey())){
				//从struts上下文取值
				stack_obj = super.findValue(getStackKey());
			}


	    	//标签数据提供者实例
	    	this.dataProvider = applicationContext.getBean(provider, ITagDataProvider.class);
	    	
			//循环查询条件
			for (DesignZdybdZddyModel fieldModel : func_zddy_list) {
				
				szz 	= fieldModel.getSzz();
				zdlx 	= fieldModel.getZdlx();
				zd	 	= fieldModel.getZd();
				
				//验证规则字符拼接
				yzlxBuilder = new StringBuilder();
				if(  "1".equals(getFunc_type()) && "1".equalsIgnoreCase(sfkqrytyzchk) && !BlankUtils.isBlank(zd) && ArrayUtils.contains(strArr,zd)){
					fieldModel.setSfbt("1");
				}
				//必填
				if("1".equals(fieldModel.getSfbt()) ){
					
					yzlxBuilder.append("validate='{required:true");
					if(!BlankUtils.isBlank(fieldModel.getYzlx())){
						yzlxBuilder.append(",").append(fieldModel.getYzlx()).append("}'");
					}else{
						yzlxBuilder.append("}'");
					}
				}else if(!BlankUtils.isBlank(fieldModel.getYzlx())){
					yzlxBuilder.append("validate='{").append(fieldModel.getYzlx()).append("}'");
				}
				fieldModel.setYzlx_temp(yzlxBuilder.toString());
				
				
				
				kdbl 	= fieldModel.getKdbl();
				if (!BlankUtils.isBlank(kdbl)) {
					String[] kdblArr = kdbl.split(",");
					fieldModel.setKdbl1(kdblArr[0]);
					fieldModel.setKdbl2(kdblArr[1]);
					fieldModel.setKdbl3(kdblArr[2]);
					fieldModel.setKdbl4(kdblArr[3]);
				}else{
					fieldModel.setKdbl1("4");
					fieldModel.setKdbl2("6");
					fieldModel.setKdbl3("4");
					fieldModel.setKdbl4("8");
				}
				
				szlx 	= fieldModel.getSzlx();
				if(!BlankUtils.isBlank(szlx)){
					listKey = "key";
					listValue = "value";
					//字段值对象
					Object field_obj = null;
					if (szlx.trim().equals("1") || szlx.trim().equals("10")) {
						//解析固定值集合
						field_obj =  FixedStringUtils.getFixedList(szz,":");
					} else if (szlx.trim().equals("2") || szlx.trim().equals("20")) {
						//解析出SQL语句
						String sql = FixedStringUtils.getQuerySQL(szz);
						//根据SQL查询数据
						field_obj =  designFuncElementQueryService.getFieldDataList(sql);
					} else if (szlx.trim().equals("4") || szlx.trim().equals("40")) {
						//根据基础数据类型查询数据
						field_obj = jcsjService.getJcsjList(szz);
						listKey = "dm";
						listValue = "mc";
					} else if (szlx.trim().equals("5") || szlx.trim().equals("50")) {
						TagDataPair tagData = this.dataProvider.getBindData(this, szz);
						if(tagData != null){
							field_obj = tagData.getListData();
							listKey = tagData.getListKey();
							listValue = tagData.getListValue();
						}
					}
					/*
					 * 字段类型 
					 * 0:仅显示 
					 * 1:隐藏域
					 * 2:下拉框 
					 * 3:单选框 
					 * 4:复选框 
					 * 5:文本域 
					 * 6:文件 
					 * 11:字符文本框 
					 * 13:日期文本框
					 * 21:照片
					 * 22:省市县选择 
					 * 23:链接 
					 * 24;学院专业班级 
					 * 99:功能自定义
					 */
					if(field_obj != null && ("2".equals(zdlx) || "3".equals(zdlx)|| "4".equals(zdlx))){
						//判断是否集合类型对象
						if(!Collection.class.isAssignableFrom(field_obj.getClass())){
							field_obj = Arrays.asList(ObjectUtils.toObjectArray(field_obj));
						}
						List<PairModel> field_data_list = null;
						//是固定值时
						if (szlx.trim().equals("1") || szlx.trim().equals("10")) {
							field_data_list = (List<PairModel>) field_obj;
						}else{
							field_data_list = new ArrayList<PairModel>();
							String key,value = null;
							for (Object fieldObj : ((List)field_obj)) {
								if(fieldObj == null){
									continue;
								}
								try {
									//如果是map直接取值
									if(fieldObj instanceof Map){
										key 	= StringUtils.getSafeStr(((Map)fieldObj).get(listKey)) ;
										value 	= StringUtils.getSafeStr(((Map)fieldObj).get(listValue)) ;
									}else{
										key 	= StringUtils.getSafeStr(ReflectionUtils.getField(listKey , fieldObj)) ;
										value 	= StringUtils.getSafeStr(ReflectionUtils.getField(listValue ,fieldObj)) ;
									}
									//添加一个option数据
									field_data_list.add(new PairModel(key,value));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						fieldModel.setData_list(field_data_list);
					}
				}
				
				if(!BlankUtils.isBlank(zd) && !BlankUtils.isBlank(zdlx) && !("98".equals(zdlx) || "99".equals(zdlx))){
					//取到了值
					if (stack_obj != null && !BlankUtils.isBlank(zd) ) {
						try {
							//如果是map直接取值
							if(stack_obj instanceof Map){
								fieldModel.setBdszz(StringUtils.getSafeStr(((Map)stack_obj).get(zd)));
							}else{
								fieldModel.setBdszz(StringUtils.getSafeStr(ReflectionUtils.getField(zd , stack_obj)));
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				//功能类型 1:增加;2:修改;3:查看
				if("1".equals(getFunc_type()) || "2".equals(getFunc_type())){
					if("1".equals(String.valueOf(fieldModel.getSfkg()).trim()) || "1".equals(String.valueOf(fieldModel.getSfsh()).trim())){
						fieldModel.setSfkbj("1");
					}else{
						fieldModel.setSfkbj("0");
					}
				}else{
					fieldModel.setSfkbj("0");
				}
			}
		}
	}
	
	/**
	 * 
	 *@描述：进行数据组装
	 *@创建人:kangzhidong
	 *@创建时间:2015-5-7上午10:37:31
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 */
	protected void process() {
		//如果页面已经自定义过元素
		if(!BlankUtils.isBlank(func_flsz_list)){
			
			ElementFlszPredicate firstPredicate = new ElementFlszPredicate();
			ElementZddyPredicate secondPredicate = new ElementZddyPredicate();
			ElementZddyComparator comparator = new ElementZddyComparator();
			//循环页面元素
			for (final DesignZdybdFlszModel flsz_element : func_flsz_list) {
				//判断页面元素关联字段是否存在，则进一步处理组件数据
				if(!BlankUtils.isBlank(func_zddy_list)){
					firstPredicate.setFlsz_element(flsz_element);
					//迭代页面元素关联字段信息
					List<DesignZdybdZddyModel>  element_zddy_list = CollectionUtils.findAll(func_zddy_list, firstPredicate);
					//自定义集合不为空
					if(!BlankUtils.isBlank(element_zddy_list)){
						
						for (final DesignZdybdZddyModel zddy_element : element_zddy_list) {
							
							secondPredicate.setZddy_element(zddy_element);
							
							//迭代页面元素关联字段信息
							List<DesignZdybdZddyModel>  sub_zddy_list = CollectionUtils.findAll(func_zddy_list,secondPredicate);
							
							if(!BlankUtils.isBlank(sub_zddy_list)){
								Collections.sort(sub_zddy_list, comparator);
								zddy_element.setZddy_list(sub_zddy_list);
							}
						}
						
						Collections.sort(element_zddy_list, comparator);
						flsz_element.setElement_zddy_list(element_zddy_list);
					}
					 
				}
			}
		}
	}
	 
	/**
	 * 	因为代码ComponentTagSupport.doStartTag 中有
	 *  boolean evalBody = component.start(pageContext.getOut());
     *     if (evalBody) {
     *         return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
     *     } else {
     *        return SKIP_BODY;
     *    }
	 */
	@Override
	public boolean start(Writer writer) {
		//判断逻辑
		if(!BlankUtils.isBlank(designZdybdService)){
			//增加功能
			if("1".equals(getFunc_type())){
				//是否开启人员统一注册接口
				sfkqrytyzchk = commonSqlService.cxXtszxz(GlobalXtszx.SFKQRYTYZCJK);
			}
			
			User user = WebContext.getUser();
			//判断是否使用缓存
			if(isCacheable()){
				//尝试从缓存中获取缓存的文件对象
				if( cacheStart ){
					
					Cache cache = this.getCache();
					
					String autoKey0 = getCacheKey("func-zdybd-flsz");
					ValueWrapper valueWrapper = cache.get(autoKey0);
					if( valueWrapper != null){
						func_flsz_list = (List<DesignZdybdFlszModel>) valueWrapper.get();
					}
					if(BlankUtils.isBlank(func_flsz_list)){
						//缓存过期重新查询
						func_flsz_list = designZdybdService.getFlszListByGndm(getFunc_code(),getFunc_type());
						//缓存
						cache.put(autoKey0, func_flsz_list);
					}
					
					String autoKey1 = getCacheKey("func-zdybd-zddy");
					valueWrapper = cache.get(autoKey1);
					if( valueWrapper != null){
						func_zddy_list = (List<DesignZdybdZddyModel>) valueWrapper.get();
					}
					if(BlankUtils.isBlank(func_zddy_list)){
						//缓存过期重新查询
						func_zddy_list = designZdybdService.getZdyzdListByGndm(getFunc_code(),getFunc_type(), user.getJsdm());
						//缓存
						cache.put(autoKey1, func_zddy_list);
					}
					
				}else{
					func_flsz_list = designZdybdService.getFlszListByGndm(getFunc_code(),getFunc_type());
					func_zddy_list = designZdybdService.getZdyzdListByGndm(getFunc_code(),getFunc_type(), user.getJsdm());
				}
			}else{
				func_flsz_list = designZdybdService.getFlszListByGndm(getFunc_code(),getFunc_type());
				func_zddy_list = designZdybdService.getZdyzdListByGndm(getFunc_code(),getFunc_type(), user.getJsdm());
			}
			//对数据进一步处理
			this.processData();
			this.process();
			this.addParameter("func_flsz_list", func_flsz_list);
			this.addParameter("user_jsdm", user.getJsdm());
		}
		return false;
	}

	/**
	 * @return the func_type
	 */
	public String getFunc_type() {
		return func_type;
	}

	/**
	 * 设置UIBean的属性，@StrutsTagAttribute(description="set func_type", type="String")注解，说明该属性是字符串（也可以是其它），这一步很重要
	 */
    @StrutsTagAttribute(description="set func_type", type="String")
	public void setFunc_type(String funcType) {
		func_type = funcType;
	}

	/**
	 * @return the display_mode
	 */
	public String getDisplay_mode() {
		return display_mode;
	}

	/**
	 * 设置UIBean的属性，@StrutsTagAttribute(description="set display_mode", type="String")注解，说明该属性是字符串（也可以是其它），这一步很重要
	 */
    @StrutsTagAttribute(description="set display_mode", type="String")
	public void setDisplay_mode(String displayMode) {
		display_mode = displayMode;
	}

	/**
	 * @return the stackKey
	 */
	public String getStackKey() {
		return stackKey;
	}

	/**
	 * 设置UIBean的属性，@StrutsTagAttribute(description="set stackKey", type="String")注解，说明该属性是字符串（也可以是其它），这一步很重要
	 */
    @StrutsTagAttribute(description="set stackKey", type="String")
	public void setStackKey(String stackKey) {
		this.stackKey = stackKey;
	}

    @StrutsTagAttribute(description="data provider ", required=false, defaultValue="String")
  	public void setProvider(String provider) {
      	this.provider = (provider == null ? "fieldDataProvider" : provider);
  	}
    
}
