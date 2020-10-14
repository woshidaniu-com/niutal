package com.woshidaniu.designer.views.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.struts2.components.template.Template;
import org.apache.struts2.components.template.TemplateEngine;
import org.apache.struts2.components.template.TemplateRenderingContext;
import org.apache.struts2.views.annotations.StrutsTag;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BooleanUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.yuicompressor.YUICompressUtils;
/**strutsTag注解指明了该UIBean的名字和Tag类的类名*/
@StrutsTag(name="func-javascript", 
		tldTagClass="com.woshidaniu.designer.views.tags.FuncJavascriptTag", 
		description="根据功能设置自动生成js脚本标记")
public class FuncJavascript extends AbstractDesignStrutsUIBean {
	
	/**
	 * 是否静态化
	 */
	protected String staticable;
	
	public FuncJavascript(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }
	
	public boolean isStaticable(){
		return BooleanUtils.parse(getStaticable());
	}
	
	public String getStaticable() {
		return staticable;
	}
	
	@StrutsTagAttribute(description="set staticable", type="String")
	public void setStaticable(String staticable) {
		this.staticable = staticable;
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		if(isStaticable() && null != getFuncModel() && "1".equals(getFuncModel().getFunc_release())){
			return "func-javascript-file";
		}
		return "func-javascript-text";
	}
	
	/**
	 * 重写模板处理逻辑
	 */
	@Override
	protected void mergeTemplate(Writer writer, Template template) throws Exception {
		//生成静态js文件
        File jsFile = getJsFile("min");
        if(jsFile.exists()){
        	jsFile.delete();
        }
		//判断是否静态化:使用缓存则生成js文件
		if(isStaticable() && null != getFuncModel() && "1".equals(getFuncModel().getFunc_release()) ){
			
        	final TemplateEngine engine = templateEngineManager.getTemplateEngine(template, templateSuffix);
            if (engine == null) {
                throw new ConfigurationException("Unable to find a TemplateEngine for template " + template);
            }

            if (LOG.isDebugEnabled()) {
                LOG.debug("Rendering template " + template);
            }
        	 //创建目标输出缓存writer
	        StringBuilderWriter builderWriter = new StringBuilderWriter();
	        //执行目标处理逻辑
	        final TemplateRenderingContext context = new TemplateRenderingContext(template, builderWriter, getStack(), getParameters(), this);
	        //渲染模板
	        engine.renderTemplate(context);
	        try {
	    		Writer	out = new OutputStreamWriter(new FileOutputStream(jsFile),"utf-8");
	    		//压缩输出js文件
		        YUICompressUtils.compressJs(builderWriter.toString(), out);
			} catch (Exception e) {
				if (LOG.isDebugEnabled()) {
					LOG.warn(" js文件 ["+jsFile.getAbsolutePath()+"] 输出失败 !",e);
				}
			} finally {
				IOUtils.closeQuietly(builderWriter);
			}
	        
        }else{
        	
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
    }

	@Override
	public boolean start(Writer writer) {
		//功能信息
		this.addParameter("funcModel", getFuncModel());
		this.addParameter("funcReport", getReportModel());
		this.addParameter("button_list",  getFuncButtonList());
		this.addParameter("func_element_list", getStack().findValue(getStackKey("func_element_list")));
		return false;
	}
	
}
