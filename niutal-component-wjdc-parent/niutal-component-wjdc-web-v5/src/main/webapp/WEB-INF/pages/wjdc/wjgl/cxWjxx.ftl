[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!DOCTYPE html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
		[#include "/globalweb/head/niutal-ui-kindeditor.ftl" /]
	</head>
	<body>
		<!--按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(wjmc:问卷名称,cjrmc:创建人)"/] 
		     [@q.multi name="wjzt" text="问卷状态" list="#(DRAFT:草稿,RUN:运行,STOP:停止)" listKey="name" listValue="text"/]
		     [@q.radio name="fblx" text="发布类型" list="#(0:普通,1:匿名)" listKey="name" listValue="text"/]
		     [@q.date name="cjsj" text="创建时间" format="YYYY-MM-DD"/]
		     [@q.date name="gqsj" text="过期时间" format="YYYY-MM-DD"/]
		[/@q.panel]
		<!--查询条件  结束  -->
		<div class="formbox">
            <table id="tabGrid">
            </table>
		</div>
		<script type="text/javascript" src="${base}/js/wjdc/wjgl.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
