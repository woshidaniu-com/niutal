package com.woshidaniu.common.progress;

/**
 * @部门:学工产品事业部
 * @日期：2014-3-18 下午02:50:42 
 */
import java.text.NumberFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @系统名称: 学生工作管理系统
 * @模块名称: 进度条
 * @类功能描述: 进度条核心类
 * @作者： 张昌路[工号:982]
 * @时间： 2014-3-18 下午02:50:42
 * @版本： V1.0
 * @修改记录: 类修改者-修改日期-修改说明
 */

public class ProgressBar {
	public enum BarState {
		none, work, finish;
	}

	private Map<String, Object> data = new HashMap<String, Object>();
	private BarState state = BarState.none;// 状态
	private String rate = "0";// 比率
	private int all;// 总数
	private AtomicLong now = new AtomicLong(0);// 当前数
	private String key;// mykey
	// 默认-1 无信息
	private String message = "-1";
	private boolean isFinish = false;
	// 进度条执行监听器，对长时间未响应的进度条进行自动回收
	private ProgressThread barThread = null;
	// 进度条监听最大次数，如果超过此次数，依然没有进度更新，则自动退出
	private int maxTime = 60000;
	/******* 自动执行进度条参数 ***************/
	// 默认数据执行秒数
	private int allTime = 100;
	// 默认更新进度条毫秒数
	private long progress = 1000;
	private boolean autoIsFinish = false;
	// 自动执行线程优先级，默认优先业务线程执行
	private int autoThreadPriority = 4;
	private AutoProgressBarThread abt;

	/******* 自动执行进度条参数end ***************/
	/**
	 * 自动执行方式构造器
	 */
	public ProgressBar(String key) {
		this.key = key;
		this.all = allTime;
		// 自动加载，设置超时时间不使用超时
		// this.setMaxTime(-1);
		init();
	}

	public void autoRun() {
		abt = new AutoProgressBarThread(this);
		abt.setName("progress-auto-thread");
		//abt.setPriority(autoThreadPriority);
		abt.start();
	}

	public ProgressBar(String key, int all) {
		this.key = key;
		this.all = all;
		init();
	}

	/**
	 * 
	 * @描述: 初始化参数
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:32:24
	 * @修改记录: 修改者名字-修改日期-修改内容 void 返回类型
	 */
	private void init() {
		this.state = BarState.work;
		this.rate = "0";
		// 设置超时监听
		barThread = new ProgressThread(this);
		barThread.setName("progress-timeout-thread");
		barThread.start();
	}

	/**
	 * 
	 * @描述: 重置进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-11 上午11:27:39
	 * @修改记录: 修改者名字-修改日期-修改内容 void 返回类型
	 */
	public void reset() {
		all = 0;
		now.set(0);
		isFinish = false;
		rate = "0";
		state = BarState.none;
	}

	/**
	 * 
	 * @描述: 更新当前进度
	 * @作者：张昌路[工号：982]
	 * @日期：2014-3-28 下午03:59:24
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param now
	 * @return String 返回类型
	 */
	public String change(int now) {
		// 重置超时时间
		if (this.now.intValue() != now) {
			barThread.setDate(new Date());
		}
		this.now.set(now);
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();
		// 设置精确到小数点后2位
		numberFormat.setMaximumFractionDigits(2);
		rate = numberFormat.format((float) now / (float) all * 100);
		state = BarState.work;
		if (now == all) {
			rate = "100";
			finish();
		}
		return getRate();
	}

	/**
	 * 
	 * @描述: 进度条进度更新（如果需要指定更新进度，请使用change(now)）
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-11 上午11:26:55
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @return String 返回类型
	 */
	public String change() {
		int nowint = now.intValue() + 1;
		return change(nowint);
	}

	/**
	 * 
	 * @描述: 进度条执行完成
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-11 上午11:26:38
	 * @修改记录: 修改者名字-修改日期-修改内容 void 返回类型
	 */
	public void finish() {
		state = BarState.finish;
		rate = "100";
		isFinish = true;
		autoIsFinish = true;
	}

	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}

	/**
	 * @return the all
	 */
	public int getAll() {
		return all;
	}

	/**
	 * @return the now
	 */
	public int getNow() {
		return now.intValue();
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the state
	 */
	public BarState getState() {
		return state;
	}

	/**
	 * @return the isFinish
	 */
	public boolean isFinish() {
		return isFinish;
	}

	/**
	 * @deprecated 暂时未确定
	 */
	@Override
	public boolean equals(Object obj) {
		if (this.equals(obj)) {
			ProgressBar pb = (ProgressBar) obj;
			if (this.all == pb.all && this.key.equals(pb.key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @deprecated 暂时未确定
	 */
	public boolean equals(String key, int all) {
		ProgressBar pb = new ProgressBar(key, all);
		return equals(pb);
	}

	/**
	 * @return the maxTime
	 */
	public int getMaxTime() {
		return maxTime;
	}

	/**
	 * @param maxTime要设置的
	 *            maxTime
	 */
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message要设置的
	 *            message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the autoIsFinish
	 */
	public boolean isAutoIsFinish() {
		return autoIsFinish;
	}

	/**
	 * 
	 * @描述: 自动加载进度条完成
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:39:40
	 * @修改记录: 修改者名字-修改日期-修改内容 void 返回类型
	 */
	public void autoFinish() {
		this.autoIsFinish = true;
	}

	/**
	 * @return the allTime
	 */
	public int getAllTime() {
		return allTime;
	}

	/**
	 * @param allTime
	 *            the allTime to set
	 */
	public void setAllTime(int allTime) {
		this.allTime = allTime;
	}

	/**
	 * @return the progress
	 */
	public long getProgress() {
		return progress;
	}
	//更新自动进度条执行速度
	/**
	 * @param progress
	 *            the progress to set
	 */
	public void setProgress(long progress) {
		this.progress = progress;
		if (null != abt) {
			abt.setProgress(progress);
		}
	}

	/**
	 * 进度增加返回数据
	 * 
	 * @param key
	 *            业务数据key
	 * @param obj
	 *            具体业务数据
	 */
	public void returnData(String key, Object obj) {
		data.put(key, obj);
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public int getAutoThreadPriority() {
		return autoThreadPriority;
	}

	public void setAutoThreadPriority(int autoThreadPriority) {
		this.autoThreadPriority = autoThreadPriority;
	}
}
