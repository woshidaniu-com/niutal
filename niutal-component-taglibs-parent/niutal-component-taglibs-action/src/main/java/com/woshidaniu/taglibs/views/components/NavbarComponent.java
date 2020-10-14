package com.woshidaniu.taglibs.views.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

/** strutsTag注解指明了该UIBean的名字和Tag类的类名 */
@StrutsTag(name = "navbar", tldTagClass = "com.woshidaniu.taglibs.views.tags.NavbarTag", description = "生成页面功能工具栏")
public class NavbarComponent extends AbstractTaglibsStrutsUIBean {
	
	protected static Logger LOG = LoggerFactory.getLogger(NavbarComponent.class);
	public NavbarComponent(ValueStack stack,
			HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "tag-navbar";
	}
	
	@Override
	public boolean start(Writer writer) {
		this.addParameter("func_list",  getFuncList());
		return false;
	}

}
