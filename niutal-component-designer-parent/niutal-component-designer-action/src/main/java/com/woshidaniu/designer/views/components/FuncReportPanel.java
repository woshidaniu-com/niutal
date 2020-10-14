package com.woshidaniu.designer.views.components;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.Predicate;
import org.apache.struts2.views.annotations.StrutsTag;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.CollectionUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.designer.dao.entities.DesignFuncElementFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryFieldModel;
import com.woshidaniu.designer.dao.entities.DesignFuncElementQueryModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementFieldService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementQueryService;
import com.woshidaniu.designer.service.svcinterface.IDesignFuncElementService;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-report", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncReportPanelTag", 
		description="根据条件自动生成查询条件区域（普通查询/高级查询）标记")
@SuppressWarnings("unchecked")
public class FuncReportPanel extends AbstractDesignStrutsUIBean {
	
	protected IDesignFuncElementService designFuncElementService;
	protected IDesignFuncElementQueryService designFuncElementQueryService;
	protected IDesignFuncElementFieldService designFuncElementFieldService;
	protected List<DesignToolbarButtonModel> report_button_list = null;
	protected List<DesignFuncElementModel> func_element_list = null;
	protected List<DesignFuncElementQueryModel> func_element_query_list  = null;
	protected List<DesignFuncElementFieldModel> func_element_field_list = null;
	
	public FuncReportPanel(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
        //从spring容器获取service
        designFuncElementService = (IDesignFuncElementService) (designFuncElementService == null ? ServiceFactory.getService("designFuncElementService") : designFuncElementService);
        designFuncElementQueryService  = (IDesignFuncElementQueryService) (designFuncElementQueryService == null ? ServiceFactory.getService("designFuncElementQueryService") : designFuncElementQueryService);
        designFuncElementFieldService = (IDesignFuncElementFieldService) (designFuncElementFieldService == null ? ServiceFactory.getService("designFuncElementFieldService") : designFuncElementFieldService);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-report";
	}
	
