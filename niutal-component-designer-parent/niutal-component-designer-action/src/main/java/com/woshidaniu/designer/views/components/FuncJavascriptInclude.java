package com.woshidaniu.designer.views.components;

import java.io.File;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.util.base.MessageUtil;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-javascript-include", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncJavascriptIncludeTag", 
		description="根据功能设置自动引入生成的js脚本文件")
public class FuncJavascriptInclude extends AbstractDesignStrutsUIBean {
	
	
	public FuncJavascriptInclude(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-javascript-include";
	}
	
	@Override
	public boolean start(Writer writer) {
		DesignFuncModel funcModel = getFuncModel();
		if(!BlankUtils.isBlank(funcModel) && "1".equals(getFuncModel().getFunc_release())){
			this.addParameter("jsVersion", StringUtils.getSafeStr(funcModel.getRelease_time(), MessageUtil.getText("niutal.jsVersion")) );
			this.addParameter("cssVersion", StringUtils.getSafeStr(funcModel.getRelease_time(), MessageUtil.getText("niutal.cssVersion")) );
			//生成静态js文件
	        File jsFile = getJsFile("min");
	        if(jsFile.exists()){
	        	this.addParameter("jsName", jsFile.getName());
	        }
		}
		return false;
	}
	
}
