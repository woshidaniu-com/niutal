package com.woshidaniu.common.progress;
/**
 * 
 * @系统名称: 学生工作管理系统
 * @模块名称: 自动进度条线程
 * @作者： 张昌路[工号:982]
 * @时间： 2014-4-25 上午09:38:39 
 * @版本： V1.0
 * @修改记录: 类修改者-修改日期-修改说明
 */
public class AutoProgressBarThread extends Thread {
	private ProgressBar pb;
	private boolean isRuning = true;
	//默认更新进度条毫秒数
	private long progress=1000;
	public AutoProgressBarThread(ProgressBar pb) {
		this.pb = pb;
		this.progress=pb.getProgress();
	}
	@Override
	public void run() {
		while (isRuning) {
			try {
				//如果任务已经完成，则减少更新时间（加快进度条速度）
				if(pb.isAutoIsFinish()){
					progress-=100;
				}
				//如果进度已经结束，则结束线程
				if(pb.isFinish()){
					isRuning=false;
					break;
				}
				//如果进度已经大于90，且没有完成，则线程挂起，等待进度执行完成
				if(Float.parseFloat(pb.getRate())>=90&&!pb.isAutoIsFinish()){
					progress=500;
					continue;
				}
				//更新进度
				pb.change();
				if(progress<0){
					progress=100;
				}
				Thread.sleep(progress);
			} catch (InterruptedException e) {
				throw new RuntimeException("auto进度条线程异常!");
			}
		}
	}
	public long getProgress() {
		return progress;
	}
	public void setProgress(long progress) {
		this.progress = progress;
	}
}
