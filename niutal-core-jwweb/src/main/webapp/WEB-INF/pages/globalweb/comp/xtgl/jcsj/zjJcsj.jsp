<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<s:form action="/xtgl/jcsj_zjBcJcsj.html" id="ajaxForm" method="post"
		theme="simple" cssClass="form-horizontal sl_all_form">
		<!-- 防止浏览器自动填充密码:增加 class="ignore"忽略校验-->
		<input type="text" class="ignore" style="display: none;" /> 
		<input type="password" class="ignore" style="display: none;" />
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><span style="color: red;">*</span>类型</label>
					<div class="col-sm-7">
						<s:select list="lxdmList" listKey="lxdm" listValue="lxmc"
							name="lxdm" id="lxdm" headerKey="" headerValue=""
							cssClass="form-control chosen-select" validate="{required:true}"></s:select>
						<SCRIPT type="text/javascript">
							jQuery('#lxdm').trigger("chosen");
						</SCRIPT>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><span
						style="color: red;">*</span>代码</label>
					<div class="col-sm-7">
						<s:textfield maxlength="20" name="dm" id="dm"
							cssClass="form-control"
							validate="{required:true,chrnum:true,jcsjUnique:true,stringMaxLength:20}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><span style="color: red;">*</span>名称</label>
					<div class="col-sm-7">
						<s:textfield maxlength="50" name="mc" id="mc" cssClass="form-control" validate="{required:true,stringMaxLength:200}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">英文名称</label>
					<div class="col-sm-7">
						<s:textfield  name="ywmc" id="ywmc" cssClass="form-control" validate="{stringMaxLength:200}"></s:textfield>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>
<script type="text/javascript"
	src="<%=systemPath%>/js/globalweb/comp/xtgl/jcsj/jcsj_zj.js?ver=<%=jsVersion%>"></script>
<script type="text/javascript">
	//扩展自己的方法
	jQuery(function($) {
		$("#ajaxForm").validateForm();
	})
</script>
</html>