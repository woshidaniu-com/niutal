package com.woshidaniu.globalweb.word2pdf;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public class DocxToPDFConverter extends Converter {

	public DocxToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages, boolean closeStreamsWhenComplete) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete);
	}

	@Override
	public void convert() throws Exception {
		this.loading();

		PdfOptions options = PdfOptions.create();

		XWPFDocument document = new XWPFDocument(inStream);

		/*//支持中文字体
		options.fontProvider(new ITextFontRegistry() {
			@Override
			public Font getFont(String familyName, String encoding, float size, int style, Color color) {
				try {
					Resource fileRource = new ClassPathResource("simsunb.ttf", this.getClass());
					String path = fileRource.getFile().getAbsolutePath();

					BaseFont bfChinese = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
					Font fontChinese = new Font(bfChinese, size, style, color);
					if (familyName != null) {
						fontChinese.setFamily(familyName);
					}
					return fontChinese;
				} catch (Throwable e) {
					e.printStackTrace();
					return ITextFontRegistry.getRegistry().getFont(familyName, encoding, size, style, color);
				}
			}
		});*/

		this.processing();

		PdfConverter.getInstance().convert(document, outStream, options);

		this.finished();
	}
}