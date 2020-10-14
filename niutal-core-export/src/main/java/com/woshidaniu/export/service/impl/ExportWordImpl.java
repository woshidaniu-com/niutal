/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.dao.entities.ExportModel;

import java.io.File;

/**
 * @author Penghui.Qu
 * 公用导出word实现
 */
public class ExportWordImpl extends AbstractExportService {

	@Override
	public File getExportFile(ExportModel model) {
		throw new UnsupportedOperationException("暂未实现");
	}

	@Override
	protected String getSheetNameConfigKey() {
		throw new UnsupportedOperationException("暂未实现");
	}

	@Override
	protected File doGetExportFile(ExportModel model) throws Exception {
		throw new UnsupportedOperationException("暂未实现");
	}
}