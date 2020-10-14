package com.woshidaniu.designer.views.components;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.common.factory.ServiceFactory;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignWidgetResourceModel;
import com.woshidaniu.designer.service.svcinterface.IDesignWidgetResourceService;
import com.woshidaniu.util.base.MessageUtil;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-resources", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncResourcesTag", 
		description="根据条件自动生成引入资源代码标记")
public class FuncResources extends AbstractDesignStrutsUIBean {
	
	protected IDesignWidgetResourceService designWidgetResourceService;
	protected List<DesignWidgetResourceModel> widget_resource_list;
	public FuncResources(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
        //从spring容器获取service
		designWidgetResourceService = (IDesignWidgetResourceService) (designWidgetResourceService == null ? ServiceFactory.getService("designWidgetResourceService") : designWidgetResourceService);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-resources";
	}

	@Override
	public boolean start(Writer writer) {
		//调用父级方法
		super.start(writer); 
		DesignFuncModel funcModel = getFuncModel();
		if(!BlankUtils.isBlank(funcModel)){
			//功能信息
			this.addParameter("funcModel", funcModel);
			this.addParameter("jsVersion", StringUtils.getSafeStr(funcModel.getRelease_time(), MessageUtil.getText("niutal.jsVersion")) );
			this.addParameter("cssVersion", StringUtils.getSafeStr(funcModel.getRelease_time(), MessageUtil.getText("niutal.cssVersion")) );
			
			//判断逻辑
			if(!BlankUtils.isBlank(func_code) && !BlankUtils.isBlank(designWidgetResourceService)){
				//判断是否使用缓存
				if(isCacheable()){
					//尝试从缓存中获取缓存的文件对象
					if( cacheStart ){
						
						Cache cache = this.getCache();
						
						String autoKey1 = getCacheKey("widget_resource_list");
						ValueWrapper valueWrapper = cache.get(autoKey1);
						if( valueWrapper != null){
							widget_resource_list = (List<DesignWidgetResourceModel>) valueWrapper.get();
						}
						if(BlankUtils.isBlank(widget_resource_list)){
							//缓存过期重新查询
							widget_resource_list = designWidgetResourceService.getFuncWidgetResourceList(funcModel);
							//缓存
							cache.put(autoKey1, widget_resource_list);
						}
					}else{
						widget_resource_list = designWidgetResourceService.getFuncWidgetResourceList(funcModel);
					}
					
				}else{
					widget_resource_list = designWidgetResourceService.getFuncWidgetResourceList(funcModel);
				}
			}
		}
		this.addParameter("widget_resource_list", widget_resource_list);
		//自定义js,css资源信息
		this.addParameter("file_resource_list", getFuncFileResources());
		return false;
	}
	
}
