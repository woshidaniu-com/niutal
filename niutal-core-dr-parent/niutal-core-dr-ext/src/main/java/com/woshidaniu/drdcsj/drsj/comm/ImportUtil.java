/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.comm;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.woshidaniu.drdcsj.drsj.dao.entities.DrlpzModel;
import com.woshidaniu.drdcsj.drsj.ruler.IValidatorRuler;

public class ImportUtil {
	
	private static final Logger log = LoggerFactory.getLogger(ImportUtil.class);
	/**
	 * map数据结构转换为二维数组模式
	 * @param data
	 * @param drlpzList
	 * @return
	 */
	public static String[][] convertMap2Arr(Map<String, List<String>> data,List<DrlpzModel> drlpzList) {
		int colLength = drlpzList.size();
		int rowLength = data.get(drlpzList.get(0).getDrlmc()).size();
		List<String> list = null;
		String[][] dataArr = new String[rowLength][colLength];
		DrlpzModel drlpzModel = null;
		for (int i = 0; i < drlpzList.size(); i++) {
			drlpzModel = drlpzList.get(i);
			list = data.get(drlpzModel.getDrlmc());
			if (null == list) {
				continue;
			}
			for (int j = 0; j < list.size(); j++) {
				dataArr[j][i] = list.get(j);
			}
		}
		return dataArr;
	}
	/**
	 * 转换2为数组为Map类型
	 * @param data
	 * @param drlpzList
	 * @return
	 */
	public static Map<String, List<String>> convertArr2Map(String[][] data,List<DrlpzModel> drlpzList) {
		Map<String, List<String>> newdata = new HashMap<String, List<String>>();
		String row[] = data[0];// 列名数组
		for (int i = 0; i < row.length; i++) {// 查找每个列名对应的值
			if (null == row[i]) {
				continue;
			}
			List<String> cloumn = new ArrayList<String>();
			for (int j = 0; j < data.length; j++) {
				cloumn.add(data[j][i]);
			}
			newdata.put(row[i], cloumn);
		}
		return newdata;
	}
	/**
	 * 获取验证实例
	 * @param className
	 * @param param
	 * @return
	 */
	public static IValidatorRuler getValidatorRuler(String className,Object[] param) {
		try {
			Class<?> clazz = Class.forName(className);
			
			if (null == param) {
				return (IValidatorRuler) clazz.newInstance();
			}
			Class<?> clas[] = new Class<?>[param.length];
			for (int i = 0; i < param.length; i++) {
				clas[i] = param[i].getClass();
			}
			Constructor<?> cons = clazz.getConstructor(clas);
			return (IValidatorRuler) cons.newInstance(param);
		} catch (Exception e) {
			log.error("获得校验规则异常,类名称:{},参数",className,param,e);
		}
		return null;
	}
}
