package com.woshidaniu.common.progress;

import com.opensymphony.xwork2.util.ValueStack;
import com.woshidaniu.common.action.BaseAction;

public class ProgressAction extends BaseAction {
	private static final long serialVersionUID = 3247603320057085624L;
	private String barkey;

	/**
	 * 获取进度条进度
	 * 
	 * @return
	 */
	public String getProgressBar() {
		ValueStack vs = getValueStack();
		ProgressBar pb = BarSource.getProgressBar(barkey);
		vs.set("bar", pb);
		vs.set(DATA, pb);
		return DATA;
	}
	public String finish() {
		BarSource.finishBar(barkey);
		return DATA;
	}
	public String getBarkey() {
		return barkey;
	}

	public void setBarkey(String barkey) {
		this.barkey = barkey;
	}
}
