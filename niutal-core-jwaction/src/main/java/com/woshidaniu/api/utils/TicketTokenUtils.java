package com.woshidaniu.api.utils;

import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.basicutils.BlankUtils;
import com.woshidaniu.basicutils.DateUtils;
import com.woshidaniu.basicutils.TimeUtils;
import com.woshidaniu.basicutils.URLUtils;
import com.woshidaniu.common.ZFtalParameter;
import com.woshidaniu.common.ZFtalParameters;
import com.woshidaniu.common.constant.BaseConstant;
import com.woshidaniu.security.algorithm.DesBase64Codec;
import com.woshidaniu.security.algorithm.DesBase64Utils;
/**
 * 
 *@类名称		： SecurityUtils.java
 *@类描述		：系统对接接口appid和token生成和校验
 *@创建人		：kangzhidong
 *@创建时间	：Sep 6, 2016 4:18:20 PM
 *@修改人		：
 *@修改时间	：
 *@版本号	:v2.0.0
 */
public class TicketTokenUtils {
	
	protected static DesBase64Codec codec = new DesBase64Codec();
	protected static Logger LOG = LoggerFactory.getLogger(TicketTokenUtils.class);
	/**
	 * 
	 *@描述		：根据给出的学校代码生成appid,该appid应当不管何时计算都是唯一的值
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20164:19:07 PM
	 *@param xxdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static String genAppid(String xxdm) {
		
		return Hex.encodeHexString(Base64.encodeBase64(codec.encrypt(xxdm).getBytes()));
		//return UUID.randomUUID().toString();
	}
	
	public static String genSecret(String xxdm) {
		return new String(Base64.encodeBase64(xxdm.getBytes()));
	}

	/**
	 * 
	 *@描述		：根据给出的学校代码生成token,该token应当不管何时计算都是唯一的值
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20164:20:32 PM
	 *@param xxdm
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static String genToken(String appid,String secret,String xxdm,String dbtime) {
		if(BlankUtils.isBlank(xxdm)){
			xxdm = BaseConstant.DEFAULT_XXDM;
		}
		//return Base64.encodeBase64String((xxdm + "-" + UUID.randomUUID().toString()).getBytes());
		//数据库时间
		Date now = DateUtils.parseDate(dbtime, DateUtils.DATE_FORMAT_TWO);
		return DesBase64Utils.encrypt(xxdm + "-" + DateUtils.format(now, DateUtils.DATE_FORMAT_LONG) + "-" + UUID.randomUUID().toString());
		//return codec.encrypt(xxdm + "-" + DateUtils.format(now, DateUtils.DATE_FORMAT_LONG) + "-" + UUID.randomUUID().toString());
	}
	
	/**
	 * 
	 *@描述		：
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 8, 201612:05:35 PM
	 *@param userid	: 用户ID
	 *@param roleid	: 角色ID，xs,js：方便区别用户角色
	 *@param timestamp ： 时间戳;格式: yyyyMMddHHmmssSSS
	 *@param token : 系统双方约定的秘钥:基于Des + Base64加密的值
	 *@return : MD5加密信息（大写）:格式为：(卡号-用户类型-时间戳-token)值组合的MD5值
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static String genVerify(String userid,String roleid,String timestamp,String token) {
		token = token.contains("%") ? URLUtils.unescape(token) : token;
		return DigestUtils.md5Hex(userid + "-" + roleid + "-" + timestamp + "-" + token);
	}
	
	/**
	 * 
	 *@描述		：
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 8, 201612:22:26 PM
	 *@param domian : 应用访问域名地址
	 *@param token  : 系统双方约定的秘钥:基于Des + Base64加密的值
	 *@param verify : MD5加密信息（大写）:格式为：(卡号-用户类型-时间戳-token)值组合的MD5值
	 *@param userid	: 用户ID
	 *@param roleid	: 角色ID，xs,js：方便区别用户角色
	 *@param timestamp ： 时间戳;格式: yyyyMMddHHmmssSSS
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static String genTicketLoginURL(String domian,String token,String verify,String userid,String roleid,String timestamp) {
		StringBuilder builder = new StringBuilder(domian);
		//应用访问路径
		builder.append("/api/login_tickitLogin.html");
		builder.append("?token=").append(token);
		builder.append("&verify=").append(verify);
		builder.append("&userid=").append(userid);
		builder.append("&roleid=").append(roleid);
		builder.append("&time=").append(timestamp);
		return builder.toString();
	}
	
	/**
	 * 
	 *@描述		：验证appid有效性
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20169:19:26 PM
	 *@param xxdm
	 *@param appid
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean validAppid(String xxdm,String appid) {
		if(appid == null){
			return false;
		}
		return genAppid(xxdm).equals(appid);
	}
	
	/**
	 * 
	 *@描述		：验证token有效性
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 7, 20164:41:48 PM
	 *@param xxdm
	 *@param token
	 *@param dbtime : 数据库时间；格式 yyyy-MM-dd HH24:mi:ss
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean validToken(String xxdm,String token,String dbtime) {
		if(token == null){
			return false;
		}
		if(BlankUtils.isBlank(xxdm)){
			xxdm = BaseConstant.DEFAULT_XXDM;
		}
		String tokenText = null;
		try {
			tokenText = codec.decrypt(token);
		} catch (Exception e) {
			tokenText = DesBase64Utils.decrypt(token);
		}
		String[] tokenArr = tokenText.split("-");
		if(!xxdm.equals(tokenArr[0])){
			return false;
		}
		//以下为校验token的有效期逻辑
		Date time = DateUtils.parseDate(tokenArr[1], DateUtils.DATE_FORMAT_LONG);
		//数据库时间
		Date now = DateUtils.parseDate(dbtime);
		//票据登录（通过握手秘钥等参数认证登录）中的token失效时间；可使用单位 (s:秒钟,m:分钟,h:小时,d:天)；如10m表示10分钟
		long period = TimeUtils.getTimeMillis(ZFtalParameters.getGlobalString(ZFtalParameter.LOGIN_TICKET_TOKEN_PERIOD));
		//提交过来的时间戳大于当前数据时间或超过有效期
		if( (time.getTime() > now.getTime()) || (now.getTime() - time.getTime()) > period ){
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 *@描述		：验证参数有效性
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20168:54:09 PM
	 *@param verify
	 *@param toValid : 卡号-用户类型-时间戳-token值组合
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean validVerify(String verify,String toValid) {
		if( verify == null || toValid == null){
			return false;
		}
		return verify.toUpperCase().equals(DigestUtils.md5Hex(toValid).toUpperCase());
	}
	
	/**
	 * 
	 *@描述		：验证时间戳的有效性 
	 *@创建人		: kangzhidong
	 *@创建时间	: Sep 6, 20169:21:32 PM
	 *@param timestamp ： 时间戳;格式: yyyyMMddHHmmssSSS
	 *@return
	 *@修改人		: 
	 *@修改时间	: 
	 *@修改描述	:
	 */
	public static boolean validTimestamp(String timestamp,String dbtime) {
		try {
			if( timestamp == null ){
				return false;
			}
			//获取转换后的date对象
			Date time = DateUtils.parseDate(timestamp, DateUtils.DATE_FORMAT_LONG);
			//数据库时间
			Date now = DateUtils.parseDate(dbtime, DateUtils.DATE_FORMAT_TWO);
			//票据登录（通过握手秘钥等参数认证登录）中的时间戳允许时差范围；可使用单位 (s:秒钟,m:分钟,h:小时,d:天)；如1m表示1分钟
			long period = TimeUtils.getTimeMillis(ZFtalParameters.getGlobalString(ZFtalParameter.LOGIN_TICKET_TIMESTAMP_PERIOD));
			//提交过来的时间戳大于当前数据时间或超过有效期
			if( (time.getTime() > now.getTime()) || (now.getTime() - time.getTime()) > period ){
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	public static void main(String[] args) {
		
		String xxdm = "10427";//10427,00000
		
		String gendbtime = "2016-09-18 10:21:57";
		
		String timestamp = "20160918102135554";
		
		String dbtime = "2016-09-18 10:21:57";
		
		String userid = "000020063586";
		String roleid = "js";
		String domain = "http://10.71.33.192:8080/jwglxt";
		
		
		String appid = TicketTokenUtils.genAppid(xxdm);
		String secret = TicketTokenUtils.genSecret(xxdm);
		
		
		
		System.out.println("appid:" + appid);
		System.out.println("secret:" + secret);
		
		String token = TicketTokenUtils.genToken(appid , secret , xxdm ,  gendbtime );
		System.out.println("token:" + token);
		
		String verify = TicketTokenUtils.genVerify( userid , roleid, timestamp, token );
		System.out.println("verify:" + verify);
		
		
		System.out.println("validAppid:" + TicketTokenUtils.validAppid(xxdm, appid));
		//yyyy-MM-dd HH24:mi:ss
		System.out.println("validToken:" + TicketTokenUtils.validToken(xxdm, token , dbtime));
		System.out.println("validTimestamp:" + TicketTokenUtils.validTimestamp(timestamp, dbtime));
		
		System.out.println("ticketLoginURL: " + TicketTokenUtils.genTicketLoginURL(domain ,token , verify, userid , roleid, timestamp));
	}
	
}

