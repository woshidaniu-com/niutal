package com.woshidaniu.util.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * <p>
 *   <h3>niutal框架<h3>
 *   说明：产生唯一的ID值
 * <p>
 * 
 * @deprecated
 * @see com.woshidaniu.basicutils.UniqID
 * @author <a href="mailto:337836629@qq.com">Penghui.Qu[445]<a>
 * @version 2016年6月8日上午10:02:05
 */
@Deprecated
public class UniqID {
	
    private static final Logger logger    = LoggerFactory.getLogger(UniqID.class);
    
    private static char[] digits = {
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    
    
    /*使用静态内部类，延迟加载单例对象*/
    private static class SingleFactory{
    	public static UniqID me = new UniqID();
    }
    
    
    private MessageDigest mHasher;

    private UniqID() {
        try {
            mHasher = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException nex) {
            mHasher = null;
            logger.error("[UniqID]new MD5 Hasher error", nex);
        }
    }

    public static UniqID getInstance() {
        return SingleFactory.me;
    }

     /**
      * 
      * <p>方法说明：生成唯一ID<p>
      * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
      * <p>时间：2016年6月8日上午10:03:00<p>
      * @return Stirng ID
      */
    public String getUniqID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 
     * <p>方法说明：生成唯一ID，使用加密算法<p>
     * <p>作者：<a href="mailto:337836629@qq.com">Penghui.Qu[445]<a><p>
     * <p>时间：2016年6月8日上午10:02:21<p>
     * @return Stirng ID
     */
    public String getUniqIDHash() {
        return hash(getUniqID());
    }
    
    private synchronized String hash(String str) {
        byte[] bt = mHasher.digest(str.getBytes());
        int    l = bt.length;

        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++]     = digits[(0xF0 & bt[i]) >>> 4];
            out[j++]     = digits[0x0F & bt[i]];
        }

        if (logger.isDebugEnabled()) {
            logger.debug("[UniqID.hash]" + (new String(out)));
        }
        
        return new String(out);
    }

}
