<!doctype html>
<html lang="zh-CN">
<head>  
   	[#include "/globalweb/head/niutal-ui-meta.ftl" /]
	[#include "/globalweb/head/niutal-ui-head.ftl" /]
	<style type="text/css">
	.bootbox.modal{overflow-y: hidden !important;text-align: justify;}
	</style>
	<!--v5公用代码调用-->
	<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/niutal/v5/niutal-ui-page.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
	[#include "/globalweb/head/niutal-ui-validation.ftl" /]
	<sitemesh:write property='head'/>
</head>  
<body>  
	<div style="width: 100%; padding: 0px; margin: 0px;overflow: hidden;" id="bodyContainer" class="bodyContainer">
		<div class="container-fluid container-func" style="width:100%;padding: 10px; margin: 0px;">
	   <!-- page content start -->
	    <sitemesh:write property='body'/>
	   <!-- page content end -->  
	   </div>
	</div>
</body>  
<script type="text/javascript">
jQuery(function($) {
	var pageHeight = $(window).height() - $('#navbar_container').outerHeight(true) - $('#footerID').outerHeight(true);
	$('.bodyContainer').css("min-height",pageHeight);
	// console.debug('>>niutal console debug<<');
});


</script>
[#include "/globalweb/head/niutal-ui-evaluate.ftl" /]
</html>