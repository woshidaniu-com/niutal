/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.common.datarange;

import java.util.Collections;
import java.util.Set;

/**
 * @author 康康（1571）
 * 数据范围提示类
 */
public class DataRangeHitInfo {
	/**
	 * mapper类的方法,方法名称是全限定的,如com.woshidaniu.DemoMapper的类的select方法就是com.woshidaniu.DemoMapper.select
	 */
	private String mapperMethod;
	/**
	 * 提示信息对
	 */
	private Set<DataRangeHitPairInfo> dataRangeHitPairInfos = Collections.emptySet();
	
	public String getMapperMethod() {
		return mapperMethod;
	}
	public void setMapperMethod(String mapperMethod) {
		this.mapperMethod = mapperMethod;
	}
	public Set<DataRangeHitPairInfo> getDataRangeHitPairInfos() {
		return dataRangeHitPairInfos;
	}
	public void setDataRangeHitPairInfos(Set<DataRangeHitPairInfo> dataRangeHitPairInfos) {
		this.dataRangeHitPairInfos = dataRangeHitPairInfos;
	}
}
