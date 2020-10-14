package com.woshidaniu.globalweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
 * @date 2017-07-03
 * @mail
 * @version 1.0
 * @desc IndexController unit test
 */
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class IndexControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected IndexController indexController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/index";

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
	 * InitMenu
	 * 
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	public void testInitMenu() throws Exception {
		String path = "/initMenu";
		String uri = ctx + path;

		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("", "/exception/busiError", name);
	}

	@After
	public void tearDown() throws Exception {
		clearSubject();
	}
}