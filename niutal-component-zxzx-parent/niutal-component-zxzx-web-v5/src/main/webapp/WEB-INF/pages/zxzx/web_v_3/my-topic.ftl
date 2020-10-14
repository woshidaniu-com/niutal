<!doctype html>
<html>
<head>
	<script src="${base}/js/zxzx/web-pagination.js" type="text/javascript" charset="utf-8"></script>
	[#include "/zxzx/web_v_3/no-data.ftl" /]
	<script id="my-topic-item-templ" type="text/x-jsrender">
			{{for items}}
					<div class="block" data-bkdm="{{:bkdm}}", data-zxid="{{:zxid}}" data-type="item-block">
						<div class="issue">
							<span class="number">Q{{:#index+1}}: </span>{{:zxnr}}
						</div>
						<div class="operator clearfix">
							{{if !zxhfModel.hfid}}
							<div class="float_l">
								<i data-type="del" class="glyphicon glyphicon-trash"></i> 
								<i data-type="edit" class="glyphicon glyphicon-pencil"></i>
							</div>
							{{/if}}
							
							{{if zxhfModel.hfid}}
							<div class="float_l">
								<i class="glyphicon glyphicon-trash disabled"></i> 
								<i class="glyphicon glyphicon-pencil disabled"></i>
							</div>
							{{/if}}

							<div class="float_r">
								<span class="date">{{:zxsj}}</span>
							</div>
						</div>
						{{if zxhfModel.hfid}}
						<div class="article clearfix" data-hfid="{{:zxhfModel.hfid}}">
							<div class="font">
								<span class="answerer">【回复】</span>
								<p style="word-wrap: break-word;word-break: normal;">{{:zxhfModel.hfnr}}</p>
								<div class="release-time clearfix">
									<div>
										<span class="date">{{:zxhfModel.hfsj}}</span>
									</div>
								</div>
							</div>
						</div>
						{{/if}}

						{{if !zxhfModel.hfid}}
						<div class="noAnswer">
							暂无解答
						</div>
						{{/if}}

					</div>
			{{else tmpl="#no-data-templ"}}
				
			{{/for}}		
	</script>
	
	<script type="text/javascript">
		$(function(){
			$('.right-area').trigger('event-loading');
			
			var _load_and_render_my_topic_data = function(pagination){
				$('.right-area').trigger('event-loading');
				return $.getJSON($('#my-topic-data').attr('data-url'),pagination||{}, function(data){
					if(data['totalResult']<=15){
						$('#web-pagination-my-topic').hide();
					}
					var templ = $.templates("#my-topic-item-templ");
					var html = templ.render({'items':data['items']}); 
				    $("#my-topic-data").empty().prepend(html);
				    $('.right-area').trigger('event-loaded');
				});
			};
			
			//加载首页数据
			$.when(_load_and_render_my_topic_data({
				'queryModel.showCount':15,
				'queryModel.currentPage':1
			})).done(function(m1){
				//var blocks = $('.block','#my-topic-data');
				//console.debug(m1);
				$('#web-pagination-my-topic').pagination(m1['totalResult'],{
					items_per_page: 15,
					callback: function(page, component) {
						_load_and_render_my_topic_data({
							'queryModel.showCount':15,
							'queryModel.currentPage':page+1
						});
						$('#scroll-up-btn').trigger('click');
					}
				});

				$('[data-type="edit"]', '#my-topic-data').off('click').on('click', function(){
					var zxid = $(this).closest('[data-type="item-block"]').attr('data-zxid');
					var edit_topic_url = _path + '/zxzx/web/page/auth/' + zxid + '/edit-topic.zf';
					//topicId
					var dialog = bootbox.dialog({
					    message: '<div class="web-loading"></div>',
					    closeButton: false
					});
					dialog.init(function(){
					    setTimeout(function(){
					        dialog.find('.bootbox-body').load(edit_topic_url);
					    }, 100);
					});
					
				});
				
				$('[data-type="del"]', '#my-topic-data').off('click').on('click', function(){
					var zxid = $(this).closest('[data-type="item-block"]').attr('data-zxid');
					var del_confirm = bootbox.confirm({
						message:'<h4 class="text-danger">您确认要删除此记录？</h4>',
					    closeButton:false,
					    buttons: {
					        confirm: {
					            label: '确定',
					            className: 'btn-danger'
					        },
					        cancel: {
					            label: '取消',
					            className: 'btn-default'
					        }
					    },
					    callback: function (result) {
					        if(result){
					        	var loading = $('<div class="web-loading"></div>');
					        	del_confirm.find('.bootbox-body').empty().append(loading);
					        	del_confirm.find('.modal-footer').hide();
					        	$.post(_path + '/zxzx/web/topic/auth/my-topic/' + zxid + '/del.zf').done(
					        		function(data){
					        			var status = data['status'];
					        			var res_msg = $.founded(data['message']) ? data['message'] : '删除成功！';
						               	var message = $('<h4 class=" text-center">' + res_msg + '</h4>');
						               	if(status === 'success'){
						               		message.addClass('text-success');
						               	}else{
						               		message.addClass('text-danger');
						               	}
						               	
						               	loading.hide();
						               	del_confirm.find('.bootbox-body').append(message);
						               	
						               	setTimeout(function(){
						               		del_confirm.modal('hide');
						               		$('#my-topic').trigger('click');
						               	}, 1000);
					        		}		
					        	);
					        	return false;
					        }
					    }
					});
				});
			}).always(function(){
				$('.right-area').trigger('event-loaded');
			});
		});
	</script>
</head>
<body>
	<div class="issue wdtw" id="my-topic-data" data-url="${base}/zxzx/web/topic/auth/my-topic/list/json.zf">
		
		<!-- topic item goes here -->
	</div>
	<nva id="web-pagination-my-topic"></nav>
</body>
</html>