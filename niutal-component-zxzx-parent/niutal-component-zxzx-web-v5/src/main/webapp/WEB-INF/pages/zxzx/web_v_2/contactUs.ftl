<!doctype html>
<html>
<head>
<script type="text/javascript">
	function refreshCode(){
		document.getElementById("yzmPic").src = _path + '/kaptcha?time=' + new Date().getTime();
	}
	
	jQuery(function(){
		$('.main-body-con>ul li').removeClass('active');
		$('#lxwm').parent('li').addClass('active');
	});
	
</script>
</head>
<body style="">
	<div class="panel-body" id="lxfs">
		<h4>联系方式</h4>
		<p>
			联系老师：${zxzxConfig.CSSZ_LXR_DM}<br> 
			电话：${zxzxConfig.CSSZ_DH_DM}<br>
			邮箱：${zxzxConfig.CSSZ_DZYX_DM}<br> 
			地址: ${zxzxConfig.CSSZ_DZ_DM}
		</p>
		
	</div>
</body>
</html>
