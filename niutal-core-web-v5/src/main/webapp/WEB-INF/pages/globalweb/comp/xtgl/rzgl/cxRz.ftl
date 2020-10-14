[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
	</head>
	<body>
		[@q.panel theme="default"] 
		     [@q.input list="#(czmk:业务模块,czr:操作用户,czms:操作描述,zjm:主机名,ipdz:IP地址)"/] 
		     [@q.multi name="czlx" text="操作类型" list="#(select:查询,insert:新增,update:更改,delete:删除,login:登录,logout:登出)" listKey="key" listValue="value" /]
		     [@q.date name="czrq" text="操作日期" format="YYYY-MM-DD hh:mm:ss"/]
		[/@q.panel]
		<table id="tabGrid"></table>
		<!-- <div id="pager"></div> -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/rzgl.js"></script>
	</body>
</html>
