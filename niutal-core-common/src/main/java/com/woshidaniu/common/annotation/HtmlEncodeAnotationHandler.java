/**
 * 
 */
package com.woshidaniu.common.annotation;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;

/**
 * @author kzd
 * 
 */
public class HtmlEncodeAnotationHandler {

	public static final String HTML = "HTML";

	public static final String HTML_ATTRIBUTE = "HTML_ATTRIBUTE";

	public static final String JAVASCRIPT = "JAVASCRIPT";

	public static final String JAVASCRIPT_ATTRIBUTE = "JAVASCRIPT_ATTRIBUTE";

	static Class<?> HTMLEncodeAnotationClazz = HTMLEncode.class;

	public static void handle(List<?> list){
		if(list != null && list.size() > 0){
			for (Object object : list) {
				handle(object);
			}
		}
	}
	
	/**
	 * 处理字段编码
	 * 
	 * @param target
	 */
	public static void handle(Object target) {
		if (target != null) {
			Class<?> clazz = target.getClass();
			Field[] fields = clazz.getDeclaredFields();
			try {
				for (Field field : fields) {
					Annotation[] annotations = field.getAnnotations();
					for (Annotation annotation : annotations) {
						if (HTMLEncodeAnotationClazz == annotation
								.annotationType()) {
							HTMLEncode htmlAn = (HTMLEncode) annotation;
							String type = htmlAn.type();
							PropertyDescriptor pd = new PropertyDescriptor(field
									.getName(), clazz);
							Method readMethod = pd.getReadMethod();
							Method writeMethod = pd.getWriteMethod();
							Object readField = readMethod != null ? readMethod
									.invoke(target) : null;
							String encodeValue = null;
							if (readField != null
									&& readField instanceof String
									&& StringUtils.isNotBlank(readField
											.toString())) {
								if (HTML.equalsIgnoreCase(type)) {
									encodeValue = Encode.forHtml(readField
											.toString());
								} else if (HTML_ATTRIBUTE
										.equalsIgnoreCase(type)) {
									encodeValue = Encode
											.forHtmlAttribute(readField
													.toString());
								} else if (JAVASCRIPT.equalsIgnoreCase(type)) {
									encodeValue = Encode
											.forJavaScript(readField.toString());
								}
								if (writeMethod != null) {
									writeMethod.invoke(target, encodeValue);
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
