/**********************************************************************
 * <pre>
 * FILE : PdfTextReplacer.java
 * CLASS : PdfTextReplacer
 *
 * AUTHOR : caoxu-yiyang@qq.com
 *
 * FUNCTION : TODO
 *
 *
 *======================================================================
 * CHANGE HISTORY LOG
 *----------------------------------------------------------------------
 * MOD. NO.|   DATE   |   NAME  | REASON  | CHANGE REQ.
 *----------------------------------------------------------------------
 * 		    |2016年11月8日|caoxu-yiyang@qq.com| Created |
 * DESCRIPTION:
 * </pre>
 ***********************************************************************/

package com.woshidaniu.globalweb.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.woshidaniu.globalweb.PWUtils;

/**
 * 替换PDF文件某个区域内的文本
 * @user : caoxu-yiyang@qq.com
 * @date : 2016年11月8日
 */
public class PdfReplacer {
	private static final Logger logger = LoggerFactory.getLogger(PdfReplacer.class);

	private int fontSize;
	private Map<String, List<ReplaceRegion>> replaceRegionMap = new HashMap<String, List<ReplaceRegion>>();
	private Map<String, Object> replaceTextMap = new HashMap<String, Object>();
	private Map<String, Integer> brMap = new HashMap<String, Integer>();
	private ByteArrayOutputStream output;
	private PdfReader reader;
	private PdfStamper stamper;
	private PdfContentByte canvas;
	private List<PdfContentByte> canvasList;
	private Font font;
	private byte[] pdfBytes2;

	private int pageNum;

	//private static final String BR = "BR";

	//private static final String BR_SPLIT = "-";

	public PdfReplacer(byte[] pdfBytes) throws DocumentException, IOException{
		this.init(pdfBytes);
	}

	public PdfReplacer(String fileName) throws IOException, DocumentException{
		FileInputStream in = null;
		try{
			in = new FileInputStream(fileName);
			byte[] pdfBytes = new byte[in.available()];
			in.read(pdfBytes);
			this.init(pdfBytes);
		}finally{
			in.close();
		}
	}

	public PdfReplacer(String fileName, int i) throws IOException, DocumentException{
		FileInputStream in = null;
		try{
			in = new FileInputStream(fileName);
			byte[] pdfBytes = new byte[in.available()];
			in.read(pdfBytes);
			if(i == 2){
				this.init2(pdfBytes);
			}else{
				this.init(pdfBytes);
			}
		}finally{
			in.close();
		}
	}

	private void init(byte[] pdfBytes) throws DocumentException, IOException{
		logger.info("初始化开始");
		reader = new PdfReader(pdfBytes);
		output = new ByteArrayOutputStream();
		stamper = new PdfStamper(reader, output);
		canvas = stamper.getOverContent(1);
		this.setFont(10);
		logger.info("初始化成功");
	}

	private void init2(byte[] pdfBytes) throws DocumentException, IOException{
		logger.info("初始化开始");
		pdfBytes2 = pdfBytes;
		this.setFont(10);
		logger.info("初始化成功");
	}

	private void close() throws DocumentException, IOException{
		if(reader != null){
			reader.close();
		}
		if(output != null){
			output.close();
		}
		output = null;
		replaceRegionMap = null;
		replaceTextMap = null;
		brMap = null;
	}

	public void reNew(){
		//if(reader != null){
		//	reader.close();
		//}
		//replaceRegionMap = null;
		//replaceTextMap = null;
		replaceRegionMap = new HashMap<String, List<ReplaceRegion>>();
		replaceTextMap = new HashMap<String, Object>();
		brMap = new HashMap<String, Integer>();
	}

	public void replaceText(float x, float y, float w, float h, String text){
		//用文本作为别名
		ReplaceRegion region = new ReplaceRegion(text + Math.random());
		region.setH(h);
		region.setW(w);
		region.setX(x);
		region.setY(y);
		this.addReplaceRegion(region);
		this.replaceText(text, text, null);
	}

	public void replaceText(String name, String text, String br){
		replaceTextMap.put(name, text);
		if(null != br){
			brMap.put(name, Integer.parseInt(br));
		}
	}

