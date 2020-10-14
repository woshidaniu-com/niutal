[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!DOCTYPE html>
<html>
	<head>
	<style>
		.advanced-query-fixed .panel-heading{height:auto !important; }
	</style>
	</head>
	<body>
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default"]
		    [@q.input list="#(dcclbh:配置编号,dcclmc:配置名称)"/]
		[/@q.panel]
		<!--查询条件  结束  -->
		
		<!--JQGrid 开始 -->
		<div class="formbox">
			<table id="tabGrid"></table>
			<!--<div id="pager"></div>-->
		</div>
		<!--JQGrid 结束  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		[#include "/globalweb/head/niutal-ui-strength.ftl" /]
		[#include "/globalweb/head/niutal-core-dc.ftl" /]
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/dcpz.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
