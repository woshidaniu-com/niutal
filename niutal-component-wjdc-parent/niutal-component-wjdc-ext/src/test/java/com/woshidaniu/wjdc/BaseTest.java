package com.woshidaniu.wjdc;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/conf/spring/config-datasource.xml","/conf/spring/common.xml"})
public class BaseTest {

	protected void renderFile(File f) {
		File dir = new File("c:\\\\test_case_dir");
		try { 
			FileUtils.copyFileToDirectory(f, dir);
		} catch (IOException e) { 
			e.printStackTrace();
		}
	}
}