	/**
	 * 添加扩展参数
	 */
	protected void evaluateExtraParams() {
		//调用父级方法初始化参数:如果使用父级引用对象，一定要执行此代码
		super.evaluateExtraParams();
		//初始化自定义功能的，用户功能操作权限
		evaluateSession();
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
		if(!BlankUtils.isBlank(func_element_query_list)){
			//循环查询条件
			for (DesignFuncElementQueryModel element_query : func_element_query_list) {
				if( !BlankUtils.isBlank(element_query.getQuery_field_list())){
					//获取列信息
					int colmun = Integer.parseInt(StringUtils.getSafeStr(element_query.getQuery_column(), "3"));
					//循环自定义字段信息
					for (DesignFuncElementQueryFieldModel fieldModel : element_query.getQuery_field_list()) {
						//初始化字段值
						initFieldModel(colmun,fieldModel);
					}
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
		if(!BlankUtils.isBlank(func_element_list)){
			//循环页面元素
			for (final DesignFuncElementModel func_element : func_element_list) {
				
				func_element.setMax_ordinal(String.valueOf(func_element_list.size() + 1));
				
				//判断页面元素关联查询是否存在，则进一步处理组件数据
				if(!BlankUtils.isBlank(func_element_query_list)){
					//迭代页面元素关联查询信息
					for (Iterator<DesignFuncElementQueryModel> iterator = func_element_query_list.iterator(); iterator.hasNext();) {
						DesignFuncElementQueryModel element_query =  iterator.next();
						//匹配元素关联字段信息
						if( func_element.getElement_guid().equals(element_query.getElement_guid())){
							//设置匹配的关联字段
							func_element.setElement_query(element_query);
							func_element_query_list.remove(element_query);
							break;
						}
					}
				}
				
				//判断页面元素关联字段是否存在，则进一步处理组件数据
				if(!BlankUtils.isBlank(func_element_field_list)){
					//迭代页面元素关联字段信息
					List<DesignFuncElementFieldModel>  element_field_list = CollectionUtils.findAll(func_element_field_list, new Predicate(){

						public boolean evaluate(Object object) {
							DesignFuncElementFieldModel element_field =  (DesignFuncElementFieldModel)object;
							//匹配元素关联字段信息
							if( func_element.getElement_guid().equals(element_field.getElement_guid())){
								//设置匹配的关联字段
								return true;
							}
							return false;
						}
					});
					func_element.setElement_field_list(element_field_list);
				}
				//循环页面元素;匹配关联元素
				for (DesignFuncElementModel func_element_relate : func_element_list) {
					if(func_element.getElement_related_guid()!=null && func_element.getElement_related_guid().equals(func_element_relate.getElement_guid())){
						func_element_relate.setElement_related(func_element);
						func_element.setElement_related(func_element_relate);
						break;
					}
				}
			}
			//将当前功能元素放到栈中
			getStack().set(getStackKey("func_element_list"), func_element_list);
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
		DesignFuncModel funcModel = getFuncModel();
		if(BlankUtils.isBlank(funcModel)){
			return false;
		}
		//功能信息
		this.addParameter("funcModel", funcModel);
		this.addParameter("funcReport", getReportModel());
		this.addParameter("requestMap", getStack().findValue("requestMap"));
		
		//判断逻辑
		if(!BlankUtils.isBlank(funcModel) ){
			
			if(!BlankUtils.isBlank(designToolbarButtonService)){
				report_button_list =  designToolbarButtonService.getReportButtonList(getFunc_code());
				this.addParameter("report_button_list", report_button_list);
			}
			//判断逻辑
			if( !BlankUtils.isBlank(designFuncElementService)){
				//判断是否使用缓存
				if(isCacheable()){
					//尝试从缓存中获取缓存的文件对象
					if( cacheStart ){
						
						Cache cache = this.getCache();
						
						String autoKey0 = getCacheKey("func-elements");
						ValueWrapper valueWrapper = cache.get(autoKey0);
						if( valueWrapper != null){
							func_element_list = (List<DesignFuncElementModel>) valueWrapper.get();
						}
						if(BlankUtils.isBlank(func_element_list)){
							//缓存过期重新查询
							func_element_list = designFuncElementService.getFuncElementList(getFunc_code(), getOpt_code());
							//缓存
							cache.put(autoKey0, func_element_list);
						}
						
						String autoKey1 = getCacheKey("func-element-querys");
						valueWrapper = cache.get(autoKey1);
						if( valueWrapper != null){
							func_element_query_list = (List<DesignFuncElementQueryModel>) valueWrapper.get();
						}
						if(BlankUtils.isBlank(func_element_query_list)){
							//缓存过期重新查询
							func_element_query_list = designFuncElementQueryService.getFuncElementQueryList(getFunc_code(), getOpt_code());
							//缓存
							cache.put(autoKey1, func_element_query_list);
						}
						
						String autoKey2 = getCacheKey("func-element-fields");
						valueWrapper = cache.get(autoKey2);
						if( valueWrapper != null){
							func_element_field_list = (List<DesignFuncElementFieldModel>) valueWrapper.get();
						}
						if(BlankUtils.isBlank(func_element_field_list)){
							//缓存过期重新查询
							func_element_field_list = designFuncElementFieldService.getFuncElementFieldList(getFunc_code(), getOpt_code());
							//缓存
							cache.put(autoKey2, func_element_field_list);
						}
						 
					}else{
						func_element_list = designFuncElementService.getFuncElementList(getFunc_code(), getOpt_code());
						func_element_query_list = designFuncElementQueryService.getFuncElementQueryList(getFunc_code(), getOpt_code());
						func_element_field_list = designFuncElementFieldService.getFuncElementFieldList(getFunc_code(), getOpt_code());
					}
				}else{
					func_element_list = designFuncElementService.getFuncElementList(getFunc_code(), getOpt_code());
					func_element_query_list = designFuncElementQueryService.getFuncElementQueryList(getFunc_code(), getOpt_code());
					func_element_field_list = designFuncElementFieldService.getFuncElementFieldList(getFunc_code(), getOpt_code());
				}
				//对数据进一步处理
				this.processData();
				this.process();
				this.addParameter("func_element_list", func_element_list);
			}
		}
		
		return false;
	}

}
