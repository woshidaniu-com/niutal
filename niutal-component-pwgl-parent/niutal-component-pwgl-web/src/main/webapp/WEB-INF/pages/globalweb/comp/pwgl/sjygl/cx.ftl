[#assign q=JspTaglibs["/niutal-search-tags"]/]
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/css/globalweb/comp/pwgl/style.css?ver=${messageUtil("niutal.cssVersion")}"/>
		<link rel="stylesheet" type="text/css" href="${base}/css/globalweb/comp/pwgl/checkboxAndRadioStyle.css?ver=${messageUtil("niutal.cssVersion")}"/>
		[#-- 自己整合的工具类 --]
		<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/api/tools.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		[#-- 列表展现 --]
		<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/sjygl/sjyglPaged.js?ver=${messageUtil("niutal.jsVersion")}"></script>
		[#-- 功能绑定 --]
		<script type="text/javascript" src="${base}/js/globalweb/comp/pwgl/sjygl/sjygl.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</head>
	<body>
		[#-- 操作按钮 --]
		[#include "/globalweb/comm/buttons.ftl"/]
		
		[#-- 查询条件 开始 --]
		[@q.panel theme="default"]
			[@q.input list="#(mkmc:模块名称)"/]
			[@q.radio name="mkdm" text="数据源" list="sjyList" listKey="dm" listValue="mc"/]
		[/@q.panel]
		[#-- 查询条件 结束 --]
		
		<div class="formbox"> 
			<table id="tabGrid"></table>
		</div>
		[#include "/globalweb/head/bsTable.ftl"/]
	</body>
</html>