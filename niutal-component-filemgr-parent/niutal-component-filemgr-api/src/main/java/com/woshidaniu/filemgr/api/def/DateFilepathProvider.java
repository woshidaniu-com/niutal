package com.woshidaniu.filemgr.api.def;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

import com.woshidaniu.filemgr.api.FilepathProvider;
import com.woshidaniu.io.utils.DirectoryUtils;

public class DateFilepathProvider implements FilepathProvider{

	
	@Override
	public String generate(File file, String uuid) {
		
		//按日期定义文件名
		String fileDir = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE).format(Calendar.getInstance().getTime());
		String extension = FilenameUtils.getExtension(file.getName());
		return fileDir +  File.separator + uuid + "." + extension;
		
	}

	@Override
	public String generate(String fileName, String uuid) {

		//按日期定义文件名
		String fileDir = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.SIMPLIFIED_CHINESE).format(Calendar.getInstance().getTime());
		String extension = FilenameUtils.getExtension(fileName);
		return fileDir +  File.separator + uuid + "." + extension;
				
	}
	
	@Override
	public String parse(String filepath) {
		//因为只有一场日期归类的目录，getPath获取的就是根目录
		return DirectoryUtils.getResolvePath(FilenameUtils.getPath(filepath));
	}

}
