package com.woshidaniu.util.base;

import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：PingYinUtils 单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月12日下午5:46:10
 */
public class PingYinUtilsTest {

	@Test
	public void testConverterToFUllSpell(){
		Assert.assertEquals(PingYinUtils.converterToFUllSpell("中华人民共和国"), "zhong hua ren min gong he guo ");
		Assert.assertEquals(PingYinUtils.converterToFUllSpell("中華人民共和國"), "zhong hua ren min gong he guo ");
		Assert.assertEquals(PingYinUtils.converterToFUllSpell("zhong hua ren min gong he guo"), "zhong hua ren min gong he guo");
	}
	
	
	@Test
	public void testConverterToFirstSpell(){
		Assert.assertEquals(PingYinUtils.converterToFirstSpell("中华人民共和国"), "zhrmghg");
		Assert.assertEquals(PingYinUtils.converterToFirstSpell("中華人民共和國"), "zhrmghg");
		Assert.assertEquals(PingYinUtils.converterToFirstSpell("zhong hua ren min gong he guo"), "zhong hua ren min gong he guo");
	}
	
	
	@Test
	public void testStringToPinyin(){
		Assert.assertArrayEquals(PingYinUtils.stringToPinyin("中华人民共和国"), new String[]{"zhong","hua","ren","min","gong","he","guo"});
		Assert.assertArrayEquals(PingYinUtils.stringToPinyin("中華人民共和國"), new String[]{"zhong","hua","ren","min","gong","he","guo"});
		Assert.assertArrayEquals(PingYinUtils.stringToPinyin("重",true,","), new String[]{"zhong,chong"});
	}
}
