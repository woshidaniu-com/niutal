package com.woshidaniu.common.progress;
/**
 * @类功能描述: 进度条key重复异常
 * @作者： 张昌路[工号:982]
 * @时间： 2014-3-18 下午03:45:22
 * @版本： V1.0
 */
public class ProgressUniqueKeyException extends RuntimeException {
	private static final long serialVersionUID = 2031587107229609990L;

	public ProgressUniqueKeyException() {
		super("进度条key被占用,请更改.");
	}

	public ProgressUniqueKeyException(String message) {
		super(message);
	}

	@Override
	public String getMessage() {
		return "进度条key必须唯一!" + super.getMessage();
	}
}
