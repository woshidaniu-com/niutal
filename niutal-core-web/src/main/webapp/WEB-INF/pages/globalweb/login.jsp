<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%
	String appLogo = MessageUtil.getText("system.logo.app");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/validate.js"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/checkPassword.js"></script>
		<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/login.js"></script>
		
		<!-- RSA加密 -->
		<script type='text/javascript' src="<%=systemPath %>/js/rsa/jsbn.js"></script>
		<script type='text/javascript' src="<%=systemPath %>/js/rsa/prng4.js"></script>
		<script type='text/javascript' src="<%=systemPath %>/js/rsa/rng.js"></script>
		<script type='text/javascript' src="<%=systemPath %>/js/rsa/rsa.js"></script>
		<script type='text/javascript' src="<%=systemPath %>/js/rsa/base64.js"></script>
		
		<script type="text/javascript">
			var modulus,exponent;
		
			jQuery(document).ready(function(){
                refreshCode();
                
                jQuery.getJSON("<%=systemPath %>/xtgl/login_getPublicKey.html?time="+new Date().getTime(),function(data){
            		modulus = data["modulus"];
            		exponent = data["exponent"];
            	});
			});
			
			//登录
			function dl() {
				if(isEmpty('yhm') || isEmpty('mm')){
					showErrMsg("用户名和密码不能为空！");
					return false;
				}
				
				if(isEmpty('yzm')){
					showErrMsg("验证码不能为空！");
					return false;
				}
				
				var rsaKey = new RSAKey();
				rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
				var enPassword = hex2b64(rsaKey.encrypt(jQuery("#mm").val()));
				jQuery("#mm").val(enPassword);
				jQuery("#loginForm").submit();
			}
		</script>
	</head>
	<body class="login_bg" onload="document.getElementById('btn_dl').focus();">
		<s:form action="login_slogin" theme="simple" id="loginForm" namespace="/xtgl">
			<!-- 防止浏览器自动填充密码 -->
			<input type="text" style="display: none;"/>
			<input type="password" style="display: none;"/>
			<!-- 防止浏览器自动填充密码 end -->
		
			<input type="hidden" id="yhmmdj" name="yhmmdj" />
			<div class="login_main">
				<div class="login_logo">
					<h2>
						<img src="<%=stylePath%>logo/logo_school.png" />
					</h2>
					<h3>
						<img src="<%=stylePath%>logo/<%=appLogo%>" />
					</h3>
				</div>
				<div class="login_left">
					<img class="login_pic" src="<%=stylePath%>logo/login_pic.png" />
				</div>
				<div class="login_right">
					<div class="login">
						<div class="user">
							<label for="">
								用户名：
							</label>
							<input name="yhm" id="yhm" type="text" class="text_nor"  autocomplete="off"
								maxlength="20" style="width: 120px" 
								onchange="isNotChar(this);" />
						</div>
						<div class="passw">
							<label for="">
								密&nbsp;&nbsp;&nbsp;码：
							</label>
							<input name="mm" id="mm" type="password" onkeyup="passStrength(this.value);" autocomplete="off"
								class="text_nor" maxlength="20" style="width: 120px" />
						</div>
						<div class="yzm">
							<label>
								验证码：
							</label>
							<input name="yzm" type="text" id="yzm" class="text_nor"
								style="width: 45px;" maxlength="4" value=""
								onkeypress="if(event.keyCode==13){dl();}" />
							<img border="0" align="absmiddle" id="yzmPic"
								style="cursor: pointer;" onclick="javascript:refreshCode();"
								name="yzmPic" width="75px" height="19px" />
						</div>

						<div class="btn">
							<button class="btn_dl" id="btn_dl" onclick="dl();return false;" type="button"></button>
							<button class="btn_cz" type="reset"></button>
						</div>
						<div class="prompt_dl" id="login_errorMsg" style="display: none;">
							
						</div>
						<div class="login_notice">
							<h3>
								热心提示：
							</h3>
							<p>
								为了您的帐号安全，请及时修改您的初始密码。
							</p>
						</div>
					</div>
				</div>
				<div class="login_copyright">
					<span><%=MessageUtil.getText("niutal_copy") %> <a href="<%=MessageUtil.getText("niutal_link") %> "
						target="_blank"><%=MessageUtil.getText("niutal_bottom") %></a> <span>版权所有</span>&nbsp;&nbsp;<%=MessageUtil.getText("niutal_phone") %></span>
					<a class="ver" href="#"> <%=MessageUtil.getText("niutal_version") %></a>
				</div>
			</div>
			<div class="errors" style="display: none">

			</div>
		</s:form>
		<%-- <shiro:authenticated>
			<script  type="text/javascript">
				document.location.href="${pageContext.request.contextPath}/xtgl/index_initMenu.html";	
			</script>
		</shiro:authenticated> --%>
		<script type="text/javascript">
			if('${message}' != null && '${message}' != ''){
				showErrMsg("${message}");
			}
		</script>
	</body>
</html>
