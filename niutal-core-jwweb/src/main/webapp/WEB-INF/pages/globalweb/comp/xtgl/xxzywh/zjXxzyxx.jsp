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
	<s:form  cssClass="form-horizontal sl_all_form sl_all_bg" role="form" name="form" id="ajaxForm"  method="post" action="/xxzy/xxzy_zjBcZyxx.html" theme="simple" onsubmit="return false;">
		<s:hidden name="dlbs" value="zy"></s:hidden>
		<div class="row">
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>批次</label>
			      <div class="col-sm-8">
			          <s:select id="pcdm" name="pcdm" list="njList" listKey="njdm_id" listValue="njmc" headerKey=""  validate="{required:true}"
			          cssClass="form-control chosen-select" headerValue="---请选择---"/>
			          <SCRIPT type="text/javascript">
			    	  	jQuery('#pcdm').trigger("chosen");
			    	  </SCRIPT>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业代码</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="zydm" name="zydm" cssClass="form-control" validate="{required:true,stringMaxLength:10,chrnum:true,multiUnique:['V014',['#pcdm','#zydm'],'违反唯一性约束，已经存在相同批次专业']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>专业名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="zymc" name="zymc" cssClass="form-control" validate="{required:true,stringMaxLength:60,multiUnique:['V014',['#pcdm','#zydm'],'违反唯一性约束，已经存在相同批次专业']}"></s:textfield>
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
	function selectXy(){
		jQuery.showSelectDialog("3",{"multiselect":false},function(data){
			var	jg_id = data[0].key;
			var	jgmc  = data[0].text;
			jQuery("#jg_id").val(jg_id);
			jQuery("#jgmc").val(jgmc);
			jQuery("#jgmc").blur();
  		});
	
	}
	jQuery(function($){
		$('#ajaxForm').validateForm();
	});
</script>
</html>