/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.service.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @类名 PasswordService.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2015 2015年11月19日 上午11:10:43
 * @功能描述 系统密码服务工具(hash)
 * 
 */
public class PasswordServiceUtility {

	private static final Logger log = LoggerFactory.getLogger(PasswordServiceUtility.class);
	
	/**
	 * salt
	 */
	public static final String DEFAULT_SALT = "";
	
	/**
	 * hash 算法
	 */
	public static final String DEFAULT_HASH_ALGORITHMNAME = "md5";
	
	/**
	 * 迭代次数
	 */
	public static final int DEFAULT_HASH_ITERATIONS = 1;
	
	/**
	 * 编码方式
	 */
	public static final String[] ENCODINGS = {"base64","hex"};
	
	private String hashAlgorithmName = DEFAULT_HASH_ALGORITHMNAME;
	
	private String salt = DEFAULT_SALT;
	
	private int hashIterations = DEFAULT_HASH_ITERATIONS; 
	
	private String encoding = ENCODINGS[0];

	
	/**
	 * 
	 * @param prefix
	 * @param password
	 * @return
	 */
	public String getHashedPassword(String prefix,String password){
		return prefix + getHashedPassword(password);
	}
	
	/**
	 * 获取hash计算后的密码
	 * @param password
	 * @return
	 */
	public String getHashedPassword(String password){
		if(null == password){
			return null;
		}
		
		byte[] hash = hash(password);
		
		String returnVal = null;
		
		if(hash != null){
		
			/**
			 * 如果是base64编码
			 */
			if(ENCODINGS[0].equalsIgnoreCase(encoding)){
				returnVal = Base64.encodeBase64String(hash);
			/**
			 * 如果是HEX编码
			 */
			}else{
				returnVal = Hex.encodeHexString(hash);
			}
		}
		
		return returnVal;
	}
	
	/**
	 * hash 计算
	 * @param source
	 * @return
	 */
	private byte[] hash(String source){
		byte[] sourceByte = source.getBytes();
		byte[] saltByte = salt == null ? null : salt.getBytes();
		
		if(saltByte == null ){
			log.info("密码服务工具发生错误:salt的值为空，请先配置！");
			return null;
		}
		
		MessageDigest digest = getDigest(getHashAlgorithmName());
        if (salt != null) {
            digest.reset();
            digest.update(saltByte);
        }
        byte[] hashed = digest.digest(sourceByte);
        int iterations = hashIterations - DEFAULT_HASH_ITERATIONS; 
        for (int i = 0; i < iterations; i++) {
            digest.reset();
            hashed = digest.digest(hashed);
        }
        return hashed;
		
		
	}
	
	
	protected MessageDigest getDigest(String algorithmName) throws IllegalArgumentException {
        try {
            return MessageDigest.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            String msg = "No native '" + algorithmName + "' MessageDigest instance available on the current JVM.";
            throw new IllegalArgumentException(msg, e);
        }
    }
	
	
	public String getHashAlgorithmName() {
		return hashAlgorithmName;
	}

	public void setHashAlgorithmName(String hashAlgorithmName) {
		this.hashAlgorithmName = hashAlgorithmName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public int getHashIterations() {
		return hashIterations;
	}

	public void setHashIterations(int hashIterations) {
		this.hashIterations = hashIterations;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public PasswordServiceUtility() {
		super();
	}

	public PasswordServiceUtility(String hashAlgorithmName, String salt,
			int hashIterations, String encoding) {
		super();
		this.hashAlgorithmName = hashAlgorithmName;
		this.salt = salt;
		this.hashIterations = hashIterations;
		this.encoding = encoding;
	}
	
}
