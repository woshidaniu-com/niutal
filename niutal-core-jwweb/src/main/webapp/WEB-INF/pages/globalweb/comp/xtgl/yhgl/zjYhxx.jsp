<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<form action="yhgl_zjBcYhxx.html" data-toggle="validation"  data-widget='{"onkeyup": false}' role="form" class="form-horizontal sl_all_form"  id="ajaxForm" method="post" theme="simple">
	<!-- 防止浏览器自动填充密码:增加 class="ignore"忽略校验-->
	<input type="text" class="ignore" style="display: none;" /> 
	<input type="password" class="ignore" style="display: none;" />
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>用户名
				</label>
				<div class="col-sm-8">
					<s:textfield maxlength="20" name="yhm" id="yhm"  value="" cssClass="form-control input-sm span2"  data-rules='{"required":true,"chrnum":true,"unique":["V0102"]}'></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>姓名
				</label>
				<div class="col-sm-8">
					<s:textfield maxlength="20" name="xm" id="xm" cssClass="form-control  input-sm span3" data-rules='{"required":true,"stringMaxLength":20}'> </s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label"  for="dzyx">
					Email
				</label>
				<div class="col-sm-8">
					<s:textfield name="dzyx" id="dzyx"  data-rules='{"email2":true}'  cssClass="form-control input-sm span4" data-toggle="autoEmail"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group form-group-sm">
				<label for="" class="col-sm-2 control-label" for="sjhm" >
					联系电话	
				</label>
				<div class="col-sm-8">
					<input type="text" name="sjhm" id="sjhm" data-rules='{"mobile":true}'  class="form-control input-sm span5" value=""/>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>登录密码
				</label>
				<div class="col-sm-8" >
					<s:password maxlength="16" name="kl" id="kl" cssClass="form-control input-sm" data-rules='{"required":true,"strength":true}'  value=""></s:password>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">&nbsp;</label>
				<div class="col-sm-8" id="strengthID"></div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>重复密码
				</label>
				<div class="col-sm-8">
					<s:password maxlength="16" name="nmm" id="nmm"  value="" cssClass="form-control input-sm" data-rules='{"required":true,"equalTo":"#kl"}'></s:password>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label" for="jg_id">
					<span class="red">*</span>所属机构
				</label>
				<div class="col-sm-8">
					<input type="hidden" name="jgmc" id="jg_mc">
					<s:select list="jgxxList" listKey="jg_id" listValue="jgmc" id="jg_id"  name="jg_id"  headerKey=""  headerValue="---请选择---" cssClass="form-control  input-sm chosen-select" 
					 data-rules='{"required":true}'></s:select>
					 <SCRIPT type="text/javascript">
			    		jQuery('#jg_id').trigger("chosen");
			    	</SCRIPT>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">
					<span class="red">*</span>所属角色
				</label>
				<div class="col-sm-8">
					<div class="checkbox">
					<s:iterator value="jsxxList" id="s" status="substa">
						<div class="col-md-4 col-sm-4 ">
							<label class="checkbox-inline">
						    	<input type="checkbox" validate="{required:true}"  class="cbvclass" style="cursor: pointer;" id="cbvjsxx" name="cbvjsxx" value="${s.jsdm}" /> ${s.jsmc}
					    	</label>
					  	</div>
					</s:iterator>
					</div>
				</div>
			</div>
			
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-sm-2 control-label">
					是否启用
				</label>
				<div class="col-sm-8">
					<s:iterator value="isNot" status="substa">
						<label class="radio-inline">
							<s:if test="model.sfqy == key">
								<input type="radio" style="cursor: pointer;" checked="checked" id="sfqy" name="sfqy" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:if>
							<s:else>
								<input type="radio" style="cursor: pointer;" id="sfqy" name="sfqy" value="<s:property value="key"/>" /><s:property value="value"/>
							</s:else>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
<script type="text/javascript">
//扩展自己的方法
jQuery(function($) {
	
	$.extend($.validator.messages, {
		equalTo: "两次输入的内容不同"
	});
	
	$("#kl").strength({"target":"#strengthID"});

	$("#jg_id").change(function(){
		$("#jg_mc").val($(this).getSelected().text());
	});

	//$("#drop6").dropdown("show");
});
</script>
</html>