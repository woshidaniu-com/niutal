<!doctype html>
<html>
	<head>
		<script type="text/javascript" src="${base}/js/globalweb/comp/xtgl/login.js?ver=${messageUtil("niutal.jsVersion")}"></script>
	</head>
	<body style="background:#fafafa;" >
	    	<div class="container container_1170">
				<div class="row sl_log_top">
				  <div class="col-sm-6 logo_1"><img src="${messageUtil("system.stylePath")}/assets/images/logo/logo_jw_d.png" style="margin-top:-3px" />${messageUtil("system.title")}</div>
				  <div class="col-sm-6 text-right hidden-xs">
				
				  </div>
				</div>
				<div class="row sl_log_bor4">
				<div class="col-sm-8 hidden-xs sl_log_lf">
				<img class="img-responsive" src="${messageUtil("system.stylePath")}/assets/images/login_bg_pic.jpg"  />
				</div>
				<div class="col-sm-4 sl_log_rt">
				  <form class="form-horizontal" role="form" action="${base}/xtgl/login/slogin.zf" method="post" autocomplete="off">
				  	<input type="hidden" id="niutal_CSRF_TOKEN" name="niutal_CSRF_TOKEN" value="${CSRF_HTTP_SESSION_TOKEN}"/>
				  	<!-- 防止浏览器自动填充密码 -->
					<input type="text" style="display: none;" />
					<input type="password" style="display: none;" />
					<!-- 防止浏览器自动填充密码 end -->
				  
				    <h5>用户登录</h5>
				    [#if message??]
				    <p id="systips" class="bg-danger sl_danger">
				    	[#if message?size > 0]
							[#list message as item]
								<span class="glyphicon glyphicon-minus-sign"></span>${item}<br/>
							[/#list]
						[/#if]
				    </p>
				    [/#if]
				    <p id="tips" class="bg-danger sl_danger" style="display: none;"></p>
				    <div class="form-group">
				      <div class="input-group">
				        <div class="input-group-addon"><img src="${messageUtil("system.stylePath")}/assets/images/log_ic01.png" width="16" height="16"/></div>
				        	<input type="text" class="form-control" name="yhm" id="yhm"  placeholder="用户名"  autocomplete="off"/>
				      </div>
				    </div>
				    <div class="form-group">
				      <div class="input-group">
				        <div class="input-group-addon"><img src="${messageUtil("system.stylePath")}/assets/images/log_ic02.png" width="16" height="16"/></div>
				        <input type="password" class="form-control" name="mm" id="mm"  placeholder="密码"   autocomplete="off"/>
				      </div>
				    </div>
				    [#if messageUtil("isOpenKaptcha") == '' || messageUtil("isOpenKaptcha")=="true"]
				  	<div id="yzmDiv" class="form-group">
				      <div class="input-group col-xs-4 pull-left">   
				        <input name="yzm" type="text" id="yzm" class="form-control" placeholder="验证码">
				      </div>
				      <div class="input-group col-xs-4 col-xs-offset-5">
					      <img border="0" align="absmiddle" id="yzmPic" style="cursor: hand;"
					       onclick="javascript:refreshCode();" name="yzmPic" width="108" height="34" />
				      </div>
				    </div>
				   [/#if]
				   <div class="form-group">
						<!--<a href="<%=systemPath %>/xtgl/init_fkIndex.html" class="checkbox pull-left" target="_blank">访客登录</a> -->
						<a href="${base}/pwdmgr/retake/index.zf" class="checkbox pull-right" target="_blank">忘记密码了？</a>
					</div>
				   <div class="form-group">
				    <div class="form-group">
				        <button type="button" class="btn btn-primary btn-block" id="dl">登 录</button>
				    </div>
				  </form>
				  <!--
				  <div class="sl_log_ewm hiddmen-xs"><img src="${messageUtil("system.stylePath")}/img/login_ewm.gif" width="90" height="90"/>&nbsp;&nbsp;<span>用手机扫一扫,安全、便捷登录</span></div>
				  </div>
				  -->
				</div>
			</div>
		</div>
	</body>
</html>

