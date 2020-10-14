package com.woshidaniu.util.encrypt;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：数据库加、解密工具
 * <p>
 * @deprecated
 * @see com.woshidaniu.security.algorithm.DesBase64Codec
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月17日上午9:58:50
 */
@Deprecated
public class DBEncrypt {


	/**
	 * 
	 * <p>方法说明：加密<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午10:00:07<p>
	 * @param needEncrypt String
	 * @return String
	 */
	public String eCode(String needEncrypt){
		byte result[] = null;
		try {
			Cipher enCipher = Cipher.getInstance("DES");
			javax.crypto.SecretKey key = Key.loadKey();
			enCipher.init(1, key);
			result = enCipher.doFinal(needEncrypt.getBytes());
			result = Base64.encodeBase64(result);
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return new String(result);
	}

	
	/**
	 * 
	 * <p>方法说明：解密<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午10:00:30<p>
	 * @param bytes byte[]
	 * @return String
	 */
	public String dCode(byte bytes[]){
		String s = null;
		try {
			Cipher deCipher = Cipher.getInstance("DES");
			deCipher.init(2, Key.loadKey());
			byte[] result = Base64.decodeBase64(bytes);
			byte strByte[] = deCipher.doFinal(result);
			s = new String(strByte);
		} catch (Exception e) {
			throw new IllegalStateException("System doesn't support DES algorithm.");
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(new DBEncrypt().eCode("niutal_demo"));
	}
}