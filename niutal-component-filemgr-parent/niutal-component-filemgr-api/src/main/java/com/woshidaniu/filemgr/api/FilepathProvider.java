package com.woshidaniu.filemgr.api;

import java.io.File;

public interface FilepathProvider {

	String generate(File file, String uuid);

	String generate(String fileName, String uuid);
	
	String parse(String filepath);
	
}
