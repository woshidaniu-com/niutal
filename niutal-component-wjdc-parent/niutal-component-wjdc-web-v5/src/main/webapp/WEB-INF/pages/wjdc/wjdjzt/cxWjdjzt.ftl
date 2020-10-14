[#assign q=JspTaglibs["/niutal-search-tags"] /]
[#assign shiro=JspTaglibs["http://shiro.apache.org/tags"] /]
<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
		[#include "/globalweb/head/niutal-ui-kindeditor.ftl" /]
		<title>答卷状态查询</title>
	</head>
	<body>
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default" id="defaultId2"]
		     [@q.input list="#(xh:学号,xm:姓名)"/]
		     [@q.multi name="wjid" text="问卷名称" list="wjList" listKey="WJID" listValue="WJMC"/]
		     [@q.radio name="djzt_flag" text="答卷状态" list="#(0:未开始,1:答卷中,2:完成)" listKey="value" listValue="text"/]
		     [@q.multi name="bmdm_id" text="部门" list="bmList" listKey="bmdm_id" listValue="bmmc"/]
		[/@q.panel]
		<!--查询条件  结束  -->
		<div class="formbox">
            <table id="tabGrid_djzt">
            </table>
		</div>
		<form method="get" action="${base}/wjdc/wjdjzt/exportDjztList.zf" id="export">
		</form>
		<script type="text/javascript" src="${base}/js/wjdc/cxWjdjzt.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>