<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<s:form id="ajaxForm" method="post" action="/xtgl/mmgl_xgMm.html" theme="simple" cssClass="form-horizontal sl_all_form">
		<!-- 防止浏览器自动填充密码:增加 class="ignore"忽略校验-->
		<input type="text" class="ignore" style="display: none;" /> 
		<input type="password" class="ignore" style="display: none;" />
		<input type="hidden" name="doType" value="save"/>  
		<div class="row">
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>原密码</label>
		          <div class="col-sm-7">
		            	 <input type="password"  name="ymm" id="ymm" class="form-control"  placeholder="请输入原密码"
		            	  validate="{required:true,checkPassword:true}" >
		          </div>
		        </div>
		      </div>
		
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>新密码</label>
		          <div class="col-sm-7">
		            <input type="password"  name="mm" id="mm" class="form-control"  placeholder="请输入新密码"
		            validate="{required:true,strength:true}">
		          </div>
		        </div>
		      </div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-sm-3 control-label">&nbsp;</label>
					<div class="col-sm-7" id="strengthID"></div>
				</div>
			</div>
			 <div class="col-md-12 col-sm-12">
		        <div class="form-group">
		          <label for="" class="col-sm-3 control-label"><span style="color:red;">*</span>重复新密码</label>
		          <div class="col-sm-7">
		             <input type="password"  name="nmm" id="nmm" class="form-control"  placeholder="请再次输入新密码"
		             validate="{required:true,equalTo:'#mm'}">
		          </div>
		        </div>
		      </div>
		</div>
   </s:form>
 </body>
 <%@ include file="/WEB-INF/pages/globalweb/head/strength.ini"%>
<script type="text/javascript">
//扩展自己的方法
jQuery(function($) {
	// 验证用户原密码
	$.validator.addMethod("checkPassword", function(value, element, param) {
		var isUnique = false;
		$.ajax({
			url:_path+'/xtgl/mmgl_cxCheckYhMm.html',
			async: false,
			type:"post",
			dataType:"json",
			data:{kl:value},					
			success:function(data){
				isUnique = data;
			}
		});
		return isUnique || this.optional(element) ;
	},"原密码输入错误");
	
	
	  
	 
	
	$("#ajaxForm").validateForm({
		afterSuccess:function(responseText,statusText){
		 	$.alert(responseText,function(){
			 	$.closeModal("addModal");
			 	if(responseText.indexOf("成功") > -1){
				 	document.location.href=_path+"/xtgl/login_logout.html?login_type=" + $("#login_type").val();
			 	} 
		 	});
	   	}
	});
	$("#mm").strength({"target":"#strengthID"});
});
</script>
</html>
