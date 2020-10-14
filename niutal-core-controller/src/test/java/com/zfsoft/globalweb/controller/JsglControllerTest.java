package com.woshidaniu.globalweb.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import com.woshidaniu.qa.ZTesterRuner;
import com.woshidaniu.qa.annotation.Order;

import static org.junit.Assert.*;

/**
 * Copyright (C), woshidaniu Co., Ltd
 * 
 * @author Administrator
 * @date 2017-07-05
 * @mail
 * @version 1.0
 * @desc JsglController unit test
 */
@RunWith(ZTesterRuner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class JsglControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected JsglController jsglController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/jsgl";

	private String jsdm = "f08035df36f61d731767dc0ab0aeb587";

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
	 * SaveGnqx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testSaveGnqx() throws Exception {
	// String path = "/saveGnqx";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * Fpyh
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testFpyh() throws Exception {
	// String path = "/fpyh";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * ScJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	@Order(index = 4)
	public void testScJsxx() throws Exception {
		String path = "/scJsxx";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", jsdm);
		MvcResult result = mockMvc.perform(post(uri).param("id", params.get("id"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
		assertEquals("校验删除角色是否成功", "success", JSONPath.read(result.getResponse().getContentAsString(), "$.status"));
	}

	/**
	 * GetEjsqList
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testGetEjsqList() throws Exception {
	// String path = "/getEjsqList";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * GetWfpyhList
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testGetWfpyhList() throws Exception {
	// String path = "/getWfpyhList";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * GetYfpyhList
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testGetYfpyhList() throws Exception {
	// String path = "/getYfpyhList";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * InertYhfp
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testInertYhfp() throws Exception {
	// String path = "/inertYhfp";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * DeleteYhfp
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testDeleteYhfp() throws Exception {
	// String path = "/deleteYhfp";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * CxEjsq
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testCxEjsq() throws Exception {
	// String path = "/cxEjsq";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * Gnsq
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testGnsq() throws Exception {
	// String path = "/gnsq";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * CkJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testCkJsxx() throws Exception {
	// String path = "/ckJsxx";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jsgl/cxJsxx", name);
	// }

	/**
	 * CxJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	@Order(index = 1)
	public void testCxJsxx() throws Exception {
		String path = "/cxJsxx";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验查询角色页面是否成功", "/globalweb/comp/xtgl/jsgl/cxJsxx", name);
	}

	/**
	 * GetJsxxList
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testGetJsxxList() throws Exception {
	// String path = "/getJsxxList";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * XgJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	@Order(index = 4)
	public void testXgJsxx() throws Exception {
		String path = "/xgJsxx";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsdm", jsdm);
		MvcResult result = mockMvc.perform(post(uri).param("jsdm", jsdm)).andDo(print()).andExpect(status().isOk())
				.andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验修改基础数据", "/globalweb/comp/xtgl/jsgl/xgJsxx", name);
	}

	/**
	 * SaveJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	@Order(index = 3)
	public void testSaveJsxx() throws Exception {
		String path = "/saveJsxx";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("jsdm", jsdm);
		params.put("jsmc", "unit.testjs");
		params.put("qyzt", "1");
		params.put("jssm", "unit.tets desc");
		MvcResult result = mockMvc
				.perform(post(uri).param("jsdm", params.get("jsdm")).param("jsmc", params.get("jsmc"))
						.param("qyzt", params.get("qyzt")).param("jssm", params.get("jssm")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验增加角色是否正确", "success", JSONPath.read(result.getResponse().getContentAsString(), "$.status"));
	}

	/**
	 * ZjJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	@Test
	@Order(index = 2)
	public void testZjJsxx() throws Exception {
		String path = "/zjJsxx";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验增加角色页面是否正确", "/globalweb/comp/xtgl/jsgl/zjJsxx", name);
		assertNotNull("校验生成的角色代码不为空", result.getModelAndView().getModel().get("jsdm"));// f08035df36f61d731767dc0ab0aeb587
	}

	/**
	 * ModifyJsxx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testModifyJsxx() throws Exception {
	// String path = "/modifyJsxx";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result = mockMvc
	// .perform(post(uri).param("lxdm", "lx").param("dm", "dm").param("mc",
	// "").param("zt", "zt"))
	// .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath(".status").value("success")).andReturn();
	// assertEquals("", "{\"status\":\"success\"}",
	// result.getResponse().getContentAsString());
	// }

	/**
	 * Sjsq
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testSjsq() throws Exception {
	// String path = "/sjsq";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * Ejsq
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testEjsq() throws Exception {
	// String path = "/ejsq";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * SaveEjsq
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testSaveEjsq() throws Exception {
	// String path = "/saveEjsq";
	// String uri = ctx + path;
	// Map<String, String> params = new HashMap<String, String>();
	//
	// MvcResult result =
	// mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
	// String name = result.getModelAndView().getViewName();
	// assertEquals("", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	// }

	/**
	 * SaveSjqx
	 * 
	 * @date 2017-07-05
	 * @return
	 */
	// @Test
	// public void testSaveSjqx() throws Exception {
	// String path = "/saveSjqx";
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