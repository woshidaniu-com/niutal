package com.woshidaniu.util.encrypt;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：DES加密KEY
 * <p>
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月17日上午10:08:55
 */
@Deprecated
public class Key{
	
	private static String encryptKey = "7EV/Zzutjzg=";

	/**
	 * 
	 * <p>方法说明：加载KEY<p>
	 * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
	 * <p>时间：2016年6月17日上午10:09:11<p>
	 * @return SecretKey
	 * @throws Exception
	 */
	public static SecretKey loadKey() throws Exception{
		byte b[] = Base64.decodeBase64(encryptKey);
		DESKeySpec dks = new DESKeySpec(b);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		return  keyFactory.generateSecret(dks);
	}
}