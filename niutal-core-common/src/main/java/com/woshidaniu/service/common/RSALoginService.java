package com.woshidaniu.service.common;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.security.RSAUtils;


/*******RSA登录工具**********/
public class RSALoginService {

	private static final String PRIVATE_KEY_ATTRIBUTE_NAME = "privateKey";

	/*****生成密钥对，返回公钥、私钥放session********/
	public RSAPublicKey generateKey(HttpServletRequest request) {
		KeyPair keyPair = RSAUtils.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		HttpSession session = request.getSession();
		session.setAttribute(PRIVATE_KEY_ATTRIBUTE_NAME, privateKey);
		return publicKey;
	}

	/*******从session中清除私钥*************/
	public void removePrivateKey(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(PRIVATE_KEY_ATTRIBUTE_NAME);
	}

	
	/*********解密字符串*****************/
	public String decryptParameter(String parameter, HttpServletRequest request) {
		if (parameter != null) {
			HttpSession session = request.getSession();
			RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute(PRIVATE_KEY_ATTRIBUTE_NAME);
			if (privateKey != null && StringUtils.isNotEmpty(parameter)) {
				return RSAUtils.decrypt(privateKey, parameter);
			}
		}
		return null;
	}
	
}