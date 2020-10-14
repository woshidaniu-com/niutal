<!doctype html>
<html>
<head>
	<script src="${base}/js/zxzx/web-pagination.js" type="text/javascript" charset="utf-8"></script>
	[#include "/zxzx/web_v_3/no-data.ftl" /]
	<script id="topic-item-templ" type="text/x-jsrender">
	<style type="text/css">
		.issue > .item > .problem > .left .icon{
			width:auto;
		}
	</style>
	{{for items}}
		<div class="item">
			<div class="problem clearfix">
				<div class="left">
					<span class="number">Q{{:#index+1}}: </span>{{:zxnr}}&nbsp;&nbsp;&nbsp;&nbsp;<span class="icon">{{:kzdkModel['bkmc']}}</span>
				</div>
				<div class="right">
					<span class="quizzer">【{{:zxr}}】</span> 提问于 <span class="date">{{:zxsj}}</span>
				</div>
			</div>
			<div class="answer">
				<div class="item">
					<span class="answerer">【回复】</span>
					<p style="word-wrap: break-word;word-break: normal;">{{:zxhfModel.hfnr}}</p>
				</div>
			</div>
		</div>
	{{else tmpl="#no-data-templ"}}
	{{/for}}
	</script>
	
	<script type="text/javascript">
		$(function(){
			$('.right-area').trigger('event-loading');
			var _selected_bkdms = eval('${webSearchBkdmValues}'.split(','));
			
			var _get_search_data = function(){
				var search_val = $.trim($('#zxzx-web-search-box').val());
				return {
						'webSearchValue': search_val||'',
						'webSearchBkdmValue':_selected_bkdms.toString()
				};
			};
			
			var _load_and_render_topic_data = function(pagination){
				$('.right-area').trigger('event-loading');
				var _search_val =  _get_search_data();
				return $.getJSON($('#topic-data').attr('data-url'),$.extend({}, pagination||{}, _search_val), function(data){
					if(data['totalResult']<=15){
						$('#web-pagination').hide();
					}
					var topic_item_templ = $.templates("#topic-item-templ");
					var html = topic_item_templ.render({'items':data['items']}); 
				    $("#topic-data").empty().prepend(html);
				    if($.founded(_search_val['webSearchValue'])){
				    	$("#topic-data").mark(_search_val['webSearchValue'], {
				    		element:'strong',
				    		className:'text-highlight',
				    		separateWordSearch:false,
				    		each:function(){},
				    		complete:function(){}
				    	}); 
				    }
				    $('.right-area').trigger('event-loaded');
				})
			};
			
			//debugger;
			$.when($.getJSON($('#topic-category').attr('data-url')).done(function(data){
				$.each(data,function(i,n){
					var item = $('<div>').addClass('item').on('click',function(event){
						//$('#topic-category>.item').toggleClass('active');
						$(this).toggleClass('active');
						$('#topic').click();
// 						_load_and_render_topic_data({
// 							'queryModel.showCount':15,
// 							'queryModel.currentPage':1
// 						});

						//需要重新创建分页控件
						
						return true;
					});
					
					if($.inArray(n['dm'],_selected_bkdms) >= 0){
						item.addClass('active');
					}
					
					item.attr('data-dm', n['dm']);
					item.html(n['mc']);
					$('#topic-category').append(item);
				});
			}), _load_and_render_topic_data({
				'queryModel.showCount':15,
				'queryModel.currentPage':1
			})).done(function(m1,m2){
				$('#web-pagination').pagination(m2[0]['totalResult'],{
					items_per_page: 15,
					callback: function(page, component) {
						_load_and_render_topic_data({
							'queryModel.showCount':15,
							'queryModel.currentPage':page+1
						});
						$('#scroll-up-btn').trigger('click');
					}
				});
			}).always(function(){
				$('.right-area').trigger('event-loaded');
			});
		});
	</script>
</head>
<body>
	<div class="classify clearfix" id="topic-category" data-url="${base}/zxzx/web/kzdk/list/json.zf">
<!-- 		<div class="item">缴费问题</div> -->
	</div>

	<div class="issue" id="topic-data" data-url="${base}/zxzx/web/topic/getTopics/json.zf">
		<!-- topic item goes here -->

<!-- 		<nav> -->
<!-- 			<ul class="pager"> -->
<!-- 				<li class="previous"><a href="#">上一页</a></li> -->
<!-- 				<li class="next"><a href="#">下一页 </a></li> -->
<!-- 			</ul> -->
<!-- 		</nav> -->

		
	</div>
	<nva id="web-pagination"></nav>
</body>
</html>