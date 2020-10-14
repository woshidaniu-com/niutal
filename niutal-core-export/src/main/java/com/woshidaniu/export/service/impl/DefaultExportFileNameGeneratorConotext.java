/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.export.api.ExportFileNameGeneratorContext;

/**
 * @description	： 默认上下文
 * @author 		：康康（1571）
 */
class DefaultExportFileNameGeneratorConotext implements ExportFileNameGeneratorContext {
	
	private String dcclbh;
	private String dcclmc;
	private String phid;
	private String phmc;
	private String suffix;
	
	public DefaultExportFileNameGeneratorConotext(String dcclbh, String dcclmc, String phid, String phmc, String suffix) {
		super();
		this.dcclbh = dcclbh;
		this.dcclmc = dcclmc;
		this.phid = phid;
		this.phmc = phmc;
		this.suffix = suffix;
	}

	@Override
	public String getSuffix() {
		return this.suffix;
	}

	@Override
	public String getDcclbh() {
		return this.dcclbh;
	}

	@Override
	public String getDcclmc() {
		return this.dcclmc;
	}

	@Override
	public String getPhid() {
		return phid;
	}

	@Override
	public String getPhmc() {
		return phmc;
	}
}
