/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 *@类名称	: JCSJConstant.java
 *@类描述	：基础数据常量
 *@创建人	：kangzhidong
 *@创建时间	：Mar 23, 2016 1:48:51 PM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public abstract class JCSJConstant {

	/**
	 * 1	0001	学期代码表	xt
	 */
	public static final String XQDMB = "0001";
	
	/**
	 * 2	0002	审核状态代码表	xt
	 */
	public static final String SHZTDMB = "0002";
	
	/**
	 * 3	0003	审核结果代码表	xt
	 */
	public static final String SHJGDMB = "0003";
	/**
	 * 4	0004	是否对照表	xt
	 */
	public static final String SFDZB = "0004";
	/**
	 * 5	0005	证件类型代码表	xt
	 */
	public static final String ZJLXDMB = "0005";
	/**
	 * 6	0006	性别代码表	xt
	 */
	public static final String XBDMB = "0006";
	/**
	 * 7	0007	民族代码表	xt
	 */
	public static final String MZDMB = "0007";
	/**
	 * 8	0008	政治面貌代码表	xt
	 */
	public static final String ZZMMDMB = "0008";
	/**
	 * 9	0009	血型代码表	xt
	 */
	public static final String XXDMB = "0009";
	/**
	 * 10	0010	健康状况代码表	xt
	 */
	public static final String JKZKDMB = "0010";
	/**
	 * 11	0011	婚姻状况代码表	xt
	 */
	public static final String HYZKDMB = "0011";
	/**
	 * 12	0012	港澳台侨外代码表	xt
	 */
	public static final String GATQWDMB = "0012";
	/**
	 * 13	0013	高考科目代码表	xt
	 */
	public static final String GKKMDMB = "0013";
	/**
	 * 14	0014	培养层次代码表	xt
	 */
	public static final String PYCCDMB = "0014";
	/**
	 * 15	0015	培养方式代码表	xt
	 */
	public static final String PYFSDMB = "0015";
	/**
	 * 16	0016	学生类别代码表	xt
	 */
	public static final String XSLBDMB = "0016";
	/**
	 * 17	0017	学生来源代码表	xt
	 */
	public static final String XSLYDMB = "0017";
	/**
	 * 18	0018	处分名称代码表	xt
	 */
	public static final String CFMCDMB = "0018";
	/**
	 * 19	0019	奖励方式代码表	xt
	 */
	public static final String JLFSDMB = "0019";
	/**
	 * 20	0020	奖励级别代码表	xt
	 */
	public static final String JLJBDMB = "0020";
	/**
	 * 21	0021	奖励等级代码表	xt
	 */
	public static final String JLDJDMB = "0021";
	/**
	 * 22	0022	学籍异动原因代码表	xt
	 */
	public static final String XJYDYYDMB = "0022";
	/**
	 * 23	0023	家庭关系代码表	xt
	 */
	public static final String JTGXDMB = "0023";
	/**
	 * 24	0024	奖励类型代码表	yw
	 */
	public static final String JLLXDMB = "0024";
	/**
	 * 25	0025	角色类型代码表	yw
	 */
	public static final String JSLXDMB = "0025";
	
	/**
	 * 26	0026	学生获奖类别代码	yw
	 */
	public static final String HJLBDMB = "0026";
	
	/**
	 * 27	0027	学科门类代码	xt
	 */
	public static final String XKMLDMB = "0027";
	
	/**
	 * 28	0028	学籍状态代码代码	yw
	 */
	public static final String XJZTDMB = "0028";
	/**
	 * 33	0033	考试形式	yw
	 */
	public static final String KSXSDMB = "0033";
	/**
	 * 34	0034	考试性质	wwb
	 */
	public static final String KSXZDMB = "0034";
	/**
	 * 35	0035	考试方式	wwb
	 */
	public static final String KSFSDMB = "0035";
	/**
	 * 31	0035	授课方式	gc
	 */
	public static final String SKFSDMB = "0031";
	/**
	 * 32	0032	教学模式	gc
	 */
	public static final String JXMSDMB = "0032";	
	
	public static Map<String,String> getTableMap(){
		final Map<String,String> tableMap = new HashMap<String,String>();
		tableMap.put("table_001", "jw_xjgl_jlxmdmb");  //奖励名称表
		tableMap.put("table_002", "jw_xjgl_xjydlbdmb"); //学籍异动类别
		tableMap.put("table_003", "jw_xjgl_xsjlxmlxdmb"); //交流项目类型
		tableMap.put("table_004", "jw_gg_mxdxb"); //公共面向对象
		tableMap.put("table_005", "jw_xmgl_xmbmtjmxdxb"); //公共面向对象
		tableMap.put("table_006", "jw_xk_yxkzmxdxb"); //预选控制面向对象
		tableMap.put("table_007", "jw_jh_kcyxmxb"); //课程预选面向表
		tableMap.put("table_008", "jw_xjgl_jsxyzzytjmxb"); //接受学院转专业条件面向表
		return tableMap;
	}
	
}
