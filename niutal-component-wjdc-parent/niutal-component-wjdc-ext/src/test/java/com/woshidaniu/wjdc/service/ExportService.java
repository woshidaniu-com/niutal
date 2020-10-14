/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.wjdc.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.woshidaniu.wjdc.BaseTest;
import com.woshidaniu.wjdc.service.svcinterface.IWjdcExportService;

public class ExportService  extends BaseTest{

	@Autowired
	IWjdcExportService wjdcExportService;
	
	private static final String wjid = "b129a69767efadb43fdc3ca65b4a43e1";
	
	@Test
	public void test_getDtxqById() {
		
		try {
			File f = this.wjdcExportService.getDtxqById("wjid");
			this.renderFile(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testExportWjtj() {
		File file;
		try {
			file = wjdcExportService.exportWjtj(wjid);
			this.renderFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testgetDtxqById_10698() {
		File file;
		try {
			file = wjdcExportService.exportDtxqById_10698(wjid);
			this.renderFile(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
