package com.woshidaniu.pwdmgr.service.test;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woshidaniu.pwdmgr.api.strategy.PwdStrategy;
import com.woshidaniu.pwdmgr.dao.entities.StrategyModel;
import com.woshidaniu.pwdmgr.service.svcinterface.StrategyService;
import com.woshidaniu.util.base.MessageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/conf/spring/config*.xml", "classpath*:conf/dataTable/config*.xml" })
public class StrategyServiceTest {

	@Autowired
	protected StrategyService strategyService;

	@Before
	public void setup(){
		MessageUtil.reload();
	}
	
	//@Test
	public void testInsert() {

		StrategyModel strategy = new StrategyModel();
		strategy.setName(PwdStrategy.PWD_RETAKE_BY_EMAIL);
		strategy.setDesc("通过密保邮箱");
		strategy.setStatus("1");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		
		strategy.setName(PwdStrategy.PWD_RETAKE_BY_PHONE);
		strategy.setDesc("通过密保手机");
		strategy.setStatus("1");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		
		strategy.setName(PwdStrategy.PWD_RETAKE_BY_OTP);
		strategy.setDesc("通过动态口令");
		strategy.setStatus("0");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		
		strategy.setName(PwdStrategy.PWD_RETAKE_BY_ONECARD);
		strategy.setDesc("通过一卡通");
		strategy.setStatus("0");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		

		strategy.setName(PwdStrategy.PWD_RETAKE_BY_SQ);
		strategy.setDesc("通过密保问题");
		strategy.setStatus("0");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		
		strategy.setName(PwdStrategy.PWD_RETAKE_BY_APPEAL);
		strategy.setDesc("通过申诉");
		strategy.setStatus("0");

		System.out.println(strategy);
		strategyService.insert(strategy);
		System.out.println("===========");
		
		
	}

	// @Test
	public void testGetModel() {
		StrategyModel strategy = new StrategyModel();
		strategy.setId("4CCAF0CCEEC5390BE0538713470A75A2");
		StrategyModel validate2 = strategyService.getModel(strategy);
		System.out.println(validate2);
	}

	@Test
	public void testUpdate() {

		StrategyModel strategy = new StrategyModel();
		strategy.setId("4CF5C5E0B44A6321E0538713470AD7E8");
		
		StrategyModel strategyBefor = strategyService.getModel(strategy);
		System.out.println(strategyBefor);

		strategyBefor.setDesc("通过邮箱找回密码(修改).");
		strategyBefor.setStatus("1");

		strategyService.update(strategyBefor);
	}
	
	//@Test
	public void testGetList() {
		StrategyModel strategy = new StrategyModel();
		List<StrategyModel> validateList = strategyService.getModelList(strategy);
		for (StrategyModel validate : validateList) {
			System.out.println(validate);
		}
	}

	// @Test
	public void testDelete() {
		StrategyModel strategy = new StrategyModel();
		strategy.setId("4CCB1FF3FEFD3CA8E0538713470AD66B");
		strategyService.delete(strategy);
	}

	@After
	public void shutdown(){
		
		
		
	}
	
}