	/**
	 * 替换文本
	 * @throws IOException
	 * @throws DocumentException
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 */
	private void process() throws DocumentException, IOException{
		try{
			this.parseReplaceText();
			canvas.saveState();
			Set<Entry<String, List<ReplaceRegion>>> entrys = replaceRegionMap.entrySet();
			for (Entry<String, List<ReplaceRegion>> entry : entrys) {
				//ListReplaceRegion value = entry.getValue();
				for(ReplaceRegion value : entry.getValue()){
					canvas.setColorFill(BaseColor.RED);
					canvas.rectangle(value.getX(), value.getY(), value.getW(), value.getH());
				}
			}
			canvas.fill();
			canvas.restoreState();
			//开始写入文本
			canvas.beginText();
			for (Entry<String, List<ReplaceRegion>> entry : entrys) {
				//ReplaceRegion value = entry.getValue();
				for(ReplaceRegion value : entry.getValue()){
					//设置字体
					canvas.setFontAndSize(font.getBaseFont(), this.getFontSize());
					//修正背景与文本的相对位置
					canvas.setTextMatrix(value.getX(),value.getY() + 2);
					canvas.showText((String) replaceTextMap.get(value.getAliasName()));
				}
			}
			canvas.endText();
		}finally{
			if(stamper != null){
				stamper.close();
			}
		}
	}

	/**
	 * 替换文本
	 * @throws IOException
	 * @throws DocumentException
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 */
	private void process2() throws DocumentException, IOException{
		try{
			this.parseReplaceText2();
			for(int i = 0; i < canvasList.size(); i++){
				PdfContentByte canvas = canvasList.get(i);
				canvas.saveState();
				Set<Entry<String, List<ReplaceRegion>>> entrys = replaceRegionMap.entrySet();
				for (Entry<String, List<ReplaceRegion>> entry : entrys) {
					for(ReplaceRegion value : entry.getValue()){
						if(i != value.getPn()){
							continue;
						}
						canvas.setColorFill(BaseColor.WHITE);
						canvas.rectangle(value.getX(), value.getY(), value.getW(), value.getH());
					}
				}
				canvas.fill();
				canvas.restoreState();
				//开始写入文本
				canvas.beginText();

				String aliasName;
				String aliasValue;
				Integer br;
				//String[] brArr;
				for (Entry<String, List<ReplaceRegion>> entry : entrys) {
					for(ReplaceRegion value : entry.getValue()){
						if(i != value.getPn()){
							continue;
						}
						aliasName = value.getAliasName();
						if(replaceTextMap.get(aliasName) == null){
							continue;
						}
						aliasValue = (String) replaceTextMap.get(aliasName);
						br = brMap.get(aliasName);
						if(null != br){
							for(int brIndex = 0; aliasValue.length() > br * brIndex; brIndex++){
								int end = aliasValue.length() >= br * (brIndex + 1) ? br * (brIndex + 1) : aliasValue.length();

								//设置字体
								canvas.setFontAndSize(font.getBaseFont(), this.getFontSize());
								/*修正背景与文本的相对位置*/
								canvas.setTextMatrix(value.getX(), value.getY() + 2 - brIndex * this.getFontSize());
								canvas.showText(aliasValue.substring(brIndex * br, end));
							}
						}
						/* 改为数据库参数配置取是否换行
						 * else if(aliasName.indexOf(BR) > 0 && (brArr = aliasName.substring(1, aliasName.length() - 1).split(BR_SPLIT)).length == 3){
							br = Integer.parseInt(brArr[2]);
							for(int brIndex = 0; aliasValue.length() > br * brIndex; brIndex++){
								int end = aliasValue.length() >= br * (brIndex + 1) ? br * (brIndex + 1) : aliasValue.length();

								//设置字体
								canvas.setFontAndSize(font.getBaseFont(), this.getFontSize());
								修正背景与文本的相对位置
								canvas.setTextMatrix(value.getX(), value.getY() + 2 - brIndex * this.getFontSize());
								canvas.showText(aliasValue.substring(brIndex * br, end));
							}

						}
						 */
						else{
							//设置字体
							canvas.setFontAndSize(font.getBaseFont(), this.getFontSize());
							/*修正背景与文本的相对位置*/
							canvas.setTextMatrix(value.getX(), value.getY() + 2);
							canvas.showText(aliasValue);
						}
					}
				}
				canvas.endText();
			}
		}finally{
			if(stamper != null){
				stamper.close();
			}
		}
	}

