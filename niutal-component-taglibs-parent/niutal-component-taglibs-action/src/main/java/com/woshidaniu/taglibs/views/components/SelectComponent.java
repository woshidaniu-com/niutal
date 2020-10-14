package com.woshidaniu.taglibs.views.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

/** strutsTag注解指明了该UIBean的名字和Tag类的类名 */
@StrutsTag(name = "select", tldTagClass = "com.woshidaniu.taglibs.views.tags.SelectTag", description = "根据指定参数生成统一的下拉框")
public class SelectComponent extends AbstractDatasetStrutsUIBean {
	
	protected static Logger LOG = LoggerFactory.getLogger(SelectComponent.class);
		
	public SelectComponent(ValueStack stack,
			HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "tag-select";
	}
	
	@Override
	public boolean start(Writer writer) {
		//调用父级方法
		super.start(writer);
		return false;
	}

}
