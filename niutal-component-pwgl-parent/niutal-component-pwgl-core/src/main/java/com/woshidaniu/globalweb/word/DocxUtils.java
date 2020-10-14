package com.woshidaniu.globalweb.word;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlToken;
import org.docx4j.Docx4J;
import org.docx4j.Docx4jProperties;
import org.docx4j.convert.out.HTMLSettings;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;

import com.woshidaniu.globalweb.PWUtils;

import oracle.sql.BLOB;

/**
 * @className: DocxUtils
 * @description: TODO(描述这个类的作用)
 * @author : Hanyc
 * @date: 2018-12-18 10:11:52
 * @version V1.0
 */
public class DocxUtils extends PWUtils {

	public static StringBuilder textSB = new StringBuilder();

	public static String text = EMPTY_STRING, key = EMPTY_STRING;
	public static List<XWPFRun> runs;
	public static XWPFParagraph paragraph;
	public static XWPFTable table;
	public static Integer[] p = new Integer[2];
	public static boolean b = false;
	public static StringBuilder sb = new StringBuilder();
	public static List<CTR> tobeRemoveCtrs;
	public static CTR ctr;
	public static List<Integer> ctris;
	public static XWPFRun imgRun;
	public static int picWidth;
	public static int picHeight;
	public static int picType;
	public static List<Integer> pList;
	public static List<String> pstrList;
	public static List<List<XWPFRun>> prList;

	public static final String KEY_QR = "【QR二维码";
	public static final String KEY_JBAR = "【JBAR条形码";

	public static final int PIC_TYPE_BLOB = 0;
	public static final int PIC_TYPE_JBAR = 1;
	public static final int PIC_TYPE_QR = 2;

