<!--jQuery.validation -->
<link rel="stylesheet" type="text/css" href="${messageUtil("system.stylePath")}/assets/plugins/validation/css/jquery.validate-min.css?ver=${messageUtil("niutal.cssVersion")}" />
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/validation/js/jquery.validate-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/validation/js/jquery.validate.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${base}/js/plugins/validation/jquery.validate.methods.contact-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/validation/lang/zh_CN-min.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
<script type="text/javascript">
	jQuery(function($){
		$('[data-toggle*="validation"]').trigger("validation");
	});
</script>