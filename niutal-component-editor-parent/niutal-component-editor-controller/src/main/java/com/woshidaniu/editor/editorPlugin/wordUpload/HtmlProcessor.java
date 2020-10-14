/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.editor.editorPlugin.wordUpload;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.LineIterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @description	： html处理器，提取字符串的body标签里的内容
 * @author 		：康康（1571）
 * @date		： 2018年10月19日 上午11:44:51
 * @version 	V1.0
 */
public class HtmlProcessor {
	
	private Document document;
	private Element headElement;
	private Element styleElement;
	private Element bodyElement;
	
	private Map<String,String> cssClassToStyleMapping = new HashMap<String,String>();
	
	public HtmlProcessor(String html) {
		this.document = Jsoup.parse(html);
	}
	
	public String process() {
		
		//解析head
		this.headElement = this.selectOne("head", this.document);
		this.styleElement = this.selectOne("style", this.headElement);
		this.bodyElement = this.selectOne("body", this.document);
		
		this.parseStyle();
		
		//递归替换标签的样式成为内联样式
		this.doReplace(this.bodyElement);
		
		//处理表格
		this.processTables();
		
		//处理表格
		this.processImages();
		//等等，处理其他的标签
		
		String result = this.buildResult();
		return result;
	}

	private void processImages() {
		//后续处理
	}

	private String buildResult() {
		//构建结果
		StringBuilder sb = new StringBuilder();
		Elements bodyChildren = this.bodyElement.children();
		Iterator<Element> bodyIterator = bodyChildren.iterator();
		while(bodyIterator.hasNext()) {
			Element e = bodyIterator.next();
			sb.append(e.toString());
		}
		String result = sb.toString();
		return result;
	}

	/**
	 * @description	： 处理所有表格
	 */
	private void processTables() {
		Elements tableElements = this.bodyElement.select("table");
		Iterator<Element> tableIt = tableElements.iterator();
		while(tableIt.hasNext()) {
			Element e = tableIt.next();
			this.processTableTag(e);
		}
	}

	/**
	 * @description	： 处理表格
	 * @param e
	 */
	private void processTableTag(Element e) {
		e.attr("style", "width:900px");
		e.attr("cellpadding", "1");
		e.attr("border", "1");
	}

	/**
	 * @description	： 根据css查询,获得唯一一个元素
	 * @param cssQuery
	 * @param context
	 * @return
	 */
	private Element selectOne(String cssQuery,Element context) {
		Elements elements = context.select(cssQuery);
		return elements.iterator().next();
	}
	
	/**
	 * @description	： 解析样式表
	 */
	private void parseStyle() {
		//解析样式表
		String styleText = this.styleElement.html();
		Reader reader = new StringReader(styleText);
		LineIterator lineIterator = new LineIterator(reader);
		while(lineIterator.hasNext()) {
			String line = lineIterator.next();
			
			int start = line.indexOf("{");
			int end = line.indexOf("}", start+1);
			
			String css = line.substring(1, start);
			String cssContent = line.substring(start+1, end);
			this.cssClassToStyleMapping.put(css, cssContent);
		}
	}

	/**
	 * @description	： 替换样式类成为内联样式
	 * @param e
	 */
	private void doReplace(Element e) {
		String className = e.className();
		if(this.cssClassToStyleMapping.containsKey(className)) {
			String style = this.cssClassToStyleMapping.get(className);
			e.removeClass(className);
			e.attr("style", style);
		}
		Elements children = e.children();
		Iterator<Element> it = children.iterator();
		while(it.hasNext()) {
			Element ee = it.next();
			this.doReplace(ee);
		}
	}
}
