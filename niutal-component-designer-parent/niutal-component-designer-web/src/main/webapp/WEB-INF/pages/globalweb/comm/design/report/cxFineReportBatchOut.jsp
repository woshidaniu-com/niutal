<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%@ page language="java" import="java.util.*"%>
<%@ page language="java" import="java.lang.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<style type="text/css">
.container{
	border: 1px solid #0483d4;
    border-radius: 4px;
    color: #000;
    font-size: 24px;
    line-height: 50px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 50px;
    padding-left: 15px;
    padding-right: 15px;
    width: 50%;
}
</style>
</head>
<body style="height:100%">
	<div class="container ">报表打印中...</div>
</body>
	<!-- 不需要查询  -->
	<form action="<%=MessageUtil.getText("system.reportLocation")%>/cxFineReportBatchOut.jsp?_t=<%=new Date().getTime()%>" 
	id="reportSearchForm" class="form-horizontal" role="form" method="post" target="_self" style="display: none;">
		<input type="hidden" name="format" value="<s:property value="format"/>"/>
		<input type="hidden" name="reportlet" value="<s:property escape="false" value="funcReport.report_alias"/>.cpt"/>
		<s:iterator value="requestMap" var="requestRow">
			<input type="hidden" name="${requestRow.key}" value="${requestRow.value}"/>
		</s:iterator>
	</form>
</body>
<s:if test="requestMap != null">
<script type="text/javascript">
window.setTimeout(function(){
	document.getElementById("reportSearchForm").submit();
}, 200);
</script>
</s:if>
</html>