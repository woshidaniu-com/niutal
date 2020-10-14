<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/operation.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/szSsjs.js?_rv_=<%=resourceVersion%>"></script>
</head>
<style>
.ui-jqgrid tr.jqgrow td {
  	white-space: normal !important;
  	vertical-align:middle;
  	text-align: center;
}
.ui-jqgrid tr.jqgrow td a{
	text-decoration: underline;
	color: blue;
}
.dataRangeBox{
    clear:both;
	text-align: left;
	min-height: 50px;
	width:790px;
	height:435px;
	word-wrap: break-word;
	overflow-x:hidden;
	margin:2px;
}

.lisnavBox{
	clear: both;
}

.lisnavBox li{
	min-height: 23px;
	line-height: 23px;
	list-style: none;
	list-style-type: none;
	overflow: hidden;
	-moz-background-clip: padding;
	-webkit-background-clip: padding-box;
	background-clip: padding-box;
}

.lisnavBox li p{
	background: none;
	padding-bottom: 0;
	margin: 0;
}

</style>
<script type="text/javascript">
</script>
<body style="padding: 2px;">
<div class="formbox "  style="width: 100%">
	<table id="tabGrid" style="width: 100%"></table>
	<%--<div id="pager" style="width: 100%"></div>
	--%><input type="hidden" id="zgh" name="zgh" value="<s:property value="model.zgh"/>" />
	<s:iterator value="jsList">
		<input type="hidden" name="jsdm" value="<s:property value="jsdm" />" />
	</s:iterator>
</div>
<table width="100%" border="0" class="formlist">
	<tfoot>
		<tr>
			<td colspan="4">
				<div class="btn">
					<button type="button" onclick="saveForm();return false;" name="btn_tj">
						保 存
					</button>
					<button id="btn_gb" onclick="iFClose(); returnfalse;">
						关 闭
					</button>
				</div>
			</td>
		</tr>
	</tfoot>
</table>
</body>
</html>