package com.woshidaniu.util.encrypt;

import java.security.MessageDigest;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：加密用到的工具类,MD5加密
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月17日上午9:51:58
 */
@Deprecated
public class Encrypt {
	

	/**
	 * 
	 * <p>方法说明：MD5加密<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午10:02:37<p>
	 * @param PlainStr String
	 * @return String
	 */
	public static String encrypt(String PlainStr) {
		return "{MD5}" + testHA2(PlainStr, "md5");
	}
	
	private static String testHA2(String data, String ha) {
		byte[] buffer = null;
		MessageDigest messageDigest = null;
		try {
			buffer = data.getBytes("UTF-8");
			messageDigest = MessageDigest.getInstance(ha);
			messageDigest.update(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer = messageDigest.digest();
		return new String(Base64.encodeBase64(buffer));
	}

	/**
	 * 
	 * <p>方法说明：DES解密
	 * 	<br>已弃用该方法，使用{@link com.woshidaniu.util.encrypt.DBEncrypt}中的dCode()
	 * <p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午10:02:58<p>
	 * @param bytes byte[]
	 * @return String
	 */
	@Deprecated
	public static String dCode(byte bytes[]){
		try {
			Cipher deCipher = Cipher.getInstance("DES");
			deCipher.init(2, Key.loadKey());
			byte strByte[] = deCipher.doFinal(Base64.decodeBase64(bytes));
			return new String(strByte);
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
	}
}