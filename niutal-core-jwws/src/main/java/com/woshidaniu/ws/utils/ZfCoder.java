/**
 * 
 */
package com.woshidaniu.ws.utils;

import java.security.MessageDigest;


/**
 * @author Administrator lwz
 *
 */
public class ZfCoder {

	public static String encryptkey(String data) {
		return "{MD5}" + testHA2(data, "md5");
	}

	public static String testHA2(String data, String ha) {
		byte[] buffer = null;
		MessageDigest messageDigest = null;
		String s = "";
		try {
			buffer = data.getBytes("UTF-8");
			messageDigest = MessageDigest.getInstance(ha);
			messageDigest.update(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer = messageDigest.digest();
		s = new sun.misc.BASE64Encoder().encode(buffer);
		return s;
	}
	
	
	//调用
	public static void main (String[] args){
		String test = "test";
		System.out.println(ZfCoder.encryptkey(test).length()	);
	}
}
