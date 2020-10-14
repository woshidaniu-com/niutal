package com.woshidaniu.common.excel.template;

/**
 * 模板建造者
 * @author JiangDong.Yi
 *
 */
public class TemplateDirector {
	private ATemplateBuilder templateBuilder;
	
	/**
	 * 注入建造工具类
	 * @param templateBuilder
	 */
	public TemplateDirector(ATemplateBuilder templateBuilder) {
		this.templateBuilder = templateBuilder;
	}
	
	/**
	 * 建造模板
	 */
	public void constructTemplate() throws Exception{
		templateBuilder.getExcelTemplate();
	}
}
