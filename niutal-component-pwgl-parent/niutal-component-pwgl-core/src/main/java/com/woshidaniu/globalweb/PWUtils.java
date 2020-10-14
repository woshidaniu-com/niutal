package com.woshidaniu.globalweb;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.io.FileUtils;
import org.jbarcode.JBarcode;
import org.jbarcode.encode.Code39Encoder;
import org.jbarcode.encode.EAN13Encoder;
import org.jbarcode.paint.BaseLineTextPainter;
import org.jbarcode.paint.EAN13TextPainter;
import org.jbarcode.paint.WideRatioCodedPainter;
import org.jbarcode.paint.WidthCodedPainter;
import org.jbarcode.util.ImageUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.druid.util.StringUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.woshidaniu.dao.entities.PWModel;
import com.woshidaniu.dao.entities.SjbzModel;
import com.woshidaniu.util.base.MessageUtil;
import com.woshidaniu.web.context.WebContext;

/**
 * @className: PWUtils
 * @description: TODO(描述这个类的作用)
 * @author : Hanyc
 * @date: 2018-12-04 11:37:56
 * @version V1.0
 */
public class PWUtils{

	public static final String MAGIC_0 = "0";
	public static final String MAGIC_1 = "1";
	public static final String MAGIC_YES = "是";
	public static final String MAGIC_NO = "否";

	/** @fields : EMPTY_STRING : 空字符串 */
	public static final String EMPTY_STRING = "";
	/** @fields : MIN_COPY_FILE_SIZE : 最小复制的文件大小 */
	public static final int MIN_COPY_FILE_SIZE = 50 * 1024;
	/** @fields : DOT : 小数点 */
	public static final String DOT = ".";
	/** @fields : PW_FILE_UPLOADPATH : PW上传文件的根目录 */
	public static final String PW_FILE_UPLOADPATH = MessageUtil.getText("pw.file.uploadpath");
	/** @fields : PW_FILE_UPLOADPATH : PW上传文件的根目录 */
	public static final String PW_X_FRAME_OPTIONS = MessageUtil.getText("pw.x.frame.options");
	/** @fields : JAVA_IO_TMPDIR : java临时目录 */
	public static final String JAVA_IO_TMPDIR = System.getProperty("java.io.tmpdir");

	/** @fields : TYPE_PDF : PDF文档 */
	public static final String TYPE_PDF = ".PDF";
	/** @fields : TYPE_DOC : WORD文档，97-2003 */
	public static final String TYPE_DOC = ".DOC";
	/** @fields : TYPE_DOCX : WORD文档 */
	public static final String TYPE_DOCX = ".DOCX";

	public static final int WORONG_TYPE = -1;
	public static final int IS_PDF = 0;
	public static final int IS_DOC = 1;
	public static final int IS_DOCX = 2;

	public static final String FILENAME = "EXP_FILENAME";

	public static final String COL = "col";
	public static final String COM = "com";
	public static final String BR = "br";

	public static final char INDEX_START = '（';

	public static final char INDEX_END = '）';

	public static final int EMU = 9525;

	public static final char START_FLAG = '【';

	public static final char END_FLAG = '】';

	/**
	 * @description: 获取是否列表
	 * @author : Hanyc
	 * @date : 2018-12-04 14:55:25
	 * @return
	 */
	public static List<SjbzModel> getSfList(){
		List<SjbzModel> list = new ArrayList<SjbzModel>(0);
		SjbzModel model = new SjbzModel();
		model.setDm(MAGIC_1);
		model.setMc(MAGIC_YES);
		list.add(model);
		model = new SjbzModel();
		model.setDm(MAGIC_0);
		model.setMc(MAGIC_NO);
		list.add(model);
		return list;
	}

	/**
	 * @description: 初始化导入路径，同时上传文件
	 * @author : Hanyc
	 * @date : 2018-12-04 16:37:35
	 * @param model
	 * @param request
	 */
	public static void initDrpath(PWModel model, MultipartHttpServletRequest request){
		String fileName = String.valueOf(System.currentTimeMillis());
		MultipartFile file = request.getFile("file");
		if(null != file){
			String fileType = file.getOriginalFilename().substring(
					file.getOriginalFilename().lastIndexOf(PWUtils.DOT), file.getOriginalFilename().length());
			model.setDrpath(PW_FILE_UPLOADPATH + File.separator + fileName + fileType);
			String path = PWUtils.getRealPath(PW_FILE_UPLOADPATH);

			PWUtils.singleFileUpload(file, fileName, path);

			if(null != model.getExpath() && !EMPTY_STRING.equals(model.getExpath())){
				PWUtils.deleteAllFilesOfDir(new File(PWUtils.getRealPath(model.getExpath())));
			}
		}
	}


