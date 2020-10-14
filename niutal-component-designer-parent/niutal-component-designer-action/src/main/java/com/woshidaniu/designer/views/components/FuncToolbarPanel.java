package com.woshidaniu.designer.views.components;

import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.components.template.Template;
import org.apache.struts2.components.template.TemplateEngine;
import org.apache.struts2.components.template.TemplateRenderingContext;
import org.apache.struts2.views.annotations.StrutsTag;

import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.designer.dao.entities.DesignFuncMenuButtonModel;
import com.woshidaniu.designer.dao.entities.DesignFuncModel;
import com.woshidaniu.designer.dao.entities.DesignToolbarButtonModel;
import com.woshidaniu.basicutils.BlankUtils;

/** strutsTag注解指明了该UIBean的名字和Tag类的类名 */
@StrutsTag(name = "func-toolbar", 
		tldTagClass = "com.woshidaniu.designer.views.tags.FuncToolbarPanelTag", 
		description = "生成按钮根据条标签")
public class FuncToolbarPanel extends AbstractDesignStrutsUIBean {
	
	
	protected List<DesignToolbarButtonModel> button_list = null;
	protected List<DesignFuncMenuButtonModel> button_func_list = null;
	
	public FuncToolbarPanel(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
		//从spring容器获取service
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "func-toolbar";
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
	
	protected void process() {
		
		//如果功能有功能按钮
		if(!BlankUtils.isBlank(button_list) && !BlankUtils.isBlank(button_func_list)){
			//循环功能按钮
			for (DesignToolbarButtonModel btn_element : button_list) {
				//循环功能按钮绑定信息，进行匹配
				for (DesignFuncMenuButtonModel btn_func: button_func_list) {
					//匹配元素关联字段信息
					if( btn_func.getFunc_code().equals(btn_element.getFunc_code()) && btn_func.getOpt_code().equals(btn_element.getOpt_code())){
						//设置匹配的关联字段
						btn_element.setBindFunc(btn_func);
						button_func_list.remove(btn_func);
						break;
					}
				}
			}
		}
	}
	
	protected void mergeTemplate(Writer writer, Template template) throws Exception {
        final TemplateEngine engine = templateEngineManager.getTemplateEngine(template, templateSuffix);
        if (engine == null) {
            throw new ConfigurationException("Unable to find a TemplateEngine for template " + template);
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("Rendering template " + template);
        }

        final TemplateRenderingContext context = new TemplateRenderingContext(template, writer, getStack(), getParameters(), this);
        engine.renderTemplate(context);
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
		//调用父级方法
		super.start(writer); 
		DesignFuncModel funcModel = getFuncModel();
		if(!BlankUtils.isBlank(funcModel)){
			//功能信息
			this.addParameter("funcModel", funcModel);
			button_list = getFuncButtonList();
			if(!BlankUtils.isBlank(button_list) && !BlankUtils.isBlank(designFuncService)){
				button_func_list = designFuncService.getFuncOptLinkList(getFunc_code());
			}
			this.process();
			this.addParameter("button_list", button_list);
		}
		return false;
	}

}
