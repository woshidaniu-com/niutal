package com.woshidaniu.globalweb.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.apache.poi.hwpf.usermodel.Range;
import org.w3c.dom.Document;

import com.woshidaniu.globalweb.PWUtils;

import oracle.sql.BLOB;

/**
 * @className: DocUtils
 * @description: TODO(描述这个类的作用)
 * @author : Hanyc
 * @date: 2018-12-18 10:10:53
 * @version V1.0
 */
public class DocUtils extends PWUtils {

	/**
	 * @description: 返回多个word文档
	 * @author : Hanyc
	 * @date : 2018-12-17 13:57:12
	 * @param inputStream
	 * @param cols
	 * @param dataList
	 * @return
	 */
	public static List<File> toDocBatch(InputStream inputStream
			, List<HashMap<String, String>> cols, List<HashMap<String, Object>> dataList){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len;
		try {
			while((len = inputStream.read(buffer)) > -1){
				baos.write(buffer, 0, len);
			}
			baos.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		List<File> list = new ArrayList<File>(0);
		FileOutputStream outputStream = null;
		String dir = JAVA_IO_TMPDIR + File.separator + String.valueOf(System.currentTimeMillis()) + File.separator;
		File dirFile = new File(dir);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		Map<String, Object> map = null;
		try{
			for(int i = 0; i < dataList.size(); i++){
				map = new HashMap<String, Object>();
				for(HashMap<String, String> tmp : cols){
					map.put(tmp.get(COM), null == dataList.get(i).get(tmp.get(COL))
							? EMPTY_STRING : dataList.get(i).get(tmp.get(COL)) );
				}
				String filePath = new StringBuilder(dir).append(null == dataList.get(i).get(FILENAME) ? EMPTY_STRING: dataList.get(i).get(FILENAME))
						.append(INDEX_START).append(i).append(INDEX_END).append(TYPE_DOC).toString();
				outputStream = new FileOutputStream(filePath);
				DocUtils.replaceTextDoc(new ByteArrayInputStream(baos.toByteArray()), outputStream, map);
				list.add(new File(filePath));
			}
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(outputStream != null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * @description: TODO
	 * @author : Hanyc
	 * @date : 2018-12-17 11:00:20
	 * @param inputStream
	 * @param outputStream
	 * @param map
	 * @from 转：https://blog.csdn.net/hintcnuie/article/details/23934895
	 */
	public static void replaceTextDoc(InputStream inputStream, OutputStream outputStream, Map<String, Object> map) {
		HWPFDocument document = null;
		try {
			document = new HWPFDocument(inputStream);
			Range range = document.getRange();
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if(entry.getValue() instanceof String){
					range.replaceText(entry.getKey(), entry.getValue().toString());
				}else if(entry.getValue() instanceof BLOB){
					/*
					//图片
					text = text.replace(key, "");
					Map pic = (Map) value;
					int width = Integer.parseInt(pic.get("width").toString());
					int height = Integer.parseInt(pic.get("height").toString());
					int picType = getPictureType(pic.get("type").toString());
					byte[] byteArray = (byte[]) pic.get("content");
					ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
					try {
						int ind = doc.addPicture(byteInputStream,picType);
						document.createPicture(ind, width , height,paragraph);
					} catch (Exception e) {
						e.printStackTrace();
					}
					 */
				}
			}
			document.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != document){
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @description: 合并DOCX
	 * @author : Hanyc
	 * @date : 2019-06-26 14:57:36
	 * @param source 源文件
	 * @param dest 目标文件
	 */
	public static void mergeDoc(List<File> source, String dest) {
		if(source.size() > 0){
		}
	}

	/**
	 * @description: DOC格式转HTML
	 * @author : Hanyc
	 * @date : 2019-06-26 10:23:09
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String toHtml(String path) throws Exception {
		InputStream input = new FileInputStream(path);
		HWPFDocument wordDocument = new HWPFDocument(input);
		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(
				DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
		wordToHtmlConverter.setPicturesManager(new PicturesManager() {
			@Override
			public String savePicture(byte[] content, PictureType pictureType,
					String suggestedName, float widthInches, float heightInches) {
				return suggestedName;
			}
		});
		wordToHtmlConverter.processDocument(wordDocument);
		List<Picture> pics = wordDocument.getPicturesTable().getAllPictures();
		if (pics != null) {
			for (int i = 0; i < pics.size(); i++) {
				Picture pic = pics.get(i);
				pic.writeImageContent(new FileOutputStream(path + pic.suggestFullFileName()));
			}
		}
		Document htmlDocument = wordToHtmlConverter.getDocument();
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(outStream);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer serializer = tf.newTransformer();
		serializer.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		serializer.setOutputProperty(OutputKeys.INDENT, "yes");
		serializer.setOutputProperty(OutputKeys.METHOD, "html");
		serializer.transform(domSource, streamResult);
		outStream.close();
		return new String(outStream.toByteArray());
	}
}