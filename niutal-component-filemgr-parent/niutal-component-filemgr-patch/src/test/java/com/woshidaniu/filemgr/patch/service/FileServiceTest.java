package com.woshidaniu.filemgr.patch.service;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.woshidaniu.filemgr.patch.exception.UploadException;

public class FileServiceTest {

	@Test
	public void testUpload() throws UploadException{
		File file = new File("G:\\UserManual.doc");
//		File file = new File("E:\\设计原型\\家教管理.zip");
//		File file = new File("E:\\设计原型\\勤工黑名单.rp");
		
//		FileService service = new LocalFileService();
		FileService service = new FtpFileService();
		try {
			String info = service.upload(file,"TEST",true);
			System.out.println(info);
			String info1 = service.upload(file,"TEST",true);
			System.out.println(info1);
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@Test
	public void testDownload() throws IOException{
//		FileService service = new LocalFileService();
		FileService service = new FtpFileService();
		
		File f = service.download("FTP:sfxt:1470820797339.doc");
		System.out.println(f.getAbsolutePath());
	}
	
	@Test
	public void testDeleteFile() throws IOException{
//		FileService service = new LocalFileService();
		FileService service = new FtpFileService();
		
		boolean resulst = service.deleteFile("FTP:2016-5-30:1464580242984.xls");
		System.out.println(resulst);
	}
}
