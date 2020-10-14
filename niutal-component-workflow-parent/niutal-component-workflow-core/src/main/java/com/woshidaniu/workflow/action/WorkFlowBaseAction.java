package com.woshidaniu.workflow.action;

import com.opensymphony.xwork2.ActionContext;
import com.woshidaniu.common.action.BaseAction;

/**
 * 人事是系统基础Action
 * 
 * @author <a href="mailto:Yongwu_Chen@126.com">陈永武</a>
 * @since 2012-5-28
 * @version V1.0.0
 */
public abstract class WorkFlowBaseAction extends BaseAction {

	private static final long serialVersionUID = 1577874760931510447L;

	protected static final String SESSION_FORM_INFO = "_s_from_info";

	/**
	 * 列表页面
	 */
	protected static final String LIST_PAGE = "list";

	/**
	 * 编辑页面
	 */
	protected static final String EDIT_PAGE = "edit";

	private Message message = new Message();

	/**
	 * 设置错误信息
	 * 
	 * @param text信息内容
	 */
	public void setErrorMessage(String text) {
		setMessage(false, text);
	}

	/**
	 * 设置成成功信息
	 * 
	 * @param text
	 *            信息内容
	 */
	public void setSuccessMessage(String text) {
		setMessage(true, text);
	}

	/**
	 * 返回操作结果信息
	 */
	public Message getMessage() {
		return message;
	}

	protected void setMessage(boolean success, String text) {
		message.setSuccess(success);
		message.setText(text);
	}

	/**
	 * 从页面中获取int类型的值
	 * 
	 * @param num
	 * @return
	 */
	public int getInt(String num) {
		String number = getRequest().getParameter(num);
		if (number == null || "".equals(number)) {
			return -1;
		}

		return Integer.parseInt(number.trim());
	}

	/**
	 * 从页面http请求中获到String类型的参数值
	 * 
	 * @param str
	 *            http参数名称
	 * @return 如果http请求中含有该参数则返加该参数对应的值,如果没有则返回null
	 */
	public String getString(String str) {
		String st = getRequest().getParameter(str);
		if (st == null) {
			return null;
		}
		return st.trim();
	}

	/**
	 * 将一个对象存放到我们的ActionContext的上下文环境context中。
	 * 
	 * @param key
	 * @param value
	 */
	public void setInActionContext(String key, Object value) {

		ActionContext.getContext().put(key, value);
	}
	
	/**
	 * 
	 * 方法描述：获取登录用户所具有的角色数组
	 *
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: <a href="mailto:fanyingjie@126.com">rogerfan</a>
	 * @since: 2013-5-9 上午09:21:04
	 */
	public String[] getRole(){
		return getUser().getJsdms().toArray(new String[getUser().getJsdms().size()]);
	}
}
