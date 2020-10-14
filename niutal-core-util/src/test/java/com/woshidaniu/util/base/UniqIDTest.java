package com.woshidaniu.util.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：UniqID 单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月8日上午10:04:10
 */
public class UniqIDTest {

	@Test
	public void testGetUniqID(){
		Assert.assertNotNull(UniqID.getInstance().getUniqID());
	}
	
	@Test
	public void testGetUniqIDHash(){
		Assert.assertNotNull(UniqID.getInstance().getUniqIDHash());
	}
}
