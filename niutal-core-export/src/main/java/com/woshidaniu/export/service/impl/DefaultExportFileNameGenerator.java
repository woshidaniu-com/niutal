/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.export.service.impl;

import com.woshidaniu.basicutils.StringUtils;
import com.woshidaniu.export.api.ExportFileNameGenerator;
import com.woshidaniu.export.api.ExportFileNameGeneratorContext;
import com.woshidaniu.export.api.ExportFileNameGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description	： 默认文件名称构造器
 * @author 		：康康（1571）
 */
class DefaultExportFileNameGenerator implements ExportFileNameGenerator {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultExportFileNameGenerator.class);
	
	private static final String FILE_NAME_FORMAT = "%s_%s.%s";
	
	private static final String DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd_HH-mm-ss_S";
	
	private static Charset UTF_8;
	
	private static Charset ISO_8859_1;
	
	static {
		try {
			UTF_8 = Charset.forName("UTF-8");			
		}catch (Exception e) {
			log.error("",e);
		}
		try {
			ISO_8859_1 = Charset.forName("ISO-8859-1");			
		}catch (Exception e) {
			log.error("",e);
		}
	}

	@Override
	public String generateFileName(ExportFileNameGeneratorContext context) {
		
		String suffix = context.getSuffix();
		String dcclmc = context.getDcclmc();
		String phmc = context.getPhmc();
		
		String fileNamePrefix = dcclmc;
		if(StringUtils.isNotEmpty(phmc)) {
			fileNamePrefix = phmc;
		}
		DateFormat dateTimeFormat = new SimpleDateFormat(DATE_TIME_FORMAT_PATTERN);
		String dateTime = dateTimeFormat.format(new Date());
		String utf8FileName = String.format(FILE_NAME_FORMAT, fileNamePrefix,dateTime,suffix);
		String gbkFileName = new String(utf8FileName.getBytes(UTF_8), ISO_8859_1);
		return gbkFileName;
	}
}
