<script type="text/javascript">
	//全局变量
	var _path = "${base}";
	var _systemPath = "${base}";
	var _stylePath = "${messageUtil("system.stylePath")}";
</script>	

[#include "/globalweb/head/niutal-ui-base.ftl" /]
<!--
jquery.ui 需要在bootstrap之前加载，解决冲突问题
http://blog.csdn.net/remote_roamer/article/details/14105999
-->
<script src="${messageUtil("system.stylePath")}/assets/js/jquery-ui-custom.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" type="text/javascript" charset="utf-8"></script>
[#include "/globalweb/head/niutal-ui-bs.ftl" /]
[#include "/globalweb/head/niutal-ui-v5.ftl" /]
[#include "/globalweb/head/niutal-ui-bootbox.ftl" /]
[#include "/globalweb/head/niutal-ui-icheck.ftl" /]
[#include "/globalweb/head/niutal-ui-chosen.ftl" /]
[#include "/globalweb/head/niutal-ui-tagsinput.ftl" /]
[#include "/globalweb/head/niutal-ui-ie8-fix.ftl" /]

<!--业务框架jQuery全局设置和通用函数库-->
<script type="text/javascript" src="${base}/js/jquery.niutal.contact-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<!--基于Nifty样式库的新版界面前端依赖-->
<script src="${messageUtil("system.stylePath")}/assets/plugins/nifty/js/nifty.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<link href="${messageUtil("system.stylePath")}/assets/plugins/nifty/css/nifty.min.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<!--皮肤+字体-->
<link href="${messageUtil("system.stylePath")}/assets/css/theme/niutal-ui-nifty-icons.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<link href="${messageUtil("system.stylePath")}/assets/css/theme/theme-ocean.min.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<link href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-base.css?ver=${messageUtil("niutal.jsVersion")}"  rel="stylesheet" type="text/css"/>
<link href="${messageUtil("system.stylePath")}/assets/css/niutal-ui-index.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>
<link href="${base}/css/niutal-ui-app.css?ver=${messageUtil("niutal.jsVersion")}" rel="stylesheet" type="text/css"/>


