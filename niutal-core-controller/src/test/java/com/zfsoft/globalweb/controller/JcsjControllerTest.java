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

import com.alibaba.fastjson.JSONPath;
import com.woshidaniu.qa.ZTesterRuner;
import com.woshidaniu.qa.annotation.Order;

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
 * @date 2017-07-03
 * @mail
 * @version 1.0
 * @desc JcsjController unit test
 */
@RunWith(ZTesterRuner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:conf/spring/config-cache.xml",
		"classpath:conf/spring/config-datasource.xml", "classpath:conf/spring/config-shiro.xml",
		"classpath:conf/spring/config-sjfw.xml", "classpath*:conf/dataTable/config*.xml",
		"classpath:conf/spring/config-webmvc.xml" })
public class JcsjControllerTest extends PreSetupTest {
	private MockMvc mockMvc;
	@Autowired
	protected WebApplicationContext wac;

	@Autowired
	protected JcsjController jcsjController;

	private TestContextManager testContextManager;

	private static final String ctx = "/xtgl/jcsj";

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
	 * CxJcsj
	 * 
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 0)
	public void testCxJcsj() throws Exception {
		String path = "/cxJcsj";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验查询基础数据是否成功", "/globalweb/comp/xtgl/jcsj/cxJcsj", name);
	}

	/**
	 * ZjJcsj
	 * 
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 1)
	public void testZjJcsj() throws Exception {
		String path = "/zjJcsj";
		String uri = ctx + path;
		MvcResult result = mockMvc.perform(get(uri)).andDo(print()).andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验增加基础数据是否成功", "/globalweb/comp/xtgl/jcsj/zjJcsj", name);
	}

	/**
	 * ValideDm
	 *
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 2)
	public void testValideDm() throws Exception {
		String path = "/valideDm";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("param1", "bmlx001");
		params.put("param2", "lx01abc");
		MvcResult result = mockMvc.perform(post(uri).param("pkValue", params.get("param1"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
		// String name = result.getModelAndView().getViewName();
		assertEquals("校验验证代码唯一性是否正确", "false", result.getResponse().getContentAsString());
		result = mockMvc.perform(post(uri).param("pkValue", params.get("param2"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
		// String name = result.getModelAndView().getViewName();
		assertEquals("校验验证代码唯一性是否正确", "true", result.getResponse().getContentAsString());

	}

	

	/**
	 * SaveJcsj
	 *
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 3)
	public void testSaveJcsj() throws Exception {
		String path = "/saveJcsj";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("lxdm", "lx01");
		params.put("dm", "7788");
		params.put("mc", "单元测试124");
		params.put("zt", "1");
		MvcResult result = mockMvc
				.perform(post(uri).param("lxdm", params.get("lxdm")).param("dm", params.get("dm"))
						.param("mc", params.get("mc")).param("zt", params.get("zt")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验增加基础数据是否成功", "success", JSONPath.read(result.getResponse().getContentAsString(), "$.status"));
	}
	
	/**
	 * GetJcsjList
	 *
	 * @date 2017-07-03
	 * @return
	 */

	@Test
	@Order(index = 4)
	public void testGetJcsjList() throws Exception {
		String path = "/getJcsjList";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("searchModel.inputType", "{\"dm\":[\"单元测试124\"],\"mc\":[\"单元测试124\"]}");
		params.put("searchModel.selectType", "{\"lx\":[\"lx01\"],\"zt\":[\"1\"]}");
		params.put("searchModel.dateType", "{}");
		params.put("searchModel.numberType", "{}");
		params.put("searchModel.inputSqlType", "0");
		params.put("queryModel.sortName", "lxmc");
		params.put("queryModel.sortOrder", "asc");
		params.put("queryModel.showCount", "15");
		params.put("queryModel.currentPage", "1");
		MvcResult result = mockMvc
				.perform(post(uri).param("searchModel.inputType", params.get("searchModel.inputType"))
						.param("searchModel.selectType", params.get("searchModel.selectType"))
						.param("searchModel.dateType", params.get("searchModel.dateType"))
						.param("searchModel.numberType", params.get("searchModel.numberType"))
						.param("searchModel.inputSqlType", params.get("searchModel.inputSqlType"))
						.param("queryModel.sortName", params.get("queryModel.sortName"))
						.param("queryModel.sortOrder", params.get("queryModel.sortOrder"))
						.param("queryModel.showCount", params.get("queryModel.showCount"))
						.param("queryModel.currentPage", params.get("queryModel.currentPage")))
				.andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验查询基础数据是否成功", 1, JSONPath.read(result.getResponse().getContentAsString(),"$.totalResult"));
	}

	/**
	 * XgJcsj
	 *
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 5)
	public void testXgJcsj() throws Exception {
		String path = "/xgJcsj";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("pkValue", "lx017788");
		MvcResult result = mockMvc.perform(post(uri).param("pkValue", params.get("pkValue"))).andDo(print())
				.andExpect(status().isOk()).andReturn();
		String name = result.getModelAndView().getViewName();
		assertEquals("校验修改基础数据是否成功", "/globalweb/comp/xtgl/jcsj/xgJcsj", name);
	}

	
	/**
	 * ModifyJcsj
	 *
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 6)
	public void testModifyJcsj() throws Exception {
		String path = "/modifyJcsj";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("pkValue", "lx017788");
		params.put("lxdm", "lx01");
		params.put("dm", "7788");
		params.put("lxmc", "学期代码");
		params.put("mc", "单元测试421");
		params.put("zt", "0");
		MvcResult result = mockMvc.perform(post(uri).param("pkValue", params.get("pkValue"))
				.param("lxdm", params.get("lxdm")).param("dm", params.get("dm")).param("lxmc", params.get("lxmc"))
				.param("mc", params.get("mc")).param("zt", params.get("zt"))).andDo(print()).andExpect(status().isOk())
				.andReturn();
		assertEquals("校验修改基础数据是否成功", "success", JSONPath.read(result.getResponse().getContentAsString(), "$.status"));
	}

	/**
	 * ScJcsj
	 *
	 * @date 2017-07-03
	 * @return
	 */
	@Test
	@Order(index = 7)
	public void testScJcsj() throws Exception {
		String path = "/scJcsj";
		String uri = ctx + path;
		Map<String, String> params = new HashMap<String, String>();
		params.put("ids", "lx017788");
		params.put("lxs", "lx01");
		params.put("dms", "7788");
		MvcResult result = mockMvc.perform(post(uri).param("ids", params.get("ids")).param("lxs", params.get("lxs"))
				.param("dms", params.get("dms"))).andDo(print()).andExpect(status().isOk()).andReturn();
		assertEquals("校验删除基础数据是否成功", "success", JSONPath.read(result.getResponse().getContentAsString(), "$.status"));
	}
	@After
	public void tearDown() throws Exception {
		clearSubject();
	}
}