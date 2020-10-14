/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.exportTest;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.woshidaniu.dao.entities.ExportConfigModel;
import com.woshidaniu.dao.entities.ExportConfigVoModel;
import com.woshidaniu.dao.entities.ExportModel;
import com.woshidaniu.export.service.svcinterface.IExportAuthService;
import com.woshidaniu.export.service.svcinterface.IExportService;

/**
 * @author 		：康康（1571）
 */
//@org.junit.runner.RunWith(org.springframework.test.context.junit4.SpringJUnit4ClassRunner.class)
//@org.springframework.test.context.ContextConfiguration(locations= {"classpath:config-datasource-test.xml",})
public class ExportTest {
	
	@Autowired(required=true)
	@Qualifier("exportExcelPOI")
	private IExportService exportService;
	
	@Autowired(required=true)
	private IExportAuthService exportAuthService;

	@Test
	@Ignore
	public void testQueryExportConfigModel() {
		try {
			ExportConfigVoModel model = exportAuthService.getExportConfigVoModel("yhgl_yhdc", "zgh");
			System.out.println(model);			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testQueryExportConfigVoModels() {
		
		try {
			List<ExportConfigVoModel> list = exportAuthService.getPagedExportConfigVoModelList(new ExportConfigModel());
			System.out.println(list);			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	@Ignore
	public void testQuery() {
		
		ExportModel model = new ExportModel();
		model.setJs("admin");
		model.setZgh("public");
		model.setDcclbh("yhgl_yhdc");
		List<ExportConfigModel> list = this.exportService.getConfigList(model);
		for(ExportConfigModel m : list) {
			System.out.println(m.toString());
		}
	}
	
	@Test
	@Ignore
	public void testUpdate() {
		
		int c = this.exportAuthService.updateExportAuth("yhgl_yhdc", "zgh",new String[] {"admin"});
		System.out.println(c);
	}
}
