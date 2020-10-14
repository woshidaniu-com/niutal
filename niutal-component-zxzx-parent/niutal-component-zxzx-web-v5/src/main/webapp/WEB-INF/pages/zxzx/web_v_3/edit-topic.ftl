
<form id="edit-topic-form" method="POST" action="${base}" data-base-url="${base}" 
	data-url = "${base}/zxzx/web/topic/auth/my-topic/${topicId}/get.zf"
	enctype="application/x-www-form-urlencoded">
	<input type="hidden" name="zxid" id="edit-topic-zxid"/>
	<input type="hidden" name="bkdm" id="edit-topic-bkdm"/>
	
	<div class="alert alert-success success-msg-tip hidden" role="alert"
		id="success-msg-tip">
		<strong>提示：</strong> <span id="success-msg"></span>
	</div>
	
	<div class="alert alert-danger error-msg-tip hidden" role="alert"
		id="error-msg-tip">
		<strong>提示：</strong> <span id="error-msg"></span>
	</div>
	
	<div class="modal-body" id="edit-topic-body">
		<textarea name="zxnr" placeholder="提出你的问题" rows="" cols="" id="edit-topic-zxnr"
			class="ask-textarea"></textarea>
	</div>
	<div class="modal-footer" id="edit-topic-footer">
		<div class="float_l">
			<div id="wtlb" class="question-classify" data-url="${base}/zxzx/web/kzdk/zxlist/json.zf">
				<div class="item">
					<i class="glyphicon glyphicon-tags"></i><span id="edit-topic-bkmc">请选择问题分类</span><i
						class="glyphicon glyphicon-chevron-down"></i>
				</div>
				<div class="panel" id="wtlb-blocks">
					<!-- 板块代码 -->
				</div>
			</div>
		</div>
		<div class="float_r">
			<div class="checkbox-inline" style="margin-right: 10px;">
				<label class="radio-inline" for="edit-topic-sfnm"> 
				<input type="checkbox" name="sfnm" id="edit-topic-sfnm" value="1" />匿名
				</label>
			</div>
			<button type="button" class="btn btn-primary" id="edit-topic-submit">保存</button>
			<button type="button" class="btn btn-default" data-dismiss="modal" id="edit-topic-cancel">取消</button>
		</div>
	</div>
</form>
<script type="text/javascript">

	$(function(){
		var formEl = $('#edit-topic-form');
		var base = formEl.attr('data-base-url');
		
		var FormUtils = {
				lock : function() {
					formEl.find('button, textatrea, input').attr('disabled', 'disabled');
					formEl.prepend('<div id="web-loading" class="web-loading"></div>');
				},

				readonly : function() {
					formEl.find('button, textatrea, input').attr('disabled', 'disabled');
					formEl.find('.web-loading').remove();
					//formEl.prepend('<div id="web-loading" class="web-loading"></div>');
					formEl.find('button').hide();
				},
				
				unlock : function() {
					setTimeout(function(){
						formEl.find('button, textatrea, input').removeAttr('disabled', 'disabled');
						formEl.find('.web-loading').remove();
					}, 500);
				},

				error_message : function(msg) {
					$('#error-msg-tip', '#edit-topic-form').find('#error-msg').html(msg);
					$('#error-msg-tip', '#edit-topic-form').removeClass('hidden');
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
					if (!$.founded($('#edit-topic-zxnr', '#edit-topic-form').val())) {
						this.error_message('请填写咨询内容！');
						return false;
					}
					if (!$.founded($('input[name="bkdm"]','#edit-topic-form').val())) {
						this.error_message('请选择咨询板块！');
						return false;
					}
					return true;
				}
			};
		console.log($('#edit-topic-form').find('#wtlb').attr('data-url'));
		$.when($.getJSON($('#edit-topic-form').find('#wtlb').attr('data-url')))
		.then(
				function(data) {
					$('#edit-topic-form').find('#wtlb-blocks').empty();
					$.each(data, function(i, n) {
						var item = $('<div>').addClass('block').on('click',
								function(event) {
									$('#edit-topic-form').find('#edit-topic-bkmc').html($(this).attr('data-mc'));
									$('#edit-topic-form').find('input[name="bkdm"]').val($(this).attr('data-dm'));
									$('#edit-topic-form').find('#wtlb-blocks').toggle();
								});
						item.attr('data-dm', n['dm']);
						item.attr('data-mc', n['mc']);
						item.html(n['mc']);
						$('#edit-topic-form').find('#wtlb-blocks').append(
								item);
					});
				}).always(function() {
		});
		
		//加载数据
		FormUtils.lock();
		
		$.when($.getJSON($('#edit-topic-form').attr('data-url')))
		.then(
				function(data) {
					if(data){
						var zxnr = data['zxnr'];
						var zxid = data['zxid'];
						var bkdm = data['bkdm'];
						var bkmc = data['kzdkModel']['bkmc'];
						var sfnm = data['sfnm'];

						$('#edit-topic-zxid','#edit-topic-form').val(zxid);
						$('#edit-topic-zxnr','#edit-topic-form').val(zxnr);
						$("#edit-topic-sfnm",'#edit-topic-form').prop("checked", sfnm === '1');
						$('#edit-topic-bkdm','#edit-topic-form').val(bkdm);
						$('#edit-topic-bkmc','#edit-topic-form').html(bkmc);
						$('#wtlb-blocks','#edit-topic-form').find('[data-dm="' + bkdm + '"]').prepend('<i class="glyphicon glyphicon-ok"></i>').addClass('active');
					}
				}).always(function() {
					FormUtils.unlock();
		});
		
		
		$('#edit-topic-submit').off('click').on('click', function(event){
			
			if (FormUtils.validate()) {
				//提交表单
				$.when(function() {
					$.ajax({
						url : base + "/zxzx/web/topic/auth/my-topic/edit.zf",
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
