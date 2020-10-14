/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.global;

/**
 *@类名称:GlobalSysValues.java
 *@类描述：系统内置项设置常量
 *@创建人：majun
 *@创建时间：2014-7-18 上午08:36:35
 *@版本号:v1.0
 */

public enum GlobalSysValues {
	/**
	 * 使用学期设置
	 */
	SYXQSZ{
		public String toString(){
			return "syxqsz";
		}
	},
	/**
	 * 使用大学期
	 */
	SYDXQ{
		public String toString(){
			return "1";
		}
	},
	/**
	 * 使用小学期
	 */
	SYXXQ{
		public String toString(){
			return "0";
		}
	},
	/**
	 * 人机排课是否超学时
	 */
	PKSFCXS{
		public String toString(){
			return "pksfcxs";
		}
	},
	/**
	 * 教学班生成方式
	 */
	JXBSCFS{
		public String toString(){
			return "JXBSCFS";
		}
	},
	/**
	 * 考试学期控制
	 */
	KSXQKZ{
		public String toString(){
			return "KSXQKZ";
		}
	},
	/**
	 * 选课数据显示控制
	 */
	XKSJXSKZ{
		public String toString(){
			return "XKSJXSKZ";
		}
	},
	/**
	 * 是否办理缓考
	 */
	SFBLHK{
		public String toString(){
			return "SFBLHK";
		}
	},
	/**
	 * 机构的排序字段
	 */
	JGPXZD{
		public String toString(){
			return "JGPXZD";
		}
	},
	/**
	 * 任务标记
	 */
	RWBJ{
		public String toString(){
			return "RWBJ";
		}
	},
	/**
	 * 评价比例
	 */
	PJBL{
		public String toString(){
			return "PJBL";
		}
	},
	
	/**
	 * 执行计划学时
	 */
	ZXJHXS{
		public String toString(){
			return "ZXJHXS";
		}
	}
	
}
