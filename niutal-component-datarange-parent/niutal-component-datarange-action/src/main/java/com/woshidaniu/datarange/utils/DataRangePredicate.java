package com.woshidaniu.datarange.utils;

import org.apache.commons.collections.Predicate;

import com.woshidaniu.datarange.dao.entities.SjfwzModel;

public class DataRangePredicate implements Predicate {

	private String kzdx = null;
	
	public DataRangePredicate(String kzdx){
		this.kzdx = kzdx;
	}
	
	@Override
	public boolean evaluate(Object object) {
		SjfwzModel sjfwz = (SjfwzModel)object;
		return kzdx.equalsIgnoreCase(sjfwz.getKzdx());
	}
	
}