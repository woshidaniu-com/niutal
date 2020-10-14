<!doctype html>
<html lang="zh-CN">
<head>
</head>
<body>
	<!-- search start -->
	<form action="strategyList.zf" data-toggle="validation"  id="ajaxForm"  role="form" name="form" method="post" >
		<!-- search-end  -->
		<div class="formbox">
            <table id = "dataTable"></table>
		</div>
	</form>
	[#include "/globalweb/head/niutal-ui-bsTable.ftl" /]
<script type="text/javascript" src="${base}/js/pwdmgr/strategyList.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
</body>
</html>