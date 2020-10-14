package com.woshidaniu.designer.views.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.basicutils.BlankUtils;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-styles", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncStylesTag", 
		description="根据条件自动生成css样式")
public class FuncStyles extends AbstractDesignStrutsUIBean {
	
	public FuncStyles(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-styles";
	}

	@Override
	public boolean start(Writer writer) {
		//调用父级方法
		super.start(writer); 
		DesignFuncModel funcModel = getFuncModel();
		if(!BlankUtils.isBlank(funcModel)){
			//功能信息
			this.addParameter("funcModel", funcModel);
			//自定义js,css资源信息
			this.addParameter("file_resource_list", getFuncFileResources());
		}
		return false;
	}
	
}
