package com.woshidaniu.common.excel.template;

import jxl.write.WritableSheet;

/**
 * Excel模板建造者接口
 * @author JiangDong.Yi
 *
 */
public abstract class ATemplateBuilder {
	protected WritableSheet sheet;
	
	/**
	 * 建造格式
	 */
	public abstract void buildFormat() throws Exception;
	
	/**
	 * 建造批注
	 */
	public abstract void buildPostil() throws Exception;
	
	/**
	 * 获取Excel模板
	 * @return
	 */
	public WritableSheet getExcelTemplate() throws Exception{
		if(this.sheet == null){
			return null;
		}
		this.buildFormat();
		this.buildPostil();
		return this.sheet;
	}
	
	/**
	 * 设置工作表
	 * @param sheet
	 */
	public void setSheet(WritableSheet sheet) {
		this.sheet = sheet;
	}
}
