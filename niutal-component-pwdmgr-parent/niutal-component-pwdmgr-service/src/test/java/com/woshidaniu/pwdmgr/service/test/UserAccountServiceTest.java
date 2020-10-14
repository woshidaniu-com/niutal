package com.woshidaniu.pwdmgr.service.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woshidaniu.api.entity.UserAccount;
import com.woshidaniu.pwdmgr.service.svcinterface.UserAccountService;
import com.woshidaniu.util.base.MessageUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/conf/spring/config*.xml", "classpath*:conf/dataTable/config*.xml" })
public class UserAccountServiceTest {
	

	@Autowired
	protected UserAccountService userAccountService;

	@Before
	public void setup(){
		MessageUtil.reload();
	}
	
	@Test
	public void testGetList() {
		UserAccount strategy = new UserAccount();
		List<UserAccount> validateList = userAccountService.getModelList(strategy);
		for (UserAccount validate : validateList) {
			System.out.println(validate);
		}
	}
	
}
