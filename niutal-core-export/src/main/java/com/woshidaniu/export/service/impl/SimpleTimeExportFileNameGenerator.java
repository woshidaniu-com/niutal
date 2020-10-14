/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.export.api.ExportFileNameGenerator;
import com.woshidaniu.export.api.ExportFileNameGeneratorContext;

/**
 * @description	： 简单时间导出文件名称构造器
 * @author 		：康康（1571）
 */
class SimpleTimeExportFileNameGenerator implements ExportFileNameGenerator {

	@Override
	public String generateFileName(ExportFileNameGeneratorContext context) {
		String suffix = context.getSuffix();
		String fileName = String.format("%s.%s", System.currentTimeMillis(),suffix);
		return fileName;
	}
}
