package com.woshidaniu.globalweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.After;
import org.junit.Before;
import com.woshidaniu.qa.ZTesterRuner;
import com.woshidaniu.qa.annotation.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.junit.Assert.*;

/**
 * Copyright (C), woshidaniu Co., Ltd
 * 
 * @author Administrator
 * @date 2017-07-07
 * @mail
 * @version 1.0
 * @desc XwglController unit test
 */
@RunWith(ZTesterRuner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class XwglControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected XwglController xwglController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/xwgl";

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
	 * ModifyXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testModifyXw() throws Exception {
	// String path = "/modifyXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * ScXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testScXw() throws Exception {
	// String path = "/scXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * QxfbXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testQxfbXw() throws Exception {
	// String path = "/qxfbXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * ZdXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testZdXw() throws Exception {
	// String path = "/zdXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * SaveXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testSaveXw() throws Exception {
	// String path = "/saveXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * CkXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testCkXw() throws Exception {
	// String path = "/ckXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * ZjXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	@Test
	@Order(index = 1)
	public void testZjXw() throws Exception {
		String path = "/zjXw";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验增加新闻页面是否成功", "/globalweb/comp/xtgl/xwgl/zjXw", name);
	}

	/**
	 * FbXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testFbXw() throws Exception {
	// String path = "/fbXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * XgXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testXgXw() throws Exception {
	// String path = "/xgXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * QxzdXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testQxzdXw() throws Exception {
	// String path = "/qxzdXw";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * GetXwxxList
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testGetXwxxList() throws Exception {
	// String path = "/getXwxxList";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * CxXw
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// @Order(index = 1)
	// public void testCxXw() throws Exception {
	// String path = "/cxXw";
	// String uri = ctx + path;
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("校验查询新闻页面是否成功", "/globalweb/comp/xtgl/xwgl/cxXw", name);
	// }

	/**
	 * SetValueStack
	 * 
	 * @date 2017-07-07
	 * @return
	 */
	// @Test
	// public void testSetValueStack() throws Exception {
	// String path = "$method.path";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	@After
	public void tearDown() throws Exception {
		clearSubject();
	}
}