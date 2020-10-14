<script type="text/javascript">
	//全局变量
	var _path = "${base}";
	var _systemPath = "${base}";
	var _stylePath = "${messageUtil("system.stylePath")}";
</script>	
<!--jQuery核心框架库+浏览器检测 -->
[#include "/globalweb/head/niutal-ui-base.ftl" /]
<!--Bootstrap布局框架-->
[#include "/globalweb/head/niutal-ui-bs.ftl" /]
<!--niutal-ui样式和脚本-->
[#include "/globalweb/head/niutal-ui-v5.ftl" /]

[#include "/globalweb/head/niutal-ui-bootbox.ftl" /]
<!--jQuery单选框美化插件-->
[#include "/globalweb/head/niutal-ui-icheck.ftl" /]
<!--jQuery模拟滚动条插件-->
[#include "/globalweb/head/niutal-ui-mCustomScrollbar.ftl" /]
<!--jQuery.chosen下拉框美化插件-->
[#include "/globalweb/head/niutal-ui-chosen.ftl" /]
<!--IE浏览器html5、CSS3兼容插件-->
[#include "/globalweb/head/niutal-ui-ie8-fix.ftl" /]
<!--业务框架jQuery全局设置和通用函数库-->
<script type="text/javascript" src="${base}/js/jquery.niutal.contact-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<!--v5公用代码调用-->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/niutal/v5/v5_script.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script><!--业务框架jQuery全局设置和通用函数库-->
