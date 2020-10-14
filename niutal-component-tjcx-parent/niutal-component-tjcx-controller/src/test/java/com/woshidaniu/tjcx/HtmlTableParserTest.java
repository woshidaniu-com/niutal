/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.tjcx;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.woshidaniu.tjcx.html.DefaultHtmlTableToXlsParser;
import com.woshidaniu.tjcx.html.HtmlTableToXlsParser;
import com.woshidaniu.tjcx.html.TraceInputHtmlTableToXlsParser;

public class HtmlTableParserTest {
	
	private static final File UserDir = FileUtils.getUserDirectory();
	
	private static final File ModularTempDir = new File(UserDir,"HtmlTableParserTest");

	@Before
	public void before() throws IOException {
		if(!ModularTempDir.exists()) {
			ModularTempDir.mkdirs();
		}
		//FileUtils.cleanDirectory(modularTempDir);
	}
	
	@After
	public void after() throws IOException{
		//FileUtils.cleanDirectory(modularTempDir);
	}
	
	@Test
	public void test1() {
		
		InputStream inputStream = DefaultHtmlTableToXlsParser.class.getResourceAsStream("/com/woshidaniu/tjcx/demo.html");
		try {
			byte[] arr = IOUtils.toByteArray(inputStream);
			String html = new String(arr);
			DefaultHtmlTableToXlsParser parser = new DefaultHtmlTableToXlsParser(ModularTempDir,html);
			File f = parser.parse();
			File targetFile = new File(ModularTempDir,"aa.xls");
			if(targetFile.exists()) {
				targetFile.delete();
			}
			targetFile.createNewFile();
			FileUtils.copyFile(f, targetFile);
			System.out.println(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		
		InputStream inputStream = DefaultHtmlTableToXlsParser.class.getResourceAsStream("/com/woshidaniu/tjcx/demo_bug_wanglei_1.html");
		try {
			byte[] arr = IOUtils.toByteArray(inputStream);
			String html = new String(arr);
			DefaultHtmlTableToXlsParser parser = new DefaultHtmlTableToXlsParser(ModularTempDir,html);
			File f = parser.parse();
			File targetFile = new File(ModularTempDir,"bb.xls");
			if(targetFile.exists()) {
				targetFile.delete();
			}
			targetFile.createNewFile();
			FileUtils.copyFile(f,targetFile);
			System.out.println(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test3() {
		
		InputStream inputStream = DefaultHtmlTableToXlsParser.class.getResourceAsStream("/com/woshidaniu/tjcx/demo_bug_ydd_1.html");
		try {
			byte[] arr = IOUtils.toByteArray(inputStream);
			String html = new String(arr);
			DefaultHtmlTableToXlsParser parser = new DefaultHtmlTableToXlsParser(ModularTempDir,html);
			File f = parser.parse();
			File targetFile = new File(ModularTempDir,"cc.xls");
			if(targetFile.exists()) {
				targetFile.delete();
			}
			targetFile.createNewFile();
			FileUtils.copyFile(f,targetFile);
			System.out.println(targetFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test4() {
		
		InputStream inputStream = DefaultHtmlTableToXlsParser.class.getResourceAsStream("/com/woshidaniu/tjcx/demo_bug_tangyiping_1.html");
		try {
			byte[] arr = IOUtils.toByteArray(inputStream);
			String html = new String(arr);
			HtmlTableToXlsParser parser = new TraceInputHtmlTableToXlsParser(ModularTempDir,html);
			File f = parser.parse();
			System.out.println(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