	/**
	 * 未指定具体的替换位置时，系统自动查找位置
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 */
	private void parseReplaceText() {
		PdfPositionParse parse = new PdfPositionParse(reader);
		Set<Entry<String, Object>> entrys = replaceTextMap.entrySet();
		for (Entry<String, Object> entry : entrys) {
			if(replaceRegionMap.get(entry.getKey()) == null){
				parse.addFindText(entry.getKey());
			}
		}

		try {
			Map<String, List<ReplaceRegion>> parseResult = parse.parse();
			Set<Entry<String, List<ReplaceRegion>>> parseEntrys = parseResult.entrySet();
			for (Entry<String, List<ReplaceRegion>> entry : parseEntrys) {
				if(entry.getValue() != null){
					replaceRegionMap.put(entry.getKey(), entry.getValue());
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 未指定具体的替换位置时，系统自动查找位置
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 */
	private void parseReplaceText2() {
		PdfPositionParse parse = new PdfPositionParse(reader);
		Set<Entry<String, Object>> entrys = replaceTextMap.entrySet();
		for (Entry<String, Object> entry : entrys) {
			if(replaceRegionMap.get(entry.getKey()) == null){
				parse.addFindText(entry.getKey());
			}
		}

		try {
			Map<String, List<ReplaceRegion>> parseResult = parse.parse2(pageNum);
			Set<Entry<String, List<ReplaceRegion>>> parseEntrys = parseResult.entrySet();
			for (Entry<String, List<ReplaceRegion>> entry : parseEntrys) {
				if(entry.getValue() != null){
					replaceRegionMap.put(entry.getKey(), entry.getValue());
				}
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 生成新的PDF文件
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @param fileName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void toPdf(String fileName) throws DocumentException, IOException{
		FileOutputStream fileOutputStream = null;
		try{
			this.process();
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(output.toByteArray());
			fileOutputStream.flush();
		}catch(IOException e){
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			if(fileOutputStream != null){
				fileOutputStream.close();
			}
			this.close();
		}
		logger.info("文件生成成功");
	}

	/**
	 * 将生成的PDF文件转换成二进制数组
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public byte[] toBytes() throws DocumentException, IOException{
		try{
			this.process();
			logger.info("二进制数据生成成功");
			return output.toByteArray();
		}finally{
			this.close();
		}
	}

	/**
	 * 添加替换区域
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @param replaceRegion
	 */
	public void addReplaceRegion(ReplaceRegion replaceRegion){
		List<ReplaceRegion> rs = new ArrayList<ReplaceRegion>(0);
		rs.add(replaceRegion);
		replaceRegionMap.put(replaceRegion.getAliasName(), rs);
	}

	/**
	 * 通过别名得到替换区域
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @param aliasName
	 * @return
	 */
	public List<ReplaceRegion> getReplaceRegion(String aliasName){
		return replaceRegionMap.get(aliasName);
	}

	public int getFontSize() {
		return fontSize;
	}

	/**
	 * 设置字体大小
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @param fontSize
	 * @throws DocumentException
	 * @throws IOException
	 */
	public void setFont(int fontSize) throws DocumentException, IOException{
		if(fontSize != this.fontSize){
			this.fontSize = fontSize;
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);
			font = new Font(bf, this.fontSize, Font.BOLD);
		}
	}

	public void setFont(Font font){
		if(font == null){
			throw new NullPointerException("font is null");
		}
		this.font = font;
	}

	/**
	 * 生成新的PDF文件
	 * @user : caoxu-yiyang@qq.com
	 * @date : 2016年11月9日
	 * @param fileName
	 * @throws DocumentException
	 * @throws IOException
	 */
	public List<File> toPdfBatch(List<HashMap<String, String>> cols, List<HashMap<String, Object>> dataList) throws DocumentException, IOException{
		List<File> list = new ArrayList<File>(0);
		FileOutputStream fileOutputStream = null;
		String dir = System.getProperty("java.io.tmpdir") + File.separator + String.valueOf(System.currentTimeMillis()) + File.separator;
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		try{
			for(int i = 0; i < dataList.size(); i++){
				reader = new PdfReader(pdfBytes2);
				output = new ByteArrayOutputStream();
				stamper = new PdfStamper(reader, output);
				this.reNew();
				for(Map<String, String> col : cols){
					Object dataStr = dataList.get(i).get(col.get(PWUtils.COL));
					this.replaceText(col.get(PWUtils.COM), null == dataStr ? "":dataStr.toString()
							, null != col.get(PWUtils.BR) && !PWUtils.MAGIC_0.equals(col.get(PWUtils.BR)) ? col.get(PWUtils.BR) : null);
				}
				canvasList = new ArrayList<PdfContentByte>(0);
				pageNum = reader.getNumberOfPages();
				for(int j = 0; j < pageNum; j++){
					canvasList.add(stamper.getOverContent(j + 1));
				}

				this.process2();
				String filePath = new StringBuilder(dir)
						.append(null == dataList.get(i).get(PWUtils.FILENAME) ? PWUtils.EMPTY_STRING : dataList.get(i).get(PWUtils.FILENAME))
						.append(PWUtils.INDEX_START).append(i).append(PWUtils.INDEX_END).append(PWUtils.TYPE_PDF).toString();
				fileOutputStream = new FileOutputStream(filePath);
				fileOutputStream.write(output.toByteArray());
				fileOutputStream.flush();
				list.add(new File(filePath));
			}
			logger.info("文件生成成功");
			return list;
		}catch(IOException e){
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			if(fileOutputStream != null){
				fileOutputStream.close();
			}
			this.close();
		}
	}
}