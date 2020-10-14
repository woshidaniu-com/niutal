<!doctype html>
<html>
<head>
	[#include "/zxzx/web_v_3/no-data.ftl" /]
	<script id="faq-item-templ" type="text/x-jsrender">
	{{for items}}
		<div class="item">
			<div class="problem clearfix">
				<div class="left">
					<span class="number">Q{{:#index+1}}: </span>{{:wtnr}}&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon">{{:bkmc}}</span>
				</div>
			</div>
			<div class="answer">
				<div class="item">
					<span class="answerer">【回复】</span>
					<p style="word-wrap: break-word;word-break: normal;">{{:wthf}}</p>
				</div>
			</div>
		</div>
	{{else tmpl="#no-data-templ"}}
	{{/for}}
	</script>
	
	<script type="text/javascript">
		$(function(){
			$('.right-area').trigger('event-loading');
			//加载首页数据
			$.when($.getJSON($('#faq-data').attr('data-url'), function(data){
				var faq_item_templ = $.templates("#faq-item-templ");
				var html = faq_item_templ.render({'items':data}); 
			    $("#faq-data").prepend(html);
			})).done(function(m1){

			}).always(function(){
				$('.right-area').trigger('event-loaded');
			});
		});
	</script>
</head>
<body>
	<div class="issue" id="faq-data" data-url="${base}/zxzx/web/topic/getFAQs/json.zf">	
		<!-- faq item goes here -->
	</div>
</body>
</html>