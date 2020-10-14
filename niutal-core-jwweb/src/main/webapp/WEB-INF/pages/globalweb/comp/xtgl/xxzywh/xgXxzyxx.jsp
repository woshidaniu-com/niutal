<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
	<head>
		<style type="text/css">
		.has-error .ke-container {
		    border-color: #a94442;
		    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.075) inset;
		}
		</style>
	</head>
<body>
	<s:form  cssClass="form-horizontal sl_all_form sl_all_bg" role="form" name="form" id="ajaxForm"  method="post" action="/xxzy/xxzy_xgBcZyxx.html" theme="simple" onsubmit="return false;">
		<s:hidden id="xxzydmb_id" name="xxzydmb_id"></s:hidden>
		<div class="row">
			<div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>批次</label>
			      <div class="col-sm-8">
			      	 <s:iterator value="njList" var="item">
			      	 	<s:if test="#item.njdm_id == pcdm">
			      	 		<p class="form-control-static"><s:property value="#item.njmc"/></p>
			      	 	</s:if>
			      	 </s:iterator>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业代码</label>
			      <div class="col-sm-8">
		      		  <p class="form-control-static"><s:property value="zydm"/></p>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="zymc" name="zymc" cssClass="form-control" validate="{required:true,stringMaxLength:60,multiUnique:['V014',['#pcdm','#zydm'],['${pcdm}','${zydm}'],'违反唯一性约束，已经存在相同批次专业']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			 <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">学科代码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="xkdm" name="xkdm" cssClass="form-control" validate="{stringMaxLength:10}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">学科名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="xkmc" name="xkmc" cssClass="form-control" validate="{stringMaxLength:100}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">门类代码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="mldm" name="mldm" cssClass="form-control" validate="{stringMaxLength:10}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">门类名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="mlmc" name="mlmc" cssClass="form-control" validate="{stringMaxLength:100}"></s:textfield>
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