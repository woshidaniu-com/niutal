<!doctype html>
<html lang="zh-CN">
	<head>
	</head>
	<body>
		<!--操作按钮 开始 -->
		[#include "/globalweb/comm/buttons.ftl" /]
		<input type="hidden" id="dataUrl" value="/ms/sms/list.zf"/>
		<!--操作按钮 结束  -->
		<!-- search start -->
		<form action="${base}/ms/sms/update.zf" data-toggle="validation"  id="ajaxForm"  role="form" name="form" method="post" >
			<!-- search-end  -->
			<div class="formbox">
	            <table id = "dataTable"></table>
			</div>
			<input type="hidden" name="ssmk" value="MS-SMS" id="ssmk"/>
		</form>
		[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
		<script type="text/javascript" src="${base}/js/ms/settings.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
	</body>
</html>