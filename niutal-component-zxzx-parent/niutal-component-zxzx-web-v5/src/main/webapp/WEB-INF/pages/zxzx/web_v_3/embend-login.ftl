<!doctype html>
<html>
<head>
<script type="text/javascript">
	$(function() {
		$("#captchaPic").off('click').on(
				'click',
				function(event) {
					$(this).attr("src",
							_path + '/kaptcha?time=' + new Date().getTime());
				});

		$('#embend-login-form').off('keypress').on('keypress',function(event){
			if(event.keyCode == "13"){
				$("#embend-login-btn").trigger('click');
	        }
		});
		
		//表单提交事件
		$("#embend-login-btn").off('click').on('click', function(event) {
			//检查表单数据
			//异步提交表单
			//根据表单调用返回的接口处理逻辑
			var formEl = $('#embend-login-form');
			var base = formEl.attr('data-base-url');

			var FormUtils = {
				lock : function() {
					formEl.find('button').attr('disabled', 'disabled');
				},

				unlock : function() {
					formEl.find('button').removeAttr('disabled', 'disabled');
				},

				error_message : function(msg) {
					$('#error-msg-tip').find('#error-msg').html(msg);
					$('#error-msg-tip').removeClass('disabled');
				},

				no_error_message : function() {
					$('#error-msg-tip').addClass('disabled');
				},

				validate : function() {
					if (!$.founded($('#userName').val())) {
						this.error_message('请输入用户名！');
						return false;
					}
					if (!$.founded($('#password').val())) {
						this.error_message('请输入密码！');
						return false;
					}
					if (!$.founded($('#captcha').val())) {
						this.error_message('请输入验证码！');
						return false;
					}
					return true;
				}
			};

			if (FormUtils.validate()) {
				$.when(function() {
					$.ajax({
						url : base + "/zxzx/web/auth.zf",
						data : formEl.serialize(),
						type : "POST",
						beforeSend : function() {
							$('.right-area').trigger('event-loading');
							FormUtils.lock();
						},
						error : function(request) {

						},
						success : function(data) {
							var state = data['state'];
							//1:登录成功
							if (state === 1) {
							//跳转到指定的页面
								var origial_url = $('#embend-login-form').attr('data-original-url');
								$('#embend-login-form').find('#error-msg-tip').addClass('disabled');
								var parent_div = $('#embend-login-form').parent();
								$('.right-area').trigger('event-loading');
								$.when(
										parent_div.load(origial_url, function(){
											
										})	
								).always(function(){
									$('.right-area').trigger('event-loaded');
								});
								return true;
							}
							//0:登录不成功
							if (state === 0) {
								$('#embend-login-form').find('#error-msg').html('用户名或密码错误,请重新输入!');
							}
							//2:验证码错误
							if (state === 2) {
								$('#embend-login-form').find('#error-msg').html('验证码输入错误,请重新输入!');
							}
							//9:其他未知错误
							if (state === 9) {
								$('#embend-login-form').find('#error-msg').html('未知错误,请联系管理员!');
							}
							$('#embend-login-form').find('#error-msg-tip').removeClass('disabled');
							$("#captchaPic").trigger('click');
						}
					});
				}()).always(function() {
					$('.right-area').trigger('event-loaded');
					FormUtils.unlock();
				});

			}

		});

		$("#captchaPic").trigger('click');

	});
</script>
</head>
<body>
	<form action="${base}" enctype="application/x-www-form-urlencoded"
		method="POST" data-async 
		data-original-url="${originalUrl}"
		data-base-url="${base}" 
		id="embend-login-form"
		class="embend-login-form">
		<!-- 登录 -->
		<div class="modal-dialog">
			<div class="modal-content">

				<div class="modal-body">

					<div class="alert alert-danger error-msg-tip disabled" role="alert"
						id="error-msg-tip">
						<strong>提示：</strong> <span id="error-msg"></span>
					</div>

					<div class="input" id="input-username-group">
						<i class="glyphicon glyphicon-user"></i> <input type="text"
							class="form-control" placeholder="请输入账号" name="userName"
							id="userName" value="" />
					</div>
					<div class="input" id="input-password-group">
						<i class="glyphicon glyphicon-lock"></i> <input type="password"
							class="form-control" placeholder="请输入密码" name="password"
							id="password" value="" />
					</div>
					<div class="input" id="input-captcha-group">
						<i class="glyphicon glyphicon-info-sign"></i> <input type="text"
							class="form-control" placeholder="请输入验证码" name="CAPTCHA"
							id="captcha" value="" /> <img border="0" align="absmiddle"
							id="captchaPic"
							style="cursor: pointer; position: absolute; right: 8px; top: 2px;"
							name="captchaPic" width="108" height="30" />
					</div>
					<div class="login-btn">
						<button class="btn btn-md btn-primary width-100" type="button"
							id="embend-login-btn">登 录</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>