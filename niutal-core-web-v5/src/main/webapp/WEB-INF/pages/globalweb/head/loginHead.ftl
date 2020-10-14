<title>${messageUtil("system.title")}</title>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="Copyright" content="woshidaniu" />
<script type="text/javascript">
	//全局变量
	var _path = "${base}";
	var _systemPath = "${base}";
	var _stylePath = "${messageUtil("system.stylePath")}";
	var _reportPath = "${messageUtil("system.reportLocation")}";
</script>	
<!--jQuery核心框架库 -->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery-1.11.3-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<!--jQuery常用工具扩展库：基础工具,资源加载工具,元素尺寸相关工具 -->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery.utils.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--jQuery浏览器检测 -->
<script type="text/javascript" src="${base}/js/browse/browse-judge.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript">
	//\u6d4f\u89c8\u5668\u7248\u672c\u9a8c\u8bc1
	/*var broswer = broswer();
	if(broswer.msie==true||broswer.safari==true||broswer.mozilla==true||broswer.chrome==true){
		if(broswer.msie==true&&broswer.version<9){
		   window.location.href = _path+"/xtgl/init_cxBrowser.html";
		}
	}else{
		 window.location.href = _path+"/xtgl/init_cxBrowser.html";
	}*/
</script>
<!--jQuery基础工具库：$.browser,$.cookie,$.actual等 -->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery.plugins.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--jQuery自定义event事件库 -->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery.events.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--JavaScript对象扩展库：Array,Date,Number,String -->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/jquery.extends.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--Bootstrap布局框架-->
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/bootstrap/css/bootstrap.min.css?ver=${messageUtil("niutal.cssVersion")}" />
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/css/error.css?ver=${messageUtil("niutal.cssVersion")}" />
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/css/niutal-ui.css?ver=${messageUtil("niutal.cssVersion")}" />
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/bootstrap/js/bootstrap.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--Bootbox弹窗插件-->
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/bootbox/css/bootbox.css?ver=${messageUtil("niutal.cssVersion")}" />
<script src="${messageUtil("system.stylePath")}/assets/plugins/bootbox/bootbox.concat-min.js?ver=${messageUtil("niutal.jsVersion")}" type="text/javascript" charset="utf-8"></script>
<!--jQuery模拟滚动条库-->
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/customscrollbar/css/jquery.mCustomScrollbar.min.css?ver=${messageUtil("niutal.cssVersion")}" />
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/customscrollbar/js/jquery.mCustomScrollbar.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--jQuery.chosen美化插件-->
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/chosen/css/chosen-min.css?ver=${messageUtil("niutal.cssVersion")}" />
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/utils/jquery.utils.pinyin-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/chosen/jquery.choosen.concat-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<!--[if lt IE 9]>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/html5shiv.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/js/respond.min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<![endif]-->
<!--业务框架jQuery全局设置和通用函数库-->
<script type="text/javascript" src="${base}/js/jquery.niutal.contact-min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