	/**
	 * @description: 返回多个word文档
	 * @author : Hanyc
	 * @date : 2018-12-17 13:57:12
	 * @param inputStream
	 * @param cols
	 * @param dataList
	 * @return
	 */
	public static List<File> toDocxBatch(File f
			, List<HashMap<String, String>> cols, List<HashMap<String, Object>> dataList){
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
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
						.append(INDEX_START).append(i).append(INDEX_END).append(TYPE_DOCX).toString();
				outputStream = new FileOutputStream(filePath);
				DocxUtils.replaceTextDocx(new ByteArrayInputStream(baos.toByteArray()), outputStream, map);
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
	 * @description: DOCX文本替换
	 * @author : Hanyc
	 * @date : 2018-12-17 10:37:43
	 * @param inputStream
	 * @param outputStream
	 * @param map
	 * @from 转：https://blog.csdn.net/qq_34545192/article/details/77847849
	 */
	public static void replaceTextDocx(InputStream inputStream, OutputStream outputStream, Map<String, Object> map) {
		XWPFDocument document = null;
		try {
			document = new XWPFDocument(inputStream);
			//List<XWPFPictureData> pics = document.getAllPictures();
			//1.替换段落中的指定文字
			Iterator<XWPFParagraph> itParagraph = document.getParagraphsIterator();
			tobeRemoveCtrs = new ArrayList<CTR>(0);
			ctris = new ArrayList<Integer>(0);
			text = EMPTY_STRING;
			key = EMPTY_STRING;
			paragraph = null;
			p = new Integer[2];
			b = false;
			sb = new StringBuilder();
			pList = new ArrayList<Integer>(0);
			pstrList = new ArrayList<String>(0);
			prList = new ArrayList<List<XWPFRun>>(0);
			while (itParagraph.hasNext()) {
				paragraph = itParagraph.next();
				runs = paragraph.getRuns();
				for (int i = 0; i < runs.size(); i++) {
					text = runs.get(i).getText(runs.get(i).getTextPosition());
					if(text != null){
						b = DocxUtils.initDocxParagraph(i, runs, map, document);
					}else if(runs.get(i).getCTR().xmlText().indexOf("<w:drawing>") > -1){
						ctr = runs.get(i).getCTR();
						//ctris.add(ctr.getDrawingArray().length - 1);
						ctris.add(ctr.getDrawingList().size() - 1);
						imgRun = runs.get(i);
						b = false;
					}else{
						b = false;
					}
				}
			}
			//2.替换表格中的指定文字
			Iterator<XWPFTable> itTable = document.getTablesIterator();
			int rowsCount;
			while (itTable.hasNext()) {
				table = itTable.next();
				rowsCount = table.getNumberOfRows();
				for (int i = 0; i < rowsCount; i++) {
					XWPFTableRow row = table.getRow(i);
					List<XWPFTableCell> cells = row.getTableCells();
					for (int j = 0; j < cells.size(); j++) {
						XWPFTableCell cell = cells.get(j);
						itParagraph = cell.getParagraphs().iterator();
						while (itParagraph.hasNext()) {
							paragraph = itParagraph.next();
							runs = paragraph.getRuns();
							for (int k = 0; k < runs.size(); k++) {
								text = runs.get(k).getText(runs.get(k).getTextPosition());
								if(text != null){
									b = DocxUtils.initDocxParagraph(k, runs, map, document);
								}else if(runs.get(k).getCTR().xmlText().indexOf("<w:drawing>") > -1){
									ctr = runs.get(k).getCTR();
									//ctris.add(ctr.getDrawingArray().length - 1);
									ctris.add(ctr.getDrawingList().size() - 1);
									imgRun = runs.get(k);
									b = false;
								}else{
									b = false;
								}
							}
						}
					}
				}
			}
			if(null != tobeRemoveCtrs){
				for(int i = 0; i < tobeRemoveCtrs.size(); i++){
					tobeRemoveCtrs.get(i).removeDrawing(ctris.get(i));
				}
			}
			//3.输出流
			document.write(outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(null != document){
				try {
					Method m = document.getClass().getMethod("close");
					m.invoke(document);
					//document.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * get the xml of the picture
	 * @param blipId
	 * @param width
	 * @param height
	 * @return
	 */
	private static String getPicXml(String blipId, String id, int width, int height) {
		StringBuilder picXml = new StringBuilder("<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">")
				.append("	<a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
				.append("		<pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">")
				.append("			<pic:nvPicPr>")
				.append("				<pic:cNvPr id=\"").append(id).append("\" name=\"Generated\"/>").append("<pic:cNvPicPr/>")
				.append("			</pic:nvPicPr>")
				.append("			<pic:blipFill>")
				.append("				<a:blip r:embed=\"").append(blipId).append("\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>")
				.append("				<a:stretch><a:fillRect/>").append("</a:stretch>")
				.append("			</pic:blipFill>")
				.append("			<pic:spPr>")
				.append("				<a:xfrm>")
				.append("					<a:off x=\"0\" y=\"0\"/>")
				.append("					<a:ext cx=\"").append(width).append("\" cy=\"").append(height).append("\"/>")
				.append("				</a:xfrm>")
				.append("				<a:prstGeom prst=\"rect\">").append("<a:avLst/></a:prstGeom>")
				.append("			</pic:spPr>")
				.append("		</pic:pic>")
				.append("	</a:graphicData>")
				.append("</a:graphic>");

		return picXml.toString();
	}

	/**
	 * insert Picture
	 * @param document
	 * @param filePath
	 * @param inline
	 * @param width
	 * @param height
	 * @throws InvalidFormatException
	 * @throws FileNotFoundException
	 */
	public static void insertPicture(XWPFDocument document, int picType, CTInline inline
			, int width, int height, String blipId, String id) throws InvalidFormatException, FileNotFoundException {
		int sid = document.getAllPictures().size() - 1;
		width *= EMU;
		height *= EMU;
		String picXml = DocxUtils.getPicXml(blipId, String.valueOf(sid), width, height);
		XmlToken xmlToken = null;
		try {
			xmlToken = XmlToken.Factory.parse(picXml);
		} catch (XmlException xe) {
			xe.printStackTrace();
		}
		inline.set(xmlToken);
		inline.setDistT(0);
		inline.setDistB(0);
		inline.setDistL(0);
		inline.setDistR(0);

		CTPositiveSize2D extent = inline.addNewExtent();
		extent.setCx(width);
		extent.setCy(height);
		CTNonVisualDrawingProps docPr = inline.addNewDocPr();
		docPr.setId(sid);
		docPr.setName("IMG_" + id);
		docPr.setDescr("IMG_" + id);
	}

	/**
	 * @description: DOCX文本替换
	 * @author : Hanyc
	 * @date : 2018-12-17 10:37:43
	 * @param inputStream
	 * @param outputStream
	 * @param map
	 * @from 转：https://blog.csdn.net/qq_34545192/article/details/77847849
	 */
	public static boolean initDocxParagraph(int i, List<XWPFRun> runs, Map<String, Object> map, XWPFDocument document) {
		if(!b && (p[1] = text.indexOf(START_FLAG)) > -1){
			b = true;
			pList.clear();
			pstrList.clear();
			p[0] = i;
			textSB = new StringBuilder(text);
			if(text.indexOf(END_FLAG) > -1){
				sb = new StringBuilder(text.substring(p[1], text.indexOf(END_FLAG) + 1));
				b = false;
				key = sb.toString();
			}else{
				sb = new StringBuilder(text.substring(p[1], text.length()));
			}
			runs.get(i).setText(EMPTY_STRING, 0);
			pList.add(i);
			pstrList.add(text);
			prList.add(runs);
		}else if(b){
			textSB.append(text);
			if(text.indexOf(END_FLAG) > -1){
				key = sb.append(text.substring(0, text.indexOf(END_FLAG) + 1)).toString();
				b = false;
			}else{
				sb = sb.append(text.substring(0, text.length()));
			}
			runs.get(i).setText(EMPTY_STRING, 0);
			pList.add(i);
			pstrList.add(text);
			prList.add(runs);
		}

		if(null != map.get(key)){
			b = false;
			pList.clear();
			pstrList.clear();
			prList.clear();
			if(map.get(key) instanceof String) {
				if(null != ctr && null != imgRun && key.startsWith(KEY_QR)){
					text = textSB.toString().replace(key, EMPTY_STRING);
					try {
						DocxUtils.initDocxParagraphPic(map.get(key), document, PIC_TYPE_QR);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ctr = null;
					imgRun = null;
				}else if(null != ctr && null != imgRun && key.startsWith(KEY_JBAR)){
					text = textSB.toString().replace(key, EMPTY_STRING);
					try {
						DocxUtils.initDocxParagraphPic(map.get(key), document, PIC_TYPE_JBAR);
					} catch (Exception e) {
						e.printStackTrace();
					}
					ctr = null;
					imgRun = null;
				}else{
					text = textSB.toString().replace(key, map.get(key).toString());
				}
				runs.get(p[0]).setText(text);
				key = EMPTY_STRING;
			}else if(map.get(key) instanceof BLOB){
				text = textSB.toString().replace(key, EMPTY_STRING);
				runs.get(p[0]).setText(text);
				if(null != ctr && null != imgRun){
					try {
						DocxUtils.initDocxParagraphPic(map.get(key), document, PIC_TYPE_BLOB);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				ctr = null;
				imgRun = null;
				key = EMPTY_STRING;
			}
			if(text.indexOf(START_FLAG) > -1) {
				b = DocxUtils.initDocxParagraph(i, runs, map, document);
			}
		}else if(!b && pList.size() > 0){
			for(int pi = 0; pi < pList.size(); pi++){
				prList.get(pi).get(pList.get(pi)).setText(pstrList.get(pi), 0);
			}
		}
		return b;
	}

	/**
	 * @description: DOCX文本替换
	 * @author : Hanyc
	 * @date : 2018-12-17 10:37:43
	 * @param inputStream
	 * @param outputStream
	 * @param map
	 * @from 转：https://blog.csdn.net/qq_34545192/article/details/77847849
	 */
	public static void initDocxParagraphPic(Object obj, XWPFDocument document, int type) throws Exception{
		CTInline sInline = ctr.getDrawingArray(ctris.get(ctris.size() - 1)).getInlineArray(0);

		CTInline inline = ctr.addNewDrawing().addNewInline();

		CTPositiveSize2D sCTP = sInline.getExtent();

		int w = (int)sCTP.getCx() / EMU;

		int h = (int)sCTP.getCy() / EMU;

		String ind = null;
		if(type == PIC_TYPE_BLOB){
			//取自数据库，BLOB格式
			picType = ImageIO.read(((BLOB)obj).getBinaryStream()).getType();
			ind = document.addPictureData(((BLOB)obj).getBinaryStream(), picType);
		}else if(type == PIC_TYPE_JBAR){
			//转为条形码
			picType = Document.PICTURE_TYPE_PNG;
			ind = document.addPictureData(PWUtils.getJBARBytes(obj.toString(), true, h), picType);
		}else if(type == PIC_TYPE_QR){
			//转为二维码
			picType = Document.PICTURE_TYPE_PNG;
			ind = document.addPictureData(PWUtils.getQrCodeBytes(obj.toString(), h, w), picType);
		}
		tobeRemoveCtrs.add(ctr);
		DocxUtils.insertPicture(document, picType, inline, w, h, ind, ind);
	}

	/**
	 * @description: 合并DOCX
	 * @author : Hanyc
	 * @date : 2019-06-26 14:57:36
	 * @param source 源文件
	 * @param dest 目标文件
	 */
	public static void mergeDocx(List<File> source, String dest) {
		if(source.size() > 0){
			OutputStream out = null;
			InputStream descIn = null;
			XWPFDocument destDocument = new XWPFDocument();
			CTBody destBody = destDocument.getDocument().getBody();
			InputStream sourceIn = null;
			OPCPackage sourcePackage = null;
			XWPFDocument sourceDocument = null;
			CTBody sourceBody = null;
			try{
				//合并到第一个文件
				for(File f : source){
					sourceIn = new FileInputStream(f);
					sourcePackage = OPCPackage.open(sourceIn);
					sourceDocument = new XWPFDocument(sourcePackage);
					sourceBody = sourceDocument.getDocument().getBody();
					DocxUtils.appendBodyDocx(destBody, sourceBody);
				}
				File file = new File(dest);
				if(!file.exists()){
					file.createNewFile();
				}
				out = new FileOutputStream(file);
				destDocument.write(out);
			}catch(Exception e){
				e.printStackTrace();
			}
			finally{
				try {
					if(null != out){out.close();}
					if(null != descIn){descIn.close();}
					if(null != sourceIn){sourceIn.close();}
					if(null != sourcePackage){sourcePackage.close();}
					if(null != destDocument){destDocument.close();}
					if(null != sourceDocument){sourceDocument.close();}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static final String DOCX_BR = "<w:br w:type=\"page\"/>";

	/**
	 * @description: 合并调用方法
	 * @author : Hanyc
	 * @date : 2019-06-26 14:56:49
	 * @param src
	 * @param append
	 * @throws Exception
	 */
	private static void appendBodyDocx(CTBody src, CTBody append) throws Exception {
		XmlOptions optionsOuter = new XmlOptions();
		optionsOuter.setSaveOuter();
		if(!src.isSetSectPr()){
			src.set(append);
		}else{
			String appendString = append.xmlText(optionsOuter);
			String srcString = src.xmlText();
			String prefix = srcString.substring(0, srcString.indexOf(">")+1);
			String mainPart = srcString.substring(srcString.indexOf(">")+1, srcString.lastIndexOf("<"));
			String sufix = srcString.substring(srcString.lastIndexOf("<") );
			String addPart = appendString.substring(appendString.indexOf(">") + 1, appendString.lastIndexOf("<"));
			CTBody makeBody = CTBody.Factory.parse(prefix+mainPart + DOCX_BR +addPart+sufix);
			src.set(makeBody);
		}
	}

	//jar包引用报错
	/*public static String toHtml(String path) throws Exception {
		XWPFDocument document = new XWPFDocument(new FileInputStream(path));
		XHTMLOptions options = XHTMLOptions.create().indent(4);
		// 导出图片
		File imageFolder = new File(new File(path).getParent());
		options.setExtractor(new FileImageExtractor(imageFolder));
		// URI resolver  word的html中图片的目录路径
		options.URIResolver(new BasicURIResolver("images"));
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		XHTMLConverter.getInstance().convert(document, outStream, options);
		return new String(outStream.toByteArray());
	}*/

	/**
	 * @description: DOCX格式转HTML
	 * @author : Hanyc
	 * @date : 2019-06-26 10:23:09
	 * @param path
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	public static String toHtml(String path) throws Exception {
		//		boolean nestLists = true;

		File file = new File(path);

		if(!file.exists()){
			return EMPTY_STRING;
		}

		WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(file);

		HTMLSettings htmlSettings = Docx4J.createHTMLSettings();
		String imgPath = PWUtils.getRealPath("tmp") + "_image";
		htmlSettings.setImageDirPath(imgPath);
		htmlSettings.setImageTargetUri(".." + File.separator + ".." + File.separator + ".." + File.separator
				+ imgPath.substring(imgPath.lastIndexOf(File.separatorChar) + 1));
		htmlSettings.setWmlPackage(wordMLPackage);

		String userCSS = null;
		//		if (nestLists) {
		userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img"
				+ ", table, caption, tbody, tfoot, thead, tr, th, td "
				+ "{ margin: 0; padding: 0; border: 0;}" + "body {line-height: 1;} ";
		//		} else {
		//			userCSS = "html, body, div, span, h1, h2, h3, h4, h5, h6, p, a, img, ol, ul, li"
		//					+ ", table, caption, tbody, tfoot, thead, tr, th, td "
		//					+ "{ margin: 0; padding: 0; border: 0;}" + "body {line-height: 1;} ";
		//		}
		htmlSettings.setUserCSS(userCSS);
		Docx4jProperties.setProperty("docx4j.Convert.Out.HTML.OutputMethodXML", true);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		Docx4J.toHTML(htmlSettings, outStream, Docx4J.FLAG_EXPORT_PREFER_XSL);
		//		String s = new String(outStream.toByteArray());
		//		String fileOutName = path.substring(0, path.lastIndexOf('.')) + ".html";
		//		FileUtils.writeStringToFile(new File(fileOutName), s, "utf-8");

		//		if (wordMLPackage.getMainDocumentPart().getFontTablePart() != null) {
		//			wordMLPackage.getMainDocumentPart().getFontTablePart().deleteEmbeddedFontTempFiles();
		//		}
		//
		//		htmlSettings = null;
		//		wordMLPackage = null;
		return new String(outStream.toByteArray(), "utf-8");
	}
}