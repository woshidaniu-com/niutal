package com.woshidaniu.file.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import org.docx4j.Docx4J;
import org.docx4j.convert.in.xhtml.XHTMLImporterImpl;
import org.docx4j.model.structure.PageSizePaper;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;

import com.woshidaniu.fastdoc.docx4j.fonts.ChineseFont;
import com.woshidaniu.fastdoc.docx4j.fonts.PhysicalFontFactory;
import com.woshidaniu.fastdoc.docx4j.utils.PhysicalFontUtils;

import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class FreemarkerUtil {
	
	private static PhysicalFontFactory FONT_FACTORY = PhysicalFontFactory.getInstance();
 

	/**
	 * 生成pdf文件
	 * @param template 模版文件
	 * @param data 填充数据
	 * @param landscape true：横版，false 竖版
	 * @return
	 * @throws Exception
	 */
	public static File getPdfFile(File template,Map<String, Object> data,boolean landscape) throws Exception{
		
		String html = FreemarkerUtil.getContent(template, data);
		Document doc = Jsoup.parse(html);
		
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);  //转为 xhtml 格式
        File file = savePdf(xhtml2word(doc,landscape));
		
		return file;
	}
	
	/**
	 * 
	 * <p>方法说明：字符串模版生成pdf<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年11月25日上午11:33:12<p>
	 * @param template
	 * @param data
	 * @param landscape
	 * @return
	 * @throws Exception
	 */
	public static File getPdfFile(String template,Map<String, Object> data,boolean landscape) throws Exception{
		
		String html = FreemarkerUtil.getContent(template, data);
		Document doc = Jsoup.parse(html);
		
		doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);  //转为 xhtml 格式
		File file = savePdf(xhtml2word(doc,landscape));
		
		return file;
	}
	
	
	/**
	 * 生成word文件
	 * @param template 模版文件
	 * @param data 填充数据
	 * @param landscape true：横版，false 竖版
	 * @return
	 * @throws Exception
	 */
	public static File getWordFile(File template,Map<String, Object> data,boolean landscape) throws Exception{
		
		String html = FreemarkerUtil.getContent(template, data);
		Document doc = Jsoup.parse(html);
		
        doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);  //转为 xhtml 格式

        File file = saveDocx(xhtml2word(doc,landscape));
		return file;
	}
	
	/**
	 * 
	 * <p>方法说明：字符串模版生成word<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年11月25日上午11:33:31<p>
	 * @param template
	 * @param data
	 * @param landscape
	 * @return
	 * @throws Exception
	 */
	public static File getWordFile(String template , Map<String, Object> data,boolean landscape) throws Exception{
		
		String html = FreemarkerUtil.getContent(template, data);
		Document doc = Jsoup.parse(html);
		
		doc.outputSettings().syntax(Document.OutputSettings.Syntax.xml).escapeMode(Entities.EscapeMode.xhtml);  //转为 xhtml 格式
		
		File file = saveDocx(xhtml2word(doc,landscape));
		return file;
	}
	
	
	
	/*
	 * 将 {@link org.jsoup.nodes.Document} 对象转为 {@link org.docx4j.openpackaging.packages.WordprocessingMLPackage}
	 * landscape true 横版，false 竖版
	 */
	private static WordprocessingMLPackage xhtml2word(Document doc,boolean landscape) throws Exception {

        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.createPackage(PageSizePaper.A4, landscape); //A4纸，

        //配置中文字体
        PhysicalFontUtils.setDefaultFont(wordMLPackage, ChineseFont.SIMSUM.getFontName());

        XHTMLImporterImpl xhtmlImporter = new XHTMLImporterImpl(wordMLPackage);
        
        //导入 xhtml
        wordMLPackage.getMainDocumentPart().getContent().addAll(xhtmlImporter.convert(doc.html(), doc.baseUri()));

        return wordMLPackage;
    }
    
	/*
	 * 将 {@link org.docx4j.openpackaging.packages.WordprocessingMLPackage} 存为 pdf
	 */
	private static File savePdf(WordprocessingMLPackage wordMLPackage) throws FileNotFoundException, Docx4JException {

        File file = new File(genFilePath() + ".pdf");
        OutputStream os = null;
        
        try {
        	os = new FileOutputStream(file);
			Docx4J.toPDF(wordMLPackage, os);
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Docx4JException e) {
			throw e;
		} finally{
        	if (os != null){
                try {
                	os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

        return file;
    }
	
	
	/*
	 * 将 {@link org.docx4j.openpackaging.packages.WordprocessingMLPackage} 存为 docx
	 */
    public static File saveDocx(WordprocessingMLPackage wordMLPackage) throws Exception {

        File file = new File(genFilePath() + ".docx");
        wordMLPackage.save(file); //保存到 docx 文件

        return file;
    }
    
    
    /*
     * 生成临时文件位置
     */
	private static String genFilePath() {
        return System.getProperty("java.io.tmpdir") + "/" + System.currentTimeMillis();
    }
	
	private static String getContent(File templateFile, Map<String,Object> data)
			throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		
		Configuration config = getFreemarkerConfig();
		config.setDirectoryForTemplateLoading(templateFile.getParentFile());
		//模版文件名
		Template template= config.getTemplate(templateFile.getName());
		template.setOutputEncoding("UTF-8");
		StringWriter writer = new StringWriter();
		template.process(data, writer);
		writer.flush();
		String html = writer.toString();
		return html;
	}
	
	
	private static String getContent(String template, Map<String,Object> data)
			throws TemplateNotFoundException, MalformedTemplateNameException,
			ParseException, IOException, TemplateException {
		
		Configuration config = getFreemarkerConfig();
		StringTemplateLoader loader = new StringTemplateLoader();
		config.setTemplateLoader(loader);
		config.setClassicCompatible(true);
		loader.putTemplate("freemaker", template);
        Template t = config.getTemplate("freemaker");   
        StringWriter writer = new StringWriter();   
        t.process(data, writer); 
		String html = writer.toString();
		return html;
	}

	
	private static Configuration getFreemarkerConfig()
			throws IOException {
		Configuration config = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		config.setEncoding(Locale.CHINA, "utf-8");
		return config;
	}

}
