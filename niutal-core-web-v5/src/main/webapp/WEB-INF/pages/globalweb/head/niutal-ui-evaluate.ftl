<!--软件评价依赖库-->
<script type="text/javascript" src="${messageUtil("system.stylePath")}/assets/plugins/qrcode/jquery.qrcode.min.js?ver=${messageUtil("niutal.jsVersion")}"></script>
<script type="text/javascript" src="${base}/js/evaluate/fc.js?ver=${messageUtil("niutal.jsVersion")}"></script>

<script type="text/javascript">
	$(function(){
		//软件评价地址
		var _evaluate_url = "${evaluateUtil()}";
		addFc("right", _evaluate_url);//第一个参数控制显示位置,第二个参数是缩放大小,第三个为地址
	});
	
</script>	