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
		[#if 'true' != messageUtil("userManage.showUserType")]
			[@q.panel theme="default"] 
		     [@q.input list="#(zgh:用户名,xm:姓名,lxdh:联系电话,dzyx:电子邮箱,jsxx:角色)"/] 
		     [@q.radio name="sfqy" text="启用状态" list="#(1:启用,0:停用)" listKey="key" listValue="value" /]
		     [@q.multi name="jgdm" text="部门" provider="bmdmService" listKey="bmdm_id" listValue="bmmc" pinyin=true/]
			[/@q.panel]
			
		[#else]
			[@q.panel theme="default"] 
		     [@q.input list="#(zgh:用户名,xm:姓名,lxdh:联系电话,dzyx:电子邮箱,jsxx:角色)"/] 
		     [@q.radio name="sfqy" text="启用状态" list="#(1:启用,0:停用)" listKey="key" listValue="value" /]
			 [@q.radio name="yhlx" text="用户类型" list="#(admin:管理员,teacher:教师,student:学生)" listKey="key" listValue="value" /]
		     [@q.multi name="jgdm" text="部门" provider="bmdmService" listKey="bmdm_id" listValue="bmmc" pinyin=true/]
			[/@q.panel]
		[/#if]
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
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/yhgl.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
