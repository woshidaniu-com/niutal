/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.editor.editorPlugin.wordUpload;

import java.io.File;
import java.io.FileInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.PictureType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.woshidaniu.filemgr.api.FileObject;
import com.woshidaniu.filemgr.api.FileServProvider;

/**
 * @description	： word到html转换器
 * @author 		：康康（1571）
 * @date		： 2018年10月19日 下午2:13:08
 * @version 	V1.0
 */
public class WordToHtmlTransform {
	
	private static final Logger log = LoggerFactory.getLogger(WordToHtmlTransform.class);

	//图片请求基础路径
	private String imageDownloadUrl;
	
	private FileServProvider fileServProvider;
	
	public WordToHtmlTransform(FileServProvider fileServProvider, String imageDownloadUrl) {
		this.fileServProvider = fileServProvider;
		this.imageDownloadUrl = imageDownloadUrl;
	}

	/**
	 * @description	：  生成图片请求路径
	 * @param imageFileName
	 * @return
	 */
	protected String generateImageRequestUrl(String imageFileName) {
		return this.imageDownloadUrl +imageFileName;
	}
	
	public String transform(File wordFile) throws Exception{
		
		log.debug("转换word文件:{}",wordFile);
		
		String filepath = wordFile.getAbsolutePath();

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		HWPFDocument wordDocument = new HWPFDocument(new FileInputStream(filepath));

		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(document);
		wordToHtmlConverter.setPicturesManager(new DefaultPicturesManager());
		wordToHtmlConverter.processDocument(wordDocument);

		Document htmlDocument = wordToHtmlConverter.getDocument();
		DOMSource domSource = new DOMSource(htmlDocument);
		StreamResult streamResult = new StreamResult(out);

		TransformerFactory tf = TransformerFactory.newInstance();
		
		Transformer transform = tf.newTransformer();
		
		transform.setErrorListener(new TransformErrorListener(wordFile));
		transform.setOutputProperty(OutputKeys.ENCODING, "utf-8");
		transform.setOutputProperty(OutputKeys.INDENT, "yes");
		transform.setOutputProperty(OutputKeys.METHOD, "html");
		transform.transform(domSource, streamResult);

		IOUtils.closeQuietly(out);
		return new String(out.toByteArray());
	}
	
	/**
	 * @description	： 转换异常监听器
	 * @author 		：康康（1571）
	 * @date		： 2018年10月18日 下午4:06:18
	 * @version 	V1.0
	 */
	class TransformErrorListener implements ErrorListener{

		private File wordFile;
		
		public TransformErrorListener(File wordFile) {
			this.wordFile = wordFile;
		}

		@Override
		public void warning(TransformerException exception) throws TransformerException {
			log.warn("转换时%s发生异常",this.wordFile.getAbsoluteFile(),exception);
		}

		@Override
		public void error(TransformerException exception) throws TransformerException {
			log.error("转换时%s发生异常",this.wordFile.getAbsoluteFile(),exception);
		}

		@Override
		public void fatalError(TransformerException exception) throws TransformerException {
			log.error("转换时%s发生异常",this.wordFile.getAbsoluteFile(),exception);
		}
	}
	
	/**
	 * @description	： 图片管理器
	 * @author 		：康康（1571）
	 * @date		： 2018年10月18日 下午3:54:46
	 * @version 	V1.0
	 */
	class DefaultPicturesManager implements PicturesManager{

		@Override
		public String savePicture(byte[] content, PictureType pictureType, String suggestedName, float widthInches,float heightInches) {
			try {
				FileObject fileObject = fileServProvider.output(content, suggestedName);
				String url = generateImageRequestUrl(fileObject.getUid());
				log.debug("提取图片名称:{},类型:{},宽度:{},高度:{}  ===>>> 映射加载url:{}",suggestedName,pictureType.toString(),widthInches,heightInches,url);
				return url;
			} catch (Exception e) {
				log.error("保存word图片异常",e);
			}
			return null;
		}
	}
}
