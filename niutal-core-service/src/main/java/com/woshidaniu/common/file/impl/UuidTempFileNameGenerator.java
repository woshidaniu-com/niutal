package com.woshidaniu.common.file.impl;

import java.util.UUID;

import com.woshidaniu.common.file.TempFileNameGenerator;

/**
 * @author 康康
 */
/**public**/ final class UuidTempFileNameGenerator implements TempFileNameGenerator{

	private String suffix;
	
	public UuidTempFileNameGenerator(String suffix) {
		super();
		this.suffix = suffix;
	}
	
	@Override
	public String getName() {
		return UUID.randomUUID().toString().replace("-","")+"."+this.suffix;
	}
}
