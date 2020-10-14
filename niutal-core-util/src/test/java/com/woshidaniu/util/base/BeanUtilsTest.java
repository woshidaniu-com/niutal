package com.woshidaniu.util.base;

import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：BeanUtils单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月7日下午3:22:42
 */
public class BeanUtilsTest {

	@Test
	public void testGetDeclaredPropertys(){
		String[] arr = BeanUtils.getDeclaredPropertys(BeanUtils.class);
		Assert.assertNotNull(arr);
	}
	
	@Test
	public void testHasProperty(){
		boolean result = BeanUtils.hasProperty(BeanUtils.class, "logger");
		Assert.assertTrue(result);
	}
	
}
