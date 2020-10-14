package com.woshidaniu.common.progress;

/**
 * @部门:学工产品事业部
 * @日期：2014-4-11 上午11:28:57 
 */

import java.util.Date;

/**
 * @系统名称: 学生工作管理系统
 * @模块名称: 进度条监听
 * @类功能描述: 对超时进度条自动停止
 * @作者： 张昌路[工号:982]
 * @时间： 2014-4-11 上午11:28:57
 * @版本： V1.0
 * @修改记录: 类修改者-修改日期-修改说明
 */

public class ProgressThread extends Thread {
	private ProgressBar pb;
	private boolean isRuning = true;
	private Date date;

	public ProgressThread(ProgressBar pb) {
		this.pb = pb;
		this.date = new Date();
	}

	@Override
	public void run() {
		try {
			while (isRuning) {
				// 如果进度条完成，则结束
				if (pb.isFinish()) {
					isRuning = false;
				}
				Date nowdate = new Date();
				// 已经超时,maxtime小于零则不验证超时
				if (pb.getMaxTime()>0&&nowdate.getTime() - date.getTime() > pb.getMaxTime()) {
					isRuning = false;
					pb.finish();
					BarSource.finishBar(pb.getKey());
				}
				Thread.sleep(5000);
			}
		} catch (InterruptedException e) {
			throw new RuntimeException("进度条线程超时监听异常!");
		}
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date要设置的
	 *            date
	 */
	public void setDate(Date date) {
		this.date = date;
	}
}
