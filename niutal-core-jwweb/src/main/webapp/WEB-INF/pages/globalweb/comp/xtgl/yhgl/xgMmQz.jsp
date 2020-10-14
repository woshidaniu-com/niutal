<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagefunc_v5.ini"%>
</head>
<body>
<html>
	<!-- top -->
	<div class="navbar navbar-default navbar-static-top top1" style="margin-bottom: 15px;">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand logo_2" href="#"><img src="<%=stylePath%>/assets/images/logo/logo_jw_w.png" style="margin-top:-3px" />
					<span id="xtmc"><%=MessageUtil.getText("system.title")%></span>
				</a>
			</div>
		</div>
	</div>
	<div class="container sl_all_bg">
	 	<s:form id="ajaxForm" method="post" action="/xtgl/mmgl_xgMm.html" theme="simple" cssClass="form-horizontal sl_all_form">
	 		<!-- 防止浏览器自动填充密码:增加 class="ignore"忽略校验-->
			<input type="text" class="ignore" style="display: none;" /> 
			<input type="password" class="ignore" style="display: none;" />
			<input type="hidden" id="yhm" name="yhm" value="${user.yhm}"/>
			<input type="hidden" name="doType" value="save" />
			<input type="hidden" id="sessionUserKey" value="<s:property value="#session.user.yhm"/>" />
			<div class="row">
				 <div class="col-md-6 col-sm-6 col-md-offset-2 col-sm-offset-2">
			        <div class="form-group">
			          <label for="" class="col-sm-10 control-label">
			          	<h4>系统检测到您的密码过于简单，以免发生账户被盗引发损失。建议您立即修改用户密码！</h4>
			          </label>
			        </div>
			      </div>
			      
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label">姓名</label>
			          <div class="col-sm-5">
			            	  <p class="form-control-static">${user.xm}</p>
			          </div>
			        </div>
			      </div>
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>原密码</label>
			          <div class="col-sm-5">
			            	 <input type="password"  name="ymm" id="ymm" class="form-control"  placeholder="请输入原密码."
			            	  validate="{required:true,checkPassword:true}" >
			          </div>
			        </div>
			      </div>
			
				 <div class="col-md-12 col-sm-12">
			        <div class="form-group">
			          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>新密码</label>
			          <div class="col-sm-5">
			            <input type="password"  name="mm" id="mm" class="form-control"  placeholder="请输入新密码."
			            validate="{required:true,strength:true}">
			          </div>
			        </div>
			      </div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group">
						<label for="" class="col-sm-2 control-label">&nbsp;</label>
						<div class="col-sm-5" id="strengthID"></div>
					</div>
				</div>
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-2 control-label"><span style="color:red;">*</span>重复新密码</label>
		          <div class="col-sm-5">
		             <input type="password"  name="nmm" id="nmm" class="form-control"  placeholder="请再次输入新密码."
		             validate="{required:true,equalTo:'#mm'}">
		          </div>
		        </div>
		      </div>
		</div>
    </s:form>
  	<div class="row sl_aff_btn">
	   <div class="col-sm-12" style="text-align: center;">
	     <button type="button" class="btn btn-primary btn-sm" id="btn_xg" data-loading-text="数据提交中...">&nbsp;确&nbsp;定&nbsp;</button>
	   </div>
	</div>
</div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/strength.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/yhgl/xgMmQz.js?ver=<%=jsVersion%>"></script>
</html>