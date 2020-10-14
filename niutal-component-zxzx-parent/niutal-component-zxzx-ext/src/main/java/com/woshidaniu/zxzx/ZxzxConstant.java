/**
 * 我是大牛软件股份有限公司
 */
package com.woshidaniu.zxzx;

/**
 * @类名 ZxzxConstant.java
 * @工号 [1036]
 * @姓名 xiaokang
 * @创建时间 2016 2016年5月18日 下午4:47:22
 * @功能描述 在线咨询模块常量
 * 
 */
public interface ZxzxConstant {
	
	static final String CSSZ_KG_DM = "CSSZ_KG_DM";
	
	static final String CSSZ_KSSJ_DM = "CSSZ_KSSJ_DM";
	
	static final String CSSZ_JSSJ_DM = "CSSZ_JSSJ_DM";
	
	//咨询模式，是否需要登陆才能提价咨询信息
	static final String CSSZ_ZXMS_DM = "CSSZ_ZXMS_DM";
	
	//登陆模式，嵌入:embend, 默认：default；
	static final String CSSZ_DLMS_DM = "CSSZ_DLMS_DM";
	
	static final String CSSZ_DLMS_EMBEND_DM = "embend";
	
	static final String CSSZ_DLMS_DEFAULT_DM = "default";
	
	static final String CSSZ_DLMS_NO_CONFIGED_DM = "not-configed";
	
	//当登陆模式为默认时，调用的地址链接
	static final String CSSZ_LOGIN_URL_DM = "CSSZ_LOGIN_URL_DM";
	
	//当登陆模式为嵌入时，提叫认证数据的地址
	static final String CSSZ_AUTH_URL_DM = "CSSZ_AUTH_URL_DM";
	
	static final String CSSZ_RMZXJSFS_DM = "CSSZ_RMZXJSFS_DM";
	
	static final String CSSZ_RMZXJSSJD_DM = "CSSZ_RMZXJSSJD_DM";
	
	static final String CSSZ_RMZXXSTS_DM = "CSSZ_RMZXXSTS_DM";
	
	static final String CSSZ_LXR_DM = "CSSZ_LXR_DM";
	
	static final String CSSZ_DH_DM = "CSSZ_DH_DM";
	
	static final String CSSZ_DZYX_DM = "CSSZ_DZYX_DM";
	
	static final String CSSZ_DZ_DM = "CSSZ_DZ_DM";
	/**
	 * 是否显示"我的问题"栏目
	 */
	static final String CSSZ_SFXSWDWT = "CSSZ_SFXSWDWT";
	/**
	 * 是否显示"我要提问"栏目
	 */
	static final String CSSZ_SFXSWYTW = "CSSZ_SFXSWYTW";
	/**
	 * 是否允许未登录状态就能查询问题的配置
	 */
	static final String CSSZ_SFYXWDLCXWT = "CSSZ_SFYXWDLCXWT";
}
