package com.woshidaniu.pwdmgr.dao.entities;

/**
 * <p class="detail">
 * 功能：用户账号表:用户使用账号登录，一个用户对应多个账号表
 * </p>
 * @ClassName: UserAccount 
 * @version V2.0  
 * @date 2017年3月13日 
 * @author liudd
 * Copyright 1999-2020 woshidaniu, Inc. All rights reserved
 */
public class UserAccount {
	
	private String id;	
	
	/**
	 * 用户名
	 */
	private String username;	
	
	/**
	 * 密码
	 */
	private String password;	
	
	/**
	 * 账号状态
	 */
	private Boolean status;
	
	/**激活时间
	 * 
	 */
	private String active_time;	
	
	/**
	 * 最后更新时间
	 */
	private String last_update_time;	
	/**
	 * 密码过期时间
	 */
	private String pwd_expiredtime;		
	/**
	 * 	所属用户
	 */
	private String userid;				

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getActive_time() {
		return active_time;
	}

	public void setActive_time(String active_time) {
		this.active_time = active_time;
	}

	public String getLast_update_time() {
		return last_update_time;
	}

	public void setLast_update_time(String last_update_time) {
		this.last_update_time = last_update_time;
	}

	public String getPwd_expiredtime() {
		return pwd_expiredtime;
	}

	public void setPwd_expiredtime(String pwd_expiredtime) {
		this.pwd_expiredtime = pwd_expiredtime;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