	/**
	 * @description: 删除全部的文件或者目录
	 * @author : Hanyc
	 * @date : 2017-10-28 11:09:45
	 * @param path
	 */
	public static void deleteAllFilesOfDir(File path){
		//判断是否存在，如果已经删除完全，则直接退出
		if (!path.exists()){
			return;
		}
		//判断是否为file，若为file则进行删除，否则直接退出
		if (path.isFile()){
			path.delete();
			return;
		}
		//因为未进入上面的判断，说明是目录，则获取目录下的文件，进行遍历删除
		File[] files = path.listFiles();
		for (int i = 0; i < files.length; i++){
			PWUtils.deleteAllFilesOfDir(files[i]);
		}
		path.delete();
	}

	public static String getRealPath(String path){
		File root = new File(WebContext.getServletContext().getRealPath("").replace("/", File.separator));
		return new StringBuilder(root.getParent().toString()).append(File.separator).append(path.replace("/", File.separator)).toString();
	}

	public static void checkDirExists(String dir){
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
	}

	/**
	 * @description: 单个文件上传
	 * @author : Hanyc
	 * @date : 2017-10-28 12:10:13
	 * @param multiFile 文件
	 * @param fileName 文件名
	 * @param dirPath 路径
	 */
	public static void singleFileUpload(MultipartFile multiFile, String fileName, String dirPath){
		//输入流
		InputStream is = null;
		//输出流
		OutputStream os = null;
		try{
			PWUtils.checkDirExists(dirPath);
			String originalFileName = multiFile.getOriginalFilename();
			if(null == fileName || EMPTY_STRING.equals(fileName)){
				fileName = originalFileName;
			}else{
				fileName = fileName + originalFileName.substring(originalFileName.lastIndexOf(DOT), originalFileName.length());
			}
			File file = new File(dirPath + File.separator + fileName);
			//文件过小时不会生成临时文件，因此区分复制
			if(multiFile.getSize() > MIN_COPY_FILE_SIZE){
				CommonsMultipartFile multipartFile = (CommonsMultipartFile)multiFile;
				DiskFileItem diskFileItem = (DiskFileItem)multipartFile.getFileItem();
				FileUtils.copyFile(diskFileItem.getStoreLocation(), file, true);
			}else{
				is = multiFile.getInputStream();
				os = new FileOutputStream(file);
				IOUtils.copy(is, os);
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(null != is){
					is.close();
				}
				if(null != os){
					os.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * @description: 确认文件格式
	 * @author : Hanyc
	 * @date : 2018-12-05 17:31:26
	 * @param str
	 * @return
	 */
	public static int checkFile(String str){
		if(null == str){
			return WORONG_TYPE;
		}else{
			String type = str.substring(str.lastIndexOf(DOT), str.length()).toUpperCase();
			if(TYPE_PDF.equals(type)){
				return IS_PDF;
			}else if(TYPE_DOC.equals(type)){
				return IS_DOC;
			}else if(TYPE_DOCX.equals(type)){
				return IS_DOCX;
			}
			return WORONG_TYPE;
		}
	}


	/**
	 * @description: 压缩文件
	 * @author : Hanyc
	 * @date : 2018-08-20 15:50:38
	 * @param zipFile 压缩的目标文件（路径）
	 * @param inFile 即将被压缩的文件（路径）
	 */
	public static void compressFile(String zipFile, String inFile){
		if(!EMPTY_STRING.equals(zipFile) && !EMPTY_STRING.equals(inFile)){
			PWUtils.compressFile(new File(zipFile), new File(inFile));
		}
	}

	/**
	 * @description: 压缩文件（自行close流）
	 * @author : Hanyc
	 * @date : 2018-08-20 16:49:52
	 * @param out
	 * @param ins
	 * @param entryName
	 */
	public static void compressFile(ZipOutputStream out, InputStream ins, String entryName){
		if(null != out && null != ins && null != entryName && !EMPTY_STRING.equals(entryName)){
			try{
				ZipEntry zipEntry = new ZipEntry(entryName);
				try{
					out.putNextEntry(zipEntry);
					int len = 0;
					byte[] buffer = new byte[1024];

					while((len = ins.read(buffer)) > 0){
						out.write(buffer, 0, len);
						out.flush();
					}
					out.closeEntry();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static void compressFiles(File zipFile, List<File> inFiles){
		if(null != zipFile && null != inFiles && inFiles.size() > 0){
			ZipOutputStream out = null;
			try {
				out = new ZipOutputStream(new FileOutputStream(zipFile));
				for(File file : inFiles){
					PWUtils.doCompress(out, file);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}finally{
				try{
					if(null != out){
						out.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @description: 压缩文件
	 * @author : Hanyc
	 * @date : 2018-08-20 15:54:01
	 * @param zipFile 压缩的目标文件
	 * @param inFile 即将被压缩的文件
	 */
	public static void compressFile(File zipFile, File inFile){
		if(null != zipFile && null != inFile){
			ZipOutputStream out = null;
			try{
				out = new ZipOutputStream(new FileOutputStream(zipFile));
				PWUtils.doCompress(out, inFile);
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					if(null != out){
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:30
	 * @param out
	 * @param inFileDir
	 */
	public static void doCompress(ZipOutputStream out, String inFileDir){
		PWUtils.doCompress(out, new File(inFileDir));
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:30
	 * @param out
	 * @param inFile
	 */
	public static void doCompress(ZipOutputStream out, File inFile){
		PWUtils.doCompress(out, inFile, EMPTY_STRING);
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:48
	 * @param out
	 * @param inFile
	 * @param dir
	 */
	public static void doCompress(ZipOutputStream out, File inFile, String dir){
		if(!inFile.isDirectory()){
			PWUtils.doZip(out, inFile, dir);
		}else{
			File[] files = inFile.listFiles();
			for(File file : files){
				String name = inFile.getName();
				if(!EMPTY_STRING.equals(dir)){
					name = dir + File.separator + name;
				}
				PWUtils.doCompress(out, file, name);
			}
		}
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:30
	 * @param out
	 * @param inFileDir
	 */
	public static void doCompressRename(ZipOutputStream out, String inFileDir, String fileName){
		PWUtils.doCompressRename(out, new File(inFileDir), fileName);
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:30
	 * @param out
	 * @param inFile
	 */
	public static void doCompressRename(ZipOutputStream out, File inFile, String fileName){
		PWUtils.doCompressRename(out, inFile, EMPTY_STRING, fileName);
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:48
	 * @param out
	 * @param inFile
	 * @param dir
	 */
	public static void doCompressRename(ZipOutputStream out, File inFile, String dir, String fileName){
		if(!inFile.isDirectory()){
			PWUtils.doZip(out, inFile, dir, fileName);
		}else{
			File[] files = inFile.listFiles();
			for(File file : files){
				String name = inFile.getName();
				if(!EMPTY_STRING.equals(dir)){
					name = dir + File.separator + name;
				}
				PWUtils.doCompressRename(out, file, name, fileName);
			}
		}
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:54
	 * @param out
	 * @param inFile
	 * @param dir
	 */
	public static void doZip(ZipOutputStream out, File inFile, String dir){
		String entryName = null;
		if(!EMPTY_STRING.equals(dir)){
			entryName = dir + File.separator + inFile.getName();
		}else{
			entryName = inFile.getName();
		}
		ZipEntry zipEntry = new ZipEntry(entryName);
		try{
			out.putNextEntry(zipEntry);

			int len = 0;
			byte[] buffer = new byte[1024];

			FileInputStream fis = new FileInputStream(inFile);
			while((len = fis.read(buffer)) > 0){
				out.write(buffer, 0, len);
				out.flush();
			}
			out.closeEntry();
			fis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description: 压缩
	 * @author : Hanyc
	 * @date : 2018-08-20 15:56:54
	 * @param out
	 * @param inFile
	 * @param dir
	 */
	public static void doZip(ZipOutputStream out, File inFile, String dir, String entryName){
		if(!EMPTY_STRING.equals(entryName)){
			//不带后缀
			if(entryName.indexOf(DOT) == -1){
				String fileName = inFile.getName();
				entryName += DOT + fileName.substring(fileName.lastIndexOf(DOT) + 1);
			}
		}else{
			if(!EMPTY_STRING.equals(dir)){
				entryName = dir + File.separator + inFile.getName();
			}else{
				entryName = inFile.getName();
			}
		}
		ZipEntry zipEntry = new ZipEntry(entryName);
		try{
			out.putNextEntry(zipEntry);

			int len = 0;
			byte[] buffer = new byte[1024];

			FileInputStream fis = new FileInputStream(inFile);
			while((len = fis.read(buffer)) > 0){
				out.write(buffer, 0, len);
				out.flush();
			}
			out.closeEntry();
			fis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @description: 根据相对路径获取数据
	 * @author : Hanyc
	 * @date : 2018-11-06 11:24:26
	 * @param path
	 * @param response
	 * @return
	 */
	public static ResponseEntity<byte[]> getData(String path){
		return PWUtils.getFile(path);
	}

	/**
	 * @description: 获取数据
	 * @author : Hanyc
	 * @date : 2018-11-06 15:01:59
	 * @param path 路径
	 * @param headers 特定的参数
	 * @return
	 */
	public static ResponseEntity<byte[]> getData(String path, HttpHeaders headers){
		if(null != path && !EMPTY_STRING.equals(path)){
			try {
				if(path.indexOf(":") > 1){
					/*path = WebContext.getServletContext().getRealPath(path).replace(
							WebContext.getServletContext().getContextPath().replace("/", File.separator), EMPTY_STRING);*/
					path = PWUtils.getRealPath(path);
				}
				File file = new File(path);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @description: 获取文件
	 * @author : Hanyc
	 * @date : 2018-11-06 11:16:15
	 * @param path
	 * @return
	 */
	public static ResponseEntity<byte[]> getFile(String path) {
		if(null != path && !EMPTY_STRING.equals(path)){
			try {
				if(path.indexOf(":") > 1){
					path = PWUtils.getRealPath(path);
				}
				File file = new File(path);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				//中文文件名下载
				headers.setContentDispositionFormData("attachment", file.getName(), Charset.forName("utf-8"));
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @description: 合并PDF
	 * @author : Hanyc
	 * @date : 2018-12-13 20:26:14
	 * @param files
	 * @param newfile
	 * @return
	 */
	public static void mergePdfFiles(String[] files, String newfile) {
		Document document = null;
		try {
			document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
			document.open();
			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(files[i]);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(null != document){
				document.close();
			}
		}
	}

	/**
	 * @description: 获取条形码byte数组
	 * @author : Hanyc
	 * @date : 2019-06-12 14:56:19
	 * @param text 条形码内容
	 * @param showText 是否显示条形码内容
	 * @param h 高度
	 * @return
	 * @throws Exception
	 */
	public static byte[] getJBARBytes(String text, boolean showText, int h) {
		byte[] bytes = null;
		if(StringUtils.isEmpty(text)){
			return bytes;
		}
		//是否选择文字
		JBarcode localJBarcode = new JBarcode(EAN13Encoder.getInstance(),
				WidthCodedPainter.getInstance(), EAN13TextPainter.getInstance());

		//EAN13条形码基本属性，必要添加条件
		localJBarcode.setEncoder(Code39Encoder.getInstance());
		localJBarcode.setPainter(WideRatioCodedPainter.getInstance());
		localJBarcode.setTextPainter(BaseLineTextPainter.getInstance());
		localJBarcode.setBarHeight(h);
		//显示字符串内容中是否显示检查码内容
		localJBarcode.setShowCheckDigit(false);
		//不生成检查码
		localJBarcode.setCheckDigit(false);
		//不显示条形码下方的数字
		localJBarcode.setShowText(showText);
		//设置文本字体
		//localJBarcode.setFont(localJBarcode.getFont().deriveFont(14));
		//设置指定编码类型
		//localJBarcode.setCodeType(codeType);
		BufferedImage localBufferedImage;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			localBufferedImage = localJBarcode.createBarcode(text);
			ImageIO.write(localBufferedImage, ImageUtil.PNG, out);
			bytes = out.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//输出png
		return bytes;
	}

	/**
	 * @description: 获取二维码byte数组
	 * @author : Hanyc
	 * @date : 2019-06-12 15:06:15
	 * @param text 内容
	 * @param h 高度
	 * @param w 宽度
	 * @return
	 * @throws Exception
	 */
	public static byte[] getQrCodeBytes(String text, int h, int w) {
		byte[] bytes = null;
		if(StringUtils.isEmpty(text)){
			return bytes;
		}
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "gb2312");
		//设置容错率最高
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.MARGIN, 1);

		BitMatrix bitMatrix;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, w, h, hints);
			BufferedImage localBufferedImage = PWUtils.toBufferedImage(bitMatrix);
			ImageIO.write(localBufferedImage, ImageUtil.PNG, out);
			//输出png
			bytes = out.toByteArray();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return bytes;
	}

	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;

	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}
}