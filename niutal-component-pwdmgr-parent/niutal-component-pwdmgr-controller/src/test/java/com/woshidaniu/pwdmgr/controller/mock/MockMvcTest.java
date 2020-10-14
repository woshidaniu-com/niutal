package com.woshidaniu.pwdmgr.controller.mock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;


/**
 * 演示MockMVC使用
 * http://www.cnblogs.com/xiaohunshi/p/5706943.html
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config-datasource.xml,spring-mvc.xml")
@WebAppConfiguration
public class MockMvcTest {
	
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void setUp() throws Exception {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void demo() throws Exception {
        mockMvc.perform(get("/demo/test").accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
               .andExpect(status().isOk())
               .andExpect(content().contentType("application/json;charset=UTF-8"))
               .andExpect(content().json("{'foo':'bar'}"));
    }
}