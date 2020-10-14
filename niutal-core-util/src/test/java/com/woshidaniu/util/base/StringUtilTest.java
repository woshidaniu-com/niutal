package com.woshidaniu.util.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：StringUtil 单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月8日上午10:32:46
 */
public class StringUtilTest {

	@Test
	public void testIsNull(){
		String str1 = "" , str2 = null;
		boolean result = StringUtil.isNull(str1);
		Assert.assertTrue(result);
		result = StringUtil.isNull(str2);
		Assert.assertTrue(result);
	}
	
	@Test
	public void testRemoveLast(){
		Assert.assertTrue("str".equals(StringUtil.removeLast("str1")));
		Assert.assertTrue("".equals(StringUtil.removeLast("s")));
	}
	
	@Test
	public void testGetFirstPinYin(){
		Assert.assertTrue("Z".equals(StringUtil.getFirstPinYin("张")));
		Assert.assertTrue(StringUtil.getFirstPinYin("zhang") == null);
	}
	
	@Test
	public void testGetSafeStr(){
		Assert.assertTrue("c".equals(StringUtil.getSafeStr(null, "c")));
		Assert.assertTrue("c".equals(StringUtil.getSafeStr("", "c")));
		Assert.assertTrue("d".equals(StringUtil.getSafeStr("d", "c")));
	}
	
}
