<script type="text/javascript">
	//全局变量
	var _path = "${base}";
	var _systemPath = "${base}";
	var _stylePath = "${messageUtil("system.stylePath")}";
</script>	

[#include "/globalweb/head/niutal-ui-base.ftl" /]
[#include "/globalweb/head/niutal-ui-bs.ftl" /]
[#include "/globalweb/head/niutal-ui-v5.ftl" /]
[#include "/globalweb/head/niutal-ui-bootbox.ftl" /]
[#include "/globalweb/head/niutal-ui-ie8-fix.ftl" /]
[#include "/globalweb/head/niutal-ui-rsa.ftl" /]
<!--业务框架jQuery全局设置和通用函数库-->
<script type="text/javascript" src="${base}/js/jquery.niutal.contact-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<!--基于Nifty样式库的新版界面前端依赖-->
<link href="${messageUtil("system.stylePath")}/assets/plugins/nifty/css/nifty.min.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<!--皮肤+字体-->
<link href="${messageUtil("system.stylePath")}/assets/css/theme/theme-ocean.min.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<link href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-base.css?ver=${messageUtil("niutal.jsVersion")}"  rel="stylesheet" type="text/css"/>
<link href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-login.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>

