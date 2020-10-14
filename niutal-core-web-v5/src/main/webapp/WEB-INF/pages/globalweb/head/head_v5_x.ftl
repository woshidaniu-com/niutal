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
