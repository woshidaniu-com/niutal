<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/zxzx/yhbk.js?_rv_=<%=resourceVersion %>"></script>
		<script type="text/javascript">
			jQuery(function(){
				var yhbkGrid = new YhbkGrid();
				loadJqGrid("#tabGrid","#pager",yhbkGrid);
				bdan();
			});
		</script>
	</head>

	<body>
		<div class="toolbox">
			<jsp:include page="/WEB-INF/pages/globalweb/comm/ancd/ancd.jsp" flush="true" />
        </div> 
		<div class="formbox">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</body>
</html>
