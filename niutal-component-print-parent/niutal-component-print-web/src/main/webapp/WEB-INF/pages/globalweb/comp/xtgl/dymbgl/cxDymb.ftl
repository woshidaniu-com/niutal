[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
	</head>
	<body>
		<!-- tool bar-start  -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(mc:模板名称)"/] 
		     [@q.multi name="mblxdm" text="模板类型代码" list="dymblxList" listKey="dm" listValue="mc"  pinyin=false/]
		     [@q.radio name="qyzt" text="启用状态" list="#(1:启用,0:停用)" listKey="key" listValue="value" /]
		[/@q.panel]
		<!--查询条件  结束  -->
		<!-- table-start  -->
		<table id="tabGrid"></table>
		<!-- <div id="pager"></div> -->
		<!-- table-end  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/dymbgl.js?ver=${messageUtil("niutal.cssVersion")}"></script>
	</body>
</html>
