[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
	
	</head>
	<body>
		<!-- tool bar-start  -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!-- tool bar-end  -->
		[@q.panel theme="default"] 
		     [@q.input list="#(jsmc:角色名称,jssm:角色说明)"/] 
		     [@q.radio name="qyzt" text="启用状态" list="#(1:启用,0:停用)" listKey="key" listValue="value" /]
		[/@q.panel]
		<!-- table-start  -->
			<table id="tabGrid"></table>
			<!--<div id="pager"></div>  -->
		<!-- table-end  -->
		
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/jsgl.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>