package com.woshidaniu.globalweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.Assert.*;

/**
 * Copyright (C), woshidaniu Co., Ltd
 * 
 * @author Administrator
 * @date 2017-07-04
 * @mail
 * @version 1.0
 * @desc CommonValidationController unit test
 */
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class CommonValidationControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected CommonValidationController commonValidationController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/validate";

	@Before
	public void setup() throws Exception {
		super.preSetup();
		testContextManager = new TestContextManager(getClass());
		try {
			testContextManager.prepareTestInstance(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}

	/**
	 * CxUnique
	 * 
	 * @date 2017-07-04
	 * @return
	 */
	@Test
	public void testCxUniqueYhgl() throws Exception {
		String path = "/cxUnique";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("filed_name", "zgh");
		params.put("filed_value", "22");
		params.put("old_filed_value", "");
		params.put("table", "V0102");
		MvcResult result = mockMvc
				.perform(post(uri).param("filed_name", params.get("filed_name"))
						.param("filed_value", params.get("filed_value"))
						.param("old_filed_value", params.get("old_filed_value")).param("table", params.get("table")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验职工号唯一性是否正确", "1", result.getResponse().getContentAsString());

		params.put("filed_value", "tester");
		result = mockMvc
				.perform(post(uri).param("filed_name", params.get("filed_name"))
						.param("filed_value", params.get("filed_value"))
						.param("old_filed_value", params.get("old_filed_value")).param("table", params.get("table")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验职工号唯一性是否不正确", "0", result.getResponse().getContentAsString());
	}

	@Test
	public void testCxUniqueJsgl() throws Exception {
		String path = "/cxUnique";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("filed_name", "jsmc");
		params.put("filed_value", "ppt");
		params.put("old_filed_value", "");
		params.put("table", "V0101");
		MvcResult result = mockMvc
				.perform(post(uri).param("filed_name", params.get("filed_name"))
						.param("filed_value", params.get("filed_value"))
						.param("old_filed_value", params.get("old_filed_value")).param("table", params.get("table")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验角色名称唯一性是否正确", "1", result.getResponse().getContentAsString());
		params.put("filed_value", "系统管理员");
		result = mockMvc
				.perform(post(uri).param("filed_name", params.get("filed_name"))
						.param("filed_value", params.get("filed_value"))
						.param("old_filed_value", params.get("old_filed_value")).param("table", params.get("table")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验角色名称唯一性是否不正确", "0", result.getResponse().getContentAsString());
	}

	@After
	public void tearDown() throws Exception {
		clearSubject();
	}
}