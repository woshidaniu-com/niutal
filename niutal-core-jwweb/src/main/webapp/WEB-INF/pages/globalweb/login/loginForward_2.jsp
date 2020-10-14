<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/login_v5.ini"%>
<script type="text/javascript">
	jQuery(function($){
        //用户名密码 输错三次 需输入验证码            
		<s:if test="#session.dlCount >= 3">
			$("#yzmDiv").show();
		  	refreshCode();
		</s:if>
	});
</script>
</head>
<body style="background: #fafafa;">
	<div class="container container_1170">
		<div class="row sl_log_top">
			<div class="col-sm-8 logo_1">
				<img src="<%=stylePath%>/images/logo/logo_jw_d.png" style="margin-top: -3px" /><%=MessageUtil.getLocaleText("system.title")%></div>
			<div class="col-sm-4 text-right hidden-xs">
				<%--
  <a href="http://www.woshidaniu.com" target="_blank">教务处主页</a>
  <a href="#" target="_blank">登录说明</a>
  --%>
			</div>
		</div>
		<div class="row sl_log_bor4">
			<div class="col-sm-8 hidden-xs sl_log_lf">
				<img class="img-responsive"	src="<%=stylePath%>/images/login_bg_pic.jpg" />
			</div>
			<div class="col-sm-4 sl_log_rt">
				<form class="form-horizontal" role="form"
					action="<%=systemPath%>/xtgl/login_login.html" method="post">
					<h5>用户登录</h5>
					<!-- 防止浏览器自动填充密码 -->
					<input type="text" style="display: none;" /> <input type="password"
						style="display: none;" />
					<!-- 防止浏览器自动填充密码 end -->
					<p style="display: none;" id="tips" class="bg_danger sl_danger">
					</p>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<img src="<%=stylePath%>/images/log_ic01.png" width="16" height="16" />
							</div>
							<input type="text" class="form-control" name="yhm" id="yhm"
								value="" placeholder="用户名" onblur="" autocomplete="off">
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<img src="<%=stylePath%>/images/log_ic02.png" width="16" height="16" />
							</div>
							<input type="password" class="form-control" name="mm" id="mm"
								value="" placeholder="密码" autocomplete="off">
						</div>
					</div>
					<div id="yzmDiv" class="form-group" style="display: none;">
						<div class="input-group col-xs-4 pull-left">
							<input name="yzm" type="text" id="yzm" class="form-control"
								placeholder="验证码">
						</div>
						<div class="input-group col-xs-4 col-xs-offset-5">
							<img border="0" align="absmiddle" id="yzmPic"
								style="cursor: hand;" onclick="javascript:refreshCode();"
								name="yzmPic" width="108" height="34" />
						</div>
					</div>
					<div class="form-group">

						<!--<a href="<%=systemPath%>/xtgl/init_fkIndex.html" class="checkbox pull-left" target="_blank">访客登录</a>
      
      -->
						<%--<a href="#" class="checkbox pull-right" target="_blank">忘记密码了？</a>
    --%>
					</div>
					<div class="form-group">
						<button type="button" class="btn btn-primary btn-block" id="dl">登
							录</button>
					</div>
				</form>
				<div class="sl_log_ewm hidden-xs">
					<img src="<%=stylePath%>/images/login_ewm.gif" width="90" height="90" />&nbsp;&nbsp;<span>用手机扫一扫,安全、便捷登录</span>
				</div>
			</div>
		</div>
	</div>
	</div>
	<jsp:include page="../bottom.jsp" />
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/login.js?ver=<%=jsVersion%>"></script>
</html>
