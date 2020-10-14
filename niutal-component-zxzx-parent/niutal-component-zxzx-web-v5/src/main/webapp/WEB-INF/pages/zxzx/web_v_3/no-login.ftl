<!doctype html>
<html>
<head>
<script type="text/javascript">
	$(function() {
		$('#zxzx-login').off('click').on('click', function() {
			var mode_url = $('#zxzx-login').attr('data-mode-url');
			var base_url = $('#zxzx-login').attr('data-base-url');
		$('.right-area').trigger('event-loading');	
		$.when(
			$.getJSON(mode_url, function(data){
				var url = (data['url'] == undefined ? base_url : data['url']);
				if(data['mode'] === 'default'){
					window.open(url);
				}else if(data['mode'] === 'embend'){
					//加载嵌入的登陆页面
					$('.right-area').trigger('event-loading');	
					$('#my-topic-content').load($('#zxzx-login').attr('data-embend-url'),function(){
						$('.right-area').trigger('event-loaded');
					});
				}else{
					window.open(url);
				}
			})).always(function(){
				$('.right-area').trigger('event-loaded');
			});
			
			//检查登陆模式
			//根据登陆模式跳转到具体页面
		});
	});
</script>
</head>
<body>
	<!-- 未登录 -->
	<div class="tip-img">
		<img src="${base}/css/zxzx/images/web/tips.png" />
	</div>
	<div class="tip-font">
		<span class="gray">您还未登录,请</span> <span class="gray">
			<a data-mode-url="${base}/zxzx/web/config/loginMode/json.zf" 
			   data-base-url="${base}"
			   data-embend-url="${base}/zxzx/web/page/embend-login.zf?original-url=${originalUrl}";
			id="zxzx-login" href="javascript:void(0);">立即登录</a></span>
	</div>
</body>
</html>