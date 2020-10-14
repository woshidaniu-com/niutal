package com.woshidaniu.pwdmgr.api;

import java.text.SimpleDateFormat;

import org.junit.Test;

import com.woshidaniu.basicutils.RandomUtils;
import com.woshidaniu.basicutils.uid.Sequence;
import com.woshidaniu.pwdmgr.api.model.BindCaptcha;

import junit.framework.TestCase;

public class FormatTest extends TestCase{

	protected String format = "yyyy-MM-dd HH:mm:ss";
	protected SimpleDateFormat sdf = new SimpleDateFormat(format);
	protected Sequence sequence = new Sequence();
	
	public void wtestFormat() throws Exception {
		
		BindCaptcha bind =  new BindCaptcha(String.valueOf(sequence.nextId()) , RandomUtils.genRandomNum(6), sdf.format(System.currentTimeMillis()));
		
		System.out.println("uuid:" + bind.getUuid());
		System.out.println("Captcha:" + bind.getCaptcha());
		System.out.println("Timestamp:" + bind.getTimestamp());
		
	}
	
	public void testFormat1() throws Exception {
		
		System.err.println("23按到@qqA.com.cn".replaceAll("(\\w?)(\\w+)(\\w)(@\\w+\\.[a-z]+(\\.[a-z]+)?)", "$1****$3$4"));
		System.err.println("13012152140".replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		
	}
	
}
