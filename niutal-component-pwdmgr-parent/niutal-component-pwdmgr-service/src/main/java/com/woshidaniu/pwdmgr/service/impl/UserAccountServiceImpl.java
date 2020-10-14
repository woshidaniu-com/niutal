package com.woshidaniu.pwdmgr.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.woshidaniu.api.data.UserAccountProvider;
import com.woshidaniu.api.entity.UserAccount;
import com.woshidaniu.basemodel.BaseMap;
import com.woshidaniu.common.service.BaseServiceImpl;
import com.woshidaniu.pwdmgr.api.model.BindData;
import com.woshidaniu.pwdmgr.dao.daointerface.UserAccountDao;
import com.woshidaniu.pwdmgr.service.svcinterface.UserAccountService;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccount, UserAccountDao> implements UserAccountService{
	
	private static final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);
	
	@Autowired
	protected UserAccountDao userAccountDao;
	
	@Autowired(required = false)
	protected UserAccountProvider userAccountProvider;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		super.setDao(userAccountDao);
	}
	
	@Override
	public BaseMap getUserAccount(BindData data) {
		
		Map<String, String> statusMap = null;
		//查询用户状态
		if(this.userAccountProvider != null) {
			statusMap =  this.userAccountProvider.getAccountStatus(data.getUsername());
		}else {
			statusMap = this.userAccountDao.getAccountStatus(data.getUsername());
		}
		//管理用户或学生用户
		String num1 = statusMap.get("NUM_1");
		String num2 = statusMap.get("NUM_2");
		String num3 = statusMap.get("NUM_3");
		if("1".equals(num1) || "1".equals(num2)||"1".equals(num3)){
   			//处理原始数据
   			Map<String, Object> map = new HashMap<String, Object>(data.getMap());
   			map.put("username", data.getUsername());
   			//用户类型：1：管理用户，0：学生用户,3:专升本用户
   			if("1".equals(statusMap.get("NUM_1"))){
   				map.put("usertype",  "1");
   			}else if("1".equals(statusMap.get("NUM_2"))){
   				map.put("usertype",  "0");
   			}else if("1".equals(statusMap.get("NUM_3"))){
   				map.put("usertype",  "3");
   			}
   			//用户类型：1：管理用户，0：学生用户
   			//map.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			//查询用户信息
   			BaseMap baseMap = null;
   			if(this.userAccountProvider != null) {
   				baseMap = this.userAccountProvider.getUserAccount(map);
   			}else {
   				baseMap = this.userAccountDao.getUserAccount(map);
   			}
   			if(baseMap != null){
   			    //用户类型：1：管理用户，0：学生用户,3:专升本用户
   				baseMap.put("username", data.getUsername());
   				if("1".equals(statusMap.get("NUM_1"))){
   					baseMap.put("usertype", "1");
   	   			}else if("1".equals(statusMap.get("NUM_2"))){
   	   				baseMap.put("usertype",  "0");
   	   			}else if("1".equals(statusMap.get("NUM_3"))){
   	   				baseMap.put("usertype",  "3");
   	   			}
   	   			//用户类型：1：管理用户，0：学生用户
   				//baseMap.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			}
   			return baseMap;
   		}else {//无效用户
   			int number1 = Integer.parseInt(num1);
   			int number2 = Integer.parseInt(num2);
   			if(num3 == null) {
   				num3 = "0";
   			}
   			int number3 = Integer.parseInt(num3);
   			if(number1 > 1 || number2 > 1 || number3 > 1) {
   				log.error("存在超过一个用户具有相同的用户名{}",data.getUsername());
   			}
   			return null;
		}
	}
	
	@Override
	public BaseMap getUserAccountByEmail(BindData data) {
		
		Map<String, String> statusMap = null;
		//查询用户状态
		if(this.userAccountProvider != null) {
			statusMap =  this.userAccountProvider.getAccountStatusByEmail(data.getEmail());
		}else {
			statusMap = this.userAccountDao.getAccountStatusByEmail(data.getEmail());
		}
		//管理用户或学生用户
		String num1 = statusMap.get("NUM_1");
		String num2 = statusMap.get("NUM_2");
		String num3 = statusMap.get("NUM_3");
		if("1".equals(num1) || "1".equals(num2)||"1".equals(num3)){
   			//处理原始数据
   			Map<String, Object> map = new HashMap<String, Object>(data.getMap());
   			map.put("email", data.getEmail());
   			//用户类型：1：管理用户，0：学生用户,3:专升本用户
   			if("1".equals(statusMap.get("NUM_1"))){
   				map.put("usertype",  "1");
   			}else if("1".equals(statusMap.get("NUM_2"))){
   				map.put("usertype",  "0");
   			}else if("1".equals(statusMap.get("NUM_3"))){
   				map.put("usertype",  "3");
   			}
   			//用户类型：1：管理用户，0：学生用户
   			//map.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			//查询用户信息
   			BaseMap baseMap = null;
   			if(this.userAccountProvider != null) {
   				baseMap = this.userAccountProvider.getUserAccountByEmail(map);
   			}else {
   				baseMap = this.userAccountDao.getUserAccountByEmail(map);
   			}
   			if(baseMap != null){
   			    //用户类型：1：管理用户，0：学生用户,3:专升本用户
   				if("1".equals(statusMap.get("NUM_1"))){
   					baseMap.put("usertype", "1");
   					baseMap.put("username", baseMap.get("zgh"));
   	   			}else if("1".equals(statusMap.get("NUM_2"))){
   	   				baseMap.put("usertype",  "0");
   	   				baseMap.put("username", baseMap.get("xh"));
   	   			}else if("1".equals(statusMap.get("NUM_3"))){
   	   				baseMap.put("usertype",  "3");
   	   				baseMap.put("username", baseMap.get("xh"));
   	   			}
   	   			//用户类型：1：管理用户，0：学生用户
   				//baseMap.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			}
   			return baseMap;
   		}else {//存在多个用户留存了相同的email
   			int number1 = Integer.parseInt(num1);
   			int number2 = Integer.parseInt(num2);
   			if(num3 == null) {
   				num3 = "0";
   			}
   			int number3 = Integer.parseInt(num3);
   			if(number1 > 1 || number2 > 1 || number3 > 1) {
   				log.error("存在超过一个用户具有相同的email{}",data.getEmail());
   			}
   			return null;
		}
	}

	@Override
	public BaseMap getUserAccountByPhone(BindData data) {
		Map<String, String> statusMap = null;
		//查询用户状态
		if(this.userAccountProvider != null) {
			statusMap =  this.userAccountProvider.getAccountStatusByPhone(data.getPhone());
		}else {
			statusMap = this.userAccountDao.getAccountStatusByPhone(data.getPhone());
		}
		//管理用户或学生用户
		String num1 = statusMap.get("NUM_1");
		String num2 = statusMap.get("NUM_2");
		String num3 = statusMap.get("NUM_3");
   		if("1".equals(num1) || "1".equals(num2)||"1".equals(num3)){
   			//处理原始数据
   			Map<String, Object> map = new HashMap<String, Object>(data.getMap());
   			map.put("phone", data.getPhone());
   			//用户类型：1：管理用户，0：学生用户,3:专升本用户
   			if("1".equals(statusMap.get("NUM_1"))){
   				map.put("usertype",  "1");
   			}else if("1".equals(statusMap.get("NUM_2"))){
   				map.put("usertype",  "0");
   			}else if("1".equals(statusMap.get("NUM_3"))){
   				map.put("usertype",  "3");
   			}
   			//用户类型：1：管理用户，0：学生用户
   			//map.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			//查询用户信息
   			BaseMap baseMap = null;
   			if(this.userAccountProvider != null) {
   				baseMap = this.userAccountProvider.getUserAccountByPhone(map);
   			}else {
   				baseMap = this.userAccountDao.getUserAccountByPhone(map);
   			}
   			if(baseMap != null){
   			    //用户类型：1：管理用户，0：学生用户,3:专升本用户
   				if("1".equals(statusMap.get("NUM_1"))){
   					baseMap.put("usertype", "1");
   					baseMap.put("username", baseMap.get("zgh"));
   	   			}else if("1".equals(statusMap.get("NUM_2"))){
   	   				baseMap.put("usertype",  "0");
   	   				baseMap.put("username", baseMap.get("xh"));
   	   			}else if("1".equals(statusMap.get("NUM_3"))){
   	   				baseMap.put("usertype",  "3");
   	   				baseMap.put("username", baseMap.get("xh"));
   	   			}
   	   			//用户类型：1：管理用户，0：学生用户
   				//baseMap.put("usertype", "1".equals(statusMap.get("NUM_1")) ? "1" : "0");
   			}
   			return baseMap;
   		}else {//存在多个用户留存了相同的手机号码
   			int number1 = Integer.parseInt(num1);
   			int number2 = Integer.parseInt(num2);
   			if(num3 == null) {
   				num3 = "0";
   			}
   			int number3 = Integer.parseInt(num3);
   			if(number1 > 1 || number2 > 1 || number3 > 1) {
   				log.error("存在超过一个用户具有相同的手机号码{}",data.getPhone());
   			}
   			return null;
		}
	}
	
	@Override
	public boolean updateAccount(Map<String, Object> map) {
		if(this.userAccountProvider != null){
			this.userAccountProvider.updateAccount(map);
		} else {
			this.userAccountDao.updateAccount(map);
		}
		return true;
	}
}
