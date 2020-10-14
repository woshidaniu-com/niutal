package com.woshidaniu.common.template.method;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

/**
 *  unit(val,unit)
 */
@Component("capacityUnit")
public class CapacityUnitMethod implements TemplateMethodModelEx {

	protected static Logger LOG = LoggerFactory.getLogger(CapacityUnitMethod.class);
	protected static Pattern pattern_find = Pattern.compile("^([1-9]\\d*|[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)(B|KB|MB|GB|TB|PB|EB|ZB|YB|BB)$");
	protected static Map<String,CapacityUnit> powers = new HashMap<String, CapacityUnit>();
	
	static{
		
		powers.put("KB", CapacityUnit.KILOBYTES);
		powers.put("MB", CapacityUnit.MEGABYTES);
		powers.put("GB", CapacityUnit.GIGABYTES);
		powers.put("TB", CapacityUnit.TRILLIONBYTES);
		
	}
	
	@Override
	public Object exec(List arguments) throws TemplateModelException {
		if (arguments != null && !arguments.isEmpty() && arguments.size() == 2 
				&& arguments.get(0) != null && StringUtils.isNotEmpty(arguments.get(0).toString())
				&& arguments.get(1) != null && StringUtils.isNotEmpty(arguments.get(1).toString())) {
			String value = arguments.get(0).toString().trim();
			String unit = arguments.get(1).toString();
			BigDecimal num = new BigDecimal(value);
			BigDecimal rt  = new BigDecimal(powers.get(unit).toKilobytes(num.doubleValue()));
			return new SimpleScalar(rt.toPlainString() + unit);
		}
		return null;
	}
	
}
