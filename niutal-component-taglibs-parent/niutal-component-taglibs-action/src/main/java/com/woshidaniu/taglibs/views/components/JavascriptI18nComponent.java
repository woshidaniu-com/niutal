package com.woshidaniu.taglibs.views.components;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.struts2.StrutsException;
import org.apache.struts2.components.template.Template;
import org.apache.struts2.components.template.TemplateEngine;
import org.apache.struts2.components.template.TemplateRenderingContext;
import org.apache.struts2.views.annotations.StrutsTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.enhanced.utils.MessageSourceHolder;

import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.entities.PairModel;
import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.io.utils.IOUtils;
import com.woshidaniu.struts2.utils.LocaleUtils;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.yuicompressor.YUICompressUtils;

/** strutsTag注解指明了该UIBean的名字和Tag类的类名 */
@StrutsTag(name = "js-i18n", tldTagClass = "com.woshidaniu.taglibs.views.tags.JavascriptI18nTag", description = "根据参数自动生成指定js的国际化引用信息")
public class JavascriptI18nComponent extends AbstractTaglibsStrutsUIBean {
	
	protected static Logger LOG = LoggerFactory.getLogger(JavascriptI18nComponent.class);
	// 原js文件url,相对于web根目录的路径
	protected String fileURL;
	// 原js文件是否是v5样式
	protected boolean vstyle;
	// 指定进行国际化的key
	protected String i18nKeys;

	public JavascriptI18nComponent(ValueStack stack,
			HttpServletRequest request, HttpServletResponse response) {
		super(stack, request, response);
	}

	/**
	 * getDefaultTemplate()方法用于返回模板的名字，Struts2会自动在后面加入.ftl扩展名以找到特定的模板文件
	 */
	@Override
	protected String getDefaultTemplate() {
		return "tag-i18n-js";
	}

	
	@Override
	public boolean start(Writer writer) {
		//调用父级方法
		super.start(writer);
		// 路径构建对象
		StringBuilder hrefBulder = new StringBuilder();
		//文件路径，可能是网络路径
  		String fullPath = FilenameUtils.getFullPath(getFileURL());
  		//文件名称
  		String fileName = FilenameUtils.getBaseName(getFileURL());
  		//文件后缀
  		String fullExtension = FilenameUtils.getFullExtension(getFileURL());
  		//基础路径
  		hrefBulder.append(fullPath).append(fileName);
  		//当前local对应的国际化js路径
    	hrefBulder.append("_").append(LocaleUtils.getLocaleKey()).append(fullExtension);
    	//国际化文件对应路径
    	String i18nURL = hrefBulder.toString();
    	
    	this.addParameter("localeKey", LocaleUtils.getLocaleKey());
    	
    	//如果是v5样式一样不进行处理
    	if(isVstyle()){
    		if(i18nURL.startsWith("/")){
        		i18nURL = i18nURL.substring(1);
        	}
    		this.addParameter("v5URL", i18nURL);
    		return false;	
    	}
    	// 文件路径
		File jsFile = new File(webRootDir, getFileURL());
		if(jsFile.exists()){
			// 当前服务内部文件
			if (!BlankUtils.isBlank(getI18nKeys())) {
				// 文件路径
				File i18nFile = new File(webRootDir, i18nURL);
				if (!i18nFile.exists()) {
					// 渲染国际化js
					try {
						// 自定义国际化key
						String[] keys = StringUtils.tokenizeToStringArray(getI18nKeys());
						List<PairModel> list = new ArrayList<PairModel>();
						for (String key : keys) {
							String message = LocalizedTextUtil.findText(this.getClass(), key, LocaleUtils.getLocale(),"", new Object[] {}, getStack());
							if (message == null || key.equalsIgnoreCase(message)) {
								message = MessageSourceHolder.getMessage(key);
							}
							list.add(new PairModel(key, message));
						}
						this.addParameter("pairList", list);
						Template templatex = buildTemplateName(template, "tag-i18n-script");
						TemplateEngine engine = templateEngineManager.getTemplateEngine(templatex, templateSuffix);
						if (engine == null) {
							throw new ConfigurationException(
									"Unable to find a TemplateEngine for template "
											+ template);
						}
						if (LOG.isDebugEnabled()) {
							LOG.debug("Rendering template tag-i18n-script");
						}
						// 创建目标输出缓存writer
						StringBuilderWriter builderWriter = new StringBuilderWriter();
						// 执行目标处理逻辑
						TemplateRenderingContext context = new TemplateRenderingContext(templatex, builderWriter, getStack(), getParameters(), this);
						// 渲染模板
						engine.renderTemplate(context);
						Writer out = null;
						try {
							out = new OutputStreamWriter(new FileOutputStream(i18nFile), "utf-8");
							// 压缩输出js文件
							YUICompressUtils.compressJs(builderWriter.toString(), out);
						} catch (Exception e) {
							if (LOG.isDebugEnabled()) {
								LOG.warn(" js国际化文件 [" + i18nFile.getAbsolutePath()
										+ "] 输出失败 !", e);
							}
						} finally {
							IOUtils.closeQuietly(builderWriter);
							IOUtils.closeQuietly(out);
						}
					} catch (Exception e) {
						throw new StrutsException(e);
					}
				}
			}
	    	if(i18nURL.startsWith("/")){
	    		i18nURL = i18nURL.substring(1);
	    	}
	    	this.addParameter("i18nURL", i18nURL);
	    	return false;
		}
		this.addParameter("jsURL", i18nURL);
		return false;
	}

	@Override
	public String getStylePath() {
		//String stylePath = MessageUtil.getText("system.stylePath");
		//String reportPath = MessageUtil.getText("system.reportLocation");
		return MessageUtil.getText("system.stylePath");
	}
	
	public String getFileURL() {
		return fileURL;
	}

	public void setFileURL(String fileURL) {
		this.fileURL = fileURL;
	}

	public boolean isVstyle() {
		return vstyle;
	}

	public void setVstyle(boolean vstyle) {
		this.vstyle = vstyle;
	}

	public String getI18nKeys() {
		return i18nKeys;
	}

	public void setI18nKeys(String i18nKeys) {
		this.i18nKeys = i18nKeys;
	}

}
