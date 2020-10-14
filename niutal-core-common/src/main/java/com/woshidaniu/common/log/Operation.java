/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.log;

/**
 * 
 *@类名称	: Operation.java
 *@类描述	：操作类型枚举对象
 *@创建人	：kangzhidong
 *@创建时间	：Mar 21, 2016 11:43:36 AM
 *@修改人	：
 *@修改时间	：
 *@版本号	:v1.0
 */
public enum Operation {

	/**
	 * 查询操作
	 */
	OP_SELECT {
		public String toString() {
			return "select:查询";
		}
	},
	/**
	 * 刪除 操作
	 */
	OP_DELETE {
		public String toString() {
			return "delete:删除";
		}
	},
	/**
	 * 更改操作
	 */
	OP_UPDATE {
		public String toString() {
			return "update:更新";
		}
	},
	/**
	 * 插入操作
	 */
	OP_INSERT {
		public String toString() {
			return "insert:新增";
		}
	},
	/**
	 * 登录操作
	 */
	OP_LOGIN {
		public String toString() {
			return "login:登录";
		}
	},
	/**
	 * 登出操作
	 */
	OP_LOGOUT {
		public String toString() {
			return "logout:注销";
		}
	},
	/**
	 * 系统操作描述确认
	 */
	OP_SURE {
		public String toString() {
			return "sure:系统操作描述确认";
		}
	}
	
}
