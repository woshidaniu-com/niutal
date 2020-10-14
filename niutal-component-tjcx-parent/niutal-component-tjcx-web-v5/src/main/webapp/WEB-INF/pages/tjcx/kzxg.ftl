<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid.ini"%>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/tjcxUtils.js?_rv_=<%=resourceVersion%>"></script>
		<script type="text/javascript" src="<%=systemPath %>/js/tjcx/comp/kzxg.js?_rv_=<%=resourceVersion%>"></script>
	</head>
	<body>
	
	<div class="toolbox">
			<!-- 加载当前菜单栏目下操作     -->
	  	<div class="buttonbox">
			<ul id="but_ancd">
					<li>
						<a href="javascript:void(0);" id="btn_xg" class="btn_xg">
							修改 </a>
					</li>				
					<li>
						<a href="javascript:void(0);" id="btn_sc" class="btn_sc">
							删除 </a>
					</li>
			</ul>
		</div>
	</div>
	
	<div class="formbox">
		<table id="tabGrid"></table>
		<div id="pager"></div>
	</div>

</body>
</html>