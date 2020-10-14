<!DOCTYPE html>
<html lang="zh_CN">
<head>
	<title>${messageUtil("system.title")} - 找回密码</title>
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="Copyright" content="woshidaniu" />
	<link rel="icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	<link rel="shortcut icon" href="${base}/logo/favicon.ico" type="image/x-icon" />
	<!--新开窗口专用默认引用-->
	[#include "/globalweb/head/head_v5_x.ftl" /]
	[#include "/globalweb/head/niutal-ui-steps.ftl" /]
	[#include "/globalweb/head/niutal-ui-validation.ftl" /]
	[#include "/globalweb/head/niutal-ui-strength.ftl" /]
	<link rel="stylesheet" type="text/css" href="${base}/css/pwdmgr/screen.css?ver=${messageUtil("niutal.cssVersion")}" />
	<script type="text/javascript" src="${base}/js/pwdmgr/findPassword.js?ver=${messageUtil("niutal.jsVersion")}" charset="utf-8"></script>
	<style type="text/css">
	/* Validation */
	section .pwd-btn{width:404px;}
	@media screen and (max-width: 650px){
	 section .pwd-btn{ 
	  width: 249px;
	    text-align: center;
	    margin: 0 auto;
	    line-height: 28px;}
	}

	label.error {
	    color: #cc5965;
	    display: inline-block;
	    margin-left: 5px;
	}
	
	.form-control.error {
	    border: 1px solid #cc5965;
	}
	
	.widget.news{
		min-height:580px;
	}
	
	.widget.news .title{
		margin-bottom: 10px;
	}
	.widget.news .meta {
	    margin: 25px 0 ;
	    text-indent: 1em;
	}
	.text-padding-10px{padding: 10px;}
	.text-tip{
		font-size: 18px;
		line-height: 40px;
		margin-bottom: 20px;
	}
	.wizard .actions ul{
		width: 40% !important;
	}
	</style>
	
	
</head>
<body>
	<!-- start header -->
	<header class="main-header">
		<div class="container">
			<div class="row">
			   <div class="col-sm-12">
				  <h1> <span>找回密码</span> <small>Retrieve the password</small></h1>
			   </div>
			</div>
		</div>
	</header>
	
	<div class="container">
		<div class="row">
			<main class="post type-post col-md-10 col-md-offset-1 sidebar">
				<!-- start tag cloud widget -->
				<div class="widget news">
					<header class="entry-head">
						<h3 class="title">立即找回密码</h3>
						<div class="meta">
							<p>
								当您忘记密码时，系统会向您提供重新设置密码的途径。如果希望实时接收帐号风险提醒、开通更多安全保护。
							</p>
						</div>
					</header>
					<div class="content community">
						<!-- Entry Content -->
						<div class="entry-content">
							<input type="hidden" id="step" value="${step}"/>
						    <form id="example-advanced-form" class="form-horizontal" action="#" method = "post" >
								<input type="hidden" id="csrftoken" name="csrftoken" value="${sessionScope.csrftoken}"/>
								<h3>选择密保工具</h3>
								<section>
									<h4 class="col-md-12 text-danger text-center text-padding-10px text-verif-tip" id="verifTip0">&nbsp;</h4>
									<div class="col-md-8 col-md-offset-3">
									[#list strategyList as item]
										[#if item.status == '1' ]
										<div class="form-list-half col-md-12 m-b-30 form_retake_type">
											<div class="radio radio-primary ">
												<input type="radio" name="retakeType" id="inlineRadio${item_index}" value="${item.name}" [#if item_index == 0 ]checked="checked"[/#if] [#if item.status =='0']class="desabled"[/#if] />
												<label for="inlineRadio${item_index}">${item.desc}</label>
											</div>
											<div>
												<input type="text" name="retakeTypeValue" class="retakeTypeInput" style="width: 200px;display:none;"/>
											</div>
										</div>
										[/#if]
								  	[/#list]
								  	</div>
								</section>
								<h3>验证密保工具</h3>
								<section>
									<h4 class="col-md-12 text-danger text-center text-padding-10px text-verif-tip">&nbsp;</h4>
									<div class="col-md-12">
										<p class="col-md-12 text-center text-tip" id="verifTip1_text"></p>
								  	</div>
								</section>
								<h3>填写验证码</h3>
								<section>
									<h4 class="col-md-12 text-danger text-center text-padding-10px text-verif-tip" id="verifTip2">&nbsp;</h4>
									<div class="col-md-12">
										<p class="col-md-12 text-center text-tip" id="mobileTip">已为您预留的密保手机:<a href="#" >138*****267</a>发送了验证码，有效期为5分钟,请尽快完成验证。</p>
									</div>
									<div class="col-md-4 col-md-offset-4">
										<div class="form-group">
									    	<div class="col-md-12">
									    		<input type="hidden" id="returnUuid" name="uuid" value=""/>
									    		<input id="captcha" name="captcha" type="text" class="required form-control" placeholder="请输入验证码..."/>
									    	</div>
									  	</div>
									</div>
									<p class="col-md-4 col-md-offset-4 text-right pwd-btn">还没有收到验证码？ 您可以尝试：  <button type="button" class="btn btn-primary btn-xs" id ="reinput" autocomplete="off">重新填写</button>  <button type="button" class="btn btn-primary btn-xs" id ="repeatedSend" data-loading-text="59秒钟后再次发送..." autocomplete="off">再次发送</button></p>
								</section>
								<h3>修改密码</h3>
								<section>
									<h4 class="col-md-12 text-danger text-center text-padding-10px text-verif-tip" id="verifTip3">&nbsp;</h4>
									<div class="col-md-8 col-md-offset-2">
										<div class="col-md-12 col-sm-12">
											[#if rules?size > 0]
									 			<div id="closeAlert" class="alert alert-warning">
									 			<a href="#" class="close" data-dismiss="alert">×</a>
									 			<strong>密码规则：</strong>
												[#list rules as item]
													<p>${item_index+1}. ${item}</p>
												[/#list]
												</div>
											[/#if]
											<div class="form-group">
												<label for="" class="col-sm-3 control-label">
													<span class="red">*</span>新密码
												</label>
												<div class="col-sm-8" >
													<input type="password" maxlength="16"  placeholder="请输入新密码..." name="newPassword" id="newPassword" class="form-control" data-rules='{"required":true,"strength":true}' autocomplete="off"/>
												</div>
											</div>
										</div>
										<div class="col-md-12 col-sm-12">
											<div class="form-group">
												<label for="" class="col-sm-3 control-label">&nbsp;</label>
												<div class="col-sm-8" id="strengthID"></div>
											</div>
										</div>
										<div class="col-md-12 col-sm-12">
											<div class="form-group">
												<label for="" class="col-sm-3 control-label">
													<span class="red">*</span>再次输入
												</label>
												<div class="col-sm-8">
													<input type="password" maxlength="16" placeholder="请再次输入新密码..." name="repeatPassword" id="repeatPassword" class="form-control" data-rules='{"required":true,"equalTo":"#newPassword"}' autocomplete="off"/>
												</div>
											</div>
										</div>
										<div class="col-md-12 col-sm-12">
											<div class="form-group">
												<label for="" class="col-sm-3 control-label">&nbsp;</label>
											</div>
										</div>
										<p class="col-md-4 col-md-offset-4 text-right text-success" style="font-size: 18px;" id="successTip">&nbsp;</p>
									</div>
								</section>
							</form>	
						</div>
					</div>
				</div>
			</main>
		</div>
	</div>
	[#include "/globalweb/bottom.ftl" /]
</body>
</html>