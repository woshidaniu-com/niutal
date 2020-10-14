/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.dao.insertDb.imp;

import java.util.HashMap;

import org.springframework.stereotype.Component;

import com.woshidaniu.drdcsj.drsj.dao.insertDb.AbstractMapInsert;

/**
 * @author xiaokang[1036]
 */
@Component("customMapInsertor")
public class CustomMapInsertor extends AbstractMapInsert {
	
	@Override
	protected int doBatchInsertData(HashMap<String, Object> insertData) {
		return insertData.size();
	}

	@Override
	protected int doBatchUpdate(HashMap<String, Object> updateData) {
		return updateData.size();
	}
}
