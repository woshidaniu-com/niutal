package com.woshidaniu.common.progress;

/**
 * @部门:学工产品事业部
 * @日期：2014-3-18 下午03:45:22 
 */

import java.util.HashMap;
import java.util.Map;


/**
 * @系统名称: 学生工作管理系统
 * @模块名称: 进度条
 * @作者： 张昌路[工号:982]
 * @时间： 2014-3-18 下午03:45:22
 * @版本： V1.0
 * @修改记录: 类修改者-修改日期-修改说明
 */

public class BarSource {
	private static Map<String, ProgressBar> bar = new HashMap<String, ProgressBar>();

	/**
	 * 
	 * @描述: 获取新的进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-3-25 上午09:18:07
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @deprecated 暂时废弃，进度条窗口关闭后重新打开 进度同步后台实际进度问题，这里暂时没有好的办法判断是否同一次任务执行。
	 * @param key
	 *            进度条唯一key
	 * @param all
	 *            总处理数据条数
	 * @return ProgressBar 返回类型
	 */
	public static ProgressBar getProgressBar(String key, int all) {
		ProgressBar pb = bar.get(key);
		if (null == pb) {
			pb = new ProgressBar(key, all);
		}
		bar.put(key, pb);
		return pb;
	}

	/**
	 * 
	 * @描述: 获取新的进度条<一般在模块初时生成时使用>
	 * @作者：张昌路[工号：982]
	 * @日期：2014-3-25 上午09:18:07
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key
	 *            进度条唯一key
	 * @param all
	 *            总处理数据条数
	 * @return ProgressBar 返回类型
	 */
	public static ProgressBar initProgressBar(String key, int all) {
		ProgressBar pb = bar.get(key);
		// 如果进度条key存在对应进度条对象，且在运行中
		if (null != bar.get(key) && !pb.isFinish()) {
			ProgressUniqueKeyException puke=new ProgressUniqueKeyException();
			pb.setMessage(puke.getMessage());
			throw puke;

		}
		pb = new ProgressBar(key, all);
		bar.put(key, pb);
		return pb;
	}
	/**
	 * 
	 * @描述: 加载自动进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:34:52
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key 进度条key
	 * @return
	 * ProgressBar 返回类型
	 */
	public static ProgressBar initAutoProgressBar(String key) {
		return initAutoProgressBar(key, 10, 1000);
	}
	/**
	 * 
	 * @描述: 加载自动进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:35:15
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key 进度条key
	 * @param alltime 进度条执行预估时间(秒)
	 * @return
	 * ProgressBar 返回类型
	 */
	public static ProgressBar initAutoProgressBar(String key, int alltime) {
		return initAutoProgressBar(key, alltime,1000);
	}
	/**
	 * 
	 * @描述: 加载自动进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:35:15
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key 进度条key
	 * @param alltime 进度条执行预估时间(秒)
	 * @param ptime   进度条更新时间(秒)
	 * @return
	 * ProgressBar 返回类型
	 */
	public static ProgressBar initAutoProgressBar(String key, int alltime, long ptime) {
		ProgressBar pb = bar.get(key);
		// 如果进度条key存在对应进度条对象，且在运行中
		if (null != bar.get(key) && !pb.isFinish()) {
			throw new ProgressUniqueKeyException();
		}
		pb = new ProgressBar(key);
		pb.setAllTime(alltime);
		pb.setProgress(ptime);
		bar.put(key, pb);
		pb.autoRun();
		return pb;
	}
	/**
	 * 
	 * @描述: 获取对应进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-3-25 上午09:17:52
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key
	 *            进度条唯一key
	 * @return ProgressBar 返回类型
	 */
	public static ProgressBar getProgressBar(String key) {
		ProgressBar pb = bar.get(key);
		/*if (null == pb) {
			// 返回一个尚未开始进度条对象
			return new ProgressBar(key, 0);
		}*/
		return pb;
	}

	/**
	 * 
	 * @描述: 清空（结束）进度条
	 * @作者：张昌路[工号：982]
	 * @日期：2014-3-25 上午09:17:25
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key
	 *            进度条唯一key
	 * @return ProgressBar 返回类型
	 */
	public static ProgressBar cleanBar(String key) {
		ProgressBar pb = getProgressBar(key);
		pb.finish();
		return bar.put(key, pb);
	}
	/**
	 * 
	 * @描述: 进度条完成
	 * @作者：张昌路[工号：982]
	 * @日期：2014-4-25 上午09:37:46
	 * @修改记录: 修改者名字-修改日期-修改内容
	 * @param key
	 * void 返回类型
	 */
	public static void finishBar(String key) {
		bar.remove(key);
	}
}
