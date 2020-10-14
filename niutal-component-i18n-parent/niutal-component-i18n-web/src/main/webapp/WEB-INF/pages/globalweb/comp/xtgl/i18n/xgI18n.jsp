<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
</head>
<body>
	<s:form cssClass="form-horizontal sl_all_form sl_all_bg" role="form"
		name="form" id="ajaxForm" method="post"
		action="/i18n/i18n_xgBcI18n.html" theme="simple"
		onsubmit="return false;">
		<s:hidden name="res_oid" id="res_oid"></s:hidden>
		<div class="row">
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><span
						style="color: red;">*</span>资源主键</label>
					<div class="col-sm-8">
						<s:textfield id="res_key" name="res_key" cssClass="form-control" readonly="true"
							validate="{required:true,stringMaxLength:50,nowhitespace:true,unique:['V0200','${res_key}','违反唯一性约束，已存在相同的资源']}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label"><span
						style="color: red;">*</span>中文描述</label>
					<div class="col-sm-8">
						<s:textfield id="zh_cn" name="zh_cn" cssClass="form-control"
							validate="{required:true,stringMaxLength:280}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">英文描述</label>
					<div class="col-sm-8">
						<s:textfield id="en_us" name="en_us" cssClass="form-control"
							validate="{stringMaxLength:280}"></s:textfield>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</body>

<script type="text/javascript">	
	jQuery(function($){
		$('#ajaxForm').validateForm();
	});
</script>
</html>