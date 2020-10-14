package com.woshidaniu.designer.action;

import com.woshidaniu.common.action.BaseAction;
import com.woshidaniu.common.action.result.Result;
import com.woshidaniu.basicutils.BlankUtils;

/**
 * 
 *@类名称: QuerySQLParserAction.java
 *@类描述：查询参数解析：解析传递过来的SQL生成可用的参数
 *@创建人：kangzhidong
 *@创建时间：2015-4-28 上午09:44:39
 *@修改人：
 *@修改时间：
 *@版本号:v1.0
 */
@SuppressWarnings("serial")
public class QuerySQLParserAction extends BaseAction {

	/**
	 * 
	 *@描述：跳转至报表公共页面
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-19上午10:50:27
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxSQLParserResult(){
		try {
			String reportID = getRequest().getParameter("reportID");
			if(!BlankUtils.isBlank(reportID)){
				getValueStack().set("reportID", reportID);
				return SUCCESS;
			}else{
				getValueStack().set(Result.MESSAGE, "请求中 无 reportID 参数 !");
				return Result.EX_WARN;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}

	/**
	 * 
	 *@描述：如果报表服务于当前系统服务不是一个域名的时候，需要此方法进行代理跳转
	 *@创建人:kangzhidong
	 *@创建时间:2014-12-22下午01:43:05
	 *@修改人:
	 *@修改时间:
	 *@修改描述:
	 *@return
	 */
	public String cxReportAgentIndex(){
		try {
			String reportID = getRequest().getParameter("reportID");
			if(!BlankUtils.isBlank(reportID)){
				getValueStack().set("reportID", reportID);
				return SUCCESS;
			}else{
				getValueStack().set(Result.MESSAGE, "请求中 无 reportID 参数 !");
				return Result.EX_WARN;
			}
		} catch (Exception e) {
			logException(e);
			return ERROR;
		}
	}
	
}

