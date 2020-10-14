package com.woshidaniu.ws.svcinterface;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 *@类名称: IDataQueryWebService.java
 *@类描述：系统基础数据查询webServce接口
 *@创建人：kangzhidong
 *@创建时间：2015-3-30 下午04:22:35
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@WebService(name = "sjcjService")  
public interface IDataQueryWebService {
	
	/**
	 * 获取学生成绩信息
	 * @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getXscjList( @WebParam(name="yhm") String yhm,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign );
	
	
	/**
	 * 获取通知公告
	 * @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getTzggList( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign );
	
	/**
	 * 获取学生课表信息
	 * @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getXskbList( @WebParam(name="yhm") String yhm,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign );
	
	/**
	 * 获取教师课表信息
	 * @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getJskbList( @WebParam(name="yhm") String yhm,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign );
	
	/**
	 * 获取用户信息
	 * @param yhm 用户名
	 * @param yhlx 用户类型
	 * @param sign 验证签名
	 */
	public String getYhxxIndex( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,@WebParam(name="sign") String sign );
	
	/**
	 *@描述：待办事宜
	 *@创建人:"huangrz"
	 *@创建时间:2014-9-22上午10:07:06
	* @param yhm 用户名
	 * @param num 获取记录数
	 * @param sign 验证签名
	 * @return num个表单
	 */
	public String getDbsyList( @WebParam(name="yhm") String yhm,@WebParam(name="yhlx") String yhlx,@WebParam(name="num") Integer num, @WebParam(name="sign") String sign );
	
}
