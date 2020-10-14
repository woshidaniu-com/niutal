<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<form data-toggle="validation" class="form-horizontal sl_all_form" id="systemConfigForm" action="<%=systemPath %>/xtgl/xxxxsz_xgXxxxsz.html">
	<div class="row">
		  <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label"><font color="red">*</font>学校代码</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxdm" name="xxdm" value="<s:property value="model.xxdm"/>" 
                validate="{required:true,stringMaxLength:10,chrnum:true}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label"><font color="red">*</font> 学校名称</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxmc" name="xxmc" value="<s:property value="model.xxmc"/>" 
                 validate="{required:true,stringMaxLength:50}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">学校英文名称</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxywmc" name="xxywmc"  value="<s:property value="model.xxywmc"/>"
			 		validate="{stringMaxLength:180}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">学校地址</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxdz" name="xxdz" value="<s:property value="model.xxdz"/>"
			 		 validate="{stringMaxLength:180}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">邮政编码</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="yzbm" name="yzbm" value="<s:property value="model.yzbm"/>"
			 		validate="{stringMaxLength:6,zipCode:true}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">行政区划码</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xzqhm" name="xzqhm" value="<s:property value="model.xzqhm"/>"
			 		validate="{stringMaxLength:6,digits:true}"/>
		      </div>
		    </div>
		  </div>
             
             <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">建校年月</label>
		      <div class="col-sm-8">
                <input class="form-control Wdate" type="text" id="jxny" name="jxny" placeholder="点击选择年月" onfocus="WdatePicker({dateFmt:'yyyyMM'})"
			 		 value="<s:property value="model.jxny"/>"  readonly="true" /> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">校庆日</label>
		      <div class="col-sm-8">
                <input class="form-control Wdate" type="text" id="xqr" name="xqr"  placeholder="点击选择日期" onfocus="WdatePicker({dateFmt:'MMdd'})"
			 		 value="<s:property value="model.xqr"/>"  readonly="true"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">学校办学类型码</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxbxlxm" name="xxbxlxm"  value="<s:property value="model.xxbxlxm"/>"
			 		 validate="{stringMaxLength:3,chrnum:true}"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">学校举办者码</label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxjbzm" name="xxjbzm"  value="<s:property value="model.xxjbzm"/>"
			 		 validate="{stringMaxLength:3,chrnum:true}"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">主管部门码 </label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxzgbmm" name="xxzgbmm"  value="<s:property value="model.xxzgbmm"/>"
			 		  validate="{stringMaxLength:3,chrnum:true}"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">主管部门名称 </label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="xxzgbmmc" name="xxzgbmmc"  value="<s:property value="model.xxzgbmmc"/>"
			 		  validate="{stringMaxLength:60}"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">历史沿革 </label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="lsyg" name="lsyg"  value="<s:property value="model.lsyg"/>"
			 		 validate="{stringMaxLength:500}"/> 
		      </div>
		    </div>
		  </div>
             
			 	<div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">注册码 </label>
		      <div class="col-sm-8">
                <input class="form-control" type="text" id="zcm" name="zcm"  value="<s:property value="model.zcm"/>"
			 		 validate="{stringMaxLength:64,chrnum:true}"/> 
		      </div>
		    </div>
		  </div>
             	  
		 
		  
	  </div>
</form>
<!-- btn-start  -->
<div class="row sl_aff_btn">
	<div class="col-sm-12">
		<button class="btn btn-primary btn-sm"  id="bnt_save" type="button" > 保 存 </button>
	</div>
</div>
<!-- btn-end  -->
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript">
jQuery(function($) {
		
	$("#bnt_save").click(function(){
		
		submitForm("systemConfigForm",function(responseText,statusText){
			if(responseText.indexOf("成功") > -1){
				$.success(responseText,function() {
				});
			}else if(responseText.indexOf("失败") > -1){
				$.error(responseText,function() {
				});
			} else{
				$.alert(responseText,function() {
				});
			}
		});
		
	});
	
});
</script>
</html>

