[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!DOCTYPE html>
<html>
	<head>
	</head>
	<body>
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(zgh:用户名,xm:姓名,lxdh:联系电话,dzyx:电子邮箱)"/] 
		     [@q.radio name="ejsq" text="状态" list="#(1:已分配,0:未分配)" listKey="key" listValue="value" /]
		     [@q.multi name="jgdm" text="部门" provider="bmdmService" listKey="bmdm_id" listValue="bmmc" /]
		[/@q.panel]
		<!--查询条件  结束  -->
		
		<!--JQGrid 开始 -->
		<div class="formbox">
			<table id="tabGrid"></table>
			<!--<div id="pager"></div>-->
		</div>
		<!--JQGrid 结束  -->
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/ejsq.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
