package com.woshidaniu.common.license;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;


public class ZftalProductNameProvider extends com.woshidaniu.license.DefaultProductNameProvider {

	/**
	 * 系统名称
	 */
	private String productName;
	public static final String DEFAULT_PRODUCT_NAME = "教学管理信息服务平台";
	 
	@Override
	public String myNameIs() {
		String myName = DEFAULT_PRODUCT_NAME;
		if(productName != null){
			myName = productName;
		}
		try {
			return new Base64().encodeAsString(myName.getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}


}
