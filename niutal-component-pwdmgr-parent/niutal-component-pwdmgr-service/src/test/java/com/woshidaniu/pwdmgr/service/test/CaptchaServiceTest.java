package com.woshidaniu.pwdmgr.service.test;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woshidaniu.basicutils.RandomUtils;
import com.woshidaniu.pwdmgr.dao.entities.CaptchaModel;
import com.woshidaniu.pwdmgr.service.svcinterface.CaptchaService;
import com.woshidaniu.util.base.MessageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/conf/spring/config*.xml", "classpath*:conf/dataTable/config*.xml" })
public class CaptchaServiceTest {
	
	@Autowired
	protected CaptchaService captchaService ;
	
	@Before
	public void setup(){
		MessageUtil.reload();
	}
	
	//@Test
	public void testInsert(){
		
		CaptchaModel model =new  CaptchaModel();
		model.setCaptcha(RandomUtils.genRandomNum(6));
		model.setOut_uuid(UUID.randomUUID().toString());
		System.out.println(model);
		captchaService.insert(model);
		System.out.println("===========");
	}
	
	//@Test
	public void testGetModel(){
		CaptchaModel model = new CaptchaModel();
		
		model.setCaptcha_id("4CCB54CAAAC44076E0538713470A8637");
		
		CaptchaModel validate2 = captchaService.getModel(model);
		System.out.println(validate2);
	}
	
	//@Test
	public void testGetList(){
		CaptchaModel model = new CaptchaModel();
		List<CaptchaModel> validateList=captchaService.getModelList(model);
		for (CaptchaModel validate :validateList){
			System.out.println(validate);
		}
	}

	//@Test
	public void testDelete(){
		CaptchaModel model = new CaptchaModel();
		model.setCaptcha_id("4CCB54CAAAC44076E0538713470A8637");
		captchaService.delete(model);
	}
	
}
