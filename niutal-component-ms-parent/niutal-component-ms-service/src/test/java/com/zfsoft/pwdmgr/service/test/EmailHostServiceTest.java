package com.woshidaniu.pwdmgr.service.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.woshidaniu.ms.service.svcinterface.EmailHostService;
import com.woshidaniu.util.base.MessageUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/conf/spring/config*.xml", "classpath*:conf/dataTable/config*.xml" })
public class EmailHostServiceTest {
	
	@Autowired
	protected EmailHostService emailHostService ;
	
	@Before
	public void setup(){
		MessageUtil.reload();
	}
	
	/*@Test
	public void testInsert(){
		
		VerifiModel model =new  VerifiModel();
		model.setVerifi_name("idcard");
		model.setVerifi_label("证件号");
		model.setVerifi_desc("请输入有些的身份证号码.");
		model.setRequired("1");
		System.out.println(model);
		VerifiService.insert(model);
		System.out.println("===========");
	}
	
	//@Test
	public void testGetModel(){
		VerifiModel model = new VerifiModel();
		
		model.setVerifi_id("4CCB54CAAAC44076E0538713470A8637");
		
		VerifiModel validate2 = VerifiService.getModel(model);
		System.out.println(validate2);
	}
	
	//@Test
	public void testGetList(){
		VerifiModel model = new VerifiModel();
		List<VerifiModel> validateList=VerifiService.getModelList(model);
		for (VerifiModel validate :validateList){
			System.out.println(validate);
		}
	}

	//@Test
	public void testDelete(){
		VerifiModel model = new VerifiModel();
		model.setVerifi_id("4CCB54CAAAC44076E0538713470A8637");
		VerifiService.delete(model);
	}*/
	
}
