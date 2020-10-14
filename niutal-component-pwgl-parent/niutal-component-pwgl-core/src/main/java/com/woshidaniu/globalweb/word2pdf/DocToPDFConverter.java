/**
 * <p>Coyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.globalweb.word2pdf;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.docx4j.Docx4J;
import org.docx4j.convert.in.Doc;
import org.docx4j.convert.out.FOSettings;
import org.docx4j.fonts.IdentityPlusMapper;
import org.docx4j.fonts.Mapper;
import org.docx4j.fonts.PhysicalFont;
import org.docx4j.fonts.PhysicalFonts;
import org.docx4j.jaxb.Context;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.RFonts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class DocToPDFConverter extends Converter {

	public DocToPDFConverter(InputStream inStream, OutputStream outStream, boolean showMessages,
			boolean closeStreamsWhenComplete) {
		super(inStream, outStream, showMessages, closeStreamsWhenComplete);
	}

	@Override
	public void convert() throws Exception {
		this.loading();

		InputStream iStream = inStream;
		try {
			WordprocessingMLPackage wordMLPackage = this.getMLPackage(iStream);
			Mapper fontMapper = new IdentityPlusMapper();
			String fontFamily = "SimSun";

			Resource fileRource = new ClassPathResource("simsun.ttf");
			String path =  fileRource.getFile().getAbsolutePath();
			URL fontUrl = new URL("file:"+path);
			PhysicalFonts.addPhysicalFont(fontUrl);

			PhysicalFont simsunFont = PhysicalFonts.get(fontFamily);
			fontMapper.put(fontFamily, simsunFont);

			//设置文件默认字体
			RFonts rfonts = Context.getWmlObjectFactory().createRFonts();
			rfonts.setAsciiTheme(null);
			rfonts.setAscii(fontFamily);
			wordMLPackage.getMainDocumentPart().getPropertyResolver().getDocumentDefaultRPr().setRFonts(rfonts);
			wordMLPackage.setFontMapper(fontMapper);
			FOSettings foSettings = Docx4J.createFOSettings();
			foSettings.setWmlPackage(wordMLPackage);
			Docx4J.toFO(foSettings, outStream, Docx4J.FLAG_EXPORT_PREFER_XSL);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(outStream);
		}

		this.finished();

	}

	protected WordprocessingMLPackage getMLPackage(InputStream iStream) throws Exception {

		System.setOut(new PrintStream(new OutputStream() {
			@Override
			public void write(int b) {
				// DO NOTHING
			}
		}));

		WordprocessingMLPackage mlPackage = Doc.convert(iStream);
		return mlPackage;
	}
}