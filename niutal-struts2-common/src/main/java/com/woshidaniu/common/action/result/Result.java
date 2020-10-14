package com.woshidaniu.common.action.result;

/**
 * 
 * @package com.woshidaniu.common.action.result
 * @className: Result
 * @description: 
 * <pre>
 * &lt;result-type name="chain" class="com.opensymphony.xwork2.ActionChainResult"/&gt;
 * &lt;result-type name="dispatcher" class="org.apache.struts2.dispatcher.ServletDispatcherResult" default="true"/&gt;
 * &lt;result-type name="freemarker" class="org.apache.struts2.views.freemarker.FreemarkerResult"/&gt;
 * &lt;result-type name="httpheader" class="org.apache.struts2.dispatcher.HttpHeaderResult"/&gt;
 * &lt;result-type name="redirect" class="org.apache.struts2.dispatcher.ServletRedirectResult"/&gt;
 * &lt;result-type name="redirectAction" class="org.apache.struts2.dispatcher.ServletActionRedirectResult"/&gt;
 * &lt;result-type name="stream" class="org.apache.struts2.dispatcher.StreamResult"/&gt;
 * &lt;result-type name="velocity" class="org.apache.struts2.dispatcher.VelocityResult"/&gt;
 * &lt;result-type name="xslt" class="org.apache.struts2.views.xslt.XSLTResult"/&gt;
 * &lt;result-type name="plainText" class="org.apache.struts2.dispatcher.PlainTextResult" /&gt;
 * <!-- struts-plugin -->
 * &lt;result-type name="json" class="org.apache.struts2.json.JSONResult"/&gt;
 * &lt;result-type name="jasper" class="org.apache.struts2.views.jasperreports.JasperReportsResult"/&gt;
 * &lt;result-type name="chart" class="org.apache.struts2.dispatcher.ChartResult"/&gt;
 * &lt;result-type name="jsf" class="org.apache.struts2.jsf.FacesResult"/&gt;
 * <!-- struts-portlet-plugin -->
 * &lt;result-type name="dispatcher" class="org.apache.struts2.portlet.result.PortletResult" default="true"/&gt;
 * &lt;result-type name="redirect" class="org.apache.struts2.portlet.result.PortletResult"/&gt;
 * &lt;result-type name="redirectAction" class="org.apache.struts2.portlet.result.PortletActionRedirectResult"/&gt;
 * &lt;result-type name="freemarker" class="org.apache.struts2.views.freemarker.PortletFreemarkerResult"/&gt;
 * &lt;result-type name="velocity" class="org.apache.struts2.portlet.result.PortletVelocityResult"/&gt;
 * <!-- struts-custom-plugin -->
 * &lt;result-type name="byte" class="com.ant4j.struts2.result.types.ByteStreamResult"/&gt;
 * &lt;result-type name="file" class="com.ant4j.struts2.result.types.FileStreamResult"/&gt;
 * &lt;result-type name="ajax" class="com.woshidaniu.common.action.result.types.ServletAjaxResult"/&gt;
 * </pre>
 * @author : kangzhidong
 * @date : 2014-4-10
 * @time : 上午10:54:55
 */
public final class Result {

	/**strtus2-default,strtus2-portlet-plugin*/
	
	public static final String DISPATCHER = "dispatcher";

	public static final String HTTPHEADER = "httpheader";

	public static final String REDIRECT = "redirect";

	public static final String REDIRECT_ACTION = "redirectAction";
	
	public static final String FREEMARKER = "freemarker";

	public static final String VELOCITY = "velocity";
	
	/**strtus2-default*/
	
	public static final String CHAIN = "chain";
	
	public static final String STREAM = "stream";

	public static final String XSLT = "xslt";

	public static final String PLAIN_TEXT = "plainText";
	
	public static final String DATA = "data";
	
	public static final String QUERY = "query";
	
	
	/**struts2-plugin */
	
	/**返回JSON格式数据*/
	public static final String JSON = "json";	
	/**返回JasperReport报表结果*/
	public static final String JASPER = "jasper"; 
	/**返回JFreeChart图表结果*/
	public static final String CHART = "chart"; 
	/**返回JSF 模板*/
	public static final String JSF = "jsf";
	
	/**strtus2-custom-plugin */
	
	/**返回TEXT格式数据*/
	public static final String AJAX_TEXT = "text";
	/**返回HTML格式数据*/
	public static final String AJAX_HTML = "html"; 
	/**返回XML格式数据*/
	public static final String AJAX_XML = "xml"; 
	/**返回JSON格式数据*/
	public static final String AJAX_JSON = "json"; 
	/**返回Byte字节流数据*/
	public static final String BYTE = "byte";
	/**返回FILE文件数据*/
	public static final String FILE = "file";
	/**返回FILE文件数据,返回后,删除源文件*/
	public static final String FILE_TEMP = "file_tmp";
	/**返回JFreeChart图表图片*/
	public static final String JCHART = "jchart";
	/**返回JasperReports报表结果文件*/
	public static final String JREPORT = "jreport";
	/**返回PDF 文件流*/
	public static final String PDF = "pdf"; 
	/**返回EXCEL 文件流*/
	public static final String EXCEL = "excel"; 
	
	/**strtus2-status */
	
	/**成功*/
	public static final String SUCCESS = "success";
	/**失败*/
	public static final String FAILED = "failed";
	/**空*/
	public static final String EMPTY = "empty";
	/**错误*/
	public static final String ERROR = "error";
	/**真*/
	public static final String TRUE = "true";
	/**假*/
	public static final String FALSE = "false";
	/**相同*/
	public static final String EQUAL = "equal";
	/**不相同*/
	public static final String UNEQUAL = "unequal"; 
	/**存在*/
	public static final String EXIST = "exist"; 
	/**不存在*/
	public static final String UNEXIST = "unexist";

	/**strtus2-forward */
	/** 返回纯字符串或HTML格式代码 */
	public final static String HTML = "html";
	/** 转到NULL*/
	public static final String NULL = null; 
	/**  转到卡片index页面查询 */
	public static final String RD_TAB_INDEX  = "tab_index";
	/** 转到index页面查询*/
	public static final String RD_INDEX = "index";
	/** 转到list页面查询*/
	public static final String RD_LIST = "list";
	/** 转到display页面查询*/
	public static final String RD_DISPLAY = "display";
	/** 转到新增页面*/
	public static final String RD_ADD = "add";
	/** 转到修改页面*/
	public static final String RD_MOD = "modify";
	/** 转到导出页面*/
	public static final String RD_EXP = "export";
	/** 转到导入页面*/
	public static final String RD_IMP = "import";
	/** 双击查询*/
	public static final String RD_LINE = "line";
	/** 转到打印页面*/
	public static final String RD_PRINT = "print";
	/** 转到统计页面*/
	public static final String RD_QUERY = "query";
	/** 页面嵌入信息查询*/
	public static final String RD_INFO = "info";
	/** 空白页*/
	public static final String RD_BLANK = "blank";
	/** 友情提示页面*/
	public static final String PROMPT = "prompt";
	/** 数据导入页面  */
	public static final String IMPORT = "import";
	/** 数据导出页面 */
	public static final String EXPORT = "export";
	/** 友情提示信息页面 */
	public static final String TOOLTIP = "tooltip";
	/** 通告页面 */
	public static final String NOTES = "notes";
	
}
