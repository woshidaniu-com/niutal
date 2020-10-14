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

import com.alibaba.fastjson.JSONPath;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.Assert.*;

/**
 * Copyright (C), woshidaniu Co., Ltd
 * 
 * @author Administrator
 * @date 2017-07-05
 * @mail
 * @version 1.0
 * @desc LoginController unit test
 */
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class LoginControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected LoginController loginController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/login";

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
	 * GetPublicKey
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	public void testGetPublicKey() throws Exception {
		String path = "/getPublicKey";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		assertNotSame("校验生成公钥的exponent","",JSONPath.read(result.getResponse().getContentAsString(), "$.exponent"));
		assertNotSame("校验生成公钥的modulus","",JSONPath.read(result.getResponse().getContentAsString(), "$.modulus"));
	}
	
	/**
	 * Slogin
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	public void testSlogin() throws Exception {
		String path = "/slogin";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isFound()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验用户登录是否成功", "redirect:/xtgl/index/initMenu.zf", name);
	}

	/**
	 * SwitchRole
	 * 
	 * @date 2017-07-05
	 * @return
	 */
//	@Test
//	public void testSwitchRole() throws Exception {
//		String path = "/switchRole";
//		String uri = ctx + path;
//		Map<String, String> params = new HashMap<String, String>();
//
//		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
//		String name = result.getModelAndView().getViewName();
//		assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
//	}

	@After
	public void tearDown() throws Exception {
		clearSubject();
	}
}