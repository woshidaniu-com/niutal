package com.woshidaniu.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import freemarker.template.TemplateException;

public class FreemarkerUtilTest {

	@Test
	public void testParseByStringTemplate(){
		String template = "学号：${xh},姓名:${student.xm}";
		Map<String,Object> data = new HashMap<String, Object>();
		try {
			Map<String,String> student = new HashMap<String, String>();
			student.put("xm", "李四");
			data.put("student", student);
			data.put("xh", "31001212");
			String content = FreeMarkerUtil.parseByStringTemplate(data, template);
			Assert.assertEquals("学号：31001212,姓名:李四", content);
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}


