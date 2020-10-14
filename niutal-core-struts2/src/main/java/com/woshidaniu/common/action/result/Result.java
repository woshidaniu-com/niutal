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
public final class Result extends org.apache.struts2.plus.dispatcher.result.Result {
	 
	/**strtus2-custom-plugin */
	
	/**返回TEXT格式数据*/
	public static final String AJAX_TEXT = "text";
	/**返回HTML格式数据*/
	public static final String AJAX_HTML = "html"; 
	/**返回XML格式数据*/
	public static final String AJAX_XML = "xml"; 
	/**返回JSON格式数据*/
	public static final String AJAX_JSON = "json"; 
	
	/**strtus2-forward */
	
	/** 返回纯字符串或HTML格式代码 */
	public final static String HTML = "html";
	/** 转到NULL*/
	public static final String NULL = null; 
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
