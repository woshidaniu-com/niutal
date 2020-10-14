package com.woshidaniu.service.utils;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;

import com.woshidaniu.web.context.WebContext;
/**
 * 
 *@类名称: PercentageUtils.java
 *@类描述：完成比计算工具
 *@创建人：kangzhidong
 *@创建时间：Sep 25, 2015 3:10:24 PM
 *@版本号:v1.0
 */
public class PercentageUtils {
	
	public static BigDecimal hundred  = new BigDecimal(100);
	
	/**
	 * 
	 *@描述： 根据提供的参数自动计算完成百分比并以指定的key将值放到当前Session中
	 *@创建人:kangzhidong
	 *@创建时间:Sep 25, 20153:07:18 PM
	 *@param completeCount	： 原子计数对象；用于记录已处理总数
	 *@param maxCount		：大数字对象；记录总共需要处理记录数
	 *@param sessionKey		：百分比结果放入session中的key
	 */
	public static void setPercentage(AtomicInteger completeCount,BigDecimal maxCount,String sessionKey){
		//设置标识到session中
		PercentageUtils.setPercentage(WebContext.getSession(), completeCount, maxCount, sessionKey);
	}
	
	public static void setPercentage(HttpSession session,AtomicInteger completeCount,BigDecimal maxCount,String sessionKey){
		//成功记录数累加
		completeCount.incrementAndGet();
		//已完成百分比
		float percentum = new BigDecimal(completeCount.get()).divide(maxCount,10, BigDecimal.ROUND_HALF_UP).multiply(hundred).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();   
		//设置标识到session中
		session.setAttribute(sessionKey, percentum);
	}
	
	public static void main(String[] args) {
		int i = 100;
		while ( i > 0) {
			System.out.println("dddd:" + i);
			i --;
			return;
		}
	}
}
