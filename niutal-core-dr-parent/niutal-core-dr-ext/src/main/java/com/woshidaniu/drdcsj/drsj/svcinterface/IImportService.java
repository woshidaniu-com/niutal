/**
 * <p>Copyright (R) 2014 我是大牛软件股份有限公司。<p>
 */
package com.woshidaniu.drdcsj.drsj.svcinterface;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author 982 张昌路
 */
public interface IImportService {
	//版本v1
	static final String Version_v1 = "v1";
	//版本v2
	static final String Version_v2 = "v2";
	//
	static final String VersionKey = "version";
	//导入结果 
	//-1是系统内部错误 ，0是失败， 大于0是成功 ，v1版本使用
	static final String ResultKey = "result";
	//系统错误信息
	static final String SystemErrorKey = "systemError";
	//结果文件id
	static final String ResultFileIdKey = "resultFileId";
	//总条数
	static final String TotalSizeKey = "totalSize";
	//插入成功条数
	static final String SuccessInsertRowsSizeKey = "successInsertRowsSize";
	//更新成功条数
	static final String SuccessUpdateRowsSizeKey = "successUpdateRowsSize";
	//总共不接受的行数
	static final String TotalUnAcceptRowSizeKey = "totalUnAcceptRowSize";
	/**
	 * @description	： 导入数据
	 * @param importFile 要导入的文件
	 * @param data 要导入的Excel文件内的数据
	 * @param selectImportColumn 选择的列
	 * @param chooseDrpzModel  导入列配置模型
	 * @param yhm  用户名
	 * @return
	 */
	Map<String,String> importData(String drmkdm,String crfs,File importFile,Map<String, List<String>> data,List<String> selectImportColumn,String yhm) throws Exception;

}
