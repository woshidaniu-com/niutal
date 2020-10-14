/**
 * @部门:学工产品事业部
 * @日期：2013-5-15 上午10:55:01 
 */  
package com.woshidaniu.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：FreeMarker 模版工具
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月16日下午2:51:31
 */
@Deprecated
public class FreeMarkerUtil {

	
	private static final String ENCODING = "UTF-8";
	private static final String WORD_TYPE = ".doc";
	private static final String TEMP_DIR = "java.io.tmpdir";
	private static Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
	
	
	static {
		configuration.setDefaultEncoding(ENCODING);
	}
	
	
	
	
	
	/**
	 * 
	 * <p>方法说明：生成word文档<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午2:51:55<p>
	 * @param data 填充数据
	 * @param templateDirectory 模版文件目录
	 * @param templateName 模版名称
	 * @param fileName word文件名
	 * @return word文件
	 */
	public static synchronized File getWordFile(Map<String,Object> data,String templateDirectory,String templateName,String fileName){
		Writer out = null;
		try {
			Template template = null;
			//设置模版位置
			configuration.setDirectoryForTemplateLoading(ResourceUtils.getFile(templateDirectory));
			//模版文件名
			template=configuration.getTemplate(templateName);
			File wordFile = new File(System.getProperty(TEMP_DIR)+File.separator+fileName+ WORD_TYPE);
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(wordFile), ENCODING));
			template.process(data, out);
			return wordFile;
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if (out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	
	
	
	/**
	 * 
	 * <p>方法说明：生成word文件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月16日下午2:53:44<p>
	 * @param data 填充数据
	 * @param templateDirectory 模版文件目录
	 * @param templateName 模版名称
	 * @return word文件
	 */
	public static File getWordFile(Map<String,Object> data,String templateDirectory,String templateName){
		
		String fileName = String.valueOf(System.currentTimeMillis());
		
		return getWordFile(data, templateDirectory, templateName, fileName);
	}
	
	
	/**
	 * 
	 * <p>方法说明：生成word文件<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午9:54:49<p>
	 * @param fileName 文件名
	 * @param data 填充数据
	 * @param templateDirectory 模版文件目录
	 * @param templateName 模版名称
	 * @return word文件
	 */
	public static File getWordFile(String fileName,Map<String,Object> data,String templateDirectory,String templateName){
		
//		String fileName = String.valueOf(System.currentTimeMillis());
		
		return getWordFile(data, templateDirectory, templateName, fileName);
	}
	
	/**
	 * 
	 * <p>方法说明：解析字符串模版<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年10月17日下午3:34:01<p>
	 * @param obj
	 * @param templateSource
	 * @return
	 * @throws TemplateException
	 * @throws IOException
	 */
	public static String parseByStringTemplate(Object obj,String templateSource) throws TemplateException, IOException{
		StringTemplateLoader loader = new StringTemplateLoader();
		configuration.setTemplateLoader(loader);
		configuration.setClassicCompatible(true);
		loader.putTemplate("freemaker", templateSource);
        Template template = configuration.getTemplate("freemaker");   
        StringWriter writer = new StringWriter();   
        template.process(obj, writer); 
		return writer.toString();
	}
}
