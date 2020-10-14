package com.woshidaniu.util.base;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：JsonUtil 单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月13日上午9:28:25
 */
public class JsonUtilTest {

	public static class User{
		private String id;
		private String name;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
	
	@Test
	public void testJsonToModel(){
		User u = (User) JsonUtil.jsonToModel("{id:\"1\",name:\"zhangsan\"}", User.class);
		Assert.assertEquals("1", u.getId());
		Assert.assertEquals("zhangsan", u.getName());
	}
	
}
