/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.handler;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 康康（1571）
 * id对象,用于唯一标示Row,Cell和Pipeline
 */
public class IdObject {
	
	private static final long START = 0L/**System.currentTimeMillis()**/;
	
	private static final AtomicLong ID_SEQUENCE = new AtomicLong(START);
	
	private static final long getNextId() {
		return ID_SEQUENCE.incrementAndGet();
	}

	//行id或cellId,虚拟数据，但对于每一个行，或每一个cell都是针对此jvm唯一的，用于验证方面
	private long id = getNextId();

	/**
	 * @description	： 获得对象id
	 * @return
	 */
	public long getId() {
		return id;
	}
}
