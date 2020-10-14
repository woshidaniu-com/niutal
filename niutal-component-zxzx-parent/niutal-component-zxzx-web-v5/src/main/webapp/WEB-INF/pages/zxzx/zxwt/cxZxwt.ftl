[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
		[#include "/globalweb/head/niutal-ui-laydate.ftl" /]
		[#include "/globalweb/head/niutal-ui-bsSpinner.ftl" /]
		[#include "/globalweb/head/niutal-ui-filestyle.ftl" /]
		[#include "/globalweb/head/niutal-ui-fileupload.ftl" /]
	</head>

	<body>
		<!-- tool bar-start  -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<!-- tool bar-end  -->
		
		<!--查询条件  开始 -->
		[@q.panel theme="default"] 
		     [@q.input list="#(zxnr:咨询内容)"/] 
		     [@q.multi name="bkdm" text="版块" list="kzdkList" listKey="bkdm" listValue="bkmc" hasBlank=false/]
		     [@q.multi name="hfzt" text="回复状态" list="#(0: 未回复,1: 已回复)" hasBlank=false/]
		     [@q.date name="zxsj" text="咨询时间" format="YYYY-MM-DD hh:mm:ss"/]
		[/@q.panel]
		<!--查询条件  结束  -->
		
		
       <!--查询条件  结束  -->
		<div class="formbox">
            <table id = "tabGrid"></table>
		</div>
		
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<!-- 公共导出功能js脚本  -->
		[#include "/globalweb/head/niutal-core-dc.ftl" /]
		<script type="text/javascript" src="${base}/js/zxzx/zxwt.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</body>
</html>
