package com.woshidaniu.designer.views.components;

import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.util.ValueStack;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-body", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncBodyPanelTag", 
		description="根据自定义功能数据自动生成页面body内容")
public class FuncBodyPanel extends FuncElementsPanel {
	
	public FuncBodyPanel(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-body";
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
		return super.start(writer);
	}
}
