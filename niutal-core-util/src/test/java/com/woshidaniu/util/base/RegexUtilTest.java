package com.woshidaniu.util.base;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：RegexUtil 单元测试
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月15日下午5:21:00
 */
public class RegexUtilTest {

	@Test
	public void testIsContentMatche(){
//		("#(xh:学号,xm:姓名)", "(#\\(){1,}+(\\w+\\:.*{1,}+,*)+(\\))$"));
//		#(xh:学号,xm:姓名)", "(#\\(){1,}+(\\w+\\:.*{1,}+,*)+(\\))$"));
		String str = "a=#{searchModel.params.xb} or b =#{searchModel.params.xb} where 1=1 ";
		//(#\\{){1,}+(searchModel.params.*)+(\\})$
		System.out.println(str.replaceAll("#\\{[^\\}]*\\}", "?"));
//		
//		Pattern p=Pattern.compile("(#\\{){1,}+(searchModel.params.*)+(\\})"); 
//		Matcher m=p.matcher(str); 
//		System.out.println(m.replaceAll("*")); 

//		String html="<div><font  color='red'>example1</font></div>"; //可以是任何html文件源代码,但格式一定要正确 
//		Pattern p=Pattern.compile("<[^>]*>"); 
//		Matcher m=p.matcher(html); 
//		String result=m.replaceAll("？"); 
//		System.out.println(result); 
		
//		String html="a=#{searchModel.params.xb} or b =#{searchModel.params.xb} where 1=1"; //可以是任何html文件源代码,但格式一定要正确 
//		Pattern p=Pattern.compile("#\\{[^\\}]*\\}"); 
//		Matcher m=p.matcher(html); 
//		String result=m.replaceAll("？"); 
//		System.out.println(result); 
	}
}
