<form id="new-topic-form" method="POST" action="${base}" data-base-url="${base}" 
	enctype="application/x-www-form-urlencoded">
	<div class="alert alert-success success-msg-tip hidden" role="alert" id="success-msg-tip">
		<strong>提示：</strong> <span id="success-msg"></span>
	</div>
	
	<div class="alert alert-danger error-msg-tip hidden" role="alert" id="error-msg-tip">
		<strong>提示：</strong> <span id="error-msg"></span>
	</div>
	
	<div class="modal-body" id="new-topic-body">
		<textarea name="zxnr" placeholder="提出你的问题" rows="" cols="" id="new-topic-zxnr" class="ask-textarea"></textarea>
	</div>
	<div class="modal-footer" id="new-topic-footer">
		<div class="float_l">
			<div id="wtlb" class="question-classify" data-url="${base}/zxzx/web/kzdk/zxlist/json.zf">
				<div class="item">
					<input type="hidden" name="bkdm"/>
					<i class="glyphicon glyphicon-tags"></i>
					<span id="new-topic-bkmc">请选择问题分类</span>
					<i class="glyphicon glyphicon-chevron-down"></i>
				</div>
				<div class="panel" id="wtlb-blocks" style="overflow-y: auto;height: 200px;">
					<!-- 板块代码 -->
				</div>
			</div>
		</div>
		<div class="float_r">
			<div class="checkbox-inline" style="margin-right: 10px;">
				<label class="radio-inline" for="anonymous"> 
				<input type="checkbox" name="sfnm" id="anonymous" value="1" />匿名</label>
			</div>
			<button type="button" class="btn btn-primary" id="new-topic-submit">提交</button>
			<button type="button" class="btn btn-default" data-dismiss="modal" id="new-topic-cancel">取消</button>
		</div>
	</div>
</form>
<script type="text/javascript">

	$(function(){
		$.when($.getJSON($('#new-topic-form').find('#wtlb').attr('data-url')))
		.then(
				function(data) {
					$('#new-topic-form').find('#wtlb-blocks').empty();
					$.each(data, function(i, n) {
						var item = $('<div>').addClass('block').on('click',
								function(event) {
									//console.debug($(this));
									$('#new-topic-form').find('#new-topic-bkmc').html($(this).attr('data-mc'));
									$('#new-topic-form').find('input[name="bkdm"]').val($(this).attr('data-dm'));
									$('#new-topic-form').find('#wtlb-blocks').toggle();
								});
						item.attr('data-dm', n['dm']);
						item.attr('data-mc', n['mc']);
						item.html(n['mc']);
						$('#new-topic-form').find('#wtlb-blocks').append(item);
					});
				}).always(function() {
		});
		
		var formEl = $('#new-topic-form');
		var base = formEl.attr('data-base-url');
		
		var FormUtils = {
				readonly : function() {
					formEl.find('button, textatrea, input').attr('disabled', 'disabled');
					formEl.find('.web-loading').remove();
					formEl.find('button').hide();
				},
				
				lock : function() {
					formEl.find('button, textatrea, input').attr('disabled', 'disabled');
					formEl.prepend('<div id="web-loading" class="web-loading"></div>');
				},

				unlock : function() {
					setTimeout(function(){
						formEl.find('button, textatrea, input').removeAttr('disabled', 'disabled');
						formEl.find('.web-loading').remove();
					}, 500);
				},

				error_message : function(msg) {
					$('#error-msg-tip', '#new-topic-form').find('#error-msg').html(msg);
					$('#error-msg-tip', '#new-topic-form').removeClass('hidden');
				},

				no_error_message : function() {
					$('#error-msg-tip').addClass('hidden');
				},
				
				success_message : function(msg) {
					$('#success-msg-tip').find('#success-msg').html(msg);
					$('#success-msg-tip').removeClass('hidden');
				},

				no_success_message : function() {
					$('#success-msg-tip').addClass('hidden');
				},

				validate : function() {
					if (!$.founded($('#new-topic-zxnr', '#new-topic-form').val())) {
						this.error_message('请填写咨询内容！');
						return false;
					}
					if (!$.founded($('input[name="bkdm"]','#new-topic-form').val())) {
						this.error_message('请选择咨询板块！');
						return false;
					}
					return true;
				}
			};
		
		$('#new-topic-submit').off('click').on('click', function(event){
			
			if (FormUtils.validate()) {
				//提交表单
				$.when(function() {
					$.ajax({
						url : base + "/zxzx/web/topic/auth/my-topic/new.zf",
						data : formEl.serialize(),
						type : "POST",
						beforeSend : function() {
							FormUtils.lock();
						},
						error : function(request) {

						},
						success : function(data) {
							FormUtils.no_error_message();
							FormUtils.no_success_message();
							var status = data['status'];
							var message = data['message'];
							//1:操作成功
							if (status === 'success') {
								FormUtils.success_message(message);
							}
							//2:操作失败
							else if (status === 'fail') {
								FormUtils.error_message(message);
							}
						}
					});
				}()).always(function() {
					FormUtils.unlock();
					FormUtils.readonly();
					setTimeout(function(){
						bootbox.hideAll();
						$('#my-topic').trigger('click');
					}, 1000);
				});
			}
			return false;
		});
		
	});
	
</script>
