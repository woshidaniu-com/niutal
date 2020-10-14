/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.svcinterface.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.comm.ImportConfig;
import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;

/**
 * @author 康康（1571）
 * 是否必填处理器
 */
public abstract class SfbtColumnSwitchProcessor {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	/**
	 * @param sfbtColumnFlag (是否必填字段标志位)
	 */
	public void process(final int sfbtColumnFlag) {
		// 验证当前字段是否必填
		boolean b1 = (sfbtColumnFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0;// 当前字段插入时必填
		if (b1) {
			this.ifBtOnInsert();
		}
		boolean b2 = (sfbtColumnFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0;// 当前字段更新时必填
		if (b2) {
			this.ifBtOnUpdate();
		}
		boolean b3 = //
				(sfbtColumnFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0// 当前字段插入时必填
				&& //
				(sfbtColumnFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0;// 当前字段更新时必填
		if (b3) {
			this.ifBtOnInsertAndUpdate();
		}
	}
	/**
	 * @param crfs 插入方式(操作模式)
	 * @param sfbtColumnFlag (是否必填字段标志位)
	 */
	public void process(final String crfs,final int sfbtColumnFlag) {
		// 验证当前字段是否必填
		if (ImportConfig._CRFS_INSERT.equals(crfs)) {// 当前模式是插入模式
			boolean b = (sfbtColumnFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0;// 当前字段插入时必填
			if (b) {
				this.ifBtOnInsert();
			}
		} else if (ImportConfig._CRFS_UPDATE.equals(crfs)) {// 当前模式是更新模式
			boolean b = (sfbtColumnFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0;// 当前字段更新时必填
			if (b) {
				this.ifBtOnUpdate();
			}
		} else if (ImportConfig._CRFS_INSERT_UPDATE.equals(crfs)) {// 当前模式是插入并更新模式
			boolean b = //
					(sfbtColumnFlag & DrlpzModel.SFBT_ON_INSERT_FLAG) != 0// 当前字段插入时必填
					&& //
					(sfbtColumnFlag & DrlpzModel.SFBT_ON_UPDATE_FLAG) != 0;// 当前字段更新时必填
			if (b) {
				this.ifBtOnInsertAndUpdate();
			}
		} else {// 当前模式未定义
			log.error("插入模式未定义:crfs:{}", crfs);
		}
	}

	/**
	 * 当插入时此字段必填 
	 */
	protected abstract void ifBtOnInsert();
	
	/**
	 * 当更新时此字段必填
	 */
	protected abstract void ifBtOnUpdate();
	
	/**
	 * 当插入和更新时，此字段必填 
	 */
	protected abstract void ifBtOnInsertAndUpdate();
}
