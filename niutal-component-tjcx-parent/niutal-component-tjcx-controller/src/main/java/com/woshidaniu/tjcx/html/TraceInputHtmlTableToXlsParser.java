/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.tjcx.html;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.io.utils.FilenameUtils;
import com.woshidaniu.io.utils.IOUtils;

/**
 * @author：康康（1571）
 * 跟踪输入的解析器,记录html片段到后缀为trace的文件中
 */
public class TraceInputHtmlTableToXlsParser implements HtmlTableToXlsParser{
	
	private static final Logger log = LoggerFactory.getLogger(TraceInputHtmlTableToXlsParser.class);
	
	private HtmlTableToXlsParser delgate;
	
	private String html;
	
	private File workDir;
	
	public TraceInputHtmlTableToXlsParser(File workDir,String html) {
		
		this.workDir = workDir;
		this.html = html;
		this.delgate = new DefaultHtmlTableToXlsParser(workDir,html);
	}
	
	@Override
	public File parse() {
		File resultFile = this.delgate.parse();
		//跟踪html片段
		String fileId = FilenameUtils.getBaseName(resultFile.getName());
		
		File traceInputFile = new File(this.workDir,fileId+".trace");
		try {
			traceInputFile.createNewFile();
		} catch (IOException e1) {
			log.error("创建文件{}异常",traceInputFile.toString(),e1);
		}
		OutputStream out = null;
		try {
			out = new FileOutputStream(traceInputFile);
		} catch (FileNotFoundException e) {
			log.error("没有找到文件{}异常",traceInputFile.toString(),e);
		}
		try {
			IOUtils.copy(html.getBytes(), out);
			IOUtils.closeQuietly(out);
			log.debug("创建跟踪html的trace文件{}",traceInputFile);
		} catch (IOException e) {
			log.error("转储html片段到trace文件{}异常",traceInputFile,e);
		}
		return resultFile;
	}
}
