<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery.form.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/drdcsj/import.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/xsxxgl/cxXsxx.js?_rv_=<%=resourceVersion%>"></script>

	</head>

	<body>	
		<div class="toolbox">
				<!-- 加载当前菜单栏目下操作     -->
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
				<!-- 加载当前菜单栏目下操作 -->
	      </div> 
		<s:form theme="simple" id="form1">
			<div class="searchtab">
				<jsp:include page="/WEB-INF/pages/search/supersearch.jsp?id=jcsj_xsxx" />
			</div>
			<div class="formbox">
				<table id="tabGrid"></table>
				<div id="pager"></div>
			</div>
		</s:form>
	</body>
</html>
