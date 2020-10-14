<!doctype html>
<html lang="zh-CN">
<head>
</head>
<body>
	<!--操作按钮 开始 -->
	[#include "/globalweb/comm/buttons.ftl" /]
	<!--操作按钮 结束  -->
	<!-- search start -->
	<form action="${base}/pwdmgr/setting/updateVerifi.zf" data-toggle="validation"  id="ajaxForm"  role="form" name="form" method="post" >
		<!-- search-end  -->
		<div class="formbox">
            <table id = "dataTable"></table>
		</div>
	</form>
	[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
	<script type="text/javascript" src="${base}/js/pwdmgr/verifiList.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
</body>
</html>