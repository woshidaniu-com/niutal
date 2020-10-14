<!doctype html>
<html lang="zh-CN">
<head>  
    [#include "/globalweb/head/niutal-ui-meta.ftl" /]
	[#include "/globalweb/head/niutal-ui-head.ftl" /]
	[#include "/globalweb/head/niutal-ui-validation.ftl" /]
	<sitemesh:write property='head'/>
</head>  
<body>  
	<header class="navbar-inverse top2" id="navbar_container">
		<div class="container-fluid">
			<div class="navbar-header">
				<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse">
					<span class="sr-only"> ${gnmkmc}</span> 
					<span class="icon-bar"></span> 
				</button>
				<a href="#" id="topButton" class="navbar-brand" >
					${gnmkmc}
				</a>
			</div>
		</div>
	</header>
	<div style="width: 100%; padding: 0px; margin: 0px;" id="bodyContainer">
		<div class="container container-func" style="width:100%;">
	   <!-- page content start -->
	    <sitemesh:write property='body'/>
	   <!-- page content end -->  
	   </div>
	</div>
	[#include "/globalweb/bottom.ftl" /]
</body>  
</html>