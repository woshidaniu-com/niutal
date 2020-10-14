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
[#include "/globalweb/head/niutal-ui-mCustomScrollbar.ftl" /]
[#include "/globalweb/head/niutal-ui-chosen.ftl" /]
[#include "/globalweb/head/niutal-ui-ie8-fix.ftl" /]
[#include "/globalweb/head/niutal-ui-strength.ftl" /]
<!--业务框架jQuery全局设置和通用函数库-->
<script type="text/javascript" src="${base}/js/jquery.niutal.contact-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<!--v5公用代码调用-->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/niutal/v5/v5_script.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>

