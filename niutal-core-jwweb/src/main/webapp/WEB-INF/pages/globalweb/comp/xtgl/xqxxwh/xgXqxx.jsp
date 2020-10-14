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
	<s:form  cssClass="form-horizontal sl_all_form sl_all_bg" role="form" name="form" id="ajaxForm"  method="post" action="/xtgl/xqxx_xgBcXqxx.html" theme="simple" onsubmit="return false;">
		<s:hidden name="xqh_id" id="xqh_id"></s:hidden>
		<div class="row">
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>校区号</label>
			      <div class="col-sm-8">
			      	  <s:textfield id="xqh" name="xqh" cssClass="form-control" validate="{required:true,stringMaxLength:10,chrnum:true,unique:['V0104','${xqh}','违反唯一性约束，已存在相同的校区号']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-6 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>校区名称</label>
			      <div class="col-sm-8">
			      		<s:textfield id="xqmc" name="xqmc" cssClass="form-control" validate="{required:true,stringMaxLength:100,unique:['V0104','${xqmc}','违反唯一性约束，已存在相同的校区名称']}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-12 col-sm-12">
			    <div class="form-group">
			      <label for="" class="col-sm-2 control-label">校区地址</label>
			      <div class="col-sm-10">
			      		<!-- ,unique:['V0103','${xqdz}'] -->
			      		<s:textfield id="xqdz" name="xqdz" cssClass="form-control" validate="{required:false,stringMaxLength:50}"></s:textfield>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-12 col-sm-12">
			    <div class="form-group">
			      <label for="" class="col-sm-2 control-label">可跨校区合班</label>
			      <div class="col-sm-10">
			      	<div class="input-group">
			      		<s:hidden id="kkhbxqh_id" name="kkhbxqh_id"></s:hidden>
						<s:textfield readonly="true" id="kkhbxqhmc" name="kkhbxqhmc" cssClass="form-control"></s:textfield>
						<span class="input-group-btn"><button class="btn btn-default btn-xqSelect" data-toggle="modal" data-type="1" type="button">&gt;</button>
						<button class="btn btn-default" data-toggle="clearfix" data-target="#kkhbxqh_id,#kkhbxqhmc" type="button">清空</button></span></div>
			      	</div>
			    </div>
			  </div>
			  <div class="col-md-12 col-sm-12">
			    <div class="form-group">
			      <label for="" class="col-sm-2 control-label">可跨校区选课</label>
			      <div class="col-sm-10">
			      	<div class="input-group">
			      	    <s:hidden id="kkxkxq_id" name="kkxkxq_id"></s:hidden>
						<s:textfield readonly="true" id="kkxkxqmc" name="kkxkxqmc" cssClass="form-control"></s:textfield>
						<span class="input-group-btn"><button class="btn btn-default btn-xqSelect" data-toggle="modal" data-type="2"  type="button">&gt;</button>
						<button class="btn btn-default" data-toggle="clearfix" data-target="#kkxkxq_id,#kkxkxqmc" type="button">清空</button></span></div>
			      	</div>
			      </div>
			    </div>
			  </div>
	</s:form>
</body>

<script type="text/javascript">		
	
	jQuery(function($){
		$('#ajaxForm').validateForm();
		/**
		 * 选择校区
		 */
		$(".btn-xqSelect").click(function(){
			var type = $(this).data("type");
			jQuery.showSelectDialog("20",{
			 	multiselect	:	true,
			 	title		:	"选择校区",
			 	//checked		:	[{"key":$("#xqh_id").val(),"text":$("#xqmc").val()}],
			 	xqh_id		: 	$("#xqh_id").val()
		 	},function(data){
		 	     var xqh_ids = [];
		 	    var xqmcs = [];
		 	    $.each(data,function(n,value){
	 	   			 xqh_ids.push(value.xqh_id);
	 	   			 xqmcs.push(value.xqmc);
	 	   		});
	 	   		if(type == 1){
	 	   			$("#kkhbxqh_id").val(xqh_ids.join(","));
					$("#kkhbxqhmc").val(xqmcs.join(","));
					$("#kkhbxqhmc").blur();
	 	   		}else{
	 	   			$("#kkxkxq_id").val(xqh_ids.join(","));
					$("#kkxkxqmc").val(xqmcs.join(","));
					$("#kkxkxqmc").blur();
	 	   		}
		 	});
		});
	});
</script>
</html>